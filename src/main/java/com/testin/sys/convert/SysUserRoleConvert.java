package com.testin.sys.convert;

import java.util.ArrayList;
import java.util.List;

import com.testin.common.base.BaseConvert;
import com.testin.sys.domain.SysUserRole;
import com.testin.sys.web.form.SysUserRoleForm;

/**
 * @Description: SysUserRole转换类！ <br/>
 * @Autor: Created by JinKun on 2016-12-30.
 */
public class SysUserRoleConvert extends BaseConvert {

    public static SysUserRole formToEntity(SysUserRoleForm form) {
        if (form != null) {
            SysUserRole entity = new SysUserRole();
            entity.setId(form.getId());
            entity.setUserId(form.getUserId());
            entity.setRoleId(form.getRoleId());
            return entity;
        }
        return null;
    }

    public static List<SysUserRole> formListToEntityList(List<SysUserRoleForm> formList) {
        List<SysUserRole> entityList = new ArrayList<SysUserRole>();
        if (formList != null && formList.size() > 0) {
            for (SysUserRoleForm form : formList) {
                entityList.add(formToEntity(form));
            }
        }
        return entityList;
    }

    public static SysUserRoleForm entityToForm(SysUserRole entity) {
        if (entity != null) {
            SysUserRoleForm form = new SysUserRoleForm();
            form.setId(entity.getId());
            form.setUserId(entity.getUserId());
            form.setRoleId(entity.getRoleId());
            return form;
        }
        return null;
    }

    public static List<SysUserRoleForm> entityListToFormList(List<SysUserRole> entityList) {
        List<SysUserRoleForm> formList = new ArrayList<SysUserRoleForm>();
        if (entityList != null && entityList.size() > 0) {
            for (SysUserRole entity : entityList) {
                formList.add(entityToForm(entity));
            }
            return formList;
        }
        return formList;
    }
}
