package com.testin.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.testin.common.base.BaseResult;
import com.testin.common.base.EUDataGridResult;
import com.testin.sys.convert.SysUserOrgConvert;
import com.testin.sys.dao.SysUserOrgMapper;
import com.testin.sys.domain.SysUserOrg;
import com.testin.sys.domain.SysUserOrgExample;
import com.testin.sys.service.SysUserOrgService;
import com.testin.sys.web.form.SysUserOrgForm;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SysUserOrgServiceImpl implements SysUserOrgService {

    @Autowired
    SysUserOrgMapper sysUserOrgMapper;

    public BaseResult delete(Long id) {
        sysUserOrgMapper.deleteByPrimaryKey(id);
        return BaseResult.ok("删除成功");
    }

    @Override
    public BaseResult deleteByIds(String ids) {
        SysUserOrgExample example = new SysUserOrgExample();
        example.createCriteria().andIdIn(idsToList(ids));
        sysUserOrgMapper.deleteByExample(example);
        return BaseResult.ok("删除成功");
    }

    @Override
    public BaseResult get(Long id) {
        SysUserOrg sysUserOrg = sysUserOrgMapper.selectByPrimaryKey(id);
        return BaseResult.ok("查询成功", SysUserOrgConvert.entityToForm(sysUserOrg));
    }

    @Override
    public BaseResult list(SysUserOrgForm form) {
        SysUserOrgExample example = new SysUserOrgExample();
        List<SysUserOrg> sysUserOrgList = sysUserOrgMapper.selectByExample(example);
        return BaseResult.ok("查询成功", SysUserOrgConvert.entityListToFormList(sysUserOrgList));
    }

    @Override
    public EUDataGridResult listPage(SysUserOrgForm form) {
        SysUserOrgExample example = new SysUserOrgExample();
        //设置分页
        example.setStart((form.getPage() - 1) * form.getRows());
        example.setSize(form.getRows());

        //查询条件
        if (form != null) {
            SysUserOrgExample.Criteria criteria = example.createCriteria();

            //其它条件

        }

        //查询总记录
        long count = sysUserOrgMapper.countByExample(example);
        //查询分页列表
        List<SysUserOrg> sysUserOrgList = sysUserOrgMapper.selectPageByExample(example);

        //返回结果
        EUDataGridResult result = new EUDataGridResult(count, SysUserOrgConvert.entityListToFormList(sysUserOrgList));
        return result;
    }

    @Override
    public BaseResult saveOrUpdate(SysUserOrgForm form) {
        SysUserOrg entity = SysUserOrgConvert.formToEntity(form);
        if (entity.getId() != null) {
            sysUserOrgMapper.updateByPrimaryKey(entity);
        } else {
            sysUserOrgMapper.insert(entity);
        }
        return BaseResult.ok("保存成功");
    }

    @Override
    public BaseResult update(SysUserOrgForm form) {
        SysUserOrgExample example = new SysUserOrgExample();
        sysUserOrgMapper.updateByExample(SysUserOrgConvert.formToEntity(form), example);
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