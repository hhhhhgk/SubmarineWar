import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.Timer;

public class WorldMain extends JPanel {
    //窗口宽高
    public static final int Width = 635;
    public static final int Height = 465;
    //游戏运行状态
    public static final int Running = 0;//运行状态
    public static final int GameOver = 1;//结束状态
    private int state = Running;//默认运行状态

    private Battleship ship = new Battleship();//战舰

    private SeaObject submarines[] = {};//潜艇数组

    public void paint(Graphics g){
        switch (state){
            case GameOver://游戏结束图
                Images.gameover.paintIcon(null,g,0,0);
                break;
            case Running://运行时所需海洋，对象，分和命的图
                Images.sea.paintIcon(null,g,0,0);

                ship.painImage(g);

                for (SeaObject submarine : submarines){//潜艇数
                    submarine.painImage(g);//生成潜艇
                }
                for (int i = 0; i < bombs.length; i++) {
                    bombs[i].painImage(g);//生成战舰炸弹
                }
                for (int i = 0; i < mines.length; i++) {
                    mines[i].painImage(g);//生成潜艇鱼雷
                }

                g.drawString("Score:"+score,200,50);//分数表
                g.drawString("Life:"+ship.getLife(),400,50);//命表
        }
    }

    private int subEnterIndex = 0;//潜艇计数入场
    public void submarineEnterAction(){
         subEnterIndex++;
         if (subEnterIndex%40 == 0){
             SeaObject object = nextSubmarine();
             submarines = Arrays.copyOf(submarines,submarines.length+1);
             submarines[submarines.length-1] = object;
         }
    }

    public SeaObject nextSubmarine() {//生成潜艇对象
        Random r = new Random();
        int type = r.nextInt(20);
        if (type<10){
            return new ObserveSubmarine();
        }else if (type<11){
            return new TropedoSubmarine();
        }else {
            return new MineSubmarine();
        }
    }

    public void moveAction(){//海洋相关移动
        for (int i = 0; i < submarines.length; i++) {
            submarines[i].move();
        }
        for (int i = 0; i < mines.length; i++) {
            mines[i].move();
        }
        for (int i = 0; i < bombs.length; i++) {
            bombs[i].move();
        }
    }

    private int score = 0;//得分
    public void bombAction(){//击杀潜艇
        for (int i = 0; i < bombs.length; i++) {
            Bomb b = bombs[i];
            for (int j = 0; j < submarines.length; j++) {
                SeaObject s = submarines[j];
                if (b.WhetherLive() && s.WhetherLive() && s.WhetherHit(b)){
                    s.GoDead();
                    b.GoDead();
                    if (s instanceof EnemyScore){
                        EnemyScore es = (EnemyScore)s;
                        score += es.getScore();
                    }
                    if (s instanceof EnemyLife){
                        EnemyLife ea = (EnemyLife)s;
                        int num = ea.getLife();
                        ship.addLife(num);
                    }
                }
            }
        }
    }

    public void mintAction(){//战舰被命中
        for (int i = 0; i < mines.length; i++) {
            Mine m = mines[i];
            if (m.WhetherLive() && ship.WhetherLive() && m.WhetherHit(ship)){
                m.GoDead();
                ship.subtractLife();
            }
        }
    }

    private Mine mines[] = {};//水雷数组

    private int mineEnterIndex = 0;//水雷计数入场
    public void mineAction(){
        mineEnterIndex++;
        if (mineEnterIndex%100 == 0){
            for (int i = 0; i < submarines.length; i++) {
                if (submarines[i] instanceof MineSubmarine){
                    MineSubmarine ms = (MineSubmarine) submarines[i];
                    Mine object = ms.shootMine();
                    mines = Arrays.copyOf(mines,mines.length+1);
                    mines[mines.length-1] = object;
                }
            }
        }
    }

    public void checkGameOverAction(){//检测游戏结束
        if (ship.getLife()<=0){
            state = GameOver;
        }
    }

    public void outOfBoundsAction(){//删除越界
        for (int i = 0; i < submarines.length; i++) {//潜艇
            if (submarines[i].isOutofBounds() || submarines[i].WhetherDead()){
                submarines[i] = submarines[submarines.length-1];
                submarines = Arrays.copyOf(submarines,submarines.length-1);
            }
        }

        for (int i = 0; i < mines.length; i++) {//水雷
            if (mines[i].isOutofBounds() || mines[i].WhetherDead()){
                mines[i] = mines[mines.length-1];
                mines = Arrays.copyOf(mines,mines.length-1);
            }
        }

        for (int i = 0; i < bombs.length; i++) {//炸弹
            if (bombs[i].isOutofBounds() || bombs[i].WhetherDead()){
                bombs[i] = bombs[bombs.length-1];
                bombs = Arrays.copyOf(bombs,bombs.length-1);
            }
        }
    }

    private Bomb bombs[] = {};//炸弹数组
    public void start(){
        KeyAdapter k = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE){
                    Bomb object = ship.shoot();
                    bombs = Arrays.copyOf(bombs,bombs.length+1);
                    bombs[bombs.length-1] = object;
                }

                if (e.getKeyCode() == KeyEvent.VK_A){
                    ship.moveLeft();
                }
                if (e.getKeyCode() == KeyEvent.VK_D){
                    ship.moveRight();
                }
            }
        };
        this.addKeyListener(k);

        Timer timer = new Timer();
        int interval = 10;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (state == Running){
                    submarineEnterAction();//潜艇入场
                    moveAction();//移动
                    mineAction();//水雷入场
                    outOfBoundsAction();//删除越界
                    mintAction();//被击杀
                    bombAction();//击杀
                    checkGameOverAction();//结束
                    repaint();
                }
            }
        },interval,interval);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        WorldMain worldMain = new WorldMain();
        worldMain.setFocusable(true);
        frame.add(worldMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Width+16,Height+39);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        worldMain.start();
    }
}
