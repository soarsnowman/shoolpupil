package xin.shiro.realm;


import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import xin.entity.user.UserResponse;
import xin.service.user.UserService;

import java.util.HashSet;
import java.util.Set;


/**
 * Created by snowman on 2017/11/20.
 */
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private UserResponse userResponse;

    //授权方法
    //授权会被回调的方法
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //1.从PrincipalCollection中来获取登录用户的信息(账号)
        String principal = (String)principalCollection.getPrimaryPrincipal();

        //2.利用登录的用户的信息来用户当前用户的角色或权限（可能需要查询数据库）
        Set<String> roles = new HashSet<String>();
        userResponse = userService.inquireUserByAccount(principal);
        roles.add(userResponse.getRole());

        //3.创建SimpleAuthorizationInfo,并设置其roles属性
        SimpleAuthorizationInfo athorizationInfo = new SimpleAuthorizationInfo(roles);
        System.out.print(athorizationInfo);
        //4.返回创建SimpleAuthorizationInfo对象
        return athorizationInfo;
    }

    //认证（登录）
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authenticationToken) throws AuthenticationException {
        //1.AuthenticationToken转换为UsernamePasswordToken
        UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;
        //2.从UsernamePasswordToken中获取username
        String username = upToken.getUsername();

        //3.调用数据库查询username，进行匹配
        userResponse = userService.inquireUserByAccount(username);
        //4.如不匹配，抛出UnknowAccountException异常
        if (userResponse == null) throw new UnknownAccountException("2");

        //1).principal:认证实体类信息(账号)，可以是username，也可以是数据表对应的用户的实体类对象
        Object principal = userResponse.getAccount();
        //2).credentials：密码
        Object credentials = userResponse.getPassword();
        //3).realmName：当前realm对象的name，调用父类的getName()方法即可
        String realmName = getName();
        //4).盐值
        ByteSource credentialsSalt = ByteSource.Util.bytes(username);

        SimpleAuthenticationInfo info = null;//new SimpleAuthenticationInfo(principal,credentials,realmName);
        info = new SimpleAuthenticationInfo(principal,credentials,credentialsSalt,realmName);

        return info;
    }

}
