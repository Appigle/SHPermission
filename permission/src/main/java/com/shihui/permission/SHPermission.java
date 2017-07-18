package com.shihui.permission;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import com.shihui.permission.listener.OnPermissionCallback;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Thanks:https://github.com/yewei02538/HiPermission
 * Description:  权限申请类
 */
public class SHPermission {
  private final Context mContext;
  private String mTitle;
  private String mMsg;
  private int mStyleResId = -1;
  private OnPermissionCallback mCallback;
  private List<PermissionItem> mCheckPermissions;
  private int mPermissionType;

  private String[] mNormalPermissionNames;
  private String[] mNormalPermissions = {
      WRITE_EXTERNAL_STORAGE, ACCESS_FINE_LOCATION, CAMERA
  };
  private int[] mNormalPermissionIconRes = {
      R.drawable.permission_ic_storage, R.drawable.permission_ic_location,
      R.drawable.permission_ic_camera
  };
  private int mFilterColor = 0;
  private int mAnimStyleId = -1;

  public static SHPermission create(Context context) {
    return new SHPermission(context);
  }

  private SHPermission(Context context) {
    mContext = context;
    mNormalPermissionNames = mContext.getResources().getStringArray(R.array.permissionNames);
  }

  //设置dialog标题 如果需要可以public设置 需要开启后续的setText()方法
  private SHPermission setTitle(String title) {
    mTitle = title;
    return this;
  }
  //设置dialog内容 如果需要可以public设置
  private SHPermission setContent(String msg) {
    mMsg = msg;
    return this;
  }

  public SHPermission setPermissions(List<PermissionItem> permissionItems) {
    mCheckPermissions = permissionItems;
    return this;
  }
  //设置dialog 权限Icon背景色 如果需要可以public设置
  private SHPermission setFilterColor(int color) {
    mFilterColor = color;
    return this;
  }
  //设置dialog 显示动画 如果需要可以public设置
  private SHPermission setAnimStyle(int styleId) {
    mAnimStyleId = styleId;
    return this;
  }
  //设置dialog 样式 如果需要可以public设置
  private SHPermission setStyle(int styleResIdsId) {
    mStyleResId = styleResIdsId;
    return this;
  }

  private List<PermissionItem> getNormalPermissions() {
    List<PermissionItem> permissionItems = new ArrayList<>();
    for (int i = 0; i < mNormalPermissionNames.length; i++) {
      permissionItems.add(new PermissionItem(mNormalPermissions[i], mNormalPermissionNames[i],
          mNormalPermissionIconRes[i]));
    }
    return permissionItems;
  }

  public static boolean checkPermission(Context context, String permission) {
    int checkPermission = ContextCompat.checkSelfPermission(context, permission);
    if (checkPermission == PackageManager.PERMISSION_GRANTED) {
      return true;
    }
    return false;
  }

  /**
   * 检查多个权限
   */
  public void checkMutiPermission(OnPermissionCallback callback) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
      if (callback != null) callback.onAllGranted();
      return;
    }

    if (mCheckPermissions == null) {
      mCheckPermissions = new ArrayList<>();
      mCheckPermissions.addAll(getNormalPermissions());
    }

    //检查权限，过滤已允许的权限
    Iterator<PermissionItem> iterator = mCheckPermissions.listIterator();
    while (iterator.hasNext()) {
      if (checkPermission(mContext, iterator.next().Permission)) iterator.remove();
    }
    mCallback = callback;
    if (mCheckPermissions.size() > 0) {
      startActivity();
    } else {
      if (callback != null) callback.onAllGranted();
    }
  }

  /**
   * 检查单个权限,没有提示弹窗效果
   */
  public void checkSinglePermission(String permission, OnPermissionCallback callback) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M || checkPermission(mContext, permission)) {
      if (callback != null) callback.onGranted(permission, 0);
      return;
    }
    mCallback = callback;
    mPermissionType = PermissionActivity.PERMISSION_TYPE_SINGLE;
    mCheckPermissions = new ArrayList<>();
    mCheckPermissions.add(new PermissionItem(permission));
    startActivity();
  }

  private void startActivity() {
    PermissionActivity.setCallBack(mCallback);
    Intent intent = new Intent(mContext, PermissionActivity.class);
    intent.putExtra(Constants.DATA_TITLE, mTitle);
    intent.putExtra(Constants.DATA_PERMISSION_TYPE, mPermissionType);
    intent.putExtra(Constants.DATA_MSG, mMsg);
    intent.putExtra(Constants.DATA_FILTER_COLOR, mFilterColor);
    intent.putExtra(Constants.DATA_STYLE_ID, mStyleResId);
    intent.putExtra(Constants.DATA_ANIM_STYLE, mAnimStyleId);
    intent.putExtra(Constants.DATA_PERMISSIONS, (Serializable) mCheckPermissions);
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    mContext.startActivity(intent);
  }
}
