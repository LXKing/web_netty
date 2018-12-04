package com.aisafer.webgis.controller.authc;

import com.aisafer.base.redis.IRedis;
import com.aisafer.dpc.client.dto.LocationInfo;
import com.aisafer.psg.user.client.dto.*;
import com.aisafer.psg.vehicle.client.dto.VehicleDataRes;
import com.aisafer.sso.config.SessionManager;
import com.aisafer.webgis.controller.BaseController;
import com.aisafer.webgis.message.VehicleTree;
import com.aisafer.webgis.utils.PipelineUtils;
import com.aisafer.webgis.utils.VehicleTreeUtil;
import com.alibaba.fastjson.JSON;
import com.polaris.base.utils.date.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.*;

import javax.annotation.Resource;
import java.util.*;

@Controller
@RequestMapping("authc")
public class WebgisController extends BaseController {
    /** 日志 */
    protected static final Logger LOGGER = LoggerFactory.getLogger(WebgisController.class);

    /** redis操作对象 */
    @Resource(name = "redisMultipleFactory")
    private IRedis redis;

    @Value("${children.orgs}")
    private String CHILDREN_ORGS;

    @Value("${children.vehicles}")
    private String CHILDREN_VEHICLES;

    @Resource
    private PipelineUtils pipelineUtils;

    @Controller
    @RequestMapping("authc")
    class view{

        @RequestMapping("main")
        public ModelAndView webgisPage() {

            ModelAndView mv =new ModelAndView("ftl/main");
            UserReq req=new UserReq();
            req.setLoginName(SessionManager.getAccount());
            UserRes user=userService.getUserByloginName(req);
            mv.addObject("user",user);
            return mv;
        }

    }

    /**
     * 获取组织树
     * @param userId
     * @return
     */
    @RequestMapping(value="/getUserOrgTree",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String getUserOrgTree(@RequestParam Long userId){
        UserRes user = getUser();
        if (user==null) {
            return setJsonMessage(false,getString("msg.searchFailed"));
        }
        try {
            if(userId == null){
                return setJsonMessage(false,getString("msg.searchFailed"));
            }
            Map org = userService.getUserOrg(userId);
            if(org != null){
                convert(org);
                Long orgId = (Long) org.get("orgId");
                String orgCode = (String) org.get("orgCode");
                if(!"fleet".equals(orgCode)){
                    List<Map> orgList = userService.getChildListByParentId(orgId);
                    org.put("orgList",orgList);
                }
            }
            return setJsonMessage(true,getString("msg.searchSuccess"),org);
        }catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(getString("msg.searchFailed")+e.getMessage());
            return setJsonMessage(false,getString("msg.searchFailed"));
        }
    }

    /**
     * 获取组织下对应的车队
     * @param orgId
     * @return
     */
    @RequestMapping(value="/getVehicleList",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String getVehicleList(@RequestParam Long orgId){
        if(orgId == null){
            return setJsonMessage(false,getString("msg.searchFailed"));
        }
        try {
            UserRes user = getUser();
            if (user==null) {
                return setJsonMessage(false,getString("msg.searchFailed"));
            }
            //List<Map> resultList = new ArrayList<>();
            /*OrganizationReq req = new OrganizationReq();
            req.setId(orgId);
            OrganizationRes org = rOrganizationService.getOrganizationById(req);
            VehicleTree vehicleTreeNode = VehicleTreeUtil.createVehicleTreeNode(String.valueOf(org.getId()), "", org.getOrgName());
            vehicleTreeNode.setTotal(0);
            vehicleTreeNode.setOnline(0);*/
            long startTime = System.currentTimeMillis();
            VehicleTree vehicleTreeNode = getOrgVehicleTree(orgId);
            /*getChildOrgListAndVehicleList(orgId,vehicleTreeNode,pipeline);
            pipeline.close();
            Integer online = vehicleTreeNode.getOnline();
            Integer total = vehicleTreeNode.getTotal();
            String treeName = vehicleTreeNode.getTip() + "(" + online + "/" + total + ")";
            vehicleTreeNode.setName(treeName);*/
            long endTime = System.currentTimeMillis();
            LOGGER.info("加载车辆树查询时间为：{} S",(endTime - startTime));

            return setJsonMessage(true,getString("msg.searchSuccess"),vehicleTreeNode);
        }catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(getString("msg.searchFailed")+e.getMessage());
            return setJsonMessage(false,getString("msg.searchFailed"));
        }
    }

    /**
     * 获取组织车辆树
     * @param orgId
     * @return
     */
    public VehicleTree getOrgVehicleTree(Long orgId) throws Exception{
        Map<String,VehicleTree> treeMap = new HashMap<>();
        //获取自己所属组织和所有下属组织
        List<OrganizationRes> allChildList = rOrganizationService.getAllChildList(orgId);
        LOGGER.info("查询组织数量为："+allChildList.size());
        //获取所有车辆信息
        List<VehicleDataRes> allVehicleList = rVehicleService.getAllVehicleList();
        LOGGER.info("查询车辆数量为："+allVehicleList.size());
        //封装组织树map
        for(OrganizationRes org : allChildList){
            VehicleTree treeNode = VehicleTreeUtil.
                    createVehicleTreeNode(String.valueOf(org.getId().longValue()),"", org.getOrgName(),String.valueOf(org.getParentId().longValue()));
            treeNode.setTotal(0);
            treeNode.setOnline(0);
            treeNode.setName(treeNode.getTip()+"("+treeNode.getOnline()+"/"+treeNode.getTotal()+")");
            treeMap.put(Long.toString(org.getId().longValue()),treeNode);
        }
        //redis通道批量查询车辆状态
        Pipeline pipeline = pipelineUtils.getPipeline();
        Map<String, Response<String>> responseMap = new HashMap<>();
        for(VehicleDataRes vehicle : allVehicleList) {
            String simNo = vehicle.getSimNo();
            String key = "TerminalStatus_" + simNo;
            responseMap.put(key, pipeline.get(key));
        }
        pipeline.sync();
        //将部门下的车辆封装进组织树节点
        for(VehicleDataRes vehicle : allVehicleList){
            if(vehicle.getDeptId() != null){
                VehicleTree treeNode = treeMap.get(Long.toString(vehicle.getDeptId().longValue()));
                if(treeNode!=null) {
                    List<VehicleTree> children = treeNode.getChildren();
                    if (children == null) {
                        children = new ArrayList<VehicleTree>();
                    }
                    String simNo = vehicle.getSimNo();
                    Response<String> stringResponse = responseMap.get("TerminalStatus_" + simNo);
                    String str = stringResponse.get();
                    Map maps = (Map) JSON.parse(str);
                    Integer status = 0;
                    if(maps != null && maps.get("state") != null){
                        status = (Integer)maps.get("state");
                    }
                    VehicleTree childNode = VehicleTreeUtil.
                            createVehicleTreeNode(simNo, vehicle.getPlateNo(), status, vehicle.getSimNo(), String.valueOf(vehicle.getDeptId()));

                    children.add(childNode);
                    treeNode.setChildren(children);
                    if(status>0){
                        treeNode.setOnline(treeNode.getOnline()+1);
                    }
                    treeNode.setTotal(treeNode.getTotal()+1);
                    treeNode.setName(treeNode.getTip()+"("+treeNode.getOnline()+"/"+treeNode.getTotal()+")");

                    upDateTolal(treeMap,treeNode,status>0);
                }

            }
        }
        //将子部门放进父部门节点中
        for(OrganizationRes org : allChildList){
            VehicleTree treeNode = treeMap.get(Long.toString(org.getId().longValue()));
            VehicleTree parentNode = null;
            if(org.getParentId()!=null){
                parentNode= treeMap.get(Long.toString( org.getParentId().longValue()));
                if(parentNode != null){
                    if( parentNode.getChildren() == null) {
                        List<VehicleTree> children  = new ArrayList<VehicleTree>();
                        children.add(treeNode);
                        parentNode.setChildren(children);
                    }else{
                        parentNode.getChildren().add(treeNode);
                    }
                }
            }

        }
        pipeline.close();

        return treeMap.get(Long.toString(orgId.longValue()));
    }

    private void upDateTolal( Map<String,VehicleTree> treeMap ,VehicleTree treeNode,boolean online){
            VehicleTree parentNode = treeMap.get(treeNode.getParentId());
            if (parentNode != null) {
                if(online){
                    parentNode.setOnline(parentNode.getOnline() + 1);
                }
                parentNode.setTotal(parentNode.getTotal() + 1);
                parentNode.setName(parentNode.getTip() + "(" + parentNode.getOnline() + "/" + parentNode.getTotal() + ")");

                  upDateTolal(treeMap,parentNode,online);
            }
    }

    /**
     * 获取车队列表
     * @param orgIds
     * @return
     */
    @RequestMapping(value ="getVehicleListByIds",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String getVehicleListByIds(@RequestParam Long[] orgIds){
        if(orgIds == null || orgIds.length == 0){
            return setJsonMessage(false,getString("msg.searchFailed"));
        }
        try {
            UserRes user = getUser();
            if (user == null) {
                return setJsonMessage(false, getString("msg.searchFailed"));
            }
            List<Long> idList = new ArrayList<>();
            for(Long id : orgIds){
                if(id != null){
                    idList.add(id);
                }
            }
            List<Map> orgList = rOrganizationService.getOrgByOrgIds(idList);
            List<Map> vehicleList = rVehicleService.getVehicleByOrgIds(idList);
            Map<Long,List<Map>> vehicleMap = new HashMap<>();
            List<Map> mapList;
            if(vehicleList != null && vehicleList.size() > 0){
                for(Map map : vehicleList){
                    Long orgId = (Long) map.get("orgId");
                    if(vehicleMap.get(orgId) != null){
                        mapList = vehicleMap.get(orgId);
                    }else{
                        mapList = new ArrayList<>();
                    }
                    mapList.add(map);
                    vehicleMap.put(orgId,mapList);
                }
            }
            List<Map> result = new ArrayList<>();
            for(Map org : orgList){
                Long orgId = (Long) org.get("id");
                if(vehicleMap.get(orgId) != null){
                    mapList = vehicleMap.get(orgId);
                }else{
                    mapList = new ArrayList<>();
                }
                org.put("vehicleList",mapList);
                result.add(org);
            }
            return setJsonMessage(true,getString("msg.searchSuccess"),result);
        }catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(getString("msg.searchFailed")+e.getMessage());
            return setJsonMessage(false,getString("msg.searchFailed"));
        }
    }

    /**
     * 递归获取下属组织和车辆
     * @return
     */
    private void getChildOrgListAndVehicleList(Long orgId,VehicleTree parentVehicleTree,Pipeline pipelined){
        // 获取下级车队
        List<Map> childOrgList = redis.get(CHILDREN_ORGS + orgId,List.class);
        if(childOrgList == null){
            childOrgList = userService.getChildList(orgId);
        }
        for(Map child : childOrgList){
            Long childId = Long.parseLong(String.valueOf(child.get("orgId")));
            VehicleTree childVehicleTree = VehicleTreeUtil.createVehicleTreeNode(String.valueOf(child.get("orgId")), "", String.valueOf(child.get("orgName")));
            childVehicleTree.setTotal(0);
            childVehicleTree.setOnline(0);
            getChildOrgListAndVehicleList(childId, childVehicleTree,pipelined);
            Integer childTotal = childVehicleTree.getTotal();
            Integer childOnline = childVehicleTree.getOnline();
            parentVehicleTree.setTotal(parentVehicleTree.getTotal() + childTotal);
            parentVehicleTree.setOnline(parentVehicleTree.getOnline() + childOnline);
            String treeName = childVehicleTree.getTip() + "(" + childOnline + "/" + childTotal + ")";
            childVehicleTree.setName(treeName);
            if(parentVehicleTree.getChildren() == null || parentVehicleTree.getChildren().size() < 1) {
                List<VehicleTree> vehicleTrees = new ArrayList<>();
                vehicleTrees.add(childVehicleTree);
                parentVehicleTree.setChildren(vehicleTrees);
            }else {
                parentVehicleTree.getChildren().add(childVehicleTree);
            }
        }

        // redis获取车队下所有车辆
        List<Map> vehicleList = redis.get(CHILDREN_VEHICLES + orgId, List.class);
        if(vehicleList == null){
            vehicleList = rVehicleService.getVehicleByOrgId(orgId);
        }
        //redis通道批量查询车辆状态
        Map<String, Response<String>> responseMap = new HashMap<>();
        for(Map vehicle : vehicleList) {
            String simNo = String.valueOf(vehicle.get("simNo"));
            String key = "TerminalStatus_" + simNo;
            responseMap.put(key, pipelined.get(key));
        }
        pipelined.sync();
        // 将车辆封装为树节点 加入为子节点
        for(Map vehicle : vehicleList) {
            String simNo = String.valueOf(vehicle.get("simNo"));
            String plateNo = String.valueOf(vehicle.get("plateNo"));
            Response<String> stringResponse = responseMap.get("TerminalStatus_" + simNo);
            String str = stringResponse.get();
            Map maps = (Map) JSON.parse(str);
            Integer status = 0;
            if(maps != null && maps.get("state") != null){
                status = (Integer)maps.get("state");
            }
            parentVehicleTree.setTotal(parentVehicleTree.getTotal() + 1);
            if(status == 1){
                parentVehicleTree.setOnline(parentVehicleTree.getOnline() + 1);
            }
            VehicleTree vehicleTree1 = VehicleTreeUtil.createVehicleTreeNode(simNo, plateNo,status,simNo);
            if(parentVehicleTree.getChildren() == null || parentVehicleTree.getChildren().size() < 1) {
                List<VehicleTree> vehicleTrees = new ArrayList<>();
                vehicleTrees.add(vehicleTree1);
                parentVehicleTree.setChildren(vehicleTrees);
            }else {
                parentVehicleTree.getChildren().add(vehicleTree1);
            }
        }
    }

    /**
     * 获取车辆坐标信息
     * @param plateNo
     * @return
     */
    @RequestMapping(value="/getVehicleLocation",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String getVehicleLocation(@RequestParam String plateNo){
        if(plateNo == null){
            return setJsonMessage(false,getString("msg.searchFailed"));
        }
        try {
            UserRes user = getUser();
            if (user==null) {
                return setJsonMessage(false,getString("msg.searchFailed"));
            }
            LocationInfo location = rLocationService.getLocationByPlateNo(plateNo);
            if(location!=null)
                return setJsonMessage(true, getString("msg.searchSuccess"), location);
            else
                return setJsonMessage(false,getString("msg.searchFailed"));
        }catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(getString("msg.searchFailed")+e.getMessage());
            return setJsonMessage(false,getString("msg.searchFailed"));
        }
    }

    /**
     * 获取车辆位置信息
     * @param id
     * @return
     */
    @RequestMapping(value="/getVehicleLocationDetail",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String getVehicleLocationDetail(@RequestParam Long id){
        if(id == null){
            return setJsonMessage(false,getString("msg.searchFailed"));
        }
        try {
            UserRes user = getUser();
            if (user==null) {
                return setJsonMessage(false,getString("msg.searchFailed"));
            }
            Map location = rVehicleTrackService.getVehicleLocationDetail(id);
            return setJsonMessage(true,getString("msg.searchSuccess"),location);
        }catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(getString("msg.searchFailed")+e.getMessage());
            return setJsonMessage(false,getString("msg.searchFailed"));
        }
    }
}
