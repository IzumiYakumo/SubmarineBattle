package cn.tedu.submarine;

import javax.swing.*;

/**
 * 水雷潜艇:海洋对象
 */
public class MineSubmarine extends SeaObject implements EnemyLife{
    /**
     * 构造方法
     */
    MineSubmarine() {
        super(63, 19);
    }

    /**
     * 重写水雷潜艇移动
     */
    public void move() {
        x += speed;//x+(向右)
    }

    /**重写得命方法*/
    public int getLife() {
        return 1;
    }

    /**
     * 重写getImage()方法
     */
    public ImageIcon getImage() {
        return Images.minesubm;//返回水雷潜艇图片
    }

    /**
     * 生成水雷对象
     */
    public Mine shootMine() {
        int x = this.x + this.width;//水雷的x:水雷潜艇的x+水雷潜艇的宽
        int y = this.y - 5;//水雷的y:水雷潜艇的y-固定值5
        return new Mine(x, y);
    }



}
