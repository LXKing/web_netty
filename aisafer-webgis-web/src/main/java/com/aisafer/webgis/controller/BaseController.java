package com.aisafer.webgis.controller;


import com.aisafer.dpc.client.service.RLocationService;
import com.aisafer.dpc.client.service.RVehicleTrackService;
import com.aisafer.psg.pcs.client.dto.*;
import com.aisafer.psg.pcs.client.service.RBaseCodeService;
import com.aisafer.psg.pcs.client.service.RIndusTypeService;
import com.aisafer.psg.pcs.client.service.RRegionService;
import com.aisafer.psg.pcs.client.service.RVehicleTypeService;
import com.aisafer.psg.user.client.dto.UserReq;
import com.aisafer.psg.user.client.dto.UserRes;
import com.aisafer.psg.user.client.service.ROrganizationService;
import com.aisafer.psg.user.client.service.RUserService;
import com.aisafer.psg.vehicle.client.service.RVehicleService;
import com.aisafer.sso.config.SessionManager;
import com.aisafer.webgis.message.JsonMessage;
import com.aisafer.webgis.message.PageResultInfo;
import com.github.pagehelper.PageInfo;
import com.polaris.base.utils.date.DateUtils;
import com.polaris.base.utils.map.MapUtils;
import com.polaris.base.utils.string.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author:Bill
 * @Description:公共控制器
 * @Date:2018-10-26 20:08:37
 * @version:1.0
 **/
@Controller
public class BaseController {

    protected  String[] language={"zh","CN"};

    /** dubble提供操作数据字典的服务对象 */
    @Autowired
    protected RBaseCodeService rBaseCodeService;

    /** dubble提供操作组织的服务对象 */
    @Autowired
    protected ROrganizationService rOrganizationService;

    /** dubble提供操作用户的服务对象 */
    @Autowired
    protected RUserService userService;

    /** dubble提供操作行业的服务对象 */
    @Autowired
    protected RIndusTypeService indusTypeService;

    /** dubble提供操作地区的服务对象 */
    @Autowired
    protected RRegionService regionService;

    /** dubble提供操作车辆类型的服务对象 */
    @Autowired
    protected RVehicleTypeService vehicleTypeService;

    /** dubble提供车辆信息服务*/
    @Autowired
    protected RVehicleService rVehicleService;

    /** dubble提供车辆信息服务*/
    @Autowired
    protected RVehicleTrackService rVehicleTrackService;

    /** dubble提供车辆位置*/
    @Autowired
    protected RLocationService rLocationService;

    /**
     * 国际化
     * @param str
     * @return
     */
    protected String getString(String ... str){
        ResourceBundle resourceBundle;
        resourceBundle = ResourceBundle.getBundle("language.string",new Locale(language[0],language[1]));
        String home = resourceBundle.getString(str[0]);
        if(StringUtils.isEmpty(home)){
            if(str.length>1)
                home=str[1];
            else
                home=str[0];
        }else{
            /*try {
                home=new String(home.getBytes("ISO-8859-1"),"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }*/
        }
        return home;
    }

    /**
     * 获取登录用户
     *
     * @return
     */
    protected UserRes getUser() {
        UserRes res=null;
        Object object=SecurityUtils.getSubject().getSession().getAttribute("user");
        if(object!=null && object instanceof UserRes){
            res= (UserRes) object;
        }
        if(res==null){
            UserReq req=new UserReq();
            req.setLoginName(SessionManager.getAccount());
            res=userService.getUserByloginName(req);
            SecurityUtils.getSubject().getSession().setAttribute("user",res);
        }
        return res;
    }

    /**
     * 数据转换
     * @param object
     */
    protected void convert(Object object){
        HashMap<String, Object> hasMap=null;
        if(object instanceof Map)
            hasMap = (HashMap<String, Object>) object;
        else
            hasMap= (HashMap) MapUtils.toMap(object);
        // 组织类型转换
        if(hasMap.containsKey("orgType")){
            Integer orgType = (Integer) hasMap.get("orgType");
            BaseCodeReq req = new BaseCodeReq();
            req.setId(orgType);
            BaseCodeRes baseCodeRes = rBaseCodeService.getBaseCodeById(req);
            String baseCode = "";
            if(baseCodeRes != null){
                baseCode = baseCodeRes.getCode();
            }
            hasMap.put("orgCode",baseCode);
        }
    }

    /**
     * 当地时间转格林时间
     *
     * @return
     */
    protected String dateConvert(String dateStr) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = simpleDateFormat.parse(dateStr);
        Date date = DateUtils.getDate(parse,-8);// 北京时间转格林时间

        return DateUtils.convertToString(date,DateUtils.YYYYMMDDHHMMSS_W_C);
    }

    private UserRes getUserResById(long userId) {
        UserReq userReq = new UserReq();
        userReq.setId(userId);
        return userService.getUserById(userReq);
    }

    /**
     * 根据数据字典ID取值
     *
     * @param id
     * @return
     */
    private String convertId(int id){
        BaseCodeReq req=new BaseCodeReq(id);
        BaseCodeRes res=rBaseCodeService.getBaseCodeById(req);
        return convertBaseCodeReq(res);
    }
    /**
     * 根据数据字典Code和Type取值
     *
     * @param code
     * @return
     */
    private String convertCode(String code,String type){
        BaseCodeReq req=new BaseCodeReq();
        req.setCode(code);
        req.setType(type);
        BaseCodeRes res = rBaseCodeService.getBaseCodeByTypeAndCode(req);
        return convertBaseCodeReq(res);
    }

    /**
     * 国际化取数据字典的值
     *
     * @param res
     * @return
     */
    private String  convertBaseCodeReq(BaseCodeRes res){
        if(res!=null) {
            if ("zh".equals(language[0]))
                return res.getName();
            else
                return res.getCode();
        }else
            return "";
    }

    protected String setJsonMessage(boolean sussess, String messgae,Object data) {
        JsonMessage jsonMessage=new JsonMessage();
        jsonMessage.setSuccess(sussess);
        jsonMessage.setMessage(messgae);
        jsonMessage.setData(data);
        return com.alibaba.fastjson.JSON.toJSONString(jsonMessage);
    }

    protected String setJsonMessage(boolean sussess, String messgae) {
        return setJsonMessage(sussess,messgae,null);
    }

    protected String setJsonMessage(JsonMessage  jsonMessage) {
        return com.alibaba.fastjson.JSON.toJSONString(jsonMessage);
    }


    /**
     * 是否为超级用户
     * @return
     */
    protected boolean isSa(){
        boolean isSa=false;
        UserRes user=getUser();
        if(user!=null){
            if(user.getRoles().contains("sa"))
                isSa=true;
        }
        return isSa;
    }

    /**
     * 判断ajax请求
     * @param request
     * @return
     */
    protected boolean isAjax(HttpServletRequest request){
        return  (request.getHeader("X-Requested-With") != null  && "XMLHttpRequest".equals( request.getHeader("X-Requested-With").toString())) ;
    }

    //分页
    protected String setPageResultInfo(boolean success, String msg, long count ,long pageSize,Object data)
    {
        PageResultInfo pageResultInfo = new PageResultInfo();
        pageResultInfo.setCode(success?0:1);
        pageResultInfo.setMsg(msg);
        pageResultInfo.setCount(count);
        pageResultInfo.setData(data);
        pageResultInfo.setPageSize(pageSize);
        return com.alibaba.fastjson.JSON.toJSONString(pageResultInfo);
    }
    protected String setPageResultInfo(boolean success, String msg, PageInfo<T> pageInfo)
    {
        return setPageResultInfo(success,msg,pageInfo.getTotal(),pageInfo.getPageSize(),pageInfo.getList());
    }
    protected String setPageResultInfo(boolean success, String msg)
    {
        PageResultInfo pageResultInfo = new PageResultInfo();
        pageResultInfo.setCode(success?0:1);
        pageResultInfo.setMsg(msg);
        return com.alibaba.fastjson.JSON.toJSONString(pageResultInfo);
    }


    /**
     * 修改方向描述
     *
     * @return
     */
    protected String changeDirect(String oldDirect) {
        if(oldDirect == null || "".equals(oldDirect))
            return "";

        if("DueEast".equals(oldDirect))
            return getString("dueEast");

        if("DueNorth".equals(oldDirect))
            return getString("dueNorth");

        if("DueWest".equals(oldDirect))
            return getString("dueWest");

        if("DueSouth".equals(oldDirect))
            return getString("dueSouth");

        if("Northeast".equals(oldDirect))
            return getString("northeast");

        if("Northwest".equals(oldDirect))
            return getString("northwest");

        if("Southwest".equals(oldDirect))
            return getString("southwest");

        if("Southeast".equals(oldDirect))
            return getString("southeast");

        if(oldDirect.startsWith("EastByNorth")) {
            String newDirect = oldDirect.replace("EastByNorth",getString("eastByNorth"));
            return  newDirect.replace("Degrees",getString("degrees"));
        }

        if(oldDirect.startsWith("WestByNorth")) {
            String newDirect = oldDirect.replace("WestByNorth",getString("westByNorth"));
            return  newDirect.replace("Degrees",getString("degrees"));
        }

        if(oldDirect.startsWith("WestBySouth")) {
            String newDirect = oldDirect.replace("WestBySouth",getString("westBySouth"));
            return  newDirect.replace("Degrees",getString("degrees"));
        }

        if(oldDirect.startsWith("EastBySouth")) {
            String newDirect = oldDirect.replace("EastBySouth",getString("eastBySouth"));
            return  newDirect.replace("Degrees",getString("degrees"));
        }

        return "";
    }

}
