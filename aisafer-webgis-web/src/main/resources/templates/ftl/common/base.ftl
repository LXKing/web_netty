<#import "spring.ftl" as spring />
<#assign basePath="${request.contextPath}" />

<#assign resPath="http://resource.aisafer.com">
<!-- 静态资源引用 -->
<#assign cssPath="${basePath}/res/webgis/css">
<#assign imagesPath="${basePath}/res/webgis/img">
<#assign jsPath="${basePath}/res/webgis/js">


<#assign indexcssPath="${basePath}/res/webgis/index/css">
<#assign indeximagesPath="${basePath}/res/webgis/index/images">
<#assign indexjsPath="${basePath}/res/webgis/index/js">

<#assign carPath="${basePath}/res/webgis/car">
<#assign voicePath="${basePath}/res/webgis/voice">

<#assign fmsUrl="http://fms.aisafer.com/fms/attach/view/v1/?urlExpire=36001&sk=&sn=&attachid" />
<#assign mainPath="http://main.aisafer.com/index">
<#assign vehiclePath="http://main.aisafer.com/vehicle/authc">

<#assign layui_cssPath="${resPath}/res/css">
<#assign layui_imagesPath="${resPath}/res/images">
<#assign layui_jsPath="${resPath}/res/js">
<#assign layuiPath="${resPath}/res/layui/layui-v2.2.6">
<#assign nimpUrl="http://main.aisafer.com/nimp" />

<#--<#assign mainPath="http://localhost:2080/index">
<#assign vehiclePath="http://localhost:2085/vehicle/authc">-->

<#assign jqueryPath="${resPath}/res/js/jquery-1.10.2.min.js"/>
<#assign ztreePath="${resPath}/res/ztree/ztree-v3.5.26"/>

