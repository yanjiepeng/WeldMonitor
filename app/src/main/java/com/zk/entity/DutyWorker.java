package com.zk.entity;

/**
 * Created by Yan jiepeng on 2016/7/5.
 */
public class DutyWorker {

    String date ;
    String worker1;
    String worker2;
    String worker3;
    String worker4;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWorker1() {
        return worker1;
    }

    public void setWorker1(String worker1) {
        this.worker1 = worker1;
    }

    public String getWorker2() {
        return worker2;
    }

    public void setWorker2(String worker2) {
        this.worker2 = worker2;
    }

    public String getWorker3() {
        return worker3;
    }

    public void setWorker3(String worker3) {
        this.worker3 = worker3;
    }

    public String getWorker4() {
        return worker4;
    }

    public void setWorker4(String worker4) {
        this.worker4 = worker4;
    }

    public DutyWorker() {
    }

    public DutyWorker(String worker1, String worker2, String worker3, String worker4) {
        this.worker1 = worker1;
        this.worker2 = worker2;
        this.worker3 = worker3;
        this.worker4 = worker4;
    }

    @Override
    public String toString() {
        return "DutyWorker{" +
                "worker4='" + worker4 + '\'' +
                ", worker3='" + worker3 + '\'' +
                ", worker2='" + worker2 + '\'' +
                ", worker1='" + worker1 + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
