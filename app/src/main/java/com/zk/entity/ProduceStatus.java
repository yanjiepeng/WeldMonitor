package com.zk.entity;

/**
 * Created by Administrator on 2016/7/1.
 * 生产任务数据实例
 */
public class ProduceStatus {

    int id ;
    String name;
    String time ;
    String status ;

    public ProduceStatus() {
    }

    public ProduceStatus(int id, String name, String status, String time) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
