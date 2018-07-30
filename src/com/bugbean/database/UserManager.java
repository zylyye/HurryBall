package com.bugbean.database;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.bugbean.database.HurryBallDB.tableUsers;

/**
 * @author zylyye
 */

public class UserManager {
    private ConnectionDB mConnectionDB;
    private static UserManager sUserManager;

    public static UserManager getUserManager() {
        if (sUserManager == null) {
            sUserManager = new UserManager();
        }
        return sUserManager;
    }

    private UserManager() {
        mConnectionDB = SqlManager.getConnectionDB();
    }


    /**
     * 检查用户名与密码是否匹配
     * @param username 用户名
     * @param password 密码
     * @return
     */
    public boolean checkUser(String username, String password) {
        boolean isValid = false;
        ResultSet rs = getUserRs(username);
        int count = 0;
        try {
            count = rs.getRow();
            if (count >= 1) {
                String pwd = rs.getString(tableUsers.FIELD_PWD);
                isValid = pwd.equals(password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isValid;
    }


    /**
     * 检查用户是否已存在
     * @param username 用户名
     * @return
     */
    public boolean isUserExist(String username) {
        boolean isExist = false;
        ResultSet rs = getUserRs(username);
        try {
            isExist = rs.getRow() >= 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isExist;
    }

    private ResultSet getUserRs(String username) {
        ResultSet rs = null;
        String[] fields = null;
        String condition = tableUsers.FIELD_USERNAME + "=?";
        rs = mConnectionDB.query(tableUsers.NAME,fields,condition,username);
        return rs;
    }

    /**
     * 向数据库中添加用户
     * @param username 用户名
     * @param password 密码
     * @return 如果用户已存在，返回false; 添加成功，返回true
     */
    public boolean addUser(String username, String password) {
        if (!isUserExist(username)) {
            return false;
        }
        String[] fields = {
                tableUsers.FIELD_USERNAME,
                tableUsers.FIELD_PWD
        };
        mConnectionDB.insert(tableUsers.NAME, fields, username, password);
        return true;
    }

    /**
     * 从数据库中删除用户
     * @param username 用户名
     * @return 如果用户不存在，返回false; 删除成功返回true
     */
    public boolean deleteUser(String username) {
        if (!isUserExist(username)) {
            return false;
        }
        String condition = tableUsers.FIELD_USERNAME + "=?";
        mConnectionDB.delete(tableUsers.NAME, condition, username);
        return true;
    }
}
