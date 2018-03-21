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
                        <th>订单号</th>
                        <th>员工编号</th>
                        <th>用户编号</th>
                        <th>下单时间</th>
                        <th>地址</th>
                        <th>状态</th>
                    </tr>
                    </thead>
                    <tbody id="c_ol">
                    </tbody>
                </table>
            </div>
            <div class="pagination" style="text-align: center">
                <ul id="pager"></ul>
            </div>

            <div class="modal small hide fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
                 aria-hidden="true">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h3 id="myModalLabel">Delete Confirmation</h3>
                </div>
                <div class="modal-body">
                    <p class="error-text"><i class="icon-warning-sign modal-icon"></i>Are you sure you want to delete
                        the user?</p>
                </div>
                <div class="modal-footer">
                    <button class="btn" data-dismiss="modal" aria-hidden="true">Cancel</button>
                    <button class="btn btn-danger" data-dismiss="modal">Delete</button>
                </div>
            </div>


            <footer>
                <hr>

                <p class="pull-right">Collect from <a href="http://www.cssmoban.com/" title="网页模板"
                                                      target="_blank">网页模板</a></p>

                <p>&copy; 2012 <a href="#" target="_blank">Portnine</a></p>
            </footer>
        </div>
    </div>
</div>


<script src="lib/bootstrap/js/bootstrap.js"></script>
<script type="text/javascript">

    var g_currentPageNum = 1;

    function getOrderList(pageNum) {
        g_currentPageNum = pageNum;
        $.post('../manage/order/list.do', {
            pageNum: pageNum,
            pageSize: 10
        }, function (data) {
            var res = data;
            if (res.status === 0) {
                console.log(res.data.list);
                var tab = $('#c_ol');
                tab.html('');
                var child = '';
                for (var i = 0; i < res.data.list.length; i++) {
                    var item = res.data.list[i];
                    child += '<tr>\n' +
                        '<td>' + item['orderNo'] + '</td>\n' +
                        '<td>' + item['productId'] + '</td>\n' +
                        '<td>' + item['userId'] + '</td>\n' +
                        '<td>' + formatDateString(item['createTime']) + '</td>\n' +
                        '<td>' + item['receiverProvince'] + '省 ' + item['receiverCity'] + '市 ' + '\n' +
                        '' + item['receiverDistrict'] + '区' + item['receiverAddress'] + ' ' + '\n' +
                        '' + item['receiverPhone'] + '' +
                        '</td>\n' +
                        '<td>\n';
                    switch (parseInt(item['status'])) {
                        case 0:
                            child += '<div style="color: #f43534" class="fr">已取消</div>\n';
                            break;
                        case 20:
                            child += '<div onclick="sendOrder(' + item['userId'] + ', ' + item['orderNo'] + ')" style="cursor: pointer;" class="fr perfect">发货</div>\n';
                            // child += '<div style="color: #2d7df4" class="fr">等待中</div>\n';
                            break;
                        case 40:
                            // child += '<div onclick="successOrder(' + item['orderNo'] + ')" style="cursor: pointer;" class="fr perfect">完成服务</div>\n';
                            child += '<div style="color: #CC5522" class="fr">进行中</div>\n';
                            break;
                        case 50:
                            child += '<div style="color: #3bdf4a" class="fr">已完成</div>\n';
                            break;
                    }
                    child += '</td>\n' +
                        '</tr>';
                }
                tab.html(child);
                $('#pager').jqPaginator({
                    totalPages: res.data['pages'],
                    visiblePages: 5,
                    currentPage: res.data['pageNum'],
                    first: '<li class="first"><a href="javascript:void(0);">首页</a></li>',
                    prev: '<li class="prev"><a href="javascript:void(0);">上一页</a></li>',
                    next: '<li class="next"><a href="javascript:void(0);">下一页</a></li>',
                    last: '<li class="last"><a href="javascript:void(0);">尾页</a></li>',
                    page: '<li class="page"><a href="javascript:void(0);">{{page}}</a></li>',
                    onPageChange: function (num, type) {
                        if (type === 'change') {
                            getOrderList(num);
                        }
                    }
                });
            } else {
                window.location.href = 'sign-in.html';
            }
        })
    }

    function sendOrder(userId, orderNo) {
        $.post('../manage/order/start.do', {
            userId: userId,
            orderNo: orderNo
        }, function (res) {
            if (res.status === 0) {
                getOrderList(g_currentPageNum);
            } else {
                alert(res.msg);
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
        getOrderList(1);
    });
</script>
</body>
</html>



