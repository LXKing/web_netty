<#import "common/base.ftl" as base/>
<!DOCTYPE html>
<html>

	<head>
		<title>${user.config.monitorName}</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">

		<link rel="stylesheet" href="${base.cssPath}/bootstrapStyle/bootstrapStyle.css" type="text/css">
		<link rel="stylesheet" href="${base.cssPath}/bootstrap.min.css">
		<link rel="stylesheet" href="${base.cssPath}/index.css" type="text/css">
		<link rel="stylesheet" href="${base.cssPath}/myPagination.css">
		<link rel="stylesheet" type="text/css" href="${base.cssPath}/zeroModal.css" />
        <link rel="stylesheet" href="${base.ztreePath}/css/metroStyle/metroStyle.css" media="all">
        <link rel="icon" type="image/x-icon" href="${base.fmsUrl}=${user.config.platLogo}"/>
        <script src="${base.jqueryPath}"></script>
		<script src="${base.jsPath}/bootstrap.min.js"></script>
    	<script src="${base.jsPath}/zeroModal.min.js"></script>
		<script src="${base.jsPath}/myPagination.js"></script>
        <script src="${base.ztreePath}/js/jquery.ztree.all.min.js"></script>

        <script src="${base.layuiPath}/layui.all.js"></script>
        <script src="${base.layui_jsPath}/mylayer.js"></script>
        <script src="${base.jsPath}/menus.js"></script>

        <style>
            .dropdown-menu,.navbar-default{
                background-color:${user.config.navBarColor}!important;
            }
            .ztree li {

                margin-top: 6px;
            }

            .ztree li a {
                padding-top: 6px;
                padding-bottom: 6px;
                margin-top: -5px;
            }


            .ztree li a span {
                font-size: 14px;
                padding-left: 5px;
                padding-top: 8px;
                padding-bottom: 8px;

            }

            .ztree li a.curSelectedNode {
                padding-top: 6px;
                background: #fff;
            }

            .ztree li span.button.switch {

                vertical-align: -5px;
            }

            .ztree li span.button.chk {
                width: 21px;
                height: 21px;
                margin-left: 6px;
                margin-right: 4px;
            }

            .ztree li span.button.chk.checkbox_false_full {
                background-position: -5px -4px;
            }

            .ztree li span.button.chk.checkbox_false_full_focus {
                background-position: -5px -4px;
            }

            .ztree li span.button.chk.checkbox_false_part {
                background-position: -5px -45px;
            }

            .ztree li span.button.chk.checkbox_false_part_focus {
                background-position: -5px -45px;
            }

            .ztree li span.button.chk.checkbox_false_disable {
                background-position: -5px -45px;
            }

            .ztree li span.button.chk.checkbox_true_full {
                background-position: -26px -4px;
            }

            .ztree li span.button.chk.checkbox_true_full_focus {
                background-position: -26px -4px;
            }

            .ztree li span.button.chk.checkbox_true_part {
                background-position: -26px -45px;
            }

            .ztree li span.button.chk.checkbox_true_part_focus {
                background-position: -26px -45px;
            }

            .ztree li span.button.chk.checkbox_true_disable {
                background-position: -26px -45px;
            }

            .ztree li span.button.chk.radio_false_full {
                background-position: -47px -4px;
            }

            .ztree li span.button.chk.radio_false_full_focus {
                background-position: -47px -4px;
            }

            .ztree li span.button.chk.radio_false_part {
                background-position: -47px -45px;
            }

            .ztree li span.button.chk.radio_false_part_focus {
                background-position: -47px -45px;
            }

            .ztree li span.button.chk.radio_false_disable {
                background-position: -47px -45px;
            }

            .ztree li span.button.chk.radio_true_full {
                background-position: -68px -4px;
            }

            .ztree li span.button.chk.radio_true_full_focus {
                background-position: -68px -4px;
            }

            .ztree li span.button.chk.radio_true_part {
                background-position: -68px -45px;
            }

            .ztree li span.button.chk.radio_true_part_focus {
                background-position: -68px -45px;
            }

            .ztree li span.button.chk.radio_true_disable {
                background-position: -68px -45px;
            }
            .ztree li span.button{
                width: 24px;
            }
            div .rMenu {
                position:absolute;
                visibility:hidden;
                top:0;
                text-align: left;
                padding:4px;
                z-index: 999;
            }
            div .rMenu a{
                padding: 3px 15px 3px 15px;
                background-color:#cad4e6;
                vertical-align:middle;
            }
            .zeromodal-overlay{width: 300px!important;top:18%;height: 82%!important;    background-color: rgba(255, 255, 255, 0);}
            .pacman,.line-scale-pulse-out{left: 125px!important;top:50%!important;}
            #onlineRatio{
                z-index: 999;
                position: absolute;
                top: 20px;
                left: 5px;
                min-width: 250px;
                text-align: center;
                background: rgb(255, 255, 255 ,0.9);
                border-radius: 4px;
                box-shadow: 2px 2px 2px 2px #aaa;
                padding: 10px;
            }
            #alarmCount{
                z-index: 999999;
                position: absolute;
                top: 80%;
                left: 95%;
                padding-top: 20px;
                text-align: center;
                width: 50px;
                height:50px;
                color: white;
                font-weight: bold;
                display: none;
                background: url('${base.imagesPath}/icon_alarm.svg')
            }
            .ztree li a:hover{ text-decoration:none}
		</style>
	</head>

	<body style="background: #efefef;">
    <div style="width: 100%;height: 100%;position:relative;margin: 0;padding: 0">
	<div class="index_title" >

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

					<li class="dropdown active" style="   width: 120px;text-align: center;margin-left: 10px;margin-right:10px;display: none">
						<a href="#" id="orgs_menu" class="dropdown-toggle" data-toggle="dropdown"></a>
						<ul class="dropdown-menu">
							<li>
								<div class="list_title">融易宝</div>
								<ul>
									<li>
										<a href="#">
											<input type="checkbox" id="inlineCheckbox1" value="option1">中科小竹(1/15)
										</a>
									</li>
									<li>
										<a href="#">
											<input type="checkbox" id="inlineCheckbox1" value="option1">中科小竹(1/15)
										</a>
									</li>
									<li>
										<a href="#">
											<input type="checkbox" id="inlineCheckbox1" value="option1">中科小竹(1/15)
										</a>
									</li>

								</ul>
							</li>
						</ul>
					</li>

                    <li  style="width: 120px;text-align: center;margin-left: 10px;margin-right:10px;display: block!important">
                        <a href="#"  class="dropdown-toggle" data-toggle="dropdown"> 地图切换</a>
                        <ul class="dropdown-menu" style="min-width: 120px;">
                            <li>
                                <ul>
                                    <li>
                                        <a href="#">
                                            <input type="radio" checked="checked" name="map" id="gaode" style="vertical-align: -3px;margin-right: 7px" onclick="selectMap('gaode')">高德地图
                                        </a>
                                    </li>
                                    <li>
                                        <a href="#">
                                            <input type="radio" name="map" style="vertical-align: -3px;margin-right: 7px" id="baidu" onclick="selectMap('baidu')" >百度地图
                                        </a>
                                    </li>

                                </ul>

                            </li>
                        </ul>
                    </li>
                     <@shiro.hasPermission name="webgis:sys:index">
                    <li>
                        <a href="${base.basePath}/authc/index" target="_blank">大屏</a>
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
<div class="index_content">
	<div class="left_tree">
		<div class="tab-block information-tab">
			<div class="tab-buttons ">
				<span class="tab-button cur" data-tab="one">车辆列表</span>
				<span class="tab-button" data-tab="two">地图数据</span>

			</div>
			<div class="tabs">
				<div class="tab-item active" id="tab-one">
					<div class="information-tab">

						<div class="row">
							<div class="input-group">
								<input type="text" class="form-control">
								<span class="input-group-btn">
									<button class="btn btn-default" type="button">
										搜索
									</button>
								</span>
							</div>
						</div>
						<ul id="vehicleTree" class="ztree"></ul>
                        <div id="rMenu_o" class="rMenu">
                            <a href="#" class="list-group-item" onclick="orgReport();">车队报表</a>
                            <a href="#" class="list-group-item" onclick="orgReport();">车队资料</a>
                        </div>
                        <div id="rMenu_v" class="rMenu">
                            <a href="#" class="list-group-item" onclick="monitoring();">单独监控</a>
                            <a href="#" class="list-group-item" onclick="monitoring();">视频监控</a>
                            <a href="#" class="list-group-item" onclick="monitoring();">行驶轨迹</a>
                            <a href="#" class="list-group-item" onclick="monitoring();">行驶记录</a>
                            <a href="#" class="list-group-item" onclick="monitoring();">报警记录</a>
                        </div>
					</div>
				</div>
				<div class="tab-item" id="tab-two">
					<div class="information-tab ">

					</div>
				</div>

			</div>
		</div>

	</div>
	<div class="right_content">
		<div class="cont_cont" style="height: 100%;width: 100%;position: relative; padding-top: 15px;">

            <div id="wrap" class="my-map" style="height: 100%;width: 100%">
                <iframe id="mapIfram" name="mapIfram" src="${base.basePath}/map/gaode" style="width: 100%;height: 100%" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes" ></iframe>
                <div id="onlineRatio"></div>
                <div id="alarmCount" >
                </div>
            </div>
			<div class="row" style="position: absolute;width: 100%;bottom:0px;z-index: 999;background: #fff">
				<div class="col-sm-12 " style="padding-right: 0;padding-left: 0;">
					<ul id="myTab" class="nav nav-tabs">
						<li class="active">
							<a href="#realTimeData" data-toggle="tab">实时数据</a>
						</li>
						<li>
							<a href="#alarmData" data-toggle="tab">报警数据</a>
						</li>
						<li style="float: right;margin: 10px 20px; cursor: pointer" data-toggle="collapse" data-target="#myTabContent" id="openTabContent">展现图表</li>

					</ul>

					<div id="myTabContent" class="tab-content collapse in">
						<div class="tab-pane fade in active" id="realTimeData">
							<iframe src="${base.basePath}/authc/webgisRealList" width="100%" height="250px" frameborder="no" border="0" marginheight="0" marginwidth="0"></iframe>
						</div>
						<div class="tab-pane fade" id="alarmData">
                            <iframe src="${base.basePath}/authc/webgisAlarmList"  width="100%" height="250px"   frameborder="no" border="0" marginheight="0" marginwidth="0"></iframe>
					    </div>
				</div>
			</div>
		</div>
	</div>
    </div>
</div>

        <script>
            var rMenu;
            var monitoringSimNo;
            var monitoringPlateNo;
            var monitoringOrgId;
            // 在ztree上的点击事件
            function zTreeOnClick(event, treeId, treeNode) {
                if(treeNode.sim){
                    monitoringSimNo=treeNode.sim;
                    monitoringPlateNo=treeNode.tip;
                    if(!treeNode.lat||!treeNode.lng){
                        $.post("${base.basePath}/authc/getVehicleLocation",{plateNo:monitoringPlateNo},function (data) {
                            data= $.parseJSON(data);
                            log(data);
                            if(data.success==true){
                                treeNode.lat=data.data.latitude;
                                treeNode.lng=data.data.longitude;
                                treeNode.angle=data.data.angle;
                                treeNode.sendTime=formatDateBeijing(new Date(data.data.sendTime),"yyyy-MM-dd HH:mm:ss");
                                if(treeNode.status>0&&data.data.status==0){//车辆已下线更新状态
                                    treeNode.status=data.data.status;
                                    treeNode.icon = "${base.carPath}/${user.config.mapCarIcon}/icon_truck_offline.svg"
                                    treeNode.name=data.data.plateNo;
                                    zTreeObj.setting.view.fontCss["color"]="#000";
                                    zTreeObj.updateNode(treeNode);
                                    addOnline(treeNode,"minus");
                                }
                                mapIfram.window.addMarker(treeNode);
                            }
                        });

                    }else{
                        mapIfram.window.addMarker(treeNode);
                    }

                }
            };

            // 在ztree上的右击事件
            function OnRightClick(event, treeId, treeNode) {
                log(treeNode);
                if(treeNode.sim){
                    monitoringSimNo=treeNode.sim;
                    monitoringPlateNo=treeNode.tip;
                    rMenu=$("#rMenu_v");
                }else{
                    monitoringOrgId=treeNode.id;
                    rMenu=$("#rMenu_o");
                }

                 if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
                     showRMenu( event.clientX, event.clientY);
                 } else if (treeNode && !treeNode.noR) {
                     showRMenu( event.clientX, event.clientY);
                 }
            }
            //显示右键菜单
            function showRMenu( x, y) {
               // $("#rMenu_o ul").show();
                if (rMenu)
                    rMenu.css({"top":y+"px", "left":x+"px", "visibility":"visible"}); //设置右键菜单的位置、可见

                $("body").bind("mousedown", onBodyMouseDown);
            }
            //隐藏右键菜单
            function hideRMenu() {
                if (rMenu)
                    rMenu.css({"visibility": "hidden"}); //设置右键菜单不可见
                $("body").unbind("mousedown", onBodyMouseDown);
            }
            //鼠标按下事件
            function onBodyMouseDown(event){

                if (!(event.target.id == rMenu.id || $(event.target).parents(".rMenu").length>0)) {
                    rMenu.css({"visibility" : "hidden"});
                }
            }
            //车队报告
            function orgReport() {
                alert("车队报告"+monitoringOrgId);
                hideRMenu();
            }

            //单独监控
            function monitoring() {
                menus.takingPictures(monitoringPlateNo);
                hideRMenu();
            }


        </script>
		<script>

     //tab切换
    $('.tab-button').click(function() {
        var tab = $(this).data('tab')
        $(this).addClass('cur').siblings('.tab-button').removeClass('cur');
        $('#tab-' + tab + '').addClass('active').siblings('.tab-item').removeClass('active');
     });

    //切换地图
    function selectMap(value) {
        var url="${base.basePath}/map/"+value;
        $("#mapIfram").attr('src', url);
    }

    //等待数据加载
    function loading(type) {
               zeroModal.loading(type);
           }
     //关闭加载进度条
    function closeLoading() {
               zeroModal.closeAll()
           }

    //加载组织结构
    function initDropdownMenu() {
	    var userId='${user.id}';
        $.post("${base.basePath}/authc/getUserOrgTree",{userId:userId},function (data) {
            if(data.success){
                log(data);
                if(data.data.orgCode=='company'){
                    $(".dropdown").show();
                    $("#orgs_menu").html(data.data.orgName+'<b class="caret"></b>');

                   if(data.data.orgList){
                       var orgList=data.data.orgList;
                       var context=getOrgContext(orgList);
                       $(".dropdown-menu").html(context);
                   }
                }else{
                    $(".dropdown").hide();
                }
            }else{
                log(data.data.message);
            }
        },"json");
    }

    //生成组织面板
    function  getOrgContext(orgList) {
        var context=""
		for (var i=0;i<orgList.length;i++){
	        var org = orgList[i];
            var childList = org.childList;

            if(childList != null && childList.length>0){
				context+="<li><div class='list_title'>"+org.orgName+"</div>"
                context+="<ul>"
				for (var j=0;j<childList.length;j++){
					var child=childList[j];
					context+="<li>";
					context+="<a href='#'>";
					context+="<input type='checkbox' id='"+child.orgId+"' value='option1'>"+child.orgName;
					context+="</a>";
					context+="</li>";
				}
                context+="</ul>"
				context+="</li>";
            }
		}
		return context;
    }

    //初始化树
   var zTreeObj;
   var simTid = new Map;
   var setting = {
       check:{
           enable : true,
           chkStyle : "checkbox",    //复选框
           chkboxType : {"Y": "ps", "N": "ps"}
       },
       view:{
           expandSpeed: "",
           selectedMulti: false  //允许同时选中多个节点。
           //fontCss: setFontCss
       },
       callback:{
               onRightClick : OnRightClick,
               onClick : zTreeOnClick
           }
   };
   //加载车辆树
    function initTree(orgId) {
        // zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
        loading(4);
        $.post("${base.basePath}/authc/getVehicleList",{orgId : orgId},function (data) {
            if(data.success){
                var zNodes = data.data;
                initNode(zNodes);
                closeLoading();
            }else{
                log(data);
            }
        },"json");
        // zTree 的数据属性，深入使用请参考 API 文档（zTreeNode 节点数据详解）
       /* var zNodes = [
            {name:"test1(1/2)",open:true, total:2,online:1,tip:"test1", children:[
                    {name:"晋HRB850",sim:"20000001615",status:0}, {name:"test1_2",sim:"123457",status:1}]},
            {name:"test2(2/2)",open:true, total:2,online:2, children:[
                    {name:"test2_1",sim:"1252",status:2}, {name:"test2_2",sim:"1292",status:3}]}
        ];
        initNode(zNodes);*/
    }

    function initNode(nodes) {
        zTreeObj = $.fn.zTree.init($("#vehicleTree"), setting, nodes);
        var nodes = zTreeObj.getNodes();
        for (var i = 0; i < nodes.length; i++) {
            getNodes(nodes[i]);
        }
        zTreeObj.expandAll(false);
        initSocket();
    }

    function getNodes(node) {
                if(node.sim){//车
                   simTid.set(node.sim,node.tId);
                   setFontCss(node);
			   }else{       //车队
                    if(node.children) {
                        var nodes = node.children;
                        for (var i = 0; i < nodes.length; i++) {
                            getNodes(nodes[i]);
                        }
                    }
                    node.icon="${base.carPath}/${user.config.mapCarIcon}/org.svg";
                    zTreeObj.setting.view.fontCss["color"]="#000";
                    zTreeObj.updateNode(node);

                    if(!node.getParentNode()){
                        if(node.total>0){
                            $("#onlineRatio").text("车辆总数："+node.total+" 上线车辆："+node.online+" 上线率:"+(node.online*100/node.total).toFixed(2)+"%");
                        }
                    }
                }

   		 }

    //遍历渲染状态
       function setFontCss(node) {
           var color="#000";
           var icon="icon_truck_offline.svg"
           if(node.status){
               if(node.status==0){
                    color="#000";//离线
                    icon="icon_truck_offline.svg"
               }else if(node.status==1){
                    color="#00a4ad";//停车
                    icon="icon_truck_online_stop.svg"
               }else if(node.status==2){
                   color="#22cc55";//行驶
                   icon="icon_truck_online.svg"
               }else if(node.status==3){
                   color="#d61b00";//报警
                   icon="icon_truck_online_alarm.svg"
               }
           }

           zTreeObj.setting.view.fontCss["color"]=color;
           node.icon="${base.carPath}/${user.config.mapCarIcon}/"+icon;
           zTreeObj.updateNode(node);

       };

    //更新在线
    function addOnline(node,type) {
        if(node.getParentNode()){
            var parentNode=node.getParentNode();
            var online=parentNode.online;
            if("add"==type) {
                online++;
            }else{
                online--;
            }
            parentNode.online=online;
            parentNode.name =parentNode.tip+"("+parentNode.online+"/"+parentNode.total+")";
            zTreeObj.setting.view.fontCss["color"]="#000";
            zTreeObj.updateNode(parentNode);
            addOnline(parentNode,type);
        }else{
            if(node.total>0){
             $("#onlineRatio").text("车辆总数："+node.total+" 上线车辆："+node.online+" 上线率:"+(node.online*100/node.total).toFixed(2)+"%");
            }
        }
    }

    function showAlarm() {
        alarmCountNum++;
        $("#alarmCount").show();
        $("#alarmCount").text(alarmCountNum);
        playVoice('${base.voicePath}/alarm_3.mp3');
    }
    function playVoice(src) {
        $('audio').remove();
        $('body').append('<audio autoplay="autoplay"><source '
                    + 'type="audio/wav"/><source src="'+src+'" type="audio/mpeg"/></audio>');
    }

    // 初始化内容
   $(function() {
	   initDropdownMenu();
	   var orgId='${user.orgId}';
       orgId=orgId.replace(/,/g,"")
       log(orgId);
       initTree(parseInt(orgId));//117440527
       $("#openTabContent").click();
       //setTimeout('selectMap("gaode")',500);
   });
    //退出登录
   function loginOut() {
               location = "/logout";
           };
</script>
<script src="${base.jsPath}/Vsocket.js"></script>
<script>

        var alarmCountNum=0;
        function initSocket() {

            Vsocket.init();
            Vsocket.connect({
                msgId : "authcReceiveHandle",
                simNo : "94804634492",
                userId : 1,
                content : "消息体"
            });
            Vsocket.readMsg(function (data) {
                var messageBody = JSON.parse(data.data);
                if(!messageBody.content)
                    return;
                var content = messageBody.content;
                if(content.simNo&&content.plateNo){
                    var simNo=content.simNo;
                    var plateNo=content.plateNo;
                    var tId = simTid.get(simNo);
                    var node = zTreeObj.getNodeByTId(tId);
                    if(node==null)
                        return;
                    // log(messageBody.msgCode);
                    if(messageBody.msgCode == 100) {
                        // 鉴权结果
                        log("鉴权结果为 ： " + messageBody.message);
                    }else if(messageBody.msgCode == 101) {
                        // 车辆上线
                        if(node.status==0){
                            addOnline(node,"add");
                            zTreeObj.setting.view.fontCss["color"]="#00a4ad";
                            node.status=1;
                            node.icon="${base.carPath}/${user.config.mapCarIcon}/icon_truck_online_stop.svg"
                            node.name =plateNo+"【上线】";
                            node.sendTime=formatDateBeijing(new Date(content.sendTime),"yyyy-MM-dd HH:mm:ss");
                            zTreeObj.updateNode(node);
                            mapIfram.window.updateMarker(node);
                            log("车辆上线 车牌号为 ： " + content.plateNo + " 车辆ID为 ： " + content.vehicleId+" "+node.sendTime);
                        }
                    }else if(messageBody.msgCode == 102) {
                        // 车辆下线
                            if(node.status!=0){
                                addOnline(node,"minus");
                            }
                            zTreeObj.setting.view.fontCss["color"] = "#000";
                            node.status = 0;
                            node.icon = "${base.carPath}/${user.config.mapCarIcon}/icon_truck_offline.svg"
                            node.name = plateNo;
                            node.sendTime=formatDateBeijing(new Date(content.sendTime),"yyyy-MM-dd HH:mm:ss");
                            zTreeObj.updateNode(node);
                            mapIfram.window.updateMarker(node);
                            log("车辆下线 车牌号为 ： " + content.plateNo + " 车辆ID为 ： " + content.vehicleId+" "+node.sendTime)
                    }else if(messageBody.msgCode == 103) {
                        // 位置信息


                        if(node.status==0){
                            addOnline(node,"add");
                        }

                        if(content.speed>120){
                            node.status=3;
                            zTreeObj.setting.view.fontCss["color"]="#d61b00";
                            node.icon="${base.carPath}/${user.config.mapCarIcon}/icon_truck_online_alarm.svg"
                            node.name =plateNo+"【"+content.speed+"km/h】";
                            node.angle=content.direction;
                        }else if(content.speed>0){
                            node.status=2;
                            zTreeObj.setting.view.fontCss["color"]="#22cc55";
                            node.icon="${base.carPath}/${user.config.mapCarIcon}/icon_truck_online.svg"
                            node.name =plateNo+"【"+content.speed+"km/h】";
                            node.angle=content.direction;
                        }else{
                            node.status=1;
                            zTreeObj.setting.view.fontCss["color"]="#00a4ad";
                            node.icon="${base.carPath}/${user.config.mapCarIcon}/icon_truck_online_stop.svg"
                            node.name =plateNo+"【停车】";
                        }
                        node.speed=content.speed;
                        node.lat=content.latitude;
                        node.lng=content.longitude;
                        node.sendTime=formatDateBeijing(new Date(content.sendTime),"yyyy-MM-dd HH:mm:ss");
                        zTreeObj.updateNode(node);
                        mapIfram.window.updateMarker(node);
                        log("车辆位置信息 车牌号为 ： " + content.plateNo + " 车辆ID为 ： " + content.vehicleId + " 位置为 ：" + content.latitude + " " + content.longitude + " 速度为 ：" + content.speed+" "+node.sendTime)
                    }else if(messageBody.msgCode == 104) {

                        if(node.status==0){
                            addOnline(node,"add");
                        }
                        zTreeObj.setting.view.fontCss["color"]="#d61b00";
                        node.status=3;
                        node.icon="${base.carPath}/${user.config.mapCarIcon}/icon_truck_online_alarm.svg"
                        node.name =plateNo+"【"+content.alarmTypeName+"】";
                        node.lat=content.latitude;
                        node.lng=content.longitude;
                        node.angle=content.direction;
                        node.sendTime=formatDateBeijing(new Date(content.sendTime),"yyyy-MM-dd HH:mm:ss");
                        zTreeObj.updateNode(node);
                        showAlarm();
                        mapIfram.window.updateMarker(node);
                        // 报警
                        log("车辆报警 车牌号为 ： " + content.plateNo + " 车辆ID为 ： " + content.vehicleId+" "+node.sendTime);
                    }else {
                        log("其他")
                    }
                }
            });
            Vsocket.colseListener(function (data) {
                log("webSocket colse");
                log("尝试重连...");
                setTimeout(initSocket(),1000);
            });
        }


        //日志
        function  log(log) {
            console.log(formatDate(new Date(),"yyyy-MM-dd HH:mm:ss")+":"+log);
        }



        /**
         * 格式化时间  毫秒值转字符串
         *
         * @param time
         * @param format
         * @returns {string}
         */
        function formatDate(date,format='yyyy-MM-dd HH:mm:ss'){
            if(date == null)
                return null;

           // var date = new Date(time);
            var year = date.getFullYear(),
                    month = date.getMonth()+1,//月份是从0开始的
                    day = date.getDate(),
                    hour = date.getHours(),
                    min = date.getMinutes(),
                    sec = date.getSeconds();
            var preArr = Array.apply(null,Array(10)).map(function(elem, index) {
                return '0'+index;
            });
            var newTime = format.replace(/yyyy/g,year)
                    .replace(/MM/g,preArr[month]||month)
                    .replace(/dd/g,preArr[day]||day)
                    .replace(/HH/g,preArr[hour]||hour)
                    .replace(/mm/g,preArr[min]||min)
                    .replace(/ss/g,preArr[sec]||sec);
            return newTime;
        }


        /**
         * 格式化北京时间  毫秒值转字符串
         *
         * @param time
         * @param format
         * @returns {string}
         */
        function formatDateBeijing(date,format='yyyy-MM-dd HH:mm:ss'){
            if(date == null)
                return null;

            date = new Date(date.getTime()+(8*60*60*1000));

            return formatDate(date,format);
        }




</script>

</body>

</html>