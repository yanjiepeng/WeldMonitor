package com.zk.EventBus;

import java.util.Map;

/**
 * Created by Administrator on 2016/7/1.
 */
public class EventPic {

    Map<String , byte[]> img_map ;

    public EventPic(Map<String, byte[]> img_map) {
        this.img_map = img_map;
    }

    public Map<String, byte[]> getImg_map() {
        return img_map;
    }

    public void setImg_map(Map<String, byte[]> img_map) {
        this.img_map = img_map;
    }
}
