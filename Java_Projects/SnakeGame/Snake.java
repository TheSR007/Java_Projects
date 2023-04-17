import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
public class Snake {
	private JFrame GameFrame;
		Snake() {
			GameFrame = new JFrame();
			GameFrame.setTitle("Snake-Game");
			GameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			GameFrame.setResizable(false);
			GameFrame.pack();
			GameFrame.add(new GamePanel());
			GameFrame.setVisible(true);
			GameFrame.setSize(600, 600);
			GameFrame.setLocationRelativeTo(null);
		}
	
		class GamePanel extends JPanel implements ActionListener{
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

		    boolean state = true;
		    boolean grid = false;
		    private JButton insane,veryhard,hard,normal,easy,veryeasy,line,exit,restart;
			private JButton[] button = new JButton[8];
		    
			GamePanel(){
		    	this.setPreferredSize(new Dimension(screen_width,screen_height));
		    	random=new Random();
		        this.setBackground(Color.BLACK);
		        this.setFocusable(true);
		        this.addKeyListener(new MykeyAdapter());
		        this.setLayout(null);
		        startGame();
		    }
		    public void paintComponent(Graphics g) {
		        super.paintComponent(g);
		        draw(g);
		        if(state) {
		        	g.setColor(Color.green);
		    		g.setFont(new Font("Arial",Font.BOLD,30));
		    		g.drawString("Snake Game", 200,30);
		    		g.drawLine(190, 40, 390, 40);
		    		g.setFont(new Font("Arial",Font.PLAIN,15));
		    		g.drawString("Choose Dificulty :", 200, 90);
		        }
		    }
		    public void startGame() {
		    	 if(state) {
		    	    
		    		 insane= new JButton("Insane");
		    		 veryhard = new JButton("Very Hard");
		         	 hard = new JButton("Hard");
		         	 normal = new JButton("Normal");
		         	 easy = new JButton("Easy");
		         	 veryeasy = new JButton("Very Easy");
		         	 exit = new JButton("Exit");
		         	 restart  = new JButton("Restart");
		         	 line = new JButton("Grid");
		         	button[0]=veryeasy;
		        	button[1]=easy;
		        	button[2]=normal;
		        	button[3]=hard;
		        	button[4]=veryhard;
		        	button[5]=insane;
		        	button[6]=line;
		        	button[7]=exit;
		        	button[0].setBounds(200, 110, 110, 25);
		        	button[1].setBounds(200, 150, 110, 25);
		        	button[2].setBounds(200, 190, 110, 25);
		        	button[3].setBounds(200, 230, 110, 25);
		        	button[4].setBounds(200, 270, 110, 25);
		        	button[5].setBounds(200, 310, 110, 25);
		        	button[6].setBounds(200, 350, 110, 25);
		        	button[7].setBounds(200, 390, 110, 25);
		        	
					for(int i = 0;i<8;i++) {
						button[i].setFocusable(false);
						button[i].setBackground(Color.black);
						button[i].setForeground(Color.green);
						button[i].addActionListener(this);
						this.add(button[i]);
					}
		    	 }
		    	 else {		
		    		 
		    		 do{for(int i = 0;i<8;i++)
		    			 this.remove(button[i]);
		    			 }while(state);
		    		 		newApple();
		    	 			running=true;
		    	 			timer=new Timer(delay,this);
		    	 			timer.start();
		    	 			}
		    
		    }
		    
		    public void draw(Graphics g) {
		        if(running) { 
		        	if(grid) {for(int i=0; i<screen_width/unit_size;i++) {
		        	g.drawLine(i*unit_size, 0, i*unit_size, screen_height);
		         	g.drawLine(0, i*unit_size, screen_width, i*unit_size);
		        			}}
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
			     if(!state && !running) {
			    	 g.setColor(Color.red);
				  g.setFont(new Font("Arial",Font.BOLD,30));
				  FontMetrics metric = getFontMetrics(g.getFont());
				  g.drawString("score: "+apples_eaten, (screen_width-metric.stringWidth("score: "+apples_eaten))/2,g.getFont().getSize());
		    	
		    	//gameover text
		    	  g.setColor(Color.red);
		          g.setFont(new Font("Arial",Font.BOLD,75));
		          FontMetrics metrics = getFontMetrics(g.getFont());
		          g.drawString("Game Over", (screen_width-metrics.stringWidth("Game Over"))/2,screen_height/2);
		          restart.setFocusable(false);
		          restart.setBackground(Color.black);
		          restart.setForeground(Color.green);
		          restart.addActionListener(this);
		          this.add(restart);
		          this.add(exit);
			     
			     
			     }
		    
		}
		    
			@Override
		    public void actionPerformed(ActionEvent e) {
		        if(running) {
		        	move();
		        	checkApple();
		        	checkCollision();
		        }
		        if(e.getSource()==normal) {
		        	screen_width=600;
		        	screen_height=600;
		        	delay=75;
		        	state=false;
		        	this.setPreferredSize(new Dimension(screen_width,screen_height));
		        	GameFrame.pack();
		        	startGame();
		        	restart.setBounds(220, 350, 150, 50);
		        	exit.setBounds(220, 425, 150, 50);
		        }
		        else if(e.getSource()==veryeasy) {
		        	screen_width=410;
		        	screen_height=410;
		        	delay=250;
		        	state=false;
		        	this.setPreferredSize(new Dimension(screen_width,screen_height));
		        	GameFrame.pack();
		        	GameFrame.setLocationRelativeTo(null);
		        	restart.setBounds(150, 250, 100, 35);
		        	exit.setBounds(150, 300, 100, 35);
		        	startGame();
		        }
		        else if(e.getSource()==easy) {
		        	screen_width=530;
		        	screen_height=530;
		        	delay=150;
		        	state=false;
		        	this.setPreferredSize(new Dimension(screen_width,screen_height));
		        	GameFrame.pack();
		        	GameFrame.setLocationRelativeTo(null);
		        	restart.setBounds(200, 330, 120, 45);
		        	exit.setBounds(200, 405, 120, 45);
		        	startGame();
		        }
		        else if(e.getSource()==hard) {
		        	screen_width=1200;
		        	screen_height=1000;
		        	delay=35;
		        	state=false;
		        	this.setPreferredSize(new Dimension(screen_width,screen_height));
		        	GameFrame.pack();
		        	GameFrame.setLocationRelativeTo(null);
		        	restart.setBounds(475, 570, 200, 70);
		        	exit.setBounds(475, 670, 200, 70);
		        	startGame();
		        }
		        else if(e.getSource()==veryhard) {
		        	screen_width=1500;
		        	screen_height=1000;
		        	delay=25;
		        	state=false;
		        	this.setPreferredSize(new Dimension(screen_width,screen_height));
		        	GameFrame.pack();
		        	GameFrame.setLocationRelativeTo(null);
		        	restart.setBounds(650, 580, 200, 70);
		        	exit.setBounds(650, 680, 200, 70);
		        	startGame();
		        }
		        else if(e.getSource()==insane) {
		        	screen_width=1900;
		        	screen_height=1000;
		        	delay=15;
		        	state=false;
		        	this.setPreferredSize(new Dimension(screen_width,screen_height));
		        	GameFrame.pack();
		        	GameFrame.setLocationRelativeTo(null);
		        	restart.setBounds(830, 580, 200, 70);
		        	exit.setBounds(830, 680, 200, 70);
		        	startGame();
		        }
		        else if(e.getSource()==line) {
		        	grid=true;
		        }
		        else if(e.getSource()==exit) {
		        		System.exit(0);
		        }
		        else if(e.getSource()==restart) {
		        	GameFrame.dispose();
		        	new Snake();
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
		public static void main(String[] args) {
			new Snake();
		
	}
}
