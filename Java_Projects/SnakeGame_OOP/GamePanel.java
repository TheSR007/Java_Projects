package snake;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener{
	
	static int screen_width=600;
    static int screen_height=600;
    static int unit_size = 25;
    static final int game_unit=(screen_width*screen_height/unit_size);
    static int delay=75;
    final int x[]=new int[game_unit];
    final int y[]=new int[game_unit];
    int body_parts=6;
    int apples_eaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running= false;

    Timer timer;
    Random random;

	
    GamePanel(){
        random=new Random();
        this.setPreferredSize(new Dimension(screen_width,screen_height));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MykeyAdapter());
        startGame();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
}
    public void startGame() {
    			   			   		 
		   		newApple();
	    	 	running=true;
	    	 	timer=new Timer(delay,this);
	    	 	timer.start();
		       
    }
    
    public void draw(Graphics g) {
        if(running) { 
        	
        	
	        	g.setColor(Color.red);
	        	g.fillOval(appleX,appleY,unit_size,unit_size);
	        	for(int i = 0;i<body_parts;i++) {
	        	if(i==0) {
	        		g.setColor(Color.green);
	        		g.fillRect(x[i], y[i], unit_size, unit_size);
	        			}
	        	else {
	        		g.setColor(new Color(45,180,0));
	        		g.fillRect(x[i], y[i], unit_size, unit_size);
	        		}
	        		}
	        		g.setColor(Color.red);
	        		g.setFont(new Font("Arial",Font.BOLD,30));
	        		FontMetrics metrics = getFontMetrics(g.getFont());
	        		g.drawString("score: "+apples_eaten, (screen_width-metrics.stringWidth("score: "+apples_eaten))/2,g.getFont().getSize());
	        		
	       }
        else{
        	gameover(g);
        }
        
        
        
          
    }
    public void newApple() {
         appleX=random.nextInt((int)(screen_width/unit_size))*unit_size;  
         appleY=random.nextInt((int)(screen_height/unit_size))*unit_size;
         for(int i=0;i<body_parts;i++) {
        	 if((x[i]==appleX) && (y[i]==appleY)) {
        		 newApple();
        	 }
        		 
         }
    }
    public void move() {
        for(int i = body_parts;i>0;i--) {
        	x[i]=x[i-1];
        	y[i]=y[i-1];
        }
        switch(direction) { 
        case 'R':
        	x[0] = x[0] + unit_size;
			break;
        
    	case 'L':
    		x[0] = x[0] - unit_size;
			break;
    
		case 'U':
			y[0] = y[0] - unit_size;
			break;

		case 'D':
			y[0] = y[0] + unit_size;
			break;	
        }
    }
    public void checkApple() {
        if((x[0]==appleX) && (y[0]==appleY)){
        	body_parts++;
        	apples_eaten++;
        	newApple();
        }
        }
    public void checkCollision() {
        //head collision with body
    	for(int i = body_parts;i>0;i--) {
        	if((x[0]==x[i]) && (y[0]==y[i])){
        		running=false;
        	}}
    	//head collision with left wall
    	if(x[0]<0) {
    		running=false;
    	}
    	//head collision with right wall
    	if(x[0]>=screen_width) {
    		running=false;
    	}
    	//head collision with up wall
     	if(y[0]<0) {
    		running=false;
    	}
    	//head collision with down wall
    	if(y[0]>=screen_height) {
    		running=false;
    	}
    	if(!running) {
    		timer.stop();
    	}
    		
    }
   public void gameover(Graphics g) {
          //score text
	   	  if(!running) {
	   	  g.setColor(Color.red);	   	  
	      g.setColor(Color.red);
		  g.setFont(new Font("Arial",Font.BOLD,30));
		  FontMetrics metric = getFontMetrics(g.getFont());
		  g.drawString("score: "+apples_eaten, (screen_width-metric.stringWidth("score: "+apples_eaten))/2,g.getFont().getSize());
    	
    	//gameover text
    	  g.setColor(Color.red);
          g.setFont(new Font("Arial",Font.BOLD,75));
          FontMetrics metrics = getFontMetrics(g.getFont());
          g.drawString("Game Over", (screen_width-metrics.stringWidth("Game Over"))/2,screen_height/2);
          
	   	  }
    
}
    @Override
    public void actionPerformed(ActionEvent e) {
        if(running) {
        	move();
        	checkApple();
        	checkCollision();
        }
        repaint();
    }
    public class MykeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
            	if(direction!='R') {
            		direction='L';
            	}
            	break;
            case KeyEvent.VK_RIGHT:
            	if(direction!='L') {
            		direction='R';
            	}
            	break;
            case KeyEvent.VK_UP:
            	if(direction!='D') {
            		direction='U';
            	}
            	break;
            case KeyEvent.VK_DOWN:
            	if(direction!='U') {
            		direction='D';
            	}
            	break;
            case KeyEvent.VK_A:
            	if(direction!='R') {
            		direction='L';
            	}
            	break;
            case KeyEvent.VK_D:
            	if(direction!='L') {
            		direction='R';
            	}
            	break;
            case KeyEvent.VK_W:
            	if(direction!='D') {
            		direction='U';
            	}
            	break;
            case KeyEvent.VK_S:
            	if(direction!='U') {
            		direction='D';
            	}
            	break;
            	
            	
            case KeyEvent.VK_NUMPAD4:
            	if(direction!='R') {
            		direction='L';
            	}
            	break;
            case KeyEvent.VK_NUMPAD6:
            	if(direction!='L') {
            		direction='R';
            	}
            	break;
            case KeyEvent.VK_NUMPAD8:
            	if(direction!='D') {
            		direction='U';
            	}
            	break;
            case KeyEvent.VK_NUMPAD5:
            	if(direction!='U') {
            		direction='D';
            	}
            	break;
            	
           
            
            
            }

     }


    }


}
