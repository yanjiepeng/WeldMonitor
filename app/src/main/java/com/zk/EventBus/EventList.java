package com.zk.EventBus;

import com.zk.entity.User;

import java.util.List;

/**
 * Created by Yan jiepeng on 2016/7/7.
 */
public class EventList {

    List<User> list ;

    public EventList(List<User> list) {
        this.list = list;
    }

    public List<User> getList() {
        return list;
    }

    public void setList(List<User> list) {
        this.list = list;
    }
}
