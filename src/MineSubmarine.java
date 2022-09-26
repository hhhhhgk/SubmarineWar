import javax.swing.*;

public class MineSubmarine extends SeaObject implements EnemyLife{
    public MineSubmarine(){
        super(63,19);
    }

    @Override
    public void move() {//水雷潜艇右移函数
        x+=speed;
    }

    public Mine shootMine(){//鱼雷潜艇发射水雷函数
        int x = this.x + this.width;
        int y = this.y - 11;
        return new Mine(x,y);
    }

    public int getLife(){//打掉水雷潜艇获得命数函数
        return 1;
    }

    @Override
    public ImageIcon getImage() {//水雷潜艇图片函数
        return Images.minesubm;
    }
}
