package sbs.dao;

import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository
public class PermissionDao {
    public Set<String> getPermissions(String role) {
        // 模擬資料庫取得權限
        Set<String> permissions = new HashSet<>();
        if ("xxx".equals(role)) {
            permissions.add("firstFile:*");
//            permissions.add("firstFile:write");
//            permissions.add("secondFile");
        } else if ("ooo".equals(role)) {
            permissions.add("firstFile");
        }
        return permissions;
    }
}
