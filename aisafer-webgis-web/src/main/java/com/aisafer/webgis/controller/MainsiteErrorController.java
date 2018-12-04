package com.aisafer.webgis.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author:Bill
 * @Description:404
 * @Date:2018/6/11 16:06
 * @version:1.0
 **/
@Controller
public class
MainsiteErrorController implements ErrorController {

    private static final String ERROR_PATH = "/error";

    @RequestMapping(value=ERROR_PATH)
    public String handleError(){
        return "error_404";
    }

    @Override
    public String getErrorPath() {
        // TODO Auto-generated method stub
        return ERROR_PATH;
    }

}
