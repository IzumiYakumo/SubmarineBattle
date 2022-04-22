package cn.tedu.submarine;

import javax.swing.*;

/**
 * 战舰:海洋对象
 */
public class BattleShip extends SeaObject {
    private int life;//命数

    /**
     * 构造方法
     */
    BattleShip() {
        super(66, 26, 270, 124, 20);
        life = 5;
    }

    /**
     * 战舰移动
     */
    public void move() {

    }

    /**
     * 重写getImage()方法
     */
    public ImageIcon getImage() {
        return Images.battleship;//返回战舰图片
    }

    /**
     * 发射炸弹----生成炸弹对象
     */
    public Bomb shootBomb() {
        return new Bomb(this.x, this.y);
    }

    /**
     * 战舰左移方法
     */
    public void moveLeft() {
        if (x <= 0) {
            return;
        }
        x -= speed;
    }

    /**
     * 战舰右移方法
     */
    public void moveRight() {
        if (x + width >= World.WIDTH) {
            return;
        }
        x += speed;
    }

    /**
     * 战舰奖命
     */
    public void addlife(int num) {
        life += num;//命数增加num
    }

    /**
     * 战舰减命
     */
    public void substractLife() {
        life--;//减命
    }

    /**
     * 获取命数
     */
    public int getLife() {
        return life;
    }
}
