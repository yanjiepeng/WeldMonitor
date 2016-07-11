package com.zk.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.zk.EventBus.EventData;
import com.zk.EventBus.EventList;
import com.zk.EventBus.EventList2;
import com.zk.EventBus.EventMsg;
import com.zk.EventBus.EventPic;
import com.zk.EventBus.EventProduceFigure;
import com.zk.EventBus.EventRbs;
import com.zk.entity.DataTag;
import com.zk.entity.DutyWorker;
import com.zk.entity.ProduceFigure;
import com.zk.entity.ProduceStatus;
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
        timer.schedule(new ScheduleTask() , 0 ,10000);
        timer.schedule(new ProduceFigureTask() , 0 ,10000);





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
            }else {
                EventBus.getDefault().post(new EventMsg("error"));
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
            byte[] img1 = new byte[1024], img2 = new byte[1024], img3 = new byte[1024];
            if (DataTag.MYSQL_CONNECT_FLAG) {
                try {
                    DutyWorker duty = Util.QueryDutyWorkerInfo();
                    if (duty != null && duty.getWorker1() != "") {
                        String d1[] = duty.getWorker1().split("\\|");
                        if (d1[0] != null && d1[0] != "") {
                            img1 = Util.QueryHeadImageById(d1[0]);
                        }
                        String d2[] = duty.getWorker2().split("\\|");
                        if (d2[0] != null && d2[0] != "") {
                            img2 = Util.QueryHeadImageById(d2[0]);
                        }
                        String d3[] = duty.getWorker3().split("\\|");
                        if (d3[0] != null && d3[0] != "") {
                            img3 = Util.QueryHeadImageById(d3[0]);
                        }
                        map.clear();
                        map.put("d1", img1);
                        map.put("d2", img2);
                        map.put("d3", img3);
                    }
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
                    DutyWorker dutyWorker = Util.QueryDutyWorkerInfo();
                    if (dutyWorker!=null && dutyWorker.getWorker1()!="") {
                        String user1[] = dutyWorker.getWorker1().split("\\|");
                        u1 = new User(user1[1], user1[0]);
                        String user2[] = dutyWorker.getWorker2().split("\\|");
                        u2 = new User(user2[1], user2[0]);
                        String user3[] = dutyWorker.getWorker3().split("\\|");
                        u3 = new User(user3[1], user3[0]);
//                        String user4[] = dutyWorker.getWorker4().split("|");
//                        u4 = new User(user4[1], user4[0]);
                        dutyList.clear();
                        dutyList.add(u1);
                        dutyList.add(u2);
                        dutyList.add(u3);
                        dutyList.add(u4);
                    }
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
            生产任务
     */

    private class ScheduleTask extends TimerTask {

        List<ProduceStatus> list = new ArrayList<>() ;
        @Override
        public void run() {

            if (DataTag.MYSQL_CONNECT_FLAG) {
                try {
                    list = Util.QueryScheduleData();
                    if (list !=null && list.size()!=0 ) {
                        L.e("生产订单列表数据："+list.size());
                        EventBus.getDefault().post(new EventList2(list));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    /*
      生产量任务
     */
    private class ProduceFigureTask extends TimerTask {

        ProduceFigure pf = null;
        @Override
        public void run() {

            if(DataTag.MYSQL_CONNECT_FLAG) {
                try {
                    pf = Util.QueryProduceFigure();
                    if (pf != null ){
                        L.e("生产数量统计"+pf.toString());
                        EventBus.getDefault().post(new EventProduceFigure(pf));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
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
