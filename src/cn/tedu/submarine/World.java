package cn.tedu.submarine;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

/**
 * 整个游戏世界
 */
public class World extends JPanel {

    public static final int WIDTH = 641;//窗口的宽
    public static final int HEIGHT = 479;//窗口的高
    public static final int RUNNING = 0;//游戏运行状态
    public static final int PAUSE = 1;//游戏暂停状态
    public static final int GAME_OVER = 2;//游戏结束状态
    private int state = RUNNING;//当前游戏状态(默认运行状态)

    private BattleShip ship = new BattleShip();//战舰
    private SeaObject[] submarines = {};//潜艇数组(三种潜艇)
    private Mine[] mines = {};//水雷数组
    private Bomb[] bombs = {};//炸弹数组

    /**
     * 生成(侦查潜艇,鱼雷潜艇,水雷潜艇)
     */
    private SeaObject nextSubmarine() {
        Random rand = new Random();//随机数对象
        int type = rand.nextInt(20);//随机数0-19之间
        if (type < 10) {//如果type小于10返回一个侦查潜艇对象
            return new ObserveSubmarine();
        } else if (type < 14) {//10到13之间,返回鱼雷潜艇
            return new TorpedoSubmarine();
        } else {//15-19返回水雷潜艇
            return new MineSubmarine();
        }
    }

    private int subEnterIndex = 0;//潜艇入场计数

    /**
     * 潜艇(侦查潜艇,鱼雷潜艇,水雷潜艇)入场
     */
    private void submarineEnterAction() {//每10毫秒走一次
        subEnterIndex++;
        if (subEnterIndex % 40 == 0) {
            SeaObject obj = nextSubmarine();//获取潜艇对象
            submarines = Arrays.copyOf(submarines, submarines.length + 1);
            submarines[submarines.length - 1] = obj;//将obj添加到submarines最后一个下标位置
        }
    }

    private int mineEnterIndex = 0;//水雷入场计数

    /**
     * 水雷入场
     */
    private void mineEnterAction() {//每10毫秒走一次
        mineEnterIndex++;//每10毫秒增1
        if (mineEnterIndex % 100 == 0) {
            for (int i = 0; i < submarines.length; i++) {//遍历所有潜艇
                if (submarines[i] instanceof MineSubmarine) {//若潜艇为水雷潜艇
                    MineSubmarine ms = (MineSubmarine) submarines[i];
                    Mine obj = ms.shootMine();
                    mines = Arrays.copyOf(mines, mines.length + 1);
                    mines[mines.length - 1] = obj;
                }
            }
        }
    }

    /**
     * 海洋对象移动
     */
    private void moveAction() {//每10毫秒走一次
        for (int i = 0; i < submarines.length; i++) {//遍历所有潜艇
            submarines[i].move();//潜艇移动
        }
        for (int i = 0; i < mines.length; i++) {//遍历所有水雷
            mines[i].move();//水雷移动
        }
        for (int i = 0; i < bombs.length; i++) {//遍历所有炸弹
            bombs[i].move();//炸弹移动
        }
    }

    /**
     * 删除越界的海洋对象
     */
    private void outOfBoundsAction() {//每10毫秒走一次
        for (int i = 0; i < submarines.length; i++) {
            if (submarines[i].isOutOfBounds() || submarines[i].isDead()) {
                submarines[i] = submarines[submarines.length - 1];
                submarines = Arrays.copyOf(submarines, submarines.length - 1);
            }
        }
        for (int i = 0; i < mines.length; i++) {
            if (mines[i].isOutOfBounds() || mines[i].isDead()) {
                mines[i] = mines[mines.length - 1];
                mines = Arrays.copyOf(mines, mines.length - 1);
            }
        }
        for (int i = 0; i < bombs.length; i++) {
            if (bombs[i].isOutOfBounds() || bombs[i].isDead()) {
                bombs[i] = bombs[bombs.length - 1];
                bombs = Arrays.copyOf(bombs, bombs.length - 1);
            }
        }
    }

    private int score = 0;

    /**
     * 炸弹与潜艇碰撞
     */
    private void bombBangAction() {//每10毫秒走一次
        for (int i = 0; i < bombs.length; i++) {//遍历所有炸弹
            Bomb b = bombs[i];//获取每一个炸弹
            for (int j = 0; j < submarines.length; j++) {//遍历所有潜艇
                SeaObject s = submarines[j];//获取每个潜艇
                if (s.isLive() && b.isLive() && s.isHit(b)) {//判定,如果炸弹和潜艇都活着并且相撞
                    s.goDead();//潜艇去死
                    b.goDead();//炸弹去死

                    if (s instanceof EnemyScore) {//若被撞对象为分
                        EnemyScore es = (EnemyScore) s;//将被撞对象强转为得分接口
                        score += es.getScore();//玩家得分
                    }
                    if (s instanceof EnemyLife) {//若被撞对象为命
                        EnemyLife el = (EnemyLife) s;//将被撞对象强转为得命接口
                        int num = el.getLife();//获取命数
                        ship.addlife(num);//玩家奖命
                    }
                }
            }
        }
    }

    /**
     * 水雷与战舰碰撞
     */
    private void mineBangAction() {//每10毫秒走一次
        for (int i = 0; i < mines.length; i++) {//遍历所有水雷
            Mine m = mines[i];//获取每个水雷
            if (m.isLive() && ship.isLive() && m.isHit(ship)) {//若都活着并且相撞
                m.goDead();//水雷去死
                ship.substractLife();//战舰减命
            }

        }
    }

    /**
     * 检测游戏结束
     */
    private void checkGameOverAction() {
        if (ship.getLife() <= 0) {//如果战舰命数小于等零,将state改为GAME_OVER
            state = GAME_OVER;
        }
    }

    /**
     * 启动程序执行
     */
    private void action() {
        KeyAdapter k = new KeyAdapter() {//键盘侦听器
            public void keyPressed(KeyEvent e) {//键盘按下
                if (e.getKeyCode() == KeyEvent.VK_P) {//若按P则暂停游戏
                    if (state == RUNNING) {
                        state = PAUSE;
                    } else if (state == PAUSE) {
                        state = RUNNING;
                    }
                }
                if (state != RUNNING) {//若当前状态不是运行状态
                    return;//则结束事件处理
                }

                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    Bomb obj = ship.shootBomb();
                    bombs = Arrays.copyOf(bombs, bombs.length + 1);
                    bombs[bombs.length - 1] = obj;
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    ship.moveLeft();
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    ship.moveRight();
                }
            }
        };
        this.addKeyListener(k);//添加侦听

        Timer timer = new Timer();
        int interval = 10;//定时间隔(毫秒为单位)
        timer.schedule(new TimerTask() {
            public void run() {//定时干的事(每10毫秒自动执行)
                if (state == RUNNING) {
                    submarineEnterAction();//潜艇(侦查潜艇,鱼雷潜艇,水雷潜艇)入场
                    mineEnterAction();//水雷入场
                    moveAction();//海洋对象移动
                    outOfBoundsAction();//删除越界对象
                    bombBangAction();//炸弹与潜艇碰撞
                    mineBangAction();//水雷与战舰碰撞
                    checkGameOverAction();//检测游戏结束
                    repaint();//重新调用paint方法
                }
            }
        }, interval, interval);//定时日程
    }

    /**
     * 重写Jpanel类中的paint方法
     */
    public void paint(Graphics g) {
        Images.sea.paintIcon(null, g, 0, 0);//画海洋图
        ship.printImage(g);
        for (int i = 0; i < submarines.length; i++) {
            submarines[i].printImage(g);
        }
        for (int i = 0; i < mines.length; i++) {
            mines[i].printImage(g);
        }
        for (int i = 0; i < bombs.length; i++) {
            bombs[i].printImage(g);
        }
        g.drawString("Score: " + score, 200, 50);
        g.drawString("Life: " + ship.getLife(), 400, 50);

        if (state == GAME_OVER) {//如果游戏状态为游戏结束状态
            Images.gameover.paintIcon(null, g, 0, 0);//画游戏结束图
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        World world = new World();
        world.setFocusable(true);
        frame.add(world);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH + 16, HEIGHT + 39);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);//调用paint

        world.action();//启动程序执行
    }
}
