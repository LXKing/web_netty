package com.aisafer.webgis.shiro;

import com.aisafer.psg.user.client.dto.UserReq;
import com.aisafer.psg.user.client.dto.UserRes;
import com.aisafer.psg.user.client.service.RUserService;
import com.aisafer.sso.config.SessionManager;
import com.aisafer.sso.config.ShiroPac4jRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;


public class ShiroRealm extends ShiroPac4jRealm {

   @Autowired
    private RUserService rUserService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("权限配置-->Main.doGetAuthorizationInfo()");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        try {
            UserRes res=null;
            Object object=null;//SecurityUtils.getSubject().getSession().getAttribute("user");
            if(object!=null && object instanceof UserRes){
                res= (UserRes) object;
            }
            if(res==null){
                UserReq req=new UserReq();
                req.setLoginName(SessionManager.getAccount());
                res=rUserService.getUserByloginName(req);
                SecurityUtils.getSubject().getSession().setAttribute("user",res);
               //首次登陆成功
                rUserService.userloginSuccess(req);
            }

            authorizationInfo.setRoles(res.getRoles());
            authorizationInfo.setStringPermissions(res.getPerminssions());

        }catch(Exception e) {
            e.printStackTrace();
        }

        return authorizationInfo;
    }


    @Override
    public boolean isPermitted(PrincipalCollection principals, String permission) {

        if(permission == null || permission.length() == 0){
            return false;
        }

        if(!permission.contains(" or ")){
            return super.isPermitted(principals,permission);
        }

        String[] str = permission.split(" or ");
        for (String s : str) {
            if(super.isPermitted(principals, s)){
                return true;
            }
        }
        return false;
    }
}
