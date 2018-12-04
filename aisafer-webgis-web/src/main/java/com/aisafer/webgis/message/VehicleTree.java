package com.aisafer.webgis.message;

import java.io.Serializable;
import java.util.List;

/**
 * @Author:Bill
 * @Description:
 * @Date:2018/6/14 10:40
 * @version:1.0
 **/
public class VehicleTree implements Serializable {

    private static final long serialVersionUID = 7727997624493532259L;
    /**
     * 节点的 checkBox / radio 的 勾选状态。[setting.check.enable = true & treeNode.nocheck = false 时有效]
     1、如果不使用 checked 属性设置勾选状态，请修改 setting.data.key.checked
     2、建立 treeNode 数据时设置 treeNode.checked = true 可以让节点的输入框默认为勾选状态
     3、修改节点勾选状态，可以使用 treeObj.checkNode / checkAllNodes / updateNode 方法，具体使用哪种请根据自己的需求而定
     4、为了解决部分朋友生成 json 数据出现的兼容问题, 支持 "false","true" 字符串格式的数据
     默认值：false
     */
    private boolean checked;

    /**
     * 节点的子节点数据集合。
     1、如果不使用 children 属性保存子节点数据，请修改 setting.data.key.children
     2、异步加载时，对于设置了 isParent = true 的节点，在展开时将进行异步加载
     默认值：无
     */
    private List<VehicleTree> children;

    /**
     * 1、设置节点的 checkbox / radio 是否禁用 [setting.check.enable = true 时有效]
     2、为了解决部分朋友生成 json 数据出现的兼容问题, 支持 "false","true" 字符串格式的数据
     3、请勿对已加载的节点修改此属性，禁止 或 取消禁止 请使用 setChkDisabled() 方法
     4、初始化时，如果需要子孙节点继承父节点的 chkDisabled 属性，请设置 setting.check.chkDisabledInherit 属性
     默认值：false
     */
    private boolean chkDisabled;


    /**
     * 最简单的 click 事件操作。相当于 onclick="..." 的内容。 如果操作较复杂，请使用 onClick 事件回调函数。
     由于 IE 对于 onclick 和 click事件共存时的处理与其他浏览器不同，所以请不要利用此参数控制是否允许跳转的操作（例如：treeNode.click = "return false;"）。如有类似需求，请不要使用 url 属性设置网址，同时利用 onClick 回调函数控制跳转。
     默认值：无
     */
    private String click;

    /**
     * 强制节点的 checkBox / radio 的 半勾选状态。[setting.check.enable = true & treeNode.nocheck = false 时有效]
     1、强制为半勾选状态后，不再进行自动计算半勾选状态
     2、设置 treeNode.halfCheck = false 或 null 才能恢复自动计算半勾选状态
     3、为了解决部分朋友生成 json 数据出现的兼容问题, 支持 "false","true" 字符串格式的数据
     默认值：false
     */
    private boolean halfCheck;


    /**
     * 节点自定义图标的 URL 路径。
     1、父节点如果只设置 icon ，会导致展开、折叠时都使用同一个图标
     2、父节点展开、折叠使用不同的个性化图标需要同时设置 treeNode.iconOpen / treeNode.iconClose 两个属性
     3、如果想利用 className 设置个性化图标，需要设置 treeNode.iconSkin 属性
     默认值：无
     */
    private String icon;

    /**
     * 父节点自定义折叠时图标的 URL 路径。
     1、此属性只针对父节点有效
     2、此属性必须与 iconOpen 同时使用
     3、如果想利用 className 设置个性化图标，需要设置 treeNode.iconSkin 属性
     默认值：无
     */
    private String iconClose;

    /**
     * 父节点自定义展开时图标的 URL 路径。
     1、此属性只针对父节点有效
     2、此属性必须与 iconClose 同时使用
     3、如果想利用 className 设置个性化图标，需要设置 treeNode.iconSkin 属性
     默认值：无
     */
    private String iconOpen;

    /**
     * 节点自定义图标的 className
     1、需要修改 css，增加相应 className 的设置
     2、css 方式简单、方便，并且同时支持父节点展开、折叠状态切换图片
     3、css 建议采用图片分割渲染的方式以减少反复加载图片，并且避免图片闪动
     4、zTree v3.x 的 iconSkin 同样支持 IE6
     5、如果想直接使用 图片的Url路径 设置节点的个性化图标，需要设置 treeNode.icon / treeNode.iconOpen / treeNode.iconClose 属性
     默认值：无
     */
    private String iconSkin;

    /**
     * 判断 treeNode 节点是否被隐藏。
     1、初始化 zTree 时，如果节点设置 isHidden = true，会被自动隐藏
     2、请勿对已加载的节点修改此属性，隐藏 / 显示 请使用 hideNode() / hideNodes() / showNode() / showNodes() 方法
     */
    private boolean isHidden;

    /**
     * 记录 treeNode 节点是否为父节点。
     1、初始化节点数据时，根据 treeNode.children 属性判断，有子节点则设置为 true，否则为 false
     2、初始化节点数据时，如果设定 treeNode.isParent = true，即使无子节点数据，也会设置为父节点
     3、为了解决部分朋友生成 json 数据出现的兼容问题, 支持 "false","true" 字符串格式的数据
     */
    private boolean isParent;

    /**
     * 节点名称。
     1、如果不使用 name 属性保存节点名称，请修改 setting.data.key.name
     默认值：无
     */
    private String name;

    /**
     * 1、设置节点是否隐藏 checkbox / radio [setting.check.enable = true 时有效]
     2、为了解决部分朋友生成 json 数据出现的兼容问题, 支持 "false","true" 字符串格式的数据
     默认值：false
     */
    private boolean nocheck;

    /**
     * 记录 treeNode 节点的 展开 / 折叠 状态。
     1、初始化节点数据时，如果设定 treeNode.open = true，则会直接展开此节点
     2、叶子节点 treeNode.open = false
     3、为了解决部分朋友生成 json 数据出现的兼容问题, 支持 "false","true" 字符串格式的数据
     4、非异步加载模式下，无子节点的父节点设置 open=true 后，可显示为展开状态，但异步加载模式下不会生效。（v3.5.15+）
     默认值：false
     */
    private boolean open;

    /**
     * 设置点击节点后在何处打开 url。[treeNode.url 存在时有效]
     默认值：无
     */
    private String target;


    /**
     * 节点链接的目标 URL
     1、编辑模式 (setting.edit.enable = true) 下此属性功能失效，如果必须使用类似功能，请利用 onClick 事件回调函数自行控制。
     2、如果需要在 onClick 事件回调函数中进行跳转控制，那么请将 URL 地址保存在其他自定义的属性内，请勿使用 url
     默认值：无
     */
    private String url;

    /**
     * Code
     */
    private String code;

    /**
     * ID
     */
    private String id;

    private String parentId;

    private Integer status;

    private String sim;

    /**
     * 车辆总数
     */
    private Integer total;

    /**
     * 在线车辆数
     */
    private Integer online;

    /**
     * tip只记录车队名称
     */
    private String tip;

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getOnline() {
        return online;
    }

    public void setOnline(Integer online) {
        this.online = online;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSim() {
        return sim;
    }

    public void setSim(String sim) {
        this.sim = sim;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public List<VehicleTree> getChildren() {
        return children;
    }

    public void setChildren(List<VehicleTree> children) {
        this.children = children;
    }

    public boolean isChkDisabled() {
        return chkDisabled;
    }

    public void setChkDisabled(boolean chkDisabled) {
        this.chkDisabled = chkDisabled;
    }

    public String getClick() {
        return click;
    }

    public void setClick(String click) {
        this.click = click;
    }

    public boolean isHalfCheck() {
        return halfCheck;
    }

    public void setHalfCheck(boolean halfCheck) {
        this.halfCheck = halfCheck;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIconClose() {
        return iconClose;
    }

    public void setIconClose(String iconClose) {
        this.iconClose = iconClose;
    }

    public String getIconOpen() {
        return iconOpen;
    }

    public void setIconOpen(String iconOpen) {
        this.iconOpen = iconOpen;
    }

    public String getIconSkin() {
        return iconSkin;
    }

    public void setIconSkin(String iconSkin) {
        this.iconSkin = iconSkin;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }

    public boolean isParent() {
        return isParent;
    }

    public void setParent(boolean parent) {
        isParent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isNocheck() {
        return nocheck;
    }

    public void setNocheck(boolean nocheck) {
        this.nocheck = nocheck;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
