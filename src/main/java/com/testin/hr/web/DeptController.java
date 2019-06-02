package com.testin.hr.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.testin.hr.service.DeptService;
import com.testin.hr.web.form.DeptForm;
import com.testin.sys.web.form.SysOrgForm;

/**
 * @Description: HelloWorld！ <br/>
 * @Autor: Created by jinkun on 2016/12/4.
 */
@Controller
public class DeptController {

    @Autowired
    DeptService deptService;

    @RequestMapping(value = "/hr/depts/ui/{ui}", method = RequestMethod.GET)
    public String ui(@PathVariable("ui") String ui) {
        return "hr/dept/dept-" + ui;
    }

    @RequestMapping(value = "/hr/depts", method = RequestMethod.POST)
    @ResponseBody
    public Object save(DeptForm form) {
        return deptService.saveOrUpdate(form);
    }

    @RequestMapping(value = "/hr/depts/{ids}", method = RequestMethod.DELETE)
    @ResponseBody
    public Object delete(@PathVariable("ids") String ids) {
        return deptService.deleteByIds(ids);
    }

    @RequestMapping(value = "/hr/depts", method = RequestMethod.GET)
    @ResponseBody
    public Object list(DeptForm form) {
        return deptService.listPage(form);
    }

    @RequestMapping(value = "/hr/depts/tree", method = RequestMethod.GET)
    @ResponseBody
    public Object tree(DeptForm form) {
        return deptService.tree(form);
    }

    @RequestMapping(value = "/hr/depts/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Object get(@PathVariable("id") Long id) {
        return deptService.get(id);
    }
}
