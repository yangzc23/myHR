package com.testin.hr.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.testin.common.base.BaseResult;
import com.testin.common.base.EUTreeGridResult;
import com.testin.common.base.Tree;
import com.testin.hr.convert.DeptConvert;
import com.testin.hr.dao.DeptMapper;
import com.testin.hr.domain.Dept;
import com.testin.hr.domain.DeptExample;
import com.testin.hr.service.DeptService;
import com.testin.hr.web.form.DeptForm;
import com.testin.sys.convert.SysOrgConvert;
import com.testin.sys.domain.SysOrg;

@Service
@Transactional
public class DeptServiceImpl implements DeptService {

    @Autowired
    DeptMapper deptMapper;

    public BaseResult delete(Long id) {
    	deptMapper.deleteByPrimaryKey(id);
        return BaseResult.ok("删除成功");
    }

    @Override
    public BaseResult deleteByIds(String ids) {
        DeptExample example = new DeptExample();
        example.createCriteria().andIdIn(idsToList(ids));
        List<Dept> deptList = deptMapper.selectByExample(example);
        for (Dept org : deptList) {
            org.setDelFlag(1);
            deptMapper.updateByPrimaryKeySelective(org);
        }
        return BaseResult.ok("删除成功");
    }


    @Override
    public BaseResult get(Long id) {
        Dept dept = deptMapper.selectByPrimaryKey(id);
        return BaseResult.ok("查询成功", DeptConvert.entityToForm(dept));
    }

    @Override
    public BaseResult list(DeptForm form) {
        DeptExample example = new DeptExample();
        example.createCriteria().andDelFlagEqualTo(0);
        List<Dept> deptList = deptMapper.selectByExample(example);
        return BaseResult.ok("查询成功", DeptConvert.entityListToFormList(deptList));
    }

    @Override
    public EUTreeGridResult listPage(DeptForm form) {
        DeptExample example = new DeptExample();
        //设置分页
        example.setStart((form.getPage() - 1) * form.getRows());
        example.setSize(form.getRows());

        //排序
        example.setOrderByClause("seq ASC");

        //查询条件
        if (form != null) {
            DeptExample.Criteria criteria = example.createCriteria();
            //条件-关键字
            if (form.getKeyword() != null && !form.getKeyword().equals("")) {
                criteria.andNameLike("%" + form.getKeyword() + "%");
                example.or().andAddressLike("%" + form.getKeyword() + "%");
            }
            //其它条件
            criteria.andDelFlagEqualTo(0);
        }

        //查询总记录
        long count = deptMapper.countByExample(example);
        //查询分页列表
        List<Dept> deptList = deptMapper.selectPageByExample(example);

        //返回结果
        EUTreeGridResult result = new EUTreeGridResult(count, DeptConvert.entityListToFormList(deptList));
        return result;
    }

    @Override
    public List<Tree> tree(DeptForm form) {
        DeptExample example = new DeptExample();
        example.setOrderByClause("seq ASC");
        example.createCriteria().andDelFlagEqualTo(0);
        List<Dept> deptList = deptMapper.selectByExample(example);
        return prepareTree(deptList);
    }

    private List<Tree> prepareTree(List<Dept> deptList) {
        List<Tree> allTreeList = deptListToTreeList(deptList);
        List<Tree> topList = new ArrayList<>();
        for (Tree tree : allTreeList) {
            // 遍历所有节点，将父菜单id与传过来的id比较
            if (tree.getPid() == null) {
                tree.setChildren(prepareTreeChiled(tree.getId(), allTreeList));
                topList.add(tree);
            }
        }
        return topList;
    }

    private List<Tree> prepareTreeChiled(Long id, List<Tree> allTreeList) {
        // 子菜单
        List<Tree> childList = new ArrayList<>();
        for (Tree tree : allTreeList) {
            // 遍历所有节点，将父菜单id与传过来的id比较
            if (tree.getPid() != null && tree.getPid().equals(id)) {
                childList.add(tree);
            }
        }
        // 把子菜单的子菜单再循环一遍
        for (Tree tree : childList) {
            if (tree.getIsLeaf() == 1) {
                tree.setChildren(prepareTreeChiled(tree.getId(), allTreeList));
            }
        }
        // 递归退出条件
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }

    private List<Tree> deptListToTreeList(List<Dept> deptList) {
        List<Tree> treeList = new ArrayList<>();
        if (deptList != null && deptList.size() > 0) {
            for (Dept dept : deptList) {
                treeList.add(deptToTree(dept));
            }
        }
        return treeList;
    }

    private Tree deptToTree(Dept dept) {
        Tree tree = new Tree();
        tree.setId(dept.getId());
        tree.setText(dept.getName());
        tree.setIconCls(dept.getIcon());
        tree.setIsLeaf(dept.getIsLeaf());
        tree.setPid(dept.getPid());
        return tree;
    }

    @Override
    public BaseResult saveOrUpdate(DeptForm form) {
        Dept entity = DeptConvert.formToEntity(form);

        if (entity.getId() != null) {
            entity.setUpdateTime(new Date());
            deptMapper.updateByPrimaryKeySelective(entity);
        } else {
            entity.setIsLeaf(0);
            entity.setDelFlag(0);
            entity.setUpdateTime(new Date());
            entity.setCreateTime(new Date());
            deptMapper.insert(entity);
        }

        //更新父部门状态
        if (entity.getPid() != null) {
            Dept dept = deptMapper.selectByPrimaryKey(entity.getPid());
            dept.setIsLeaf(1);
            deptMapper.updateByPrimaryKey(dept);
        }
        return BaseResult.ok("保存成功");
    }

    @Override
    public BaseResult update(DeptForm form) {
        DeptExample example = new DeptExample();
        deptMapper.updateByExample(DeptConvert.formToEntity(form), example);
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