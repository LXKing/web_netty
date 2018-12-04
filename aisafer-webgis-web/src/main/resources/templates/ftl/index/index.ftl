<#import "../common/base.ftl" as base/>
<!DOCTYPE html>
<html lang="en" >

<head>
  <meta charset="UTF-8">
  <title>智能监控</title>
  <link rel="stylesheet" href="${base.indexcssPath}/indexpage.css">
  <link rel="stylesheet" href="${base.indexcssPath}/bootstrap.min.css">
  <link rel="stylesheet" href="${base.indexcssPath}/index.css" type="text/css">
    <link rel="icon" type="image/x-icon" href="${base.fmsUrl}=${user.config.platLogo}"/>
	<script src="${base.jqueryPath}"></script>
    <script src="${base.jsPath}/bootstrap.min.js"></script>
</head>

<body style=" background-color: #14111E; overflow: hidden;">

<div class="indexCont">
    <div class="index_title"  style="display: none">

        <nav class="navbar navbar-default" role="navigation" style="width: 100%;	position: absolute;
	z-index: 9999;">
            <div class="container-fluid">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#example-navbar-collapse">
                        <span class="sr-only"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="#"><span style="margin-right: 30px;"><img src="${base.fmsUrl}=${user.config.platLogo}" width="32" height="32" alt=""  style="vertical-align: -8px;margin-right: 5px"/>${user.orgName}</span>${user.config.monitorName}</a>
                </div>
                <div class="collapse navbar-collapse" id="example-navbar-collapse">
                    <ul class="nav navbar-nav">

                        <@shiro.hasPermission name="webgis:sys:main">
                        <li>
                            <a href="${base.basePath}/authc/main" target="_blank">首页</a>
                        </li>
                        </@shiro.hasPermission>

                        <li style="position: absolute;right: 10px;">

                        <@shiro.hasPermission name="manager:sys:index">
						<a href="${base.mainPath}" style="display: inline-block;  padding:10px 7px">管理平台 <span style="margin-left: 5px">|</span></a>
                        </@shiro.hasPermission>
                            <a href="#" style="display: inline-block;   padding:10px 7px">帮助 <span style="margin-left: 5px">|</span></a>

                            <a href="#" style="display: inline-block;  padding:10px 7px">${user.userName}<span style="margin-left: 5px">|</span></a>

                            <a href="javascript:void(0)" onclick="loginOut()" style="display: inline-block; ">退出</a>

                        </li>
                    </ul>

                </div>
            </div>
        </nav>

    </div>
	<div class="index_titlett">
    <img src="${base.indeximagesPath}/Group.png" width="312" height="28" alt=""/> </div>
<iframe frameborder="0" scrolling="no" src="${base.basePath}/authc/map" width="100%" height="100%"  ></iframe>
</div>
	<div class="left_data">
	<div class="data_con">
		<span>12345</span>
		<span>当前报警数量</span>
		</div>
	
			<div class="data_con">
		<span>234</span>
		<span>当前上线车辆</span>
		</div>
			<div class="data_rate">
		<span>45%</span>
		</div>
			<div class="data_title" style="padding-left: 25px">当前车辆上线率</div>
		
		<div class="data_chart">
		<iframe frameborder="0" scrolling="no" src="${base.basePath}/authc/chartLine" width="400px" height="280px" ></iframe>
		</div>
		
	</div>
	<div class="right_data">
		<div class="data_title">TOP5司机评分</div>
	<div class="data_con_rt">
		<ul class="ftcolor">
		<li><span>1</span>周平轩<em>95</em></li>
			<li><span>2</span>周平轩<em>85</em></li>
			<li>
			<span>3</span>周平轩<em>75</em></li>
		<li><span>4</span>周平轩<em>70</em></li>
		<li><span>5</span>周平轩<em>65</em></li>
		</ul>
		</div>
	<div class="data_title">TOP5报警类型排名</div>
			<div class="data_con_rt">
		<ul>
		<li><span>1</span>分心一类<em>1954</em></li>
			<li><span>2</span>分心二类<em>395</em></li>
			<li><span>3</span>超速报警<em>945</em></li>
			<li><span>4</span>急减速<em>695</em></li>
			<li><span>5</span>车道偏离<em>925</em></li>
		</ul>
		</div>
		
<div class="data_title">TOP5超高危车辆</div>
				<div class="data_con_rt">
		<ul>
		    <li><span>1</span>粤B123456</li>
			<li><span>2</span>粤BC23456</li>
			<li><span>3</span>粤BM23456</li>
			<li><span>4</span>粤BK23456</li>
			<li><span>5</span>粤BH23456</li>
		</ul>
		</div>
	
	</div>
	<script>

	$(function() {
        $(".data_rate").click(function(e) {
            $(".index_title").toggle();
            fullScreen();
        });
    });

    //全屏
    function fullScreen(){
        var el = document.documentElement;
        var rfs = el.requestFullScreen || el.webkitRequestFullScreen || el.mozRequestFullScreen || el.msRequestFullscreen;
        if(typeof rfs != "undefined" && rfs) {
            rfs.call(el);
        };
        return;
    }

    //ie低版本的全屏，退出全屏都这个方法
    function iefull(){
        var el = document.documentElement;
        var rfs =  el.msRequestFullScreen;
        if(typeof window.ActiveXObject != "undefined") {
            //这的方法 模拟f11键，使浏览器全屏
            var wscript = new ActiveXObject("WScript.Shell");
            if(wscript != null) {
                wscript.SendKeys("{F11}");
            }
        }
    }


	//退出登录
    function loginOut() {
        location = "/logout";
    };
	</script>
</body>

</html>
