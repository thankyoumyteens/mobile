<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ILove
  Date: 2018/3/21
  Time: 22:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <c:import url="head.jsp"/>
</head>

<body class="">
<c:import url="nav.jsp"/>

<div class="content">
    <div class="header">
        <h1 class="page-title">分类管理</h1>
    </div>
    <ul class="breadcrumb">
        <li><a href="">分类管理</a> <span class="divider">/</span></li>
        <li class="active">列表</li>
    </ul>

    <div class="container-fluid">
        <div class="row-fluid">
            <div class="btn-toolbar">
                <a href="${pageContext.request.contextPath}/home/category.do" class="btn btn-primary">
                    <i class="icon-save"></i> 添加
                </a>
                <div class="btn-group">
                </div>
            </div>
            <div class="well">
                <table class="table">
                    <thead>
                    <tr>
                        <th>编号</th>
                        <th>名称</th>
                        <th>状态</th>
                        <th>图片</th>
                        <th>创建时间</th>
                        <th>更新时间</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody id="c_ol">
                    <%--数据--%>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>


<script type="text/javascript">
    function getCategoryList(parentId) {
        $.post('${pageContext.request.contextPath}/manage/category/list.do', {
            parentId: parentId
        }, function (res) {
            console.log(res);
            if (res.status === 0) {
                var data = res.data;
                var tab = $('#c_ol');
                tab.html('');
                var child = '';
                for (var i = 0; i < data.length; i++) {
                    var item = data[i];
                    child += '<tr>\n' +
                        '<td>' + item['id'] + '</td>\n' +
                        '<td><em>' + item['name'] + '</em></td>\n' +
                        '<td>' + (item['status'] ? '可用' : '废弃') + '</td>\n' +
                        '<td><img src="' + item['img'] + '" style="height: 50px"/></td>\n' +
                        '<td>' + formatDateString(item['createTime']) + '</td>\n' +
                        '<td>' + formatDateString(item['updateTime']) + '</td>\n' +
                        '<td>' +
                        '<a><i class="icon-pencil"></i></a>\n' +
                        '<a onclick="deleteById(' + item['id'] + ')" role="button" data-toggle="modal"><i class="icon-remove"></i></a>' +
                        '</td>' +
                        '</tr>';
                }
                tab.html(child);
            } else {
                window.location.href = '${pageContext.request.contextPath}/home/login.do';
            }
        })
    }

    function deleteById(categoryId) {
        $.post('${pageContext.request.contextPath}/manage/category/delete.do', {
            categoryId: categoryId
        }, function (res) {
            console.log(res);
            alert(res.msg);
            if (res.status === 0) {
                getCategoryList(0);
            }
        });
    }

    function formatDateString(string) {
        var date = new Date(string);
        var r = '';
        r += date.getFullYear() + '年';
        r += (date.getMonth() + 1) + '月';
        r += date.getDate() + '日 ';
        r += date.getHours() + ':';
        r += date.getMinutes() + ':';
        r += date.getSeconds();

        return r;
    }

    $("[rel=tooltip]").tooltip();
    $(function () {
        $('.demo-cancel-click').click(function () {
            return false;
        });
        getCategoryList(0);
    });
</script>
</body>
</html>



