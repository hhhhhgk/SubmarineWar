import javax.swing.*;
import java.awt.*;
import java.util.Random;

public abstract class SeaObject {
    public static final int Live = 0;//状态活
    public static final int Dead = 1;//状态死
    protected int state = Live;

    //战舰、侦察、鱼雷、水雷潜艇属性
    protected int width;
    protected int height;
    protected int x;
    protected int y;
    protected int speed;

    //侦察、鱼雷、水雷潜艇的属性
    public SeaObject(int width,int height){
        Random r = new Random();
        this.width = width;
        this.height = height;
        x = -width;//左到右
        y = r.nextInt(WorldMain.Height-height-150+1)+150;//高度随机
        speed = r.nextInt(3)+1;//速度随机
    }

    //战舰、水雷、炸弹的属性，根据该上级对象锁定属性值
    public SeaObject(int width,int height,int x,int y,int speed){
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    //移动函数
    public abstract void move();

    //获得图片函数
    public abstract ImageIcon getImage();

    public boolean WhetherLive(){//判断活
        return state == Live;
    }

    public boolean WhetherDead(){//判断死
        return state == Dead;
    }

    public boolean isOutofBounds(){//判断潜艇是否越界
        return x>=WorldMain.Width;
    }

    public boolean WhetherHit(SeaObject other){//判断潜艇炸弹是否碰撞
        int x1 = this.x - other.width;
        int x2 = this.x + this.width;
        int y1 = this.y - other.height;
        int y2 = this.y + this.height;
        int x = other.x;
        int y = other.y;
        return x>=x1 && x<=x2 && y>=y1 && y<=y2;//判断碰撞
    }

    public void GoDead(){//海洋对象死亡
        state = Dead;
    }

    public void painImage(Graphics g){
        if (WhetherLive()){
            this.getImage().paintIcon(null,g,this.x,this.y);
        }
    }
}
