package com.aisafer.webgis.controller.authc;

import com.aisafer.base.redis.IRedis;
import com.aisafer.psg.user.client.dto.OrganizationReq;
import com.aisafer.psg.user.client.dto.OrganizationRes;
import com.aisafer.psg.user.client.dto.UserReq;
import com.aisafer.psg.user.client.dto.UserRes;
import com.aisafer.sso.config.SessionManager;
import com.aisafer.webgis.controller.BaseController;
import com.aisafer.webgis.message.VehicleTree;
import com.aisafer.webgis.utils.PipelineUtils;
import com.aisafer.webgis.utils.VehicleTreeUtil;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("authc")
public class IndexController extends BaseController {
    /** 日志 */
    protected static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    @Controller
    @RequestMapping("authc")
    class view{

        @RequestMapping("index")
        public ModelAndView index() {

            ModelAndView mv =new ModelAndView("ftl/index/index");
            UserReq req=new UserReq();
            req.setLoginName(SessionManager.getAccount());
            UserRes user=userService.getUserByloginName(req);
            mv.addObject("user",user);
            return mv;
        }
        @RequestMapping("chartLine")
        public String chartLine() {

            return "ftl/index/chartLine";
        }
        @RequestMapping("map")
        public String map() {

            return "ftl/index/map";
        }

    }
}
