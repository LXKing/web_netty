package com.aisafer.webgis.controller.authc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("map")
public class MapController {

    @RequestMapping("baidu")
    public  String baidu() {

        return "ftl/map/baidu";
    }
    @RequestMapping("gaode")
    public  String gaode() {

        return "ftl/map/gaode";
    }
}
