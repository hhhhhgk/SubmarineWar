import javax.swing.*;

public class Battleship extends SeaObject {
    private int life;   //命数

    public Battleship(){
        super(66,26,270,124,20);
        life = 10;
    }

    public void move(){//移动重写
    }

    @Override
    public ImageIcon getImage() {
        return Images.battleship;
    }

    public Bomb shoot(){//射击：战舰发射深水炸弹-生成深水炸弹对象
        return new Bomb(this.x,this.y); //深水炸弹的坐标为战舰的坐标
    }

    public int getLife(){//获得命数
        return life; //返回命数
    }

    public void subtractLife(){//减命
        life--; //命数减1
    }

    public void addLife(int num){//增命
        life += num;
    }

    public void moveLeft(){//ship左移
        x -= speed; //x-(向左)
    }

    public void moveRight(){//ship右移
        x += speed; //x+(向右)
    }




}
