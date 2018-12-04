var Vsocket={};
//var address="ws://192.168.200.113:9088";
var address="ws://47.99.202.127:9088";
//var address="ws://192.168.200.168:9088";
Vsocket.webSocket;
//初始化
Vsocket.init=function () {
    Vsocket.webSocket = new WebSocket(address);
}
//连接
Vsocket.connect=function (msg) {
    Vsocket.webSocket.onopen = function(evt) {  //绑定连接事件
        var commandStr = JSON.stringify(msg);
        Vsocket.webSocket.send(commandStr);
    };
}
//读消息
Vsocket.readMsg=function (fnc) {
    Vsocket.webSocket.onmessage = function(evt) {
        fnc(evt);
    }
}

//断开监听
Vsocket.colseListener=function (fnc) {
    Vsocket.webSocket.onclose = function(evt) { //绑定关闭或断开连接事件
        fnc(evt);
    };
}


