<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ILove
  Date: 2018/3/21
  Time: 22:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>管理员登陆</title>
    <meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <c:import url="head.jsp"/>
</head>
<body class="">
<div class="navbar">
    <div class="navbar-inner">
        <ul class="nav pull-right">
        </ul>
        <a class="brand" href=""><span class="first">Your</span> <span class="second">Company</span></a>
    </div>
</div>
<div class="row-fluid">
    <div class="dialog">
        <div class="block">
            <p class="block-heading">登陆</p>
            <div class="block-body">
                <form>
                    <label>用户名</label>
                    <input type="text" class="span12" id="username" title="">
                    <label>密码</label>
                    <input type="password" class="span12" id="password" title="">
                    <a href="#" class="btn btn-primary pull-right" onclick="login()">登陆</a>

                    <div class="clearfix"></div>
                </form>
            </div>
        </div>
        <p class="pull-right" style=""><a href="#" target="blank"></a></p>
    </div>
</div>
<script type="text/javascript">
    $("[rel=tooltip]").tooltip();
    $(function () {
        $('.demo-cancel-click').click(function () {
            return false;
        });
    });

    function login() {
        var username = $('#username').val();
        var password = $('#password').val();
        $.post('${pageContext.request.contextPath}/manage/user/login.do', {
            username: username,
            password: password
        }, function (data) {
            var res = data;
            if (res.status === 0) {
                window.location.href = '${pageContext.request.contextPath}/home/index.do';
            } else {
                alert(res.msg);
            }
        })
    }
</script>
</body>
</html>



