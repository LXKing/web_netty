<#import "common/common.ftl" as base/>
<!DOCTYPE html>
<html lang="en">
<head>

    <title>实时数据</title>
    <style>
        .layui-fluid {
            padding: 15px;
        }

        .layui-table tr td:nth-child(1), .layui-table tr th:nth-child(1),.layui-table tr td:nth-child(1) .layui-table-cell,.layui-table tr th:nth-child(1) .layui-table-cell{
            width:60px!important;
        }

        .layui-table tr td:nth-child(2), .layui-table tr th:nth-child(2),.layui-table tr td:nth-child(2) .layui-table-cell,.layui-table tr th:nth-child(2) .layui-table-cell{
            width:100px!important;
        }
        .layui-table tr td:nth-child(3), .layui-table tr th:nth-child(3),.layui-table tr td:nth-child(3) .layui-table-cell,.layui-table tr th:nth-child(3) .layui-table-cell{
            width:240px!important;
        }


        .layui-table tr td:nth-child(5), .layui-table tr th:nth-child(5),.layui-table tr td:nth-child(5) .layui-table-cell,.layui-table tr th:nth-child(5) .layui-table-cell{
            width:160px!important;
        }

        .layui-table tr td:nth-child(4), .layui-table tr th:nth-child(4),.layui-table tr td:nth-child(4) .layui-table-cell,.layui-table tr th:nth-child(4) .layui-table-cell{
            width:100px!important;
        }

        .layui-table tr td:nth-child(6), .layui-table tr th:nth-child(6),.layui-table tr td:nth-child(6) .layui-table-cell,.layui-table tr th:nth-child(6) .layui-table-cell{
            width:200px!important;
        }
        .layui-table tr td:nth-child(7), .layui-table tr th:nth-child(7), .layui-table tr td:nth-child(7) .layui-table-cell,.layui-table tr th:nth-child(7) .layui-table-cell{
            min-width:300px!important;
        }

        .layui-table tr td:nth-child(8), .layui-table tr th:nth-child(8), .layui-table tr td:nth-child(8) .layui-table-cell,.layui-table tr th:nth-child(8) .layui-table-cell{
            width:100px!important;
        }

        .layui-table tr td:nth-child(9), .layui-table tr th:nth-child(9), .layui-table tr td:nth-child(9) .layui-table-cell,.layui-table tr th:nth-child(9) .layui-table-cell{
            width:150px!important;
        }

        .layui-table tr td:last-child, .layui-table tr th:last-child{
            width:100px!important;

        }
        .layui-table tr td:last-child .layui-table-cell, .layui-table tr th:last-child .layui-table-cell
        {
            width:100px!important;

        }

        .layui-form-item .layui-input-inline,.layui-form-item .layui-input-inline .layui-form-select{

            min-width:120px;
            max-width: 150px;
        }

        .layui-laydate-header{
            background-color:#fff!important;
        }

        #layui-laydate2 .layui-laydate-header{
            background-color:#fff!important;
        }

        #layui-laydate3 .layui-laydate-header{
            background-color:#fff!important;
        }
        .layui-form-checkbox[lay-skin=primary] i {
            top: 0px !important;
        }
        .portlet{margin-top: 0
            min-width: 1250px;
        }
        .layui-table-body .layui-none {
            line-height: 40px;
            text-align: center;
            border: 1px solid #e6e6e6;
            padding: 45px 0;
            font-size: 16px;
            min-height: auto;
            color: #999;
        }
    </style>

</head>
<body  style="background: #fff">
<div class="tab-body" style="padding: 0;">
    <div class="portlet box">
        <div class="layui-fluid">
            <div class="layui-row" >
                <div class="layui-col-md12 layui-col-xs12">
                   <!-- <div id="toolbox">
                        <form class="layui-form" id="dataForm" onsubmit="return pagedataHandel.sumbit();" >
                            <input type="hidden" id="alarmEventId" value="">
                            <div class="layui-row">
                                <div class="layui-col-md4 layui-col-xs4">
                                    <div class="layui-col-md12 layui-col-xs12">
                                        <div class="layui-form-item">
                                            <label class="layui-form-label" style=""><@base.spring.message "vehicle.plateNo" /></label>
                                            <div class="layui-input-inline">
                                                <input type="text" id="plateNo" name="plateNo" lay-verify="" onblur="blurPlateNo()" placeholder="<@base.spring.message  "msg.enter" />" autocomplete="off" class="layui-input">
                                            </div>
                                            <button  class="layui-btn layui-btn-normal"  lay-filter="submit" style="margin: 0 15px;"><@base.spring.message  "vehicle.vedio" /></button>
                                            <button  class="layui-btn layui-btn-normal"  lay-filter="submit" style="margin: 0 15px;"><@base.spring.message  "vehicle.photograph" /></button>

                                        </div>
                                    </div>

                                </div>

                            </div>

                        </form>
                    </div>-->
                    <div class="layui-row" >
                        <div class="layui-col-md12 layui-col-xs12">
                            <table class="layui-hide" lay-data="{id: 'reloadData'}" id="dataTable"></table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/html" id="ID">
    {{d.LAY_TABLE_INDEX+1}}
</script>
<script type="text/html" id="opreationTool">
    <#--{{# id(d.status == "结束状态") { }}
        <a class="opreation" onclick="alarmProcess('{{d.id}}')" ><@base.spring.message "alarm.pend" /></a>
    {{# }else { }}
        <a class="opreation" style="color: black" onclick="alarmProcess('{{d.id}}')" ><@base.spring.message "alarm.end" /></a>
    {{# } }}-->
    <@shiro.hasPermission name="manager:statis:singleven">
        <a class="opreation" onclick="singleven('{{d.alarmId}}','{{d.alarmType}}','{{d.vehicleId}}')" ><@base.spring.message "alarm.review" /></a>
    </@shiro.hasPermission>
</script>

<script type="text/html" id="alarmTypeTool">
    <a class="opreation" onclick="alarmType('{{d.alarmId}}','{{d.alarmType}}')" >{{d.alarmType}}</a>
</script>

<script type="text/html" id="alarmReginTool">
    <a class="opreation" onclick="alarmRegin('{{=d.locationId}}')">{{d.location}}</a>
</script>

<script type="text/html" id="alarmTimeTool">
    <#--<a class="opreation" onclick="alarmType('{{d.alarmType}}')" >{{d.alarmType}}</a>-->
    {{formatDateTime(d.alarmTime,"yyyy-MM-dd HH:mm:ss")}}
</script>

<script>
    var myDate = new Date();
    var timeInterval = 7200000;
    var startTime = myDate.getTime() - timeInterval;
    var endTime = myDate.getTime();

    var searchUrl = "${base.basePath}/authc/getWebgisRealList";
    var cols=[[ //标题栏
        {field: '', title: '',templet: '#ID'}
        ,{field: 'plateNo', title: '<@base.spring.message  "vehicle.plateNo" />',unresize: true }
        ,{field: 'orgName', title: '<@base.spring.message  "vehicle.depName" />',unresize: true }
        ,{field: 'plateColor', title: '<@base.spring.message  "vehicle.plateColor" />', unresize: true }
        ,{field: 'alarmType', title: '<@base.spring.message  "alarm.alarmType" />',toolbar: '#alarmTypeTool',unresize: true }
        ,{field: 'alarmTime', title: '<@base.spring.message  "alarm.time" />',unresize: true }
        ,{field: 'location', title: '<@base.spring.message  "alarm.regin" />',toolbar: '#alarmReginTool',unresize: true }
        ,{field: 'speed', title: '<@base.spring.message  "alarm.speed" />(<@base.spring.message  "speed.info" />)',align:'right',unresize: true }
        ,{field: 'simNo', title: '<@base.spring.message "sim.simNo" />',  unresize: true }
        ,{field: 'operation', title: '<@base.spring.message  "alarm.proce" />', width:'8%',toolbar: '#opreationTool', unresize: true}
    ]];

    $(function(){
        // 加载数据表格
        pagedataHandel.init();

        // 初始化开始日期和结束日期控件
        createDateTime(null,formatDate(myDate.getTime()),formatDate(myDate.getTime() - timeInterval),changeStartTime,"#startTime","datetime");
        createDateTime(formatDate(myDate.getTime() - timeInterval),formatDate(myDate.getTime()),formatDate(myDate.getTime()),changeEndTime,"#endTime","datetime");

        //加载下拉框
        createSelectId3("#alarmType", "${base.basePath}/authc/findAlarmTypes", "alarmName", "alarmId");
        createSelectId2("#alarmSource", "", "name", "", "alarm_level");

        pagedataHandel.initData(searchUrl,cols);
    });

    // 下拉框值改变触发查询
    layui.use('form',function() {
        var form = layui.form;
        form.on('select(alarmType)',function(data) {
            console.log(data);
            pagedataHandel.initData(searchUrl,cols);
        });

        form.on('select(alarmSource)',function(data) {
            console.log(data);
            pagedataHandel.initData(searchUrl,cols);
        });

        /**
         * 选择快速选时触发
         */
        form.on('select(quickCheck)',function(data) {
            myDate = new Date();
            var startStr = "";
            var endStr = "";
            if(data.value == "") {
                /*$("#startTime").prop("disabled",null);
                $("#endTime").prop("disabled",null);*/
                $("#startTime").val(formatDate(startTime));
                $("#endTime").val(formatDate(endTime));
                return ;
            }else if(data.value == 0){
                startStr = myDate.getFullYear() + "-" + getMonth(myDate) + "-" + getDay(myDate) + " 00:00:00";
                endStr = formatDate(myDate.getTime());
            }else if(data.value == 1) {
                var dateStr = formatDate(myDate.getTime() - 86400000);
                var s = dateStr.replace(/-/g,"/");
                var date = new Date(s);
                startStr = date.getFullYear() + "-" + getMonth(date) + "-" + getDay(date) + " 00:00:00";
                endStr = date.getFullYear() + "-" + getMonth(date) + "-" + getDay(date) + " 23:59:59";
            }else if(data.value == 3) {
                var dateStr = formatDate(myDate.getTime() - 259200000);
                var s = dateStr.replace(/-/g,"/");
                var date = new Date(s);
                startStr = date.getFullYear() + "-" + getMonth(date) + "-" + getDay(date) + " 00:00:00";
                endStr = formatDate(myDate.getTime());
            }else if(data.value == 7) {
                var dateStr = formatDate(myDate.getTime() - 604800000);
                var s = dateStr.replace(/-/g,"/");
                var date = new Date(s);
                startStr = date.getFullYear() + "-" + getMonth(date) + "-" + getDay(date) + " 00:00:00";
                endStr = formatDate(myDate.getTime());
            }else if(data.value == 2) {
                startStr = formatDate(myDate.getTime() - 7200000);
                endStr = formatDate(myDate.getTime());
            }
            $("#startTime").val(startStr);
            $("#endTime").val(endStr);
            pagedataHandel.initData(searchUrl,cols);
        });
    });

    function getDay(date) {
        var day = date.getDate();
        if(day < 10)
            return "0" + day;
        return day;
    }

    function getMonth(date) {
        var month = date.getMonth() + 1;
        if(month < 10)
            return "0" + month;
        return month;
    }

    /**
     * 选择开始时间
     */
    function changeStartTime(value, date, endDate) {
        if(value == null || value == ''){
            $("#endInput").html('<input type="text" name="endTime" id="endTime" placeholder="<@base.spring.message "lable.endTime" />" autocomplete="off" class="layui-input">');
            createDateTime(startTime,startTime+timeInterval,null,changeEndTime,"#endTime","datetime");
            return ;
        }

        // 将字符串转换为时间
        var s = value.replace(/-/g,"/");
        var date = new Date(s);
        startTime = date.getTime();
        if((startTime + timeInterval) < endTime) {
            endTime = startTime + timeInterval;
        }else if(startTime > endTime) {
            endTime = startTime + timeInterval;
        }
        console.log(value + "===" + endTime)
        console.log(formatDate(endTime));
        $("#endInput").html('<input type="text" name="endTime" id="endTime" placeholder="<@base.spring.message "lable.endTime" />" autocomplete="off" class="layui-input">');
        createDateTime(startTime,startTime+timeInterval,formatDate(endTime),changeEndTime,"#endTime","datetime");
    }

    /**
     * 选择结束时间
     */
    function changeEndTime(value, date, endDate) {
        if(value == ''){
            $("#endInput").html('<input type="text" name="endTime" id="endTime" placeholder="<@base.spring.message "lable.endTime" />" autocomplete="off" class="layui-input">');
            createDateTime(startTime,startTime+timeInterval,formatDate(endTime),changeEndTime,"#endTime","datetime");
            return ;
        }

        var s = value.replace(/-/g,"/");
        var date = new Date(s);
        endTime = date.getTime();
    }

    /**
     * 车牌号鼠标离开事件
     */
    function blurPlateNo() {
        var plateNo = $("#plateNo").val();
        console.log(plateNo + "==")
        if(plateNo == "") {
            timeInterval = 7200000;
        }else {
            timeInterval = 172800000;
        }
        if((startTime + timeInterval) < endTime) {
            endTime = startTime + timeInterval;
        }else if(startTime > endTime) {
            endTime = startTime + timeInterval;
        }

        $("#endInput").html('<input type="text" name="endTime" id="endTime" placeholder="<@base.spring.message "lable.endTime" />" autocomplete="off" class="layui-input">');
        createDateTime(startTime,startTime+timeInterval,formatDate(endTime),changeEndTime,"#endTime","datetime");
    }

    function selectOrg(treeNode) {
        $("#org-select").attr("class","layui-form-select layui-unselect ");
        $("#orgId").val(treeNode.id);
        $("#orgName").val(treeNode.name);
        pagedataHandel.initData(searchUrl,cols);
    }

    /**
     * 报警事件处理
     *
     * @param id
     */
    function alarmProcess(id) {
        alert(id);
    }

    var alarmEvenId;
    /**
     * 单事件回顾
     *
     * @param id
     */
    function singleven(id,alarmType,vehicleId) {
        $("#alarmEventId").val(id);
        var alarmTypeCode = alarmTypeResCode(alarmType);
        alarmEvenId = id;
        mylayer.openMax("${base.nimpUrl}/singleEventReview?alarmId="+id+"&alarmType="+alarmTypeCode+"&vehicleId="+vehicleId,'<@base.spring.message  "alarm.review"/>',);
    }

    /**
     * 点击报警类型 弹出窗口
     *
     * @param id
     */
    function alarmType(alarmId,alarmType) {
        // alert(alarmType);
        var alarmTypeCode = alarmTypeResCode(alarmType);
        var url = '${base.basePath}/authc/alarmTypePage?alarmId='+alarmId+"&alarmType="+alarmTypeCode;
        mylayer.open(url,'<@base.spring.message "alarm.alarmType"/>','930px!important','680px',[0.1,"#000"]);
    }

    function alarmTypeResCode(alarmType) {
        if(alarmType == "分心二类（含吸烟）")
            return 1;
        if(alarmType == "打哈欠")
            return 2;
        if(alarmType == "闭眼")
            return 3;
        if(alarmType == "分心一类（含打电话）")
            return 4;
        return 5;
    }

    /**
     * 点击报警地点 弹出窗口
     *
     * @param id
     */
    function alarmRegin(locationId) {
        var url = '${base.basePath}/authc/alarmReginPage?locationId='+locationId;
        // mylayer.open(url,'','380px!important','220px',[0.1,"#000"]);

        layer.open({
            title: ''
            ,type: 2
            ,shadeClose: true
            ,scrollbar: false
            ,anim: 5
            ,closeBtn: 0
            ,shade: '#000'
            ,area: ['400px!important','240px']
            ,content: url
        });
    }

    function createSelectId3(selectId,searchUrl,showField){
        var select=$(selectId);
        var form = layui.form;
        myajax.success=function (data) {
            console.log(data);
            select.html('<option value=""><@base.spring.message  "msg.choose" /></option>');
            $.each(data.data,function(index,value){
                select.append("<option value='" + value["alarmId"] + "'>" + value[showField] + "</option>");
            });
            form.render();
        };
        myajax.ajax(searchUrl,{});
    }

</script>
</body>
</html>