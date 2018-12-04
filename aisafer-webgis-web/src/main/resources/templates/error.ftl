
<!DOCTYPE html>
<html lang="en">
<head>
    <#include "ftl/common/head.ftl">
    <title>error.sys.error</title>

</head>
<style type="text/css">

    .box {
        text-align: center;
        height: 100%;
    }

    h5{
        font-size: 24px;
        width: 100%;
        color: #3C4455 ;
        align-self: center;
    }
</style>

<body>

<div class="box">
    <div style="margin-top: 5%"><img src="${imagesPath}/error.png"/></div>
        <#if (exception)??>
          <pre class="layui-code">${exception}</pre>
        <#else>
        <h5>error.sys.error</h5>
        </#if>
</div>
<script>
    layui.use('code', function(){
        layui.code();
    });
</script>
</body>
</html>

