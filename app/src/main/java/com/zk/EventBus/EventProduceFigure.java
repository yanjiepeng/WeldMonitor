package com.zk.EventBus;

/**
 * Created by Yan jiepeng on 2016/7/11.
 */
public class EventProduceFigure {

    com.zk.entity.ProduceFigure pf ;

    public EventProduceFigure(com.zk.entity.ProduceFigure pf) {
        this.pf = pf;
    }

    public com.zk.entity.ProduceFigure getPf() {
        return pf;
    }

    public void setPf(com.zk.entity.ProduceFigure pf) {
        this.pf = pf;
    }
}
