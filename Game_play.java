import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

public class Game_play extends JPanel implements KeyListener, ActionListener {
    private boolean play = false;
    private int score = 0;

    private int totalBricks = 21;

    private Timer timer;
    private int delay = 5;

    private int playerX = 310;

    private int ballposX = 120;
    private int ballPosy = 350;
    private int ballxdir = -1;
    private int ballydir = -2;

    private MapGenerator map;


    public Game_play() {
        map = new MapGenerator(3,7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();

    }


    public void paint(Graphics g) {
        //background
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);

        //drawing map
        map.draw((Graphics2D)g);


        //borders
        g.setColor(Color.black);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);//above
        g.fillRect(691, 0, 3, 592);

        //scores
        g.setColor(Color.white);
        g.setFont(new Font("serif",Font.BOLD,25));
        g.drawString(""+score,590,30);

        //the paddle
        g.setColor(Color.green);
        g.fillRect(playerX, 550, 100, 8);

        //ball
        g.setColor(Color.RED);
        g.fillOval(ballposX, ballPosy, 20, 20);

        if(totalBricks<=0){
            play=false;
            ballxdir=0;
            ballydir=0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("You Won",260,300);

            g.setFont(new Font("serif",Font.BOLD,20));
            g.drawString("Press Enter to Restart",230,350);

        }

        if(ballPosy>570){
            play=false;
            ballxdir=0;
            ballydir=0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("Game Over,Scores:"+score,190,300);

            g.setFont(new Font("serif",Font.BOLD,20));
            g.drawString("Press Enter to Restart",230,350);

        }

        g.dispose();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if(play){
            if(new Rectangle(ballposX,ballPosy,20,20).intersects(new Rectangle(playerX,550,100,8))){
                ballydir=-ballydir;

            }

           A: for(int i=0;i<map.map.length;i++){
                for(int j=0;j<map.map[0].length;j++){
                    if(map.map[i][j]>0){
                        int brickX=j*map.brickwidth+80;
                        int brickY=i*map.brickheight+50;
                        int brickwidht= map.brickwidth;
                        int brickheight=map.brickheight;

                        Rectangle rect=new Rectangle(brickX,brickY,brickwidht,brickheight);
                        Rectangle ballrect=new Rectangle(ballposX,ballPosy,20,20);
                        Rectangle brickrect=rect;

                        if(ballrect.intersects(brickrect)){
                            map.setBrickValue(0,i,j);
                            totalBricks--;
                            score+=5;

                            if(ballposX+19<=brickrect.x || ballposX+1>=brickrect.x+brickrect.width){
                                ballxdir=-ballxdir;
                            }
                            else{
                                ballydir=-ballydir;
                            }
                            break A;
                        }
                    }
                }
            }
            ballposX+=ballxdir;
            ballPosy+=ballydir;
            if(ballposX<0){
                ballxdir=-ballxdir;
            }
            if(ballPosy<0){
                ballydir=-ballydir;
            }
            if(ballposX>670){
                ballxdir=-ballxdir;
            }
        }
        repaint();

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 600) {
                playerX = 600;

            } else {
                moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX < 10) {
                playerX = 10;

            } else {
                moveLeft();
            }
        }
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            if(!play){
                play=true;
                ballposX=120;
                ballPosy=350;
                ballxdir=-1;
                ballydir=-2;
                playerX=310;
                score=0;
                totalBricks=21;
                map=new MapGenerator(3,7);

                repaint();
            }
        }
    }

    public void moveRight() {
        play = true;
        playerX += 20;
    }


    public void moveLeft() {
        play=true;
        playerX-=20;
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
