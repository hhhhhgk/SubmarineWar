import javax.swing.*;

public class ObserveSubmarine extends SeaObject implements EnemyScore{
    public ObserveSubmarine(){
        super(63,19);
    }

    @Override
    public void move() {//侦察潜艇右移函数
        x+=speed;
    }

    public int getScore(){//侦察潜艇分数函数
        return 5;
    }

    @Override
    public ImageIcon getImage() {
        return Images.obsersubm;
    }
}
