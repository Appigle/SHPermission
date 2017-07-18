package com.shihui.permission.listener;

/**
 * Description: 单个权限申请回调
 */
public abstract class OnCheckPermissionCallback implements OnPermissionCallback {

  @Override public void onGranted(String permission, int position) {

  }

  @Override public void onCancel() {

  }

  @Override public void onAllGranted() {

  }

  @Override public void onDenied(String permission, int position) {

  }
}
