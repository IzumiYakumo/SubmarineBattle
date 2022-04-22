package cn.tedu.submarine;

import javax.swing.ImageIcon;

/**
 * 潜艇大战相关图片类
 */
public class Images {
    public static ImageIcon battleship;//战舰图
    public static ImageIcon bomb;//炸弹图
    public static ImageIcon mine;//水雷图
    public static ImageIcon obsersubm;//侦查潜艇图
    public static ImageIcon minesubm;//水雷潜艇图
    public static ImageIcon torpesubm;//鱼雷潜艇图
    public static ImageIcon sea;//海洋图
    public static ImageIcon gameover;//游戏结束图

    static {//初始化静态资源
        battleship = new ImageIcon("img/battleship.png");
        bomb = new ImageIcon("img/bomb.png");
        mine = new ImageIcon("img/mine.png");
        obsersubm = new ImageIcon("img/obsersubm.png");
        minesubm = new ImageIcon("img/minesubm.png");
        torpesubm = new ImageIcon("img/torpesubm.png");
        sea = new ImageIcon("img/sea.png");
        gameover = new ImageIcon("img/gameover.png");

    }

    public static void main(String[] args) {
        System.out.println(battleship.getImageLoadStatus());
        System.out.println(bomb.getImageLoadStatus());
        System.out.println(mine.getImageLoadStatus());
        System.out.println(obsersubm.getImageLoadStatus());
        System.out.println(minesubm.getImageLoadStatus());
        System.out.println(torpesubm.getImageLoadStatus());
        System.out.println(sea.getImageLoadStatus());
        System.out.println(gameover.getImageLoadStatus());
    }
}
