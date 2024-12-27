import javax.swing.*;

public class Brick_breaker_project {
    public static void main(String[] args) {
        JFrame obj=new JFrame();
        Game_play gameplay=new Game_play();
        obj.setBounds(10,10,700,600);
        obj.setTitle("Breakout Balls");
        obj.setResizable(false);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(gameplay);
    }
}
