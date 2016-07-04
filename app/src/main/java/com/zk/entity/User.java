package com.zk.entity;

import java.util.Arrays;

/**
 * Created by Administrator on 2016/6/30.
 * 用户实例
 */
public class User {

    String name ;
    String id ;
    String num;
    byte[] headImg;


    public User(byte[] headImg, String name, String num) {
        this.headImg = headImg;
        this.name = name;
        this.num = num;
    }

    public User(String name, String num) {
        this.name = name;
        this.num = num;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

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
