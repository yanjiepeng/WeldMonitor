package com.zk.entity;

/**
 * Created by Administrator on 2016/7/1.
 */
public class ProduceStatus {

    String id ;
    String name;
    String time ;
    String status ;

    public ProduceStatus(String id, String name, String status, String time) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
