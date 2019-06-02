package com.testin.hr.convert;

import java.util.ArrayList;
import java.util.List;

import com.testin.common.base.BaseConvert;
import com.testin.hr.domain.Dept;
import com.testin.hr.web.form.DeptForm;
import com.testin.sys.domain.SysOrg;
import com.testin.sys.web.form.SysOrgForm;

/**
 * @Description: SysOrg转换类！ <br/>
 * @Autor: Created by JinKun on 2016-12-30.
 */
public class DeptConvert extends BaseConvert {

    public static Dept formToEntity(DeptForm form) {
        if (form != null) {
            Dept entity = new Dept();
            entity.setId(form.getId());
            entity.setName(form.getName());
            entity.setAddress(form.getAddress());
            entity.setCode(form.getCode());
            entity.setIcon(form.getIcon());
            entity.setPid(form.getPid());
            entity.setIsLeaf(form.getIsLeaf());
            entity.setSeq(form.getSeq());
            entity.setDelFlag(form.getDelFlag());
            entity.setUpdateTime(stringToDate(form.getUpdateTime()));
            entity.setCreateTime(stringToDate(form.getCreateTime()));
            return entity;
        }
        return null;
    }

    public static List<Dept> formListToEntityList(List<DeptForm> formList) {
        List<Dept> entityList = new ArrayList<Dept>();
        if (formList != null && formList.size() > 0) {
            for (DeptForm form : formList) {
                entityList.add(formToEntity(form));
            }
        }
        return entityList;
    }

    public static DeptForm entityToForm(Dept entity) {
        if (entity != null) {
            DeptForm form = new DeptForm();
            form.setId(entity.getId());
            form.setName(entity.getName());
            form.setAddress(entity.getAddress());
            form.setCode(entity.getCode());
            form.setIcon(entity.getIcon());
            form.setPid(entity.getPid());
            form.setIsLeaf(entity.getIsLeaf());
            form.setSeq(entity.getSeq());
            form.setDelFlag(entity.getDelFlag());
            form.setUpdateTime(dateToString(entity.getUpdateTime()));
            form.setCreateTime(dateToString(entity.getCreateTime()));
            form.setIconCls(entity.getIcon());
            return form;
        }
        return null;
    }

    public static List<DeptForm> entityListToFormList(List<Dept> entityList) {
        List<DeptForm> formList = new ArrayList<DeptForm>();
        if (entityList != null && entityList.size() > 0) {
            for (Dept entity : entityList) {
                formList.add(entityToForm(entity));
            }
            return formList;
        }
        return formList;
    }
}
