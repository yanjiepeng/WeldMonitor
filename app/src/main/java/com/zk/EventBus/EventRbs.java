package com.zk.EventBus;

import com.zk.entity.RobotStatus;

/**
 * Created by Administrator on 2016/7/1.
 */
public class EventRbs  {

    RobotStatus rs ;


    public EventRbs(RobotStatus rs) {
        this.rs = rs;
    }

    public RobotStatus getRs() {
        return rs;
    }

    public void setRs(RobotStatus rs) {
        this.rs = rs;
    }
}
