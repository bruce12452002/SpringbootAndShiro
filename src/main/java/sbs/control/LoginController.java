package sbs.control;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/loginLogout")
public class LoginController {
    @GetMapping("/login")
    public String login(String username, String password, HttpSession session) {
        var token = new UsernamePasswordToken(username, password);


        Subject currentUser = SecurityUtils.getSubject();
        System.out.println("Authenticated前=" + currentUser.isAuthenticated());
        System.out.println("Remembered前=" + currentUser.isRemembered());


//        SimpleCookie simpleCookie = new SimpleCookie("myCookie");
//        simpleCookie.setMaxAge(5); // 單位為秒，測試 rememberMe
//
//        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
//        cookieRememberMeManager.setCookie(simpleCookie);

        //session.setMaxInactiveInterval(5); // 單位為秒，5秒失效，測試 rememberMe
        // 測試結果為 cookie 無效，session 可以
        // session 還可以在 config 裡設定，取得 DefaultWebSessionManager 類，然後塞到 DefaultWebSecurityManager 裡，
        // DefaultWebSessionManager 必需寫在 DefaultWebSecurityManager 前面
        try {
            if (!currentUser.isAuthenticated() && !currentUser.isRemembered()) { // 這兩個永遠一個為 true；一個 false
                token.setRememberMe(true);
                currentUser.login(token);
            }
//            System.out.println("a==" + currentUser.isPermitted("firstFile:read"));
//            System.out.println("b==" + currentUser.isPermitted(new WildcardPermission("firstFile:write,read")));
//            System.out.println("c==" + currentUser.isPermitted(new WildcardPermission("firstFile:*")));
//            System.out.println("d==" + currentUser.isPermitted("secondFile"));
            System.out.println("Authenticated後=" + currentUser.isAuthenticated());
            System.out.println("Remembered後=" + currentUser.isRemembered());
        } catch (UnknownAccountException uae) {
            return "UnknownAccountException 例外(帳號錯)";
        } catch (IncorrectCredentialsException ice) {
            return "IncorrectCredentialsException 例外(密碼錯)";
        } catch (LockedAccountException lae) {
            return "LockedAccountException 例外";
        } catch (AuthenticationException ae) {
            return "AuthenticationException 例外";
        }
        return username + "登入成功!";
    }

    @GetMapping("/logout")
    public String logout() {
        SecurityUtils.getSubject().logout();
        return "登出成功!";
    }

    @GetMapping("/aaa")
    @RequiresRoles("ooo")
    public String testRole() {
        return "testRole";
    }

    @GetMapping("/bbb")
    @RequiresPermissions("firstFile")
    public String testPermission() {
        return "testPermission";
    }

    @GetMapping("/bbb2")
    @RequiresPermissions("firstFile:read") // 和 PermissionDao 裡的字串一樣就會過
    public String testPermission2() {
        return "testPermission2";
    }

    @GetMapping("/bbb3")
    @RequiresPermissions("firstFile")
    public String testPermission3() {
        return "testPermission3";
    }

    @GetMapping("/ccc")
    @RequiresUser
    public String testUser() {
        return "testUser";
    }

    @GetMapping("/ddd")
    @RequiresGuest // 沒有認證可以，但有認證不行
    public String testGuest() {
        return "testGuest";
    }

    @GetMapping("/eee")
    @RequiresAuthentication
    public String testAuthentication() {
        return "testAuthentication";
    }

}
