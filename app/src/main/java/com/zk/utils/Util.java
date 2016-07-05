package com.zk.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.zk.entity.DataTag;
import com.zk.entity.DutyWorker;
import com.zk.entity.RobotData;
import com.zk.entity.RobotStatus;
import com.zk.entity.User;

/**
 * Created by Administrator on 2016/6/27.
 */
public class Util {


    //
    private static final String REMOTE_IP = DataTag.REMOTE_IP;
    private static final String URL = "jdbc:postgresql://"
            + REMOTE_IP
            + ":5432/tf?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8";
    private static final String USER = DataTag.USER;
    private static final String PASSWORD = DataTag.PASSWORD;
    public static Connection conn;

    // 获得连接
    private static Connection openConnection(String url, String user,
                                             String password) {
        Connection conn = null;
        try {
            final String DRIVER_NAME = "org.postgresql.Driver";
            Class.forName(DRIVER_NAME);
            conn = DriverManager.getConnection(url, user, password);
            if (conn != null)
                DataTag.MYSQL_CONNECT_FLAG = true;
            Log.i("SQLog", "connect success");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            conn = null;
        } catch (SQLException e) {
            e.printStackTrace();
            conn = null;
        }
        return conn;
    }


    /*
        机器人状态获取
     */
    public static RobotStatus QueryRobotStatus() throws SQLException {

        if (conn == null) {
            return null;
        }
        Statement stmt = null;
        ResultSet rs = null;
        String sql = "select s_r1 , s_r2 , s_r3 ,s_r4 ,s_r5 , s_r6 from linestate";

        stmt = conn.createStatement();
        RobotStatus status = new RobotStatus();
        if (stmt != null) {
            rs = stmt.executeQuery(sql);
            while (rs.next()) {

                status.setWeld1(rs.getInt("s_r1"));
                status.setWeld2(rs.getInt("s_r2"));
                status.setWeld3(rs.getInt("s_r3"));
                status.setWeld4(rs.getInt("s_r4"));
                status.setWeld5(rs.getInt("s_r5"));
                status.setWeld6(rs.getInt("s_r6"));
            }
        }

        rs.close();
        stmt.close();
        return status;
    }

    /*
     请求用户数据
     */
    public static byte[] QueryHeadImageById(int id) throws SQLException {

        byte[] img = new byte[1024];
        if (conn == null) {
            return null;
        }
        Statement stmt = null;
        ResultSet rs = null;

        String sql = "select image  from userinfo where id = '" + id + "' ";

        stmt = conn.createStatement();
        if (stmt != null) {
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                img = rs.getBytes("image");
            }
        }

        rs.close();
        stmt.close();
        return img;

    }


    /**
     * 查询今日的值班人员 信息（返回id集合）
     *
     * @author Yan jiepeng
     * @time 2016/7/5 13:10
     */
    public static DutyWorker QueryDutyWorkerId() throws SQLException {

        String today = FormatUtil.refTodayDate();
        if (conn == null) {
            return null;
        }
        Statement stmt = null;
        ResultSet rs = null;
        DutyWorker workGroup = null;
        String sql = "select *  from duty where d_date = '" + today + "' ";

        stmt = conn.createStatement();
        if (stmt != null) {
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                workGroup = new DutyWorker();
                workGroup.setDate(rs.getString("d_date"));
                workGroup.setWorker1(rs.getString("d_worker1"));
                workGroup.setWorker2(rs.getString("d_worker2"));
                workGroup.setWorker3(rs.getString("d_worker3"));
                workGroup.setWorker4(rs.getString("d_worker4"));
            }
        }

        rs.close();
        stmt.close();
        return workGroup;

    }


    /*
    请求用户的姓名 工号 数据 用于显示值班人员信息
 */
    public static User QueryUserData(int id) throws SQLException {

        User u = null;
        if (conn == null) {
            return null;
        }
        Statement stmt = null;
        ResultSet rs = null;

        String sql = "select u_num,u_name  from userinfo where id = '" + id + "' ";
        stmt = conn.createStatement();
        if (stmt != null) {
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                u = new User(rs.getString("u_name"), rs.getString("u_num"));
            }
        }
        rs.close();
        stmt.close();
        return u;
    }


    /*
     查看机器人的电压电流值
     */
    public static RobotData QueryRobotDatas() throws SQLException {

        if (conn == null) {
            return null;
        }
        Statement stmt = null;
        ResultSet rs = null;
        String sql = "select c_r1 , c_r2 , c_r3 ,c_r4 ,c_r5 , c_r6 , v_r1 ,v_r2 ,v_r3 , v_r4 ,v_r5 ,v_r6 , t_run from linestate where line = '1' ";

        stmt = conn.createStatement();
        RobotData data = new RobotData();
        if (stmt != null) {
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                //电流
                data.setCheck_time(rs.getTimestamp("t_run").toString());
                data.setWeld_elec_1(rs.getDouble("c_r1"));
                data.setWeld_elec_2(rs.getDouble("c_r2"));
                data.setWeld_elec_3(rs.getDouble("c_r3"));
                data.setWeld_elec_4(rs.getDouble("c_r4"));
                data.setWeld_elec_5(rs.getDouble("c_r5"));
                data.setWeld_elec_6(rs.getDouble("c_r6"));
                //电压
                data.setWeld_vol_1(rs.getDouble("v_r1"));
                data.setWeld_vol_2(rs.getDouble("v_r2"));
                data.setWeld_vol_3(rs.getDouble("v_r3"));
                data.setWeld_vol_4(rs.getDouble("v_r4"));
                data.setWeld_vol_5(rs.getDouble("v_r5"));
                data.setWeld_vol_6(rs.getDouble("v_r6"));
            }
        }

        rs.close();
        stmt.close();
        return data;
    }

    /*



     * 登录判定

    public static UserBean login(String user) {

        UserBean c_User = null;
        if (conn == null) {
            return null;
        }
        Statement stmt = null;
        ResultSet rs = null;
        String sql = "select * from staff where Staff_ID = '" + user + "'";

        try {
            stmt = conn.createStatement();
            if (stmt != null) {
                rs = stmt.executeQuery(sql);
                while (rs.next()) {

                    c_User = new UserBean(rs.getString(1), rs.getString(3),
                            rs.getString(2));
                }
                return c_User;
            } else {
                return null;
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (stmt != null) {
                stmt.close();
                stmt = null;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return c_User;

    }
    */
    //
    public static void onConn() {
        conn = openConnection(URL, USER, PASSWORD);
        Log.i("tag", 1 + "");
    }

    public static Bitmap Bytes2Bimap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }

}
