<#import "base.ftl" as base/>
    <meta charset="UTF-8">
     <link rel="stylesheet" href="${base.layuiPath}/css/layui.css" media="all">
    <link rel="stylesheet" href="${base.layui_cssPath}/doc.css" media="all">
    <link rel="stylesheet" href="${base.layui_cssPath}/main.css">

    <script src="${base.jqueryPath}"></script>
    <script src="${base.layui_jsPath}/main.js"></script>
    <script src="${base.layuiPath}/layui.all.js"></script>
    <script src="${base.layui_jsPath}/pagedataHandel.js"></script>
    <script src="${base.layui_jsPath}/mylayer.js"></script>
    <script src="${base.layui_jsPath}/myajax.js"></script>
    <script src="${base.ztreePath}/js/jquery.ztree.all.min.js"></script>
    <script>
        mylayer.resPath="${base.resPath}";

       /* /!**
         *  动态加载下拉选择框
         *  @Param selectId 下拉框的ID  #id
         *  @Param searchUrl 查询数据的路径  如果为数据字典 可以为空
         *  @Param showField 显示字段的属性名
         *  @Param selectedValue 默认值
         *  @Param dictType searchUrl为空时，字典类型
         **!/
        function createSelectId2(selectId,searchUrl,showField,selectedValue,dictType){
            var select=$(selectId);
            var form = layui.form;
            myajax.success=function (data) {
                console.log(data);
                select.html('<option value=""><@base.spring.message  "msg.choose" /></option>');
                $.each(data.data,function(index,value){
                    if (selectedValue == value.id) {
                        select.append("<option value='" + value.id + "' selected='selected'>" + value[showField] + "</option>");
                    }else {
                        select.append("<option value='" + value.id + "'>" + value[showField] + "</option>");
                    }
                });
                form.render();
            };
            if(searchUrl !== null && searchUrl != ""){
                myajax.ajax(searchUrl,{});
            }else {
                myajax.ajax("${base.basePath}/getBaseCodesByType",{type:dictType});
            }
        }

        /!**
         * 创建数据字典下拉框 值为code
         *
         *!/
        function createSelectCode(selectId,selectedValue,dictType){
            var select=$(selectId);
            var form = layui.form;
            myajax.success=function (data) {
                console.log(data);
                select.html('<option value=""><@base.spring.message  "msg.choose" /></option>');
                $.each(data.data,function(index,value){
                    if (selectedValue == value.code) {
                        select.append("<option value='" + value.code + "' selected='selected'>" + value.name + "</option>");
                    }else {
                        select.append("<option value='" + value.code + "'>" + value.name + "</option>");
                    }
                });
                form.render();
            };
            myajax.ajax("${base.basePath}/getBaseCodesByType",{type:dictType});
        }

        /!**
         * 关闭窗口
         *
         *!/
        var closeOwnerWindow = function () {
            console.log("guanbichuangkou");
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        }*/

        /**
         * 格式化时间  毫秒值转字符串
         *
         * @param time
         * @param format
         * @returns {string}
         */
        function formatDate(time,format='yyyy-MM-dd HH:mm:ss'){
            if(time == null)
                return null;

            var date = new Date(time);
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
         * 格式化日期时间控件
         */
        function createDateTime(minTime,maxTime,defaultTime,doneFun,timeId,dateType) {
            mylayer.dateType=dateType;
            mylayer.done=doneFun;
            mylayer.min=minTime;
            mylayer.max=maxTime;
            mylayer.value=defaultTime;
            mylayer.date(timeId);
        }

    </script>