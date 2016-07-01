package com.zk.entity;

/**
 * Created by Administrator on 2016/7/1.
 */
public class RobotStatus {
    int weld1;
    int weld2;
    int weld3;
    int weld4;
    int weld5;
    int weld6;
    int carrier;
    int material;

    public RobotStatus() {
    }

    @Override
    public String toString() {
        return "RobotStatus{" +
                "carrier=" + carrier +
                ", weld1=" + weld1 +
                ", weld2=" + weld2 +
                ", weld3=" + weld3 +
                ", weld4=" + weld4 +
                ", weld5=" + weld5 +
                ", weld6=" + weld6 +
                ", material=" + material +
                '}';
    }

    public RobotStatus(int carrier, int material, int weld1, int weld2, int weld3, int weld4, int weld5, int weld6) {
        this.carrier = carrier;
        this.material = material;
        this.weld1 = weld1;
        this.weld2 = weld2;
        this.weld3 = weld3;
        this.weld4 = weld4;
        this.weld5 = weld5;
        this.weld6 = weld6;
    }

    public int getCarrier() {
        return carrier;
    }

    public void setCarrier(int carrier) {
        this.carrier = carrier;
    }

    public int getMaterial() {
        return material;
    }

    public void setMaterial(int material) {
        this.material = material;
    }

    public int getWeld1() {
        return weld1;
    }

    public void setWeld1(int weld1) {
        this.weld1 = weld1;
    }

    public int getWeld2() {
        return weld2;
    }

    public void setWeld2(int weld2) {
        this.weld2 = weld2;
    }

    public int getWeld3() {
        return weld3;
    }

    public void setWeld3(int weld3) {
        this.weld3 = weld3;
    }

    public int getWeld4() {
        return weld4;
    }

    public void setWeld4(int weld4) {
        this.weld4 = weld4;
    }

    public int getWeld5() {
        return weld5;
    }

    public void setWeld5(int weld5) {
        this.weld5 = weld5;
    }

    public int getWeld6() {
        return weld6;
    }

    public void setWeld6(int weld6) {
        this.weld6 = weld6;
    }
}
