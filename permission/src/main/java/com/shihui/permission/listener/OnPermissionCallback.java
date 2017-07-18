package com.shihui.permission.listener;

import java.io.Serializable;

/**
 * Description:  权限申请回调
 */
public interface OnPermissionCallback extends Serializable {
  /**
   * 申请权限弹窗-拒绝-提示用户弹窗授权弹窗->取消按钮回调
   */
  void onCancel();

  /**
   * 多个权限申请全部申请完成
   */
  void onAllGranted();

  /**
   * 拒绝具体权限申请
   * @param permission 权限名称
   * @param position 权限在list中的位置
   */
  void onDenied(String permission, int position);

  /**
   * 完成具体权限申请
   * @param permission 权限名称
   * @param position 权限在list中的位置
   */
  void onGranted(String permission, int position);
}
