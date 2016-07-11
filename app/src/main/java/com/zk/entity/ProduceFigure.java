package com.zk.entity;

/**
 * Created by Yan jiepeng on 2016/7/11.
 */
public class ProduceFigure {

    int total ;
    int already ;

    @Override
    public String toString() {
        return "ProduceFigure{" +
                "already=" + already +
                ", total=" + total +
                '}';
    }

    public ProduceFigure( int total , int already) {
        this.already = already;
        this.total = total;
    }

    public int getTotal() {
        return total;
    }

    public int getAlready() {
        return already;
    }

    public void setAlready(int already) {
        this.already = already;
    }
}
