package cn.tedu.submarine;

import javax.swing.*;

/**
 * 鱼雷潜艇:海洋对象
 */
public class TorpedoSubmarine extends SeaObject implements EnemyScore {
    /**
     * 构造方法
     */
    TorpedoSubmarine() {
        super(64, 20);
    }

    /**
     * 重写鱼雷潜艇移动
     */
    public void move() {
        x += speed;//x+(向右)
    }

    /**
     * 重写得分接口方法
     */
    public int getScore() {
        return 40;//击杀鱼雷潜艇获得40分
    }

    /**
     * 重写getImage()方法
     */
    public ImageIcon getImage() {
        return Images.torpesubm;//返回鱼雷潜艇图片
    }
}
