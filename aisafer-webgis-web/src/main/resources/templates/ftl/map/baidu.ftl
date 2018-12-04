<#import "../common/base.ftl" as base/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>百度地图</title>
    <style type="text/css">
        body, html,#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;font-family:"微软雅黑";}
        .amap-info-content{min-width: 400px;min-height: 200px;}
        .input-card content-window-card{min-height: 180px;}
        p{font-size:12px;}
    </style>
    <script src="${base.jqueryPath}"></script>
    <script type="text/javascript" src="http://api.map.baidu.com/api?key=&amp;v=1.1&amp;services=true"></script>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&amp;ak=w6f1iRNfkwApDfQXv2HF6E5jj8atkCDi"></script>
</head>
<body>
<div id="allmap"></div>
</body>
<script type="text/javascript">
    // 百度地图API功能
    var map;
    var geoc;
    function mapInit(){
        // 创建Map实例
        map=new BMap.Map("allmap");
        geoc = new BMap.Geocoder();
        map.centerAndZoom(new BMap.Point(103.492792,32.303985), 5);  // 初始化地图,设置中心点坐标和地图级别
        //添加地图类型控件
        map.addControl(new BMap.MapTypeControl({
            mapTypes:[
                BMAP_NORMAL_MAP,
                BMAP_HYBRID_MAP
            ]}));
        map.setCurrentCity("北京");          // 设置地图显示的城市 此项是必须设置的
        map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
    }

    var markers = new Map;
    var markerIndex=0;
    function addMarker(node) {
        if (!node.sim)
            return;
        log(node.sim);
        markerIndex++;
        var marker =markers.get(node.sim);

        if(marker==null){
            var pt = new BMap.Point(node.lng,node.lat);
            var myIcon = new BMap.Icon(getIcon(node.status), new BMap.Size(40, 40));
            marker = new BMap.Marker(pt);  // 创建标注
            marker.setIcon(myIcon);
            marker.setTitle(node.name);
            marker.setOffset(new BMap.Size(4, 4));
            marker.addEventListener("click",function () {
                openInfo(node,marker);
            });
            if(node.angle){
                marker.setRotation(node.angle-90)//角度
            }

            map.addOverlay(marker);
            markers.set(node.sim,marker);
        }
        if(node.lng&&node.lat){
            var convertor = new BMap.Convertor();
            var point = new BMap.Point(node.lng,node.lat);
            var pointArr = [];
            pointArr.push(point);
            convertor.translate(pointArr, 1, 5, function (result){
                    if(result.status == 0) {
                        var resLnglat = result.points[0];
                        marker.setPosition(resLnglat);
                        marker.setZIndex(markerIndex);
                        openInfo(node,marker);

                        if(map.getZoom()>15){
                            map.setZoom(15);
                        }
                        map.panTo(resLnglat);

                    }
                });
        }

    }

    function updateMarker(node) {
        if (!node.sim)
            return;
        markerIndex++;
        var marker = markers.get(node.sim);
        if (marker == null)
            return;

        var myIcon = new BMap.Icon(getIcon(node.status), new BMap.Size(40, 40));
        marker.setIcon(myIcon);
        if(node.angle){
            marker.setRotation(node.angle-90)//角度
        }
        marker.setTitle(node.name);
        if(node.lng&&node.lat){
            var convertor = new BMap.Convertor();
            var point = new BMap.Point(node.lng,node.lat);
            var pointArr = [];
            pointArr.push(point);
            convertor.translate(pointArr, 1, 5, function (result){
                if(result.status == 0) {
                    var resLnglat = result.points[0];
                    marker.setPosition(resLnglat);
                    marker.setZIndex(markerIndex);

                    if(infoWindowSimNo==node.sim)
                        openInfo(node,marker);
                }
            });
        }

    }

    var infoWindow;
    var infoWindowSimNo="";
    var infoWindowPlateNo="";
    function openInfo(node,marker) {
        var opts = {
            width : 400,     // 信息窗口宽度
            height: 200     // 信息窗口高度
        }
        var address="";
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

        geoc.getLocation(marker.getPosition(), function(rs){
            log(rs);
            address=rs.address;
            var info = [];
            info.push("<div class='input-card content-window-card'>");
            info.push("<div style='width: 180px;height:160px; border: 1px solid #ddd;margin-right:20px;display:inline-block ;'>");
            info.push("<img style='float:left;width:180px;height:160px;' src='https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542024670416&di=b7ac13ba95c7976500d95c437fc53799&imgtype=0&src=http%3A%2F%2F58pic.ooopic.com%2F58pic%2F15%2F83%2F09%2F58PIC9J58PICc2U.jpg'/>");
            info.push("</div> ");
            info.push("<div style='padding:7px 0px 0px 0px;width: 190px;height:190px;float: right;line-height:8px; '>");
            info.push("<h5 style='color: "+color+"'>"+node.name+"</h5>");
            info.push("<p class='input-item'>部门:"+node.getParentNode().tip+"</p>");
            info.push("<p class='input-item'>SIM:"+node.sim+"</p>");
            info.push("<p class='input-item'>时间:"+node.sendTime+"</p>");
            info.push("<p class='input-item' style='line-height:14px;margin-top: 4px'>位置:"+address+"</p>");
            info.push("</div>");
            info.push("<div style='width: 100%;height: 32px;background-color: #eeeeee;padding-left: 6px'>");
            info.push("<img title='拍照' height='20px' width='20px' style='margin-top: 6px;margin-left: 6px' src='http://resource.aisafer.com/res/images/nav_icon_car_20.png' onclick='takingPictures()' /> ");
            info.push("<img title='拍照' height='20px' width='20px' style='margin-top: 6px;margin-left: 6px' src='http://resource.aisafer.com/res/images/nav_icon_set_20.png'/>");
            info.push("<img title='拍照' height='20px' width='20px' style='margin-top: 6px;margin-left: 6px' src='http://resource.aisafer.com/res/images/nav_icon_maintain_20.png'/>");
            info.push("<img title='拍照' height='20px' width='20px' style='margin-top: 6px;margin-left: 6px' src='http://resource.aisafer.com/res/images/nav_icon_statistics_20.png'/>");
            info.push("<img title='拍照' height='20px' width='20px' style='margin-top: 6px;margin-left: 6px' src='http://resource.aisafer.com/res/images/nav_icon_deploy_20.png'/>");info.push("<img title='拍照' height='20px' width='20px' style='margin-top: 6px;margin-left: 6px' src='http://resource.aisafer.com/res/images/nav_icon_car_20.png'/>");
            info.push("<img title='拍照' height='20px' width='20px' style='margin-top: 6px;margin-left: 6px' src='http://resource.aisafer.com/res/images/nav_icon_rule_20.png'/>");

            info.push("</div>");
            info.push("</div>");

            infoWindow = new BMap.InfoWindow(info.join(""), opts);  // 创建信息窗口对象
            map.openInfoWindow(infoWindow,marker.getPosition()); //开启信息窗口
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
</html>