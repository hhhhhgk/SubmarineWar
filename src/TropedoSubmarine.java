import javax.swing.*;

public class TropedoSubmarine extends SeaObject implements EnemyScore{
    public TropedoSubmarine(){
        super(64,20);
    }

    @Override
    public void move() {//鱼雷潜艇右移函数
        x+=speed;
    }

    @Override
    public ImageIcon getImage() {//获得鱼雷潜艇图片函数
        return Images.torpesubm;
    }

    public int getScore(){//鱼雷潜艇分数函数
        return 50;
    }
}
