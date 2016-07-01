package com.zk.EventBus;

/**
 * Created by Administrator on 2016/7/1.
 */
public class EventPic {

    byte[] pic ;

    public EventPic(byte[] pic) {
        this.pic = pic;
    }

    public byte[] getPic() {
        return pic;
    }

    public void setPic(byte[] pic) {
        this.pic = pic;
    }
}
