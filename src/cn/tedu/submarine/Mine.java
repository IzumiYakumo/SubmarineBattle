package cn.tedu.submarine;

import javax.swing.*;

/**
 * 水雷:海洋对象
 */
public class Mine extends SeaObject {
    /**
     * 构造方法
     */
    Mine(int x, int y) {//水雷的坐标不能写死,需根据水雷潜艇坐标的位置
        super(11, 11, x, y, 1);
    }

    /**
     * 重写水雷移动
     */
    public void move() {
        y -= speed;//y-(向上)
    }

    /**
     * 检测深水炸弹越界
     */
    public boolean isOutOfBounds() {
        return y <= 150 - height;//炸弹的y<=150-炸弹的高,即为越界
    }

    /**
     * 重写getImage()方法
     */
    public ImageIcon getImage() {
        return Images.mine;//返回水雷图片
    }

}
