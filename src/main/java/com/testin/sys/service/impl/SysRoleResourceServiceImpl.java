package com.testin.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.testin.common.base.BaseResult;
import com.testin.common.base.EUDataGridResult;
import com.testin.sys.convert.SysRoleResourceConvert;
import com.testin.sys.dao.SysRoleResourceMapper;
import com.testin.sys.domain.SysRoleResource;
import com.testin.sys.domain.SysRoleResourceExample;
import com.testin.sys.service.SysRoleResourceService;
import com.testin.sys.web.form.SysRoleResourceForm;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SysRoleResourceServiceImpl implements SysRoleResourceService {

    @Autowired
    SysRoleResourceMapper sysRoleResourceMapper;

    public BaseResult delete(Long id) {
        sysRoleResourceMapper.deleteByPrimaryKey(id);
        return BaseResult.ok("删除成功");
    }

    @Override
    public BaseResult deleteByIds(String ids) {
        SysRoleResourceExample example = new SysRoleResourceExample();
        example.createCriteria().andIdIn(idsToList(ids));
        sysRoleResourceMapper.deleteByExample(example);
        return BaseResult.ok("删除成功");
    }

    @Override
    public BaseResult get(Long id) {
        SysRoleResource sysRoleResource = sysRoleResourceMapper.selectByPrimaryKey(id);
        return BaseResult.ok("查询成功", SysRoleResourceConvert.entityToForm(sysRoleResource));
    }

    @Override
    public BaseResult list(SysRoleResourceForm form) {
        SysRoleResourceExample example = new SysRoleResourceExample();
        List<SysRoleResource> sysRoleResourceList = sysRoleResourceMapper.selectByExample(example);
        return BaseResult.ok("查询成功", SysRoleResourceConvert.entityListToFormList(sysRoleResourceList));
    }

    @Override
    public EUDataGridResult listPage(SysRoleResourceForm form) {
        SysRoleResourceExample example = new SysRoleResourceExample();
        //设置分页
        example.setStart((form.getPage() - 1) * form.getRows());
        example.setSize(form.getRows());

        //查询条件
        if (form != null) {
            SysRoleResourceExample.Criteria criteria = example.createCriteria();

            //其它条件

        }

        //查询总记录
        long count = sysRoleResourceMapper.countByExample(example);
        //查询分页列表
        List<SysRoleResource> sysRoleResourceList = sysRoleResourceMapper.selectPageByExample(example);

        //返回结果
        EUDataGridResult result = new EUDataGridResult(count, SysRoleResourceConvert.entityListToFormList(sysRoleResourceList));
        return result;
    }

    @Override
    public List<Long> getResourcesIdsByRoleId(Long id) {
        SysRoleResourceExample example = new SysRoleResourceExample();
        example.createCriteria().andRoleIdEqualTo(id);
        List<SysRoleResource> sysRoleResources = sysRoleResourceMapper.selectByExample(example);
        return resourcesIdList(sysRoleResources);
    }

    private List<Long> resourcesIdList(List<SysRoleResource> sysRoleResources) {
        List<Long> idList = new ArrayList<>();
        if (sysRoleResources != null && sysRoleResources.size() > 0) {
            for (SysRoleResource s : sysRoleResources) {
                idList.add(s.getResourceId());
            }
        }
        return idList;
    }

    @Override
    public void deleteByRoleId(Long id) {
        SysRoleResourceExample roleResourceExample = new SysRoleResourceExample();
        roleResourceExample.createCriteria().andRoleIdEqualTo(id);
        sysRoleResourceMapper.deleteByExample(roleResourceExample);
    }

    @Override
    public void deleteByRoleIds(List<Long> ids) {
        //删除资源中间表
        SysRoleResourceExample roleResourceExample = new SysRoleResourceExample();
        roleResourceExample.createCriteria().andRoleIdIn(ids);
        sysRoleResourceMapper.deleteByExample(roleResourceExample);
    }

    @Override
    public void deleteByResourceIds(List<Long> ids) {
        //删除中间关联关系
        SysRoleResourceExample roleResourceExample = new SysRoleResourceExample();
        roleResourceExample.createCriteria().andResourceIdIn(ids);
        sysRoleResourceMapper.deleteByExample(roleResourceExample);
    }

    @Override
    public BaseResult saveOrUpdate(SysRoleResourceForm form) {
        SysRoleResource entity = SysRoleResourceConvert.formToEntity(form);
        if (entity.getId() != null) {
            sysRoleResourceMapper.updateByPrimaryKey(entity);
        } else {
            sysRoleResourceMapper.insert(entity);
        }
        return BaseResult.ok("保存成功");
    }

    @Override
    public BaseResult update(SysRoleResourceForm form) {
        SysRoleResourceExample example = new SysRoleResourceExample();
        sysRoleResourceMapper.updateByExample(SysRoleResourceConvert.formToEntity(form), example);
        return BaseResult.ok("更新成功");
    }

    private List<Long> idsToList(String ids) {
        String[] id = ids.split(",");
        List<Long> idList = new ArrayList<>();
            for (int i = 0; i < id.length; i++) {
            idList.add(Long.parseLong(id[i]));
        }
        return idList;
    }
}