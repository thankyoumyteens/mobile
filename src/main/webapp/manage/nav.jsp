<%--
  Created by IntelliJ IDEA.
  User: ILove
  Date: 2018/3/21
  Time: 22:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="navbar">
    <div class="navbar-inner">
        <ul class="nav pull-right">
            <li id="fat-menu" class="dropdown">
                <a href="#" role="button" class="dropdown-toggle" data-toggle="dropdown">
                    <i class="icon-user"></i> 管理员
                    <i class="icon-caret-down"></i>
                </a>
                <ul class="dropdown-menu">
                    <li class="divider visible-phone"></li>
                    <li><a tabindex="-1" href="" onclick="logout()">退出登录</a></li>
                    <script>
                        function logout() {
                            $.post('${pageContext.request.contextPath}/manage/user/logout.do', function (data) {
                                window.location.href = '${pageContext.request.contextPath}/home/index.do';
                            })
                        }
                    </script>
                </ul>
            </li>
        </ul>
        <a class="brand" href="${pageContext.request.contextPath}/home/index.do">
            <span class="first">Your</span> <span class="second">Company</span>
        </a>
    </div>
</div>

<div class="sidebar-nav">
    <a href="#dashboard-menu" class="nav-header" data-toggle="collapse"><i class="icon-dashboard"></i>管理</a>
    <ul id="dashboard-menu" class="nav nav-list collapse in">
        <li><a href="${pageContext.request.contextPath}/home/index.do">主页</a></li>
        <li><a href="${pageContext.request.contextPath}/home/categories.do">分类管理</a></li>
    </ul>
    <a href="#accounts-menu" class="nav-header" data-toggle="collapse"><i class="icon-briefcase"></i>账号<span
            class="label label-info"></span></a>
    <ul id="accounts-menu" class="nav nav-list collapse">
        <li><a href="${pageContext.request.contextPath}/home/login.do">登陆</a></li>
    </ul>
</div>
