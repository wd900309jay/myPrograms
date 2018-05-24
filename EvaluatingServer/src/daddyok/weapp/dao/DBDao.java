package daddyok.weapp.dao;

import java.sql.*;

public class DBDao {
	private static final String DBDRIVER="com.mysql.jdbc.Driver";
	//private static final String DBURL="jdbc:mysql://localhost:3306/daddyok?useUnicode=true&amp;characterEncoding=UTF-8";
	private static final String DBURL="jdbc:mysql://120.78.86.189:3306/dok_td_weapp?useSSL=false&useUnicode=true&characterEncoding=utf8&autoReconnect=true&rewriteBatchedStatements=TRUE";
	private static final String DBUSER="root";
	private static final String DBPASS="Tdtw20171113@daddyok.com";
	private static Connection connection = null;
	 //连接数据库
    public static Connection getConnection(){

        try {
            Class.forName(DBDRIVER);
            connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
        } catch (Exception e) {
            System.out.println("数据库连接异常");
            e.printStackTrace();
        }
        return connection;
    }
    public  static void closeConnection(Connection connection){

        if(connection != null){
            try {
                connection.close(); // 关闭数据库连接
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

