package com.zk.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.zk.task.MyTask;
import com.zk.task.MyTaskInterface;

public class ScalService extends Service {
    public ScalService() {
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }



}
class  MyThread extends Thread {

    @Override
    public void run() {
        super.run();
        MyTask task = new MyTask(new MyTaskInterface() {
            @Override
            public String[] doBackGround() {
                return new String[0];
            }

            @Override
            public void doUi() {

            }
        });
    }
}
