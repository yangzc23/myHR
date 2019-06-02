<%--
Created by IntelliJ IDEA.
Date: 2016-12-30
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- 权限控制标签库 -->
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!-- 工具栏 -->
<div id="DeptToolbar" style="padding:5px;height:auto">
    <div>
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'" plain="true" onclick="javascript:Dept.list.add()">增加</a>
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" plain="true" onclick="javascript:Dept.list.delete()">删除</a>
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" plain="true" onclick="javascript:Dept.list.edit()">编辑</a>
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" plain="true" onclick="javascript:Dept.list.reload()">刷新</a>
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-standard-plugin-delete'" plain="true" onclick="javascript:Dept.list.collapseAll()">折叠</a>
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-standard-plugin-add'" plain="true" onclick="javascript:Dept.list.expandAll()">展开</a>
        <!-- 权限控制标签 对应com.testin.common.shiro.ShiroDbRealm.doGetAuthorizationInfo方法 -->
        <shiro:hasPermission name="user:create1">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" data-options="disabled:false" onclick="del()">删除</a>
            <span class="toolbar-item dialog-tool-separator"></span>
        </shiro:hasPermission>
    <span style="float: right;margin-right: 10px;padding: 1px">
        <span>关键字:</span>
        <input lang="searchDept" name="keyword" style="line-height:19px;border:1px solid #ccc">
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" plain="true" onclick="javascript:Dept.list.clear()">清除</a>
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" plain="true" onclick="javascript:Dept.list.search()">搜索</a>
    </span>        
    </div>
</div>
<!-- 列表 -->
<table id="DeptList" data-options="border:false"  style="width: 100%;" title="部门列表"></table>
<!-- 弹窗  --> <!-- inline:true 不然多次打开tab会重复提交表单 -->
<div id="DeptEdit" style="width:500px;height:400px;top: 100px;padding: 10px;display: none" data-options="iconCls: 'icon-save',closed: true,modal: true,inline:true,buttons:[{text:'保存',iconCls:'icon-save',handler:function(){Dept.input.submitForm()}},{text:'取消',iconCls:'icon-cancel',handler:function(){Dept.input.close()}}]"  ></div>
<script src="<%=request.getContextPath()%>/jsp/hr/dept/dept.js"></script>
<script>
    Dept.list.init('<%=request.getContextPath()%>');
</script>