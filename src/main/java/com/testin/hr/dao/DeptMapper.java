package com.testin.hr.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.testin.hr.domain.Dept;
import com.testin.hr.domain.DeptExample;
import com.testin.sys.domain.SysOrg;
import com.testin.sys.domain.SysOrgExample;


/**
 * @Description: 组织机构,数据库表为： sys_org<br/>
 * @Autor: Created by JinKun on 2016-12-30.
 */
public interface DeptMapper {
    long countByExample(DeptExample example);

    int deleteByExample(DeptExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Dept record);

    int insertSelective(Dept record);

    List<Dept> selectByExample(DeptExample example);

    List<Dept> selectPageByExample(DeptExample example);

    Dept selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Dept record, @Param("example") DeptExample example);

    int updateByExample(@Param("record") Dept record, @Param("example") DeptExample example);

    int updateByPrimaryKeySelective(Dept record);

    int updateByPrimaryKey(Dept record);
}