<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ILove
  Date: 2018/3/21
  Time: 21:27
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
    <ul class="breadcrumb">
        <li><a href="${pageContext.request.contextPath}/home/index.do">主页</a> <span class="divider">/</span></li>
    </ul>
</div>


<script src="${pageContext.request.contextPath}/manage/lib/bootstrap/js/bootstrap.js"></script>
<script type="text/javascript">
    $("[rel=tooltip]").tooltip();
    $(function () {
        $('.demo-cancel-click').click(function () {
            return false;
        });
        check();
    });

</script>
</body>
</html>



