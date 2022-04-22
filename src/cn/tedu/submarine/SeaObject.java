package cn.tedu.submarine;

import java.util.Random;
import javax.swing.ImageIcon;
import java.awt.Graphics;

/**
 * 海洋对象
 */
public abstract class SeaObject {
    public static final int LIVE = 0;//活着的
    public static final int DEAD = 1;//死了的
    protected int state = LIVE;//当前状态(默认为活着的)

    protected int width;//宽
    protected int height;//高
    protected int x;//x坐标
    protected int y;//y坐标
    protected int speed;//速度


    /**
     * 专门给侦查潜艇,鱼雷潜艇,水雷潜艇的构造
     */
    public SeaObject(int width, int height) {//宽高不能写死,需要写活
        this.width = width;//潜艇宽
        this.height = height;//潜艇高
        x = -width;//负的潜艇的宽度
        Random rand = new Random();//随机数生成
        y = rand.nextInt(World.HEIGHT - height - 150 + 1) + 150;//y坐标随机生成150-460之间
        speed = rand.nextInt(3) + 1;//潜艇速度1-3
    }

    /**
     * 专门给战舰,炸弹,水雷的构造
     */
    public SeaObject(int width, int height, int x, int y, int speed) {
        this.width = width;//宽
        this.height = height;//高
        this.x = x;//x坐标
        this.y = y;//y坐标
        this.speed = speed;//速度
    }

    public abstract void move();

    public abstract ImageIcon getImage();

    public boolean isLive() {
        return state == LIVE;//若当前状态为LIVE,表示为活着的,返回true,否则返回false
    }

    public boolean isDead() {//若当前状态为DEAD,表示为死了的,返回true,否则返回false
        return state == DEAD;
    }

    /**
     * 检测潜艇越界
     */
    public boolean isOutOfBounds() {
        return x >= World.WIDTH;//潜艇的x>=窗口的宽,即为越界
    }

    /**
     * 检测碰撞
     */
    public boolean isHit(SeaObject other) {
        //假设:this表示潜艇,other表示其他对象
        int x1 = this.x - other.width;
        int x2 = this.x + this.width;
        int y1 = this.y - other.height;
        int y2 = this.y + this.height;
        int x = other.x;
        int y = other.y;

        return x1 <= x && x2 >= x && y1 <= y && y2 >= y;
    }

    /**
     * 海洋对象去死
     */
    public void goDead() {
        state = DEAD;
    }

    /**
     * 画对象 g:画笔
     */
    public void printImage(Graphics g) {
        if (isLive()) {
            this.getImage().paintIcon(null, g, this.x, this.y);
        }
    }
}
