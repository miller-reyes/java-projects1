import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;



public class GamePanel extends JPanel implements ActionListener{
    
    
    static final int SCREEN_WIDTH=600;
    static final int SCREEN_HEIGHT=600;
    static final int UNIT_SIZE=25;
    static final int GAME_UNITS=(SCREEN_WIDTH*SCREEN_WIDTH)/UNIT_SIZE;
    static final int DELAY= 75;
    final int x[]=new int[GAME_UNITS];
    final int y[]=new int[GAME_UNITS];
    int bodyParts=6;
    int applesEaten;
    int appleX;
    int appleY;
    char direcction='R';
    boolean running = false;
    Timer timer;
    Random random;




    GamePanel(){
        random=new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();


    }
    public void startGame(){
        newApple();
        running=true;
        timer = new Timer(DELAY,this);
        timer.start();
        
            
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);

        
    }
    public void draw(Graphics g){
        if(running){
            for(int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++){
            g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
            g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
            }
         g.setColor(Color.red);
         g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            for (int i =0;i<bodyParts;i++){
                if (i==0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i],UNIT_SIZE, UNIT_SIZE);
              }
             else{
                  g.setColor(new Color(45,180,0));
                  g.fillRect(x[i], y[i],UNIT_SIZE, UNIT_SIZE);
             }
            }
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free",Font.BOLD,40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: "+applesEaten,(SCREEN_WIDTH-metrics.stringWidth("Score: "+applesEaten))/2 ,g.getFont().getSize());
        }
        else{
            gameOver(g);
        }
    }
    public void newApple(){
        appleX=random.nextInt((SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY=random.nextInt((SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;


    }
   
    public void move(){
        for(int i = bodyParts;i>0;i--){
            x[i]= x[i-1];
            y[i]= y[i-1];

        }
        switch(direcction){
            case'U':
            y[0]=y[0]-UNIT_SIZE;
            break;
            case'D':
            y[0]=y[0]+UNIT_SIZE;
            break;
            case'L':
            x[0]=x[0]-UNIT_SIZE;
            break;
            case'R':
            x[0]=x[0]+UNIT_SIZE;
            break;
        }

    }
    public void checkApple(){
        if((x[0]==appleX)&& y[0]==appleY ){
            bodyParts++;
            applesEaten++;
            newApple();
        }

    }
    public  void checkCollision(){
        //Head hits body
        for(int i = bodyParts;i>0;i--){
            if((x[0]==x[i]&&y[0]==y[i])){
                running =false;
            }
        }
        //head hit left border
        if(x[0]<0){
            running = false;
        }
        //head hit Right Border
        if(x[0]>SCREEN_WIDTH){
            running = false;
    
        }
        //Head touches Top of Border
        if(y[0]<0){
            running = false;
    }
    // Head Touches down border
    if(y[0]>SCREEN_HEIGHT){
            running = false;
    }
    if (!running){
        timer.stop();
    }
 }
    public void gameOver (Graphics g){
        //Score
         g.setColor(Color.red);
            g.setFont(new Font("Ink Free",Font.BOLD,40));
            FontMetrics metrics1 = getFontMetrics(g.getFont());
            g.drawString("Score: "+applesEaten,(SCREEN_WIDTH-metrics1.stringWidth("Score: "+applesEaten))/2 ,g.getFont().getSize());
        //Game Over Text
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD,75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over",(SCREEN_WIDTH-metrics2.stringWidth("Gamer Over"))/2 ,SCREEN_HEIGHT/2);
        //Made BY 
          g.setColor(Color.white);
        g.setFont(new Font("Ink Free",Font.BOLD,40));
        FontMetrics metrics3 = getFontMetrics(g.getFont());
        g.drawString("MADE BY MILLER REYES",(SCREEN_WIDTH-metrics3.stringWidth("MADE BY MILLER REYES"))/4 ,SCREEN_HEIGHT/4);

    }

    public void actionPerformed(ActionEvent e){
        if(running){
            move();
            checkApple();
            checkCollision();

        }
        repaint();
    }
    public class MyKeyAdapter extends KeyAdapter{
    @Override
    public void keyPressed(KeyEvent e ){
        switch(e.getKeyCode()){
            case KeyEvent.VK_LEFT:
            if(direcction!='R'){
                direcction='L';

            }
            break;
            case KeyEvent.VK_RIGHT:
            if(direcction!='L'){
                direcction='R';

            }
            break;
            case KeyEvent.VK_UP:
            if(direcction!='D'){
                direcction='U';

            }
            break;
            case KeyEvent.VK_DOWN:
            if(direcction!='U'){
                direcction='D';

            }
            break;
        }
        }
    }

}
