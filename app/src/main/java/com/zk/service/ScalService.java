package com.zk.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.zk.EventBus.EventData;
import com.zk.EventBus.EventPic;
import com.zk.EventBus.EventRbs;
import com.zk.entity.DataTag;
import com.zk.entity.RobotData;
import com.zk.entity.RobotStatus;
import com.zk.task.MyTask;
import com.zk.task.MyTaskInterface;
import com.zk.utils.L;
import com.zk.utils.Util;

import org.greenrobot.eventbus.EventBus;

import java.sql.SQLException;
import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class ScalService extends Service {
    Timer timer = new Timer();

    public ScalService() {
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        timer.schedule(new RobotStautsTask(), 0, 5000);
        timer.schedule(new RobotDataThread() , 0 ,5000);
        new LoadImageThread().start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    class RobotDataThread extends TimerTask {

        @Override
        public void run() {
            if (DataTag.MYSQL_CONNECT_FLAG) {
                try {
                    RobotData data = Util.QueryRobotDatas();
                    if (data != null) {
                        EventBus.getDefault().post(new EventData(data));
                        L.e("EVENTBUS 传输机器人电流电压数据" +data.toString());
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }

    }
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



    class LoadImageThread extends  Thread {
        @Override
        public void run() {
            super.run();
            if (DataTag.MYSQL_CONNECT_FLAG) {
                try {
                    byte[] img = Util.QueryHeadImageById( 1 );
                    EventBus.getDefault().post(new EventPic(img));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /*
      stop service when close app
     */
    class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            stopSelf();
        }
    }


}
