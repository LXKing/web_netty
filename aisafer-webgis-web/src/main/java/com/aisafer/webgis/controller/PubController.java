package com.aisafer.webgis.controller;


import com.aisafer.psg.pcs.client.dto.AlarmItemReq;
import com.aisafer.psg.pcs.client.dto.AlarmItemRes;
import com.aisafer.psg.pcs.client.service.RAlarmItemService;
import com.aisafer.psg.user.client.dto.UserRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


/**
 * @Author:Bill
 * @Description:公开控制器
 * @Date:2018/6/1 10:03
 * @version:1.0
 **/
@Controller
public class PubController extends BaseController {

    @Autowired
    private RAlarmItemService rAlarmItemService;

    /**
     * 无权限
     * @return
     */
    @RequestMapping("/error_authc")
    public String errorAuthc(HttpServletRequest request, HttpServletResponse response) {
        if(isAjax(request)){
            try {
                response.setHeader("Content-Type", "text/json;charset=UTF-8");
                response.setStatus(200);
                PrintWriter pw=response.getWriter();
                pw.print("msg.noAccess");
                pw.flush();
                pw.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return "";
        }else{
            return "error_authc";
        }
    }

    /**
     * 获取报警类型
     *
     * @return
     */
    @RequestMapping("/authc/findAlarmTypes")
    @ResponseBody
    public String findAlarmTypes() {
        AlarmItemReq alarmItemReq = new AlarmItemReq();
        UserRes user = getUser();
        alarmItemReq.setOrgId(user.getOrgId());
        List<AlarmItemRes> alarmItems = rAlarmItemService.getAlarmItems(alarmItemReq);
        return setJsonMessage(true,getString("msg.searchSuccess"),alarmItems);
    }

}
