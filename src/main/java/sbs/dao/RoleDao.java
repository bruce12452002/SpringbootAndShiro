package sbs.dao;

import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository
public class RoleDao {
    public Set<String> getRoles(String username) {
        // 模擬資料庫取得角色
        Set<String> roles = new HashSet<>();
        if ("aaa".equals(username)) {
            roles.add("xxx");
        } else if ("bbb".equals(username)) {
            roles.add("ooo");
        }
        return roles;
    }
}
