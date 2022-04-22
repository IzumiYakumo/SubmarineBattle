package cn.tedu.submarine;

import javax.swing.*;

/**
 * 炸弹:海洋对象
 */
public class Bomb extends SeaObject {
    /**
     * 构造方法
     */
    Bomb(int x, int y) {//炸弹的坐标不能写死,需根据战舰坐标的位置
        super(9, 12, x, y, 3);
    }

    /**
     * 重写炸弹移动
     */
    public void move() {
        y += speed;//y+(向下)
    }

    /**
     * 检查炸弹越界
     */
    public boolean isOutOfBounds() {
        return y >= World.HEIGHT;//炸弹的y>=窗口的高度,即为越界
    }

    /**
     * 重写getImage()方法
     */
    public ImageIcon getImage() {
        return Images.bomb;//返回炸弹图片
    }

}
