package com.testin.sys.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.testin.sys.service.SysRoleResourceService;
import com.testin.sys.web.form.SysRoleResourceForm;

/**
 * @Description: HelloWorld！ <br/>
 * @Autor: Created by jinkun on 2016/12/4.
 */
@Controller
public class SysRoleResourceController {

    @Autowired
    SysRoleResourceService sysRoleResourceService;

    @RequestMapping(value = "/sys/role/resources/ui/{ui}", method = RequestMethod.GET)
    public String ui(@PathVariable("ui") String ui) {
        return "sys/sys-role-resource/sys-role-resource-" + ui;
    }

    @RequestMapping(value = "/sys/role/resources", method = RequestMethod.POST)
    @ResponseBody
    public Object save(SysRoleResourceForm form) {
        return sysRoleResourceService.saveOrUpdate(form);
    }

    @RequestMapping(value = "/sys/role/resources/{ids}", method = RequestMethod.DELETE)
    @ResponseBody
    public Object delete(@PathVariable("ids") String ids) {
        return sysRoleResourceService.deleteByIds(ids);
    }

    @RequestMapping(value = "/sys/role/resources", method = RequestMethod.GET)
    @ResponseBody
    public Object list(SysRoleResourceForm form) {
        return sysRoleResourceService.listPage(form);
    }

    @RequestMapping(value = "/sys/role/resources/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Object get(@PathVariable("id") Long id) {
        return sysRoleResourceService.get(id);
    }
}
