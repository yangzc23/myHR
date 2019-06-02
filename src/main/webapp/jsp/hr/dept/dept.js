var ctx = "";//项目部署的工程名
var DeptList;
var DeptEdit;
var DeptForm;

//其它组件
var parentDept;

var Dept = {
    URL: {
        inputUI: function () {
            return ctx + "/hr/depts/ui/input";
        },
        listUI: function () {
            return ctx + "/hr/depts/ui/list";
        },
        list: function () {
            return ctx + "/hr/depts/";
        },
        tree: function () {
            return ctx + "/hr/depts/tree";
        },
        save: function () {
            return ctx + "/hr/depts/";
        },
        delete: function (ids) {
            return ctx + "/hr/depts/" + ids;
        },
        get: function (id) {
            return ctx + "/hr/depts/" + id;
        }
    },
    input: {
        init: function (ct) {
            ctx = ct;
            Dept.input.initComponent();
            Dept.input.initForm();
        },
        initComponent: function () {
            DeptForm = $("#DeptForm");
            parentDept = $("#parentDept");
        },
        initForm: function () {
            DeptForm.form({
                url: Dept.URL.save(),
                onSubmit: function () {
                    // do some check
                    // return false to prevent submit;
                },
                success: function (data) {
                    var data = eval('(' + data + ')');
                    if (data.code == 200) {
                        Dept.input.close();
                        Dept.list.reload();
                    }
                }
            });
        },
        submitForm: function () {
            // submit the form
            DeptForm.submit();
        },
        close: function () {
            DeptEdit.dialog('close');
        },
    },
    list: {
        init: function (ct) {
            ctx = ct;
            Dept.list.initComponent();
            Dept.list.initList();
        },
        initComponent: function () {
            DeptList = $("#DeptList");
            DeptEdit = $('#DeptEdit');
        },
        initList: function () {
            DeptList.treegrid({
                url: Dept.URL.list(),
                method: 'get',
                pagination: true,
                pageSize: 30,
                toolbar: '#DeptToolbar',//SysOrg.list.toolbar,
                singleSelect: true,
                collapsible: false,
                idField: 'id',
                treeField: 'name',
                parentField: 'pid',
                columns: [[
                    {field: 'ck', checkbox: true},
                    {field: 'id', title: '主键id', hidden: true},
                    {field: 'name', title: '部门名', width: '13.571%', hidden: false},
                    {field: 'address', title: '地址', width: '13.571%', hidden: false},
                    {field: 'code', title: '编号', width: '13.571%', hidden: false},
                    {field: 'icon', title: '图标', width: '13.571%', hidden: false},
                    {field: 'pid', title: '父级主键', width: '13.571%', hidden: true},
                    {field: 'seq', title: '排序', width: '13.571%', hidden: false},
                    {field: 'createTime', title: '创建时间', width: '13.571%', hidden: false},
                ]],
                //设置选中事件，清除之前的行选择
                onClickRow: function (row) {
                    DeptList.treegrid("unselectAll");
                    DeptList.treegrid("selectRow",row.id);
                },
                loadFilter: function (data, parentId) {
                    var opt = $(this).data().treegrid.options;
                    var parentField;
                    if (opt.parentField) {
                        parentField = opt.parentField;
                        var jsonStr = JSON.stringify(data); //可以将json对象转换成json对符串
                        jsonStr = jsonStr.replace(new RegExp(parentField, "gm"), "_parentId");
                        return JSON.parse(jsonStr); //可以将json字符串转换成json对象
                    }
                }
            });
        },
        getSelectionsIds: function () {
            var sels = DeptList.datagrid("getSelections");
            var ids = [];
            for (var i in sels) {
                ids.push(sels[i].id);
            }
            ids = ids.join(",");
            return ids;
        },
        //增
        add: function () {
        	DeptEdit.attr("title","新增部门");
            DeptEdit.dialog({
            		title:"新增部门",
                    href: Dept.URL.inputUI(),
                    onLoad: function () {
                        parentDept.combotree({
                            url: Dept.URL.tree(),
                            method: 'get',
                            panelHeight: 'auto'
                        });
                    }
                })
                .dialog("open");
        },
        //改
        edit: function () {
            var sels = DeptList.treegrid("getSelections");
            if (sels.length < 1) {
                $.messager.alert("对话框", "至少选择一行");
                return;
            }

            if (sels.length > 1) {
                $.messager.alert("对话框", "只能选择一行");
                return;
            }

        	DeptEdit.attr("title","编辑部门");
            DeptEdit.dialog({
            		title: "编辑部门",
                    href: Dept.URL.inputUI(),
                    onLoad: function () {
                        //方案一：使用Form的load去load数据
                        //SysOrgForm.form("load", SysOrg.URL.get(sels[0].id));
                        //方案二：直接使用列表的row数据
                        //SysOrgForm.form("load",sels[0]);
                        //方案三：使用Ajax请求数据
                        $.ajax({
                            type: "GET",
                            url: Dept.URL.get(sels[0].id),
                            success: function (data) {
                                if (data.code == 200) {
                                    DeptForm.form("load", data.data);
                                    parentDept.combotree({
                                        url: Dept.URL.tree(),
                                        method: 'get',
                                        panelHeight: 'auto',
                                        onLoadSuccess: function () {
                                            parentDept.combotree('setValue', data.data.pid);
                                        }
                                    });
                                }
                            }
                        });
                    }
                })
                .dialog("open");
        },
        //删
        delete: function () {
            var ids = Dept.list.getSelectionsIds();
            if (ids.length == 0) {
                $.messager.alert("对话框", "至少选择一行");
                return;
            }

            $.messager.confirm({
                title: '确认提示框',
                msg: '你确定要删除吗？',
                fn: function (r) {
                    if (r) {
                        $.ajax({
                            type: "DELETE",
                            url: Dept.URL.delete(ids),
                            dataType: "json",
                            success: function () {
                                Dept.list.reload();
                                Dept.list.clearSelectionsAndChecked();
                            }
                        });
                    }
                }
            });
        },
        //刷新
        reload: function () {
            DeptList.treegrid("reload");
        },
        collapseAll: function () {
            var roots = DeptList.treegrid("getRoots");
            for (var i in roots) {
                DeptList.treegrid("collapseAll", roots[i].id);
            }
        },
        expandAll: function () {
            var roots = DeptList.treegrid("getRoots");
            for (var i in roots) {
                DeptList.treegrid("expandAll", roots[i].id);
            }
        },
        clearSelectionsAndChecked: function () {
            DeptList.treegrid("clearChecked");
            DeptList.treegrid("clearSelections");
        },
        //搜索
        search: function () {
            var searchName = [];
            var searchValue = [];
            $("input[lang='searchDept']").each(function (i) {
                searchName[i] = $(this).attr("name");
                searchValue[i] = $(this).val();
            });

            var queryParamsArr = [];
            for (var i = 0; i < searchName.length; i++) {
                queryParamsArr.push(searchName[i] + ":'" + searchValue[i] + "'")
            }
            var queryParams = "{" + queryParamsArr.join(",") + "}";

            DeptList.treegrid({
                queryParams: eval('(' + queryParams + ')'),
                onLoadSuccess: function (data) {
                    //回显搜索内容
                    $("input[lang='searchDept']").each(function (i) {
                        $(this).val(searchValue[i]);
                    });
                }
            });
        },
        //清空
        clear: function () {
            $("input[lang='searchDept']").each(function (i) {
                $(this).val("");
            });
        }
    }
}
