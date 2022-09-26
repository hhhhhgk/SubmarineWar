import javax.swing.*;

public class Bomb extends SeaObject{
    public Bomb(int x,int y){
        super(9,12,x,y,3);
    }

    @Override
    public void move() {//炸弹下移函数
        y+=speed;
    }

    public boolean isOutOfBounds(){//检测深水炸弹是否越界函数
        return y>=WorldMain.Height;
    }

    @Override
    public ImageIcon getImage() {
        return Images.bomb;
    }
}
