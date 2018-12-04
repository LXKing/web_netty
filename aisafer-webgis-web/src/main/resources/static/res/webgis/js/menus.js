var menus= {};
menus.takingPictures=function (plateNo) {
    var url="http://main.aisafer.com/vehicle/authc/toPhotograph?plateNo="+plateNo;
    mylayer.open(url,plateNo,'455px!important','480px',[0.1,'#000']);
}