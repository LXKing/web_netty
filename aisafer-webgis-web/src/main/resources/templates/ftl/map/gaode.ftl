<#import "../common/base.ftl" as base/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>高德地图</title>
   <#-- <script language="javascript" src="//webapi.amap.com/maps?v=1.4.10&key=8325164e247e15eea68b59e89200988b&plugin=AMap.ControlBar"></script>-->

    <style>
        body,html{ margin:0;padding:0;font:12px/16px Verdana,Helvetica,Arial,sans-serif;width: 100%;height: 100%}
        .container{
            height: 100%
        }
        .amap-info-content{min-width: 400px;min-height: 200px;}
        .input-card content-window-card{min-height: 180px}
    </style>
</head>
<body>
<div id="container" style="width:100%; height:100%;resize:both;"></div>
<script src="${base.jqueryPath}"></script>
<script language="javascript" src="https://webapi.amap.com/maps?v=1.4.10&key=8b7a1738cd7671ed03723f9bcafa120d&plugin=AMap.ControlBar,AMap.Geocoder,AMap.GraspRoad"></script>
<!-- UI组件库 1.0 -->
<script src="//webapi.amap.com/ui/1.0/main.js?v=1.0.11"></script>
<script language="javascript">
    var map;
    function mapInit(){


        map = new AMap.Map('container', {
           // resizeEnable: true,
           // rotateEnable:true,
           // pitchEnable:true,
            zoom: 4,
            pitch:0,
            rotation:0,
            viewMode:'3D',//开启3D视图,默认为关闭
            //buildingAnimation:true,//楼块出现是否带动画
            expandZoomRange:true,
            zooms:[3,20],
            center:[103.492792,32.303985]
        });

        /*map.addControl(new AMap.ControlBar({
            showZoomBar:false,
            showControlButton:true,
            position:{
                right:'10px',
                top:'10px'
            }
        }))*/

        AMapUI.loadUI(['control/BasicControl'], function(BasicControl) {

            //添加一个缩放控件
            map.addControl(new BasicControl.Zoom({
                position: 'lt'
            }));

            //缩放控件，显示Zoom值
            map.addControl(new BasicControl.Zoom({
                position: 'lb',
                showZoomNum: true
            }));

            //图层切换控件
            map.addControl(new BasicControl.LayerSwitcher({
                position: 'rt'
            }));
        });

    }


    var markers = new Map;
    var texts = new Map;
    var markerIndex=0;
    function addMarker(node) {
        if(!node.sim)
            return;

        markerIndex++;
        var marker =markers.get(node.sim);
        var text =texts.get(node.sim);

        if(marker==null){
            marker= new AMap.Marker({
                position: [node.lng,node.lat],
                icon: getIcon(node.status),
                autoRotation:true,
                offset: new AMap.Pixel(-16, -12)
            });
            // 设置鼠标划过点标记显示的文字提示
            marker.setTitle(node.name);
            if(node.angle){
                marker.setAngle(node.angle-90);//角度
            }
            // 设置label标签
            marker.on('click', function () {
                openInfo(node,marker);
            });
            marker.setMap(map);
            markers.set(node.sim,marker);
        }
        if(text==null){
            // 创建纯文本标记
             text = new AMap.Text({
                text:node.tip,
                textAlign:'center', // 'left' 'right', 'center',
                verticalAlign:'middle', //middle 、bottom
               // draggable:true,
                cursor:'pointer',
                angle:0,
                offset: new AMap.Pixel(-8, 30),
                style:{
                    'padding': '.25rem .25rem',
                    'margin-bottom': '1rem',
                    'border-radius': '.25rem',
                    'background-color': 'white',
                    'width': '6rem',
                    'border-width': 0,
                    'box-shadow': '0 2px 6px 0 rgba(0, 0, 0,0)',
                    'text-align': 'center',
                    'font-size': '12px',
                    'font-weight': 'bold',
                    'color': '#000'
                }
            });
           // text.setMap(map);
            text.on('click', function () {
                openInfo(node,marker);
            });

            texts.set(node.sim,text);
        }

        if(node.lng&&node.lat){
            AMap.convertFrom([node.lng,node.lat], "gps", function (status, result) {
                if (result.info == 'ok') {
                    var resLnglat = result.locations[0];
                    marker.setPosition(resLnglat); //更新点标记位置
                    marker.setzIndex(markerIndex);
                    text.setPosition(resLnglat); //更新点标记位置
                    openInfo(node,marker);
                    map.panTo(resLnglat);
                }
            });

        }
    }

    function updateMarker(node) {
        if(!node.sim)
            return;
        markerIndex++;
        var marker=markers.get(node.sim);
        var text =texts.get(node.sim);
        if (marker==null)
            return;

        // 自定义点标记内容
        var markerContent = document.createElement("div");
        // 点标记中的图标
        var markerImg = document.createElement("img");
        markerImg.src = getIcon(node.status);
        markerContent.appendChild(markerImg);

        marker.setContent(markerContent); //更新点标记内容
       //marker.on('click', openInfo(node,marker));
        marker.setTitle(node.name);
        if(node.angle&&node.angle>0)
            marker.setAngle(node.angle-90);//角度

        if(node.lat&&node.lng){
            AMap.convertFrom([node.lng,node.lat], "gps", function (status, result) {
                if (result.info == 'ok') {
                    var resLnglat = result.locations[0];

                    markertMove(marker,marker.getPosition(),resLnglat,node.speed);

                    if(infoWindowSimNo==node.sim){

                       // marker.setPosition(resLnglat); //更新点标记位置
                       // marker.setzIndex(markerIndex);
                        openInfo(node,marker);
                        map.panTo(resLnglat);
                    }
                }
            });
        }
    }

    
    //轨迹纠偏及移动
    function markertMove(marker,op1,op2,speed) {
        var tm = parseInt(((new Date()).getTime()/1000-30));
        var pathParam = [];
        var p1={};
        var p2={};
        p1.x=op1.getLng();
        p1.y=op1.getLat();
        p1.sp=Number(speed);
        p1.ag=0;
        p1.tm=tm;
        p2.x=op2.getLng();
        p2.y=op2.getLat();
        p2.sp=Number(speed);
        p2.ag=0;
        p2.tm=30;
        pathParam.push(p1);
        pathParam.push(p2);

        log(pathParam);

     /*  pathParam = [
            {"x":116.478928,"y":39.997761,"sp":19,"ag":0, "tm":1478031031},
            {"x":116.478907,"y":39.998422,"sp":10,"ag":0, "tm":2}]*/


        var graspRoad = new AMap.GraspRoad();
        graspRoad.driving(pathParam,function(error,result){
            log(error);
            log(result);
            if(!error){
                var ps=[];
                var newPath = result.data.points;
                for(var i =0;i<newPath.length;i+=1){
                  var lnglat= new AMap.LngLat(newPath[i].x.toFixed(6),newPath[i].y.toFixed(6));
                    ps.push(lnglat);
                }
                marker.moveAlong(ps,Number(speed));
            }else{
               // marker.moveAlong([op1,op2],Number(speed));
                marker.setPosition(op2); //更新点标记位置
                marker.setzIndex(markerIndex);
            }
        });

    }


    //在指定位置打开信息窗体
    var infoWindow;
    var infoWindowSimNo="";
    var infoWindowPlateNo="";
    var geocoder;
    function openInfo(node,marker) {

        if(!geocoder){
            geocoder = new AMap.Geocoder({
                city: "010", //城市设为北京，默认：“全国”
                radius: 1000 //范围，默认：500
            });
        }
        geocoder.getAddress(marker.getPosition(), function(status, result) {
            var address;
            if (status === 'complete'&&result.regeocode) {
                 address = result.regeocode.formattedAddress;
                //构建信息窗体中显示的内容
            }else{
                address=JSON.stringify(result);
            }
            var color="#000";

            if(node.status){
                if(node.status==0){
                    color="#000";//离线
                }else if(node.status==1){
                    color="#00a4ad";//停车
                }else if(node.status==2){
                    color="#22cc55";//行驶
                }else if(node.status==3){
                    color="#d61b00";//报警
                }
            }

            var info = [];
            info.push("<div class='input-card content-window-card'>");
                info.push("<div style='width: 180px;height:160px; border: 1px solid #ddd;margin-right:20px;display:inline-block ;'>");
                    info.push("<img style='float:left;width:180px;height:160px;' src='https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542024670416&di=b7ac13ba95c7976500d95c437fc53799&imgtype=0&src=http%3A%2F%2F58pic.ooopic.com%2F58pic%2F15%2F83%2F09%2F58PIC9J58PICc2U.jpg'/>");
                info.push("</div> ");
                info.push("<div style='padding:7px 0px 0px 0px;width: 190px;height:190px;float: right;line-height:8px; '>");
                    info.push("<h4 style='color: "+color+"'>"+node.name+"</h4>");
                    info.push("<p class='input-item'>部门:"+node.getParentNode().tip+"</p>");
                    info.push("<p class='input-item'>SIM:"+node.sim+"</p>");
                    info.push("<p class='input-item'>时间:"+node.sendTime+"</p>");
                    info.push("<p class='input-item' style='line-height:14px;margin-top: 4px'>位置:"+address+"</p>");
                info.push("</div>");
                info.push("<div style='width: 100%;height: 32px;background-color: #eeeeee;padding-left: 6px'>");
                    info.push("<img title='拍照' height='20px' width='20px' style='margin-top: 6px;margin-left: 6px' src='http://resource.aisafer.com/res/images/nav_icon_car_20.png' onclick='takingPictures()' />");
                    info.push("<img title='拍照' height='20px' width='20px' style='margin-top: 6px;margin-left: 6px' src='http://resource.aisafer.com/res/images/nav_icon_set_20.png'/>");
                    info.push("<img title='拍照' height='20px' width='20px' style='margin-top: 6px;margin-left: 6px' src='http://resource.aisafer.com/res/images/nav_icon_maintain_20.png'/>");
                    info.push("<img title='拍照' height='20px' width='20px' style='margin-top: 6px;margin-left: 6px' src='http://resource.aisafer.com/res/images/nav_icon_statistics_20.png'/>");
                    info.push("<img title='拍照' height='20px' width='20px' style='margin-top: 6px;margin-left: 6px' src='http://resource.aisafer.com/res/images/nav_icon_deploy_20.png'/>");
                    info.push("<img title='拍照' height='20px' width='20px' style='margin-top: 6px;margin-left: 6px' src='http://resource.aisafer.com/res/images/nav_icon_car_20.png'/>");
                    info.push("<img title='拍照' height='20px' width='20px' style='margin-top: 6px;margin-left: 6px' src='http://resource.aisafer.com/res/images/nav_icon_rule_20.png'/>");

                info.push("</div>");
            info.push("</div>");

            infoWindow = new AMap.InfoWindow({
                content: info.join("")  //使用默认信息窗体框样式，显示信息内容
            });

            infoWindow.on("close",function(){
                infoWindowSimNo="";
            });

            infoWindow.open(map,  marker.getPosition());

            infoWindowSimNo=node.sim;
            infoWindowPlateNo=node.tip;
        });

    }

    function getIcon(status) {
        var icon="${base.carPath}/${user.config.mapCarIcon}/icon_car_gray.svg";
        if(status){
            switch (status){
                case 0:
                    icon = "${base.carPath}/${user.config.mapCarIcon}/icon_car_gray.svg";
                    break;
                case 1:
                    icon = "${base.carPath}/${user.config.mapCarIcon}/icon_car_blue.svg";
                    break;
                case 2:
                    icon = "${base.carPath}/${user.config.mapCarIcon}/icon_car_green.svg";
                    break;
                case 3:
                    icon = "${base.carPath}/${user.config.mapCarIcon}/icon_car_red.svg";
                    break;
            };
        }

        return icon;
    }

    //拍照
    function takingPictures() {
        parent.menus.takingPictures(infoWindowPlateNo);
    }

    $(function(){
        mapInit();
    });

    //日志
    function  log(log) {
        console.log(log);
    }


</script>
</body>
</html>