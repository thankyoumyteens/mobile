<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ILove
  Date: 2018/3/21
  Time: 22:40
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
        <h1 class="page-title">添加分类</h1>
    </div>
    <ul class="breadcrumb">
        <li><a href="">分类管理</a> <span class="divider">/</span></li>
        <li class="active">添加分类</li>
    </ul>

    <div class="container-fluid">
        <div class="row-fluid">
            <div class="btn-toolbar">
                <button onclick="add()" class="btn btn-primary"><i class="icon-save"></i> 完成</button>
                <div class="btn-group">
                </div>
            </div>
            <div class="well">
                <div id="myTabContent" class="tab-content">
                    <div class="tab-pane active in" id="home">
                        <form id="tab">
                            <label>名称</label>
                            <input type="text" id="c_categoryName" value="" class="input-xlarge">
                            <label>图片</label>
                            <input type="file" id="upload_file" name="upload_file" value="" class="input-xlarge">
                            <input onclick="uploadFile()" type="button" value="上传">
                            <img id="rImg" src="" alt="">
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<script type="text/javascript">
    $("[rel=tooltip]").tooltip();
    $(function () {
        $('.demo-cancel-click').click(function () {
            return false;
        });
    });

    var g_fileUri = '';

    function uploadFile() {
        var fileName = $('#upload_file').val();
        var fileType = fileName.substr(fileName.length - 4, fileName.length);
        var formData = new FormData();
        formData.append('upload_file', $('#upload_file')[0].files[0]);
        if (fileType === '.jpg' || fileType === '.png') {
            $.ajax({
                url: '${pageContext.request.contextPath}/home/upload.do',
                type: 'POST',
                cache: false,
                data: formData,
                processData: false,
                contentType: false,
                success: function (res) {
                    console.log(res);
                    if (res.status === 0) {
                        var src = res.data.url;
                        g_fileUri = res.data.uri;
                        $('#rImg').attr('src', src);
                    } else {
                        window.location.href = '${pageContext.request.contextPath}/home/login.do';
                    }
                }
            });
        } else {
            alert('上传文件类型错误,支持类型: .jpg .png');
        }
    }

    function add() {
        if (g_fileUri === '') {
            alert('请等待图片上传完成');
            return false;
        }
        var name = $('#c_categoryName').val();
        $.post('${pageContext.request.contextPath}/manage/category/add.do', {
            name: name,
            img: g_fileUri
        }, function (res) {
            console.log(res);
            alert(res.msg);
        });
    }
</script>
</body>
</html>



