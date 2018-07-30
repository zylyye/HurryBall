package com.bugbean.database;

import java.sql.*;


/**
 * MySQL数据库连接工具类，用于简化数据库的连接操作
 *
 * 构造必须提供的参数： 数据库名(database)
 * 其他参数不提供则使用以下默认值：
 *      主机名(host):  localhost
 *      端口(port):    3306
 *      用户名(user):  root
 *      密码(password):空
 */
public class ConnectionDB {
    private String host;
    private String port;
    private String user;
    private String password;
    private String database;
    private Connection con;
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static String url = "jdbc:mysql://";

    public ConnectionDB(String database) {
        this("zylyye", "123456", database);
    }

    public ConnectionDB(String user, String password, String database) {
        this("localhost", "3306", user, password, database);
    }

    public ConnectionDB(String host, String port, String user, String password, String database) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.database = database;
        init();
    }

    private void init() {
        url += host;
        url += ":" + port;
        url += "/" + database;
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("数据库连接驱动加载失败！");
        }
        try {
            con = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("无法与数据库建立连接！");
            System.err.println("错误信息:" + e.getMessage());
        }
    }

    public Connection getConnection() {
        return con;
    }

    public int insert(String tableName, String[] fields, Object... args) {
        int affectedRow = 0;
        String sql = "INSERT INTO " + tableName;
        if (fields != null) {
            sql += "(";
            for (int i = 0; i < fields.length; i++) {
                sql += fields[i];
                if (i < fields.length - 1) {
                    sql += ",";
                }
            }
            sql += ")";
        }
        sql += " VALUES(";
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                sql += "?";
                if (i < args.length - 1) {
                    sql += ",";
                }
            }
        }
        sql += ")";

        try {
            PreparedStatement pre = con.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                pre.setObject(i + 1, args[i]);
            }
            affectedRow = pre.executeUpdate();
            pre.close();
        } catch (SQLException e) {
            System.err.println("执行插入语句时发生错误！");
            System.err.println("错误信息:" + e.getMessage());
        }
        return affectedRow;
    }

    public int update(String tableName, String[] keys, Object[] values, String condition, Object... conArgs) {
        int affectedRow = 0;
        boolean ableSet = keys != null && values != null;
        String sql = "UPDATE " + tableName;
        sql += " SET ";
        if (ableSet) {
            for (int i = 0; i < keys.length; i++) {
                sql += keys[i] + "=?";
                if (i < keys.length - 1) {
                    sql += ",";
                }
            }
        }
        sql += " WHERE ";
        sql += condition;
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            if (ableSet) {
                for (int i = 0; i < keys.length; i++) {
                    pre.setObject(i + 1, values[i]);
                }
            }
            if (conArgs != null) {
                int initLength = keys.length;
                for (int i = 0; i < conArgs.length; i++) {
                    pre.setObject(initLength + i + 1, conArgs[i]);
                }
            }
            affectedRow = pre.executeUpdate();
        } catch (SQLException e) {
            System.err.println("执行更新语句时发生错误！");
            System.err.println("错误信息:" + e.getMessage());
        }
        return affectedRow;
    }

    public int delete(String tableName, String condition, Object... conArgs) {
        int affectedRow = 0;
        String sql = "DELETE FROM " + tableName;
        sql += " WHERE " + condition;
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            if (conArgs != null) {
                for (int i = 0; i < conArgs.length; i++) {
                    pre.setObject(i + 1, conArgs[i]);
                }
            }
            affectedRow = pre.executeUpdate();
        } catch (SQLException e) {
            System.err.println("执行删除语句时发生错误！");
            System.err.println("错误信息:" + e.getMessage());
        }
        return affectedRow;
    }

    public ResultSet query(String tableName, String[] fileds, String condition, Object... conArgs) {
        ResultSet rs = null;
        String sql = "SELECT ";
        boolean useCondition = condition != null && !"".equals(condition.trim());
        if (fileds == null || fileds.length == 0) {
            sql += "* ";
        } else {
            for (int i = 0; i < fileds.length; i++) {
                sql += fileds[i];
                if (i < fileds.length - 1) {
                    sql += ",";
                }
            }
        }
        sql += " FROM "+tableName;
        if (useCondition) {
            sql += " WHERE "+condition;
        }
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            if (useCondition && conArgs != null) {
                for (int i = 0; i < conArgs.length; i++) {
                    pre.setObject(i + 1, conArgs[i]);
                }
            }
            rs = pre.executeQuery();
        } catch (SQLException e) {
            System.err.println("执行查询语句时发生错误！");
            System.err.println("错误信息:" + e.getMessage());
        }
        return rs;
    }

    public void close() {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                /*ignore SQLException*/
            }
        }
    }
}