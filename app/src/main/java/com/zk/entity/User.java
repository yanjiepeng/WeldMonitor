package com.zk.entity;

import java.util.Arrays;

/**
 * Created by Administrator on 2016/6/30.
 */
public class User {

    String name ;
    String id ;
    byte[] headImg;

    public byte[] getHeadImg() {
        return headImg;
    }

    public void setHeadImg(byte[] headImg) {
        this.headImg = headImg;
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

    @Override
    public String toString() {
        return "User{" +
                "headImg=" + Arrays.toString(headImg) +
                ", name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
