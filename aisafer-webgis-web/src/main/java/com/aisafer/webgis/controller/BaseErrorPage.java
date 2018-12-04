package com.aisafer.webgis.controller;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author:Bill
 * @Description:异常页面
 * @Date:2018/6/11 14:39
 * @version:1.0
 **/
@ControllerAdvice
public class BaseErrorPage   {
    private static final Logger logger = LoggerFactory.getLogger(BaseErrorPage.class);


    /**
     * Excepiton异常
     * @param e
     * @return
     */
    @ExceptionHandler({ Exception.class})
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView processException(HttpServletRequest request, HttpServletResponse response, Exception e) {
       return exception(request,response,e);
    }

    public ModelAndView exception(HttpServletRequest request, HttpServletResponse response,Exception e) {
        String errorMsg=e.getMessage(); //getString("error.sys.error");//系统异常
        String errorUrl = "error";
        ModelAndView mav = new ModelAndView(errorUrl);
        mav.addObject("exception",errorMsg);
        return mav;
    }

}
