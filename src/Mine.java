import javax.swing.*;

public class Mine extends SeaObject{
    public Mine(int x,int y){
        super(11,11,x,y,1);
    }

    @Override
    public void move() {//水雷上移
        y-=speed;
    }

    @Override
    public ImageIcon getImage() {
        return Images.mine;
    }

    public boolean isOutOfBounds(){//判断水雷是否越界函数
        return y<=150-height;
    }
}
