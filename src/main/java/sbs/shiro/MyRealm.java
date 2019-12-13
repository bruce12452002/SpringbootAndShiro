package sbs.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;
import sbs.dao.PermissionDao;
import sbs.dao.RoleDao;
import sbs.dao.UserDao;
import sbs.entity.User;

import javax.annotation.Resource;
import java.util.Set;

@Component
public class MyRealm extends AuthorizingRealm {
    @Resource
    private UserDao userDao;

    @Resource
    private RoleDao roleDao;

    @Resource
    private PermissionDao permissionDao;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) getAvailablePrincipal(principals);
        Set<String> roles = roleDao.getRoles(username);

        var authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(roles);
        roles.forEach(r -> {
            Set<String> permissions = permissionDao.getPermissions(r);
            authorizationInfo.addStringPermissions(permissions);
        });

//        System.out.println(authorizationInfo.getRoles());
//        System.out.println("---------------------------");
//        System.out.println(authorizationInfo.getStringPermissions());
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upt = (UsernamePasswordToken) token;
        User user = userDao.getUser(upt.getUsername());
        System.out.println(getName());
        return new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), getName());
    }
}
