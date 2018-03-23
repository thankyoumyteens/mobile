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
        <h1 class="page-title">添加商品</h1>
    </div>
    <ul class="breadcrumb">
        <li><a href="">商品管理</a> <span class="divider">/</span></li>
        <li class="active">添加商品</li>
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
                        <label>分类</label>
                        <select name="" id="c_productCategory" class="input-xlarge" title=""></select>
                        <label>名称</label>
                        <input type="text" id="c_productName" value="" class="input-xlarge" title="">
                        <label>副标题</label>
                        <input type="text" id="c_productSubtitle" value="" class="input-xlarge" title="">
                        <label>单价</label>
                        <input type="number" id="c_productPrice" value="" class="input-xlarge" title="">
                        <label>库存</label>
                        <input type="number" id="c_productStock" value="" class="input-xlarge" title="">
                        <label>参数</label>
                        <input type="text" id="c_key" value="" class="input-xlarge" title="">
                        <input type="text" id="c_val" value="" class="input-xlarge" title="">
                        <button onclick="addDetail()">添加</button>
                        <div id="c_detailWrapper"></div>
                        <label>主图</label>
                        <input type="file" id="upload_file_main" name="upload_file" value="" class="input-xlarge">
                        <input onclick="uploadFile()" type="button" value="上传">
                        <img id="rImg" src="" alt="">
                        <label>详情图片</label>
                        <input type="file" id="upload_file_sub" name="upload_file" value="" class="input-xlarge">
                        <input onclick="uploadFileList()" type="button" value="上传">
                        <div id="c_imgWrapper"></div>
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

        getCategoryList(0);
    });

    function getCategoryList(parentId) {
        $.post('${pageContext.request.contextPath}/manage/category/list.do', {
            parentId: parentId
        }, function (res) {
            console.log(res);
            if (res.status === 0) {
                var data = res.data;
                var tab = $('#c_productCategory');
                tab.html('');
                var child = '';
                for (var i = 0; i < data.length; i++) {
                    var item = data[i];
                    child += '<option value="' + item['id'] + '">' + item['name'] + '</option>';
                }
                tab.html(child);
            } else {
                window.location.href = '${pageContext.request.contextPath}/home/login.do';
            }
        })
    }

    var g_fileUri = '';
    var g_fileListUri = '';
    var g_detail = {};

    function addDetail() {
        var key = $('#c_key').val();
        var val = $('#c_val').val();
        if (g_detail[key]) {
            g_detail[key]['value'].push(val);
        } else {
            g_detail[key] = {
                key: key,
                value: [val],
                selected: 0
            };
        }
        console.log(g_detail);
        var el = '';
        for (var k in g_detail) {
            var v = g_detail[k];
            el += '名称: ' + v['key'] + '<br>';
            el += '参数: ' + v['value'] + '<br>';
        }
        $('#c_detailWrapper').html(el);
    }

    function uploadFile() {
        var fileName = $('#upload_file_main').val();
        var fileType = fileName.substr(fileName.length - 4, fileName.length);
        var formData = new FormData();
        formData.append('upload_file', $('#upload_file_main')[0].files[0]);
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

    function uploadFileList() {
        var fileName = $('#upload_file_sub').val();
        var fileType = fileName.substr(fileName.length - 4, fileName.length);
        var formData = new FormData();
        formData.append('upload_file', $('#upload_file_sub')[0].files[0]);
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
                        if (g_fileListUri !== '') {
                            g_fileListUri += ',';
                        }
                        g_fileListUri += res.data.uri;
                        var img = '<img class="sub-img-item" src="' + src + '"/>'
                        $('#c_imgWrapper').append(img);
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
        if (g_fileUri === '' || g_fileListUri === '') {
            alert('请等待图片上传完成');
            return false;
        }
        var name = $('#c_productName').val();
        var categoryId = $('#c_productCategory').val();
        var subtitle = $('#c_productSubtitle').val();
        var price = $('#c_productPrice').val();
        var stock = $('#c_productStock').val();

        var arr = [];
        for (var k in g_detail) {
            var v = g_detail[k];
            arr.push(v);
        }
        var arrStr = JSON.stringify(arr);
        console.log(arrStr);
        $.post('${pageContext.request.contextPath}/manage/product/add.do', {
            name: name,
            categoryId: categoryId,
            subtitle: subtitle,
            price: price,
            stock: stock,
            detail: arrStr,
            mainImage: g_fileUri,
            subImages: g_fileListUri
        }, function (res) {
            console.log(res);
            alert(res.msg);
        });
    }
</script>
</body>
</html>


