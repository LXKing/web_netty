package com.aisafer.webgis.utils;

import com.aisafer.webgis.message.VehicleTree;

/**
 * 封装车队车辆树工具类
 *
 * @Author:weiyuanlong
 * @Date: Created in 2018-10-30 16:38:11
 * @Modified By:
 */
public class VehicleTreeUtil {

    /**
     * 封装车辆树节点
     *
     * @param treeId
     * @param treeCode
     * @param treeName
     * @return
     */
    public static VehicleTree createVehicleTreeNode(String treeId, String treeCode, String treeName) {
        VehicleTree vehicleTree = new VehicleTree();
        vehicleTree.setId(treeId);
        vehicleTree.setCode(treeCode);
        vehicleTree.setName(treeName);
        vehicleTree.setStatus(0);
        vehicleTree.setOpen(true);
        vehicleTree.setTip(treeName);
        return vehicleTree;
    }

    /**
     * 封装车辆树节点
     *
     * @param treeId
     * @param treeName
     * @param status
     * @param simNo
     * @return
     */
    public static VehicleTree createVehicleTreeNode(String treeId, String treeName, Integer status,String simNo) {
        VehicleTree vehicleTree = new VehicleTree();
        vehicleTree.setId(treeId);
        vehicleTree.setName(treeName);
        vehicleTree.setStatus(status);
        vehicleTree.setSim(simNo);
        vehicleTree.setOpen(true);
        vehicleTree.setTip(treeName);
        return vehicleTree;
    }

    public static VehicleTree createVehicleTreeNode(String treeId,String treeCode, String treeName,String parentId){
        VehicleTree vehicleTree = new VehicleTree();
        vehicleTree.setId(treeId);
        vehicleTree.setCode(treeCode);
        vehicleTree.setName(treeName);
        vehicleTree.setStatus(0);
        vehicleTree.setOpen(true);
        vehicleTree.setTip(treeName);
        vehicleTree.setParentId(parentId);
        return vehicleTree;
    }

    public static VehicleTree createVehicleTreeNode(String treeId, String treeName, Integer status,String simNo,String parentId) {
        VehicleTree vehicleTree = new VehicleTree();
        vehicleTree.setId(treeId);
        vehicleTree.setName(treeName);
        vehicleTree.setStatus(status);
        vehicleTree.setSim(simNo);
        vehicleTree.setOpen(true);
        vehicleTree.setTip(treeName);
        vehicleTree.setParentId(parentId);
        return vehicleTree;
    }
}
