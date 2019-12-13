package sbs.dao;

import org.springframework.stereotype.Repository;
import sbs.entity.User;

@Repository
public class UserDao {
    public User getUser(String username) {
        // 模擬資料庫的回傳值，帳號和密碼一樣
        return new User().setId(1).setUsername(username).setPassword(username);
    }
}
