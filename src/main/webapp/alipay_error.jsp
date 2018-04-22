<%@ page import="iloveyesterday.mobile.util.PropertiesUtil" %><%--
  Created by IntelliJ IDEA.
  User: ILove
  Date: 2018/4/22
  Time: 14:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name=viewport content="width=device-width,initial-scale=1,user-scalable=no">
    <title>支付失败</title>
    <link rel="stylesheet" href="https://res.wx.qq.com/open/libs/weui/1.1.2/weui.min.css">

    <style>
        .center {
            width: 100%;
            position: absolute;
            top: 50%;
            transform: translateY(-50%);
        }

        .icon-wrapper {
            width: 100%;
            text-align: center;
        }

        .text-wrapper {
            width: 100%;
            text-align: center;
            font-size: 1.5em;
        }

        .error-wrapper {
            width: 100%;
            text-align: center;
        }

        .link-wrapper {
            width: 90%;
            text-align: center;
            margin: auto;
            color: #777;
        }
    </style>
</head>
<body>
<div class="center">
    <div class="icon-wrapper">
        <i class="weui-icon-warn weui-icon_msg"></i>
    </div>
    <div class="text-wrapper">支付失败</div>
    <div class="error-wrapper">
        <%=(request.getAttribute("errMsg") == null ? "" : "原因: " + request.getAttribute("errMsg").toString())%>
    </div>
    <div class="link-wrapper">
        <span id="seconds">15</span>秒后回到首页, 如果没有自动跳转请点击
        <a href="<%=PropertiesUtil.getProperty("server.root", "/")%>">链接</a>
        回到首页
    </div>
</div>

<script>
    function linkTo() {
        var seconds = document.getElementById('seconds').innerHTML;
        if (parseInt(seconds) > 0) {
            document.getElementById('seconds').innerHTML = (parseInt(seconds) - 1) + '';
            setTimeout(linkTo, 1000);
        } else {
            window.location.href = '<%=PropertiesUtil.getProperty("server.root", "/")%>';
        }
    }

    setTimeout(linkTo, 1000);
</script>
</body>
</html>
