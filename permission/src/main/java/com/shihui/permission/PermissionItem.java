package com.shihui.permission;

import java.io.Serializable;

/**
 * Description:  权限封装实体类
 */
public class PermissionItem implements Serializable {
    public String PermissionName;//权限Icon底部显示名称
    public String Permission; //权限名称 eg.Manifest.permission.READ_PHONE_STATE
    public int PermissionIconRes;//权限对应的Icon资源Id

  /**
   *
   * @param permission 权限名称 eg.Manifest.permission.READ_PHONE_STATE
   * @param permissionName 权限Icon底部显示名称
   * @param permissionIconRes 权限对应的Icon资源Id
   */
    public PermissionItem(String permission, String permissionName, int permissionIconRes) {
        Permission = permission;
        PermissionName = permissionName;
        PermissionIconRes = permissionIconRes;
    }

    public PermissionItem(String permission) {
        Permission = permission;
    }
}
