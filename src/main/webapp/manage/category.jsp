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
                <button class="btn btn-primary"><i class="icon-save"></i> 完成</button>
                <a href="${pageContext.request.contextPath}/home/categories.do" data-toggle="modal" class="btn">取消</a>
                <div class="btn-group">
                </div>
            </div>
            <div class="well">
                <ul class="nav nav-tabs">
                    <li class="active"><a href="#home" data-toggle="tab">Profile</a></li>
                    <li><a href="#profile" data-toggle="tab">密码</a></li>
                </ul>
                <div id="myTabContent" class="tab-content">
                    <div class="tab-pane active in" id="home">
                        <form id="tab">
                            <label>用户名</label>
                            <input type="text" value="jsmith" class="input-xlarge">
                            <label>First Name</label>
                            <input type="text" value="John" class="input-xlarge">
                            <label>Last Name</label>
                            <input type="text" value="Smith" class="input-xlarge">
                            <label>Email</label>
                            <input type="text" value="jsmith@yourcompany.com" class="input-xlarge">
                            <label>Address</label>
                            <textarea value="Smith" rows="3" class="input-xlarge">
2817 S 49th
Apt 314
San Jose, CA 95101
        </textarea>
                            <label>Time Zone</label>
                            <select name="DropDownTimezone" id="DropDownTimezone" class="input-xlarge">
                                <option value="-12.0">(GMT -12:00) Eniwetok, Kwajalein</option>
                                <option value="-11.0">(GMT -11:00) Midway Island, Samoa</option>
                                <option value="-10.0">(GMT -10:00) Hawaii</option>
                                <option value="-9.0">(GMT -9:00) Alaska</option>
                                <option selected="selected" value="-8.0">(GMT -8:00) Pacific Time (US &amp; Canada)
                                </option>
                                <option value="-7.0">(GMT -7:00) Mountain Time (US &amp; Canada)</option>
                                <option value="-6.0">(GMT -6:00) Central Time (US &amp; Canada), Mexico City</option>
                                <option value="-5.0">(GMT -5:00) Eastern Time (US &amp; Canada), Bogota, Lima</option>
                                <option value="-4.0">(GMT -4:00) Atlantic Time (Canada), Caracas, La Paz</option>
                                <option value="-3.5">(GMT -3:30) Newfoundland</option>
                                <option value="-3.0">(GMT -3:00) Brazil, Buenos Aires, Georgetown</option>
                                <option value="-2.0">(GMT -2:00) Mid-Atlantic</option>
                                <option value="-1.0">(GMT -1:00 hour) Azores, Cape Verde Islands</option>
                                <option value="0.0">(GMT) Western Europe Time, London, Lisbon, Casablanca</option>
                                <option value="1.0">(GMT +1:00 hour) Brussels, Copenhagen, Madrid, Paris</option>
                                <option value="2.0">(GMT +2:00) Kaliningrad, South Africa</option>
                                <option value="3.0">(GMT +3:00) Baghdad, Riyadh, Moscow, St. Petersburg</option>
                                <option value="3.5">(GMT +3:30) Tehran</option>
                                <option value="4.0">(GMT +4:00) Abu Dhabi, Muscat, Baku, Tbilisi</option>
                                <option value="4.5">(GMT +4:30) Kabul</option>
                                <option value="5.0">(GMT +5:00) Ekaterinburg, Islamabad, Karachi, Tashkent</option>
                                <option value="5.5">(GMT +5:30) Bombay, Calcutta, Madras, New Delhi</option>
                                <option value="5.75">(GMT +5:45) Kathmandu</option>
                                <option value="6.0">(GMT +6:00) Almaty, Dhaka, Colombo</option>
                                <option value="7.0">(GMT +7:00) Bangkok, Hanoi, Jakarta</option>
                                <option value="8.0">(GMT +8:00) Beijing, Perth, Singapore, Hong Kong</option>
                                <option value="9.0">(GMT +9:00) Tokyo, Seoul, Osaka, Sapporo, Yakutsk</option>
                                <option value="9.5">(GMT +9:30) Adelaide, Darwin</option>
                                <option value="10.0">(GMT +10:00) Eastern Australia, Guam, Vladivostok</option>
                                <option value="11.0">(GMT +11:00) Magadan, Solomon Islands, New Caledonia</option>
                                <option value="12.0">(GMT +12:00) Auckland, Wellington, Fiji, Kamchatka</option>
                            </select>
                        </form>
                    </div>
                    <div class="tab-pane fade" id="profile">
                        <form id="tab2">
                            <label>New 密码</label>
                            <input type="password" class="input-xlarge">
                            <div>
                                <button class="btn btn-primary">Update</button>
                            </div>
                        </form>
                    </div>
                </div>

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
    $("[rel=tooltip]").tooltip();
    $(function () {
        $('.demo-cancel-click').click(function () {
            return false;
        });
    });
</script>
</body>
</html>



