interface Window
{
	int WIDTH  = 700;
	int HEIGHT = 500;
	int DELAY  = 20;
}

class Gameplay extends javax.swing.JPanel implements java.awt.event.ActionListener, java.awt.event.KeyListener
{
	boolean isRunning = true;
	int frameCount = 0;
	javax.swing.Timer gTimer = new javax.swing.Timer( Window.DELAY, this );
	
	// Player A value
	int ax = 100;
	int ay = Window.HEIGHT / 2;
	int ar = 40;
	int adx = 20;
	int ady = 20;
	int aScore = 0;
	
	// Player B value
	int bx = Window.WIDTH - ax - 50;
	int by = Window.HEIGHT / 2;
	int br = 40;
	int bdx = 20;
	int bdy = 20;
	int bScore = 0;
	
	// Ball
	int ballX = Window.WIDTH  / 2;
	int ballY = Window.HEIGHT / 2;
	int ballR = 20;
	int ballDX = -5;
	int ballDY = -5;
	
	// Border
	int borderX = Window.WIDTH  / 2 - 10;
	int borderY = 0;
	int boderWidth = 5;
	int boderHeight = Window.HEIGHT;
	
	
	// A Goal
	int goalAWidth  = 15;
	int goalAHeight = 200;
	int goalAX = 0;
	int goalAY = Window.HEIGHT / 2 - 100;
	
	
	// B Goal
	int goalBWidth  = 15;
	int goalBHeight = 200;
	int goalBX = Window.WIDTH - goalAWidth * 2;
	int goalBY = Window.HEIGHT / 2 - 100;
	
	public Gameplay()
	{
		gTimer.start();
		addKeyListener( this );
		setFocusable( true );
		setFocusTraversalKeysEnabled( false );
		setBackground( new java.awt.Color( 157, 181, 204 ) );	
	}
	
	public void paintComponent( java.awt.Graphics g )
	{
		super.paintComponent( g );
		
		java.awt.Graphics2D g2d = (java.awt.Graphics2D)g;

		g2d.setRenderingHint(
							java.awt.RenderingHints.KEY_ANTIALIASING,
							java.awt.RenderingHints.VALUE_ANTIALIAS_ON );

		g2d.setRenderingHint(
							java.awt.RenderingHints.KEY_TEXT_ANTIALIASING,
							java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_ON );
		
		g.setColor( java.awt.Color.RED );
		g.fillOval( ax, ay, ar, ar );
		
		g.setColor( java.awt.Color.BLUE );
		g.fillOval( bx, by, br, br );
		
		g.setColor( java.awt.Color.YELLOW );
		g.fillOval( ballX, ballY, ballR, ballR );
		
		g.setColor( java.awt.Color.BLACK );
		g.fillRect( borderX, borderY, boderWidth, boderHeight );
		
		g.fillRect( goalAX, goalAY, goalAWidth, goalAHeight );
		g.fillRect( goalBX, goalBY, goalBWidth, goalBHeight );
		
		g.setFont( new java.awt.Font( "Courier New", java.awt.Font.BOLD, 30 ) );
		g.setColor( java.awt.Color.BLACK );
		g.drawString( "A:" + aScore, 10, 40 );
		g.drawString( "B:" + bScore, Window.WIDTH - 100, 40 );
		g.setColor( java.awt.Color.GRAY );
		g.drawString( "TIME: " + frameCount, Window.WIDTH / 2 - 60, 40 );
		
		if ( frameCount > 0 && frameCount < 400 ) {
			g.setFont( new java.awt.Font( "Courier New", java.awt.Font.BOLD, 15 ) );
			g.setColor( java.awt.Color.BLACK );
			g.drawString( "Press Q to end the game.", 10, Window.HEIGHT - 100 );
		}
		
		if ( isRunning == false ) {
			displayGameOver( g );
		}			
	}
	
	private void displayGameOver( java.awt.Graphics g )
	{
		g.setFont( new java.awt.Font( "Courier New", java.awt.Font.BOLD, 50 ) );
		g.setColor( java.awt.Color.RED );
		g.drawString( "GAME END!", Window.WIDTH / 2 - 120, Window.HEIGHT / 2 );
	}
	
	private void spawnBall()
	{
		ballX = (int)( Math.random() * ( Window.WIDTH / 2 - Window.WIDTH / 2 - 40 ) ) + Window.WIDTH / 2 - 40;
		ballY = (int)( Math.random() * ( Window.HEIGHT / 2 - Window.HEIGHT / 2 - 40 ) ) + Window.HEIGHT / 2 - 40;
	}
	
	
	
	@Override public void actionPerformed( java.awt.event.ActionEvent e )
	{
		frameCount++;
		ballX += ballDX;
		ballY += ballDY;
		
		if ( frameCount % 200 == 0 ) ballDX -= 1;
		
		if ( ballY < 0 || ballY > Window.HEIGHT - ballR * 2 ) ballDY *= -1;
		
		java.awt.Rectangle ballRect = new java.awt.Rectangle( ballX, ballY, ballR, ballR );
		
		
		if ( ballX >= ax && ballX <= ax + ar / 2 && ballY >= ay && ballY <= ay + ar / 2 ) {
			ballDX *= -1;
		}
		
		if ( ballX >= bx && ballX <= bx + br / 2 && ballY >= by && ballY <= by + br / 2 ) {
			ballDX *= -1;
		}
		
		if ( ballRect.intersects( new java.awt.Rectangle( goalAX, goalAY, goalAWidth, goalAHeight ) ) ) {
			bScore++;
			spawnBall();
		}
		
		if ( ballRect.intersects( new java.awt.Rectangle( goalBX, goalBY, goalBWidth, goalBHeight ) ) ) {
			aScore++;
			spawnBall();
		}
		
		if ( ballX < 0 || ballX > Window.WIDTH - ballR * 2 ) {
			spawnBall();
		}
		
		
		if ( ax >= borderX - ar ) ax = borderX - ar;
		if ( ax < 0 ) ax = 0;
		if ( bx <= borderX ) bx = borderX;
		if ( bx > Window.WIDTH - br ) bx = Window.WIDTH - br;
		repaint();
	}
	
	@Override public void keyPressed( java.awt.event.KeyEvent e )
	{
		switch ( e.getKeyCode() ) {
			case java.awt.event.KeyEvent.VK_W:
				ay -= ady;
				break;
			case java.awt.event.KeyEvent.VK_S:
				ay += ady;
				break;
			case java.awt.event.KeyEvent.VK_A:
				ax -= adx;
				break;
			case java.awt.event.KeyEvent.VK_D:
				ax += adx;
				break;
				
			case java.awt.event.KeyEvent.VK_UP:
				by -= bdy;
				break;
			case java.awt.event.KeyEvent.VK_DOWN:
				by += bdy;
				break;
			case java.awt.event.KeyEvent.VK_LEFT:
				bx -= bdx;
				break;
			case java.awt.event.KeyEvent.VK_RIGHT:
				bx += bdx;
				break;
			
			case java.awt.event.KeyEvent.VK_Q:
				gTimer.stop();
				isRunning = false;
				break;
		}
	}
	
	@Override public void keyReleased( java.awt.event.KeyEvent e )
	{}
	
	@Override public void keyTyped( java.awt.event.KeyEvent e )
	{}
}



class Main extends javax.swing.JFrame
{
	public Main()
	{
		pack();
		add( new Gameplay() );
		setTitle( "Air Hockey!" );
		setSize( Window.WIDTH, Window.HEIGHT );
		setDefaultCloseOperation( javax.swing.JFrame.EXIT_ON_CLOSE );
		setLocationRelativeTo( null );
		setVisible( true );
	}
	
	public static void main( String[] args )
	{
		new Main();
	}
}
