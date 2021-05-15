package com.Code.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DBUtil {
    private static String url = "";
    private static String user = "";
    private static String password = "";

    static{
        //加载数据库账户、密码、url
        Properties ppt = new Properties();
        //远程数据库
        //InputStream is = DBUtil.class.getClassLoader().getResourceAsStream("ServiceDb.properties");
        //本地数据库
        InputStream is = DBUtil.class.getClassLoader().getResourceAsStream("LocalDb.properties");
        try {
            ppt.load(is);
            url = ppt.getProperty("url");
            user = ppt.getProperty("user");
            password = ppt.getProperty("password");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            //数据库驱动加载，com.mysql.cj.jdbc.Driver对应java版本6
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    /**
     * 用于链接数据库，得到的结果是数据库的连接对象，链接对象具备了操作数据库的很多功能。=
     * @return 链接对象
     */
    public static Connection getConn(){

        //1.    获取数据库链接
        try {
            //3.    将链接返回给工具的使用者
            return DriverManager.getConnection(url,user,password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /**
     * 断开数据库资源的链接，用于释放资源
     * @param conn 要释放链接
     * @param statement 要释放的执行环境
     * @param resultSet 要释放的结果集
     */
    public static void close(Connection conn, Statement statement, ResultSet resultSet){
        //简单的关闭资源，没有做先开的后关处理。
        if(conn != null){
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if(statement != null){
            try {
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if(resultSet != null){
            try {
                resultSet.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }



}
