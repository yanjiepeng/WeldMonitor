package com.zk.EventBus;

import com.zk.entity.RobotData;

/**
 * Created by Administrator on 2016/7/1.
 */
public class EventData {

    RobotData data ;

    public EventData(RobotData data) {
        this.data = data;
    }

    public RobotData getData() {
        return data;
    }

    public void setData(RobotData data) {
        this.data = data;
    }
}

