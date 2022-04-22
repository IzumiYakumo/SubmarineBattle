package cn.tedu.submarine;

import javax.swing.*;

/**
 * 侦查潜艇:海洋对象
 */
public class ObserveSubmarine extends SeaObject implements EnemyScore {
    /**
     * 构造方法
     */
    ObserveSubmarine() {
        super(63, 19);
    }

    /**
     * 重写侦查潜艇移动
     */
    public void move() {
        x += speed;//x+(向右)
    }

    /**
     * 重写得分方法
     */
    public int getScore() {
        return 10;//击杀侦查潜艇获得10分
    }

    /**
     * 重写getImage()方法
     */
    public ImageIcon getImage() {
        return Images.obsersubm;//返回侦查潜艇图片
    }
}
