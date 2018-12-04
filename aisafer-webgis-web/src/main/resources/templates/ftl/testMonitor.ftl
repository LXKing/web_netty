<#import "common/base.ftl" as base/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>测试ftl</title>
</head>
<body>
'你好'
<@base.spring.message "test.msg"/>
</body>
<script>

    var testSendMsg = {
        msgId : "authcReceiveHandle",
        simNo : "94804634492",
        userId : 1,
        content : "消息体"
    }

    if(window.WebSocket){
        console.log("WebSocket")
        var ws = new WebSocket("ws://192.168.200.113:9088");

        ws.onopen = function(evt) {  //绑定连接事件
            console.log("Connection open ...");
            var commandStr = JSON.stringify(testSendMsg);
            ws.send(commandStr);
        };

        ws.onmessage = function(evt) {//绑定收到消息事件
            // console.log( "Received Message: " + evt.data);
            var messageBody = JSON.parse(evt.data);
            var content = messageBody.content;
            // console.log(messageBody.msgCode);
            if(messageBody.msgCode == 100) {
                // 鉴权结果
                console.log("鉴权结果为 ： " + messageBody.message);
            }else if(messageBody.msgCode == 101) {
                // 车辆上线
                console.log("车辆上线 车牌号为 ： " + content.plateNo + " 车辆ID为 ： " + content.vehicleId)
            }else if(messageBody.msgCode == 102) {
                // 车辆下线
                console.log("车辆下线 车牌号为 ： " + content.plateNo + " 车辆ID为 ： " + content.vehicleId)
            }else if(messageBody.msgCode == 104) {
                // 报警
                console.log("车辆报警 车牌号为 ： " + content.plateNo + " 车辆ID为 ： " + content.vehicleId)
            }else if(messageBody.msgCode == 103) {
                // 位置信息
                console.log("车辆位置信息 车牌号为 ： " + content.plateNo + " 车辆ID为 ： " + content.vehicleId + " 位置为 ：" + content.latitude + " " + content.longitude + " 速度为 ：" + content.speed)
            }else {
                console.log("其他")
            }

        };

        ws.onclose = function(evt) { //绑定关闭或断开连接事件
            console.log("Connection closed.");
        };
    }

</script>
</html>