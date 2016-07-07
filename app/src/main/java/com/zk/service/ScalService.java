package com.zk.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.zk.EventBus.EventData;
import com.zk.EventBus.EventList;
import com.zk.EventBus.EventPic;
import com.zk.EventBus.EventRbs;
import com.zk.entity.DataTag;
import com.zk.entity.DutyWorker;
import com.zk.entity.RobotData;
import com.zk.entity.RobotStatus;
import com.zk.entity.User;
import com.zk.task.MyTask;
import com.zk.task.MyTaskInterface;
import com.zk.utils.L;
import com.zk.utils.Util;

import org.greenrobot.eventbus.EventBus;

import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/*
   Query data service
 */

public class ScalService extends Service {
    Timer timer = new Timer();

    public ScalService() {
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        timer.schedule(new LoadImageThread(), 2000);
        timer.schedule(new GetDutyTask(), 2000);
        timer.schedule(new RobotStautsTask(), 0, 5000);
        timer.schedule(new RobotDataThread(), 0, 5000);




        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /*
        Electricity and voltage of robots
     */
    private class RobotDataThread extends TimerTask {

        @Override
        public void run() {
            if (DataTag.MYSQL_CONNECT_FLAG) {
                try {
                    RobotData data = Util.QueryRobotDatas();
                    if (data != null) {
                        EventBus.getDefault().post(new EventData(data));
                        L.e("EVENTBUS 传输机器人电流电压数据" + data.toString());
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /*
        get robot operation status data
     */
    private class RobotStautsTask extends TimerTask {

        @Override
        public void run() {

            if (DataTag.MYSQL_CONNECT_FLAG) {
                try {
                    RobotStatus status = Util.QueryRobotStatus();
                    if (status != null) {
                        EventBus.getDefault().post(new EventRbs(status));
                        L.e("EVENTBUS 传输机器人状态数据" + status.toString());
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /*
    load image Thread
     */
    private class LoadImageThread extends TimerTask {

        @Override
        public void run() {
            Map<String , byte[]>  map = new HashMap<String , byte[]>();
            if (DataTag.MYSQL_CONNECT_FLAG) {
                try {
                    DutyWorker duty = Util.QueryDutyWorkerId();
                    byte[] img1 = Util.QueryHeadImageById(duty.getWorker1());
                    byte[] img2 = Util.QueryHeadImageById(duty.getWorker2());
                    byte[] img3 = Util.QueryHeadImageById(duty.getWorker3());
                    map.clear();
                    map.put("d1" , img1);
                    map.put("d2" , img2);
                    map.put("d3" , img3);
                    L.e("Service获取到排版头像");
                    if (map != null && map.size() != 0) {
                        EventBus.getDefault().post(new EventPic(map));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /*
      get duty info
     */

    private class GetDutyTask extends TimerTask {

        User u1, u2, u3, u4 = null;
        List<User> dutyList = new ArrayList<User>();

        @Override
        public void run() {
            if (DataTag.MYSQL_CONNECT_FLAG) {
                try {
                    DutyWorker dutyWorker = Util.QueryDutyWorkerId();
                    u1 = Util.QueryUserData(dutyWorker.getWorker1());
                    u2 = Util.QueryUserData(dutyWorker.getWorker2());
                    u3 = Util.QueryUserData(dutyWorker.getWorker3());
                    u4 = Util.QueryUserData(dutyWorker.getWorker2());
                    dutyList.clear();
                    dutyList.add(u1);
                    dutyList.add(u2);
                    dutyList.add(u3);
                    dutyList.add(u4);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (dutyList!=null && dutyList.size() != 0 ){
                L.e("值班人员信息",dutyList.size() +"");
                EventBus.getDefault().post(new EventList(dutyList));
            }
        }
    }

    /*
      stop service when close app
     */
    public class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ScalService.this.stopSelf();
        }
    }


}
