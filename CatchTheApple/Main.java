interface Window
{
	int WIDTH  = 660;
	int HEIGHT = 440;
	int DELAY  = 20;
}

class Gameplay extends javax.swing.JPanel implements java.awt.event.KeyListener, java.awt.event.ActionListener
{
	private javax.swing.Timer gTimer = new javax.swing.Timer( Window.DELAY, this );

	private java.awt.Image backgroundImg = new javax.swing.ImageIcon( "img/background.png" ).getImage();
	private java.awt.Image bowlImg = new javax.swing.ImageIcon( "img/bowl.png" ).getImage();
	private java.awt.Image appleImg = new javax.swing.ImageIcon( "img/apple.png" ).getImage();

	private int px = Window.WIDTH / 2, py = Window.HEIGHT - 60 - 40, dx = 7, health = 5;

	private int appleX;
	private int appleY;
	private int appleDY = 4;

	private boolean isLose = false;

	private int frameCount = 0;

	private void newApple()
	{
		appleX = (int)( Math.random() * ( Window.WIDTH - 60 ) ) + 60;
		
		if ( Window.WIDTH - appleX <= 60 ) newApple();
			
		appleY = 0;
	}
	
	public Gameplay()
	{

		gTimer.start();
		
		setBackground( java.awt.Color.BLACK );
		addKeyListener( this );
		setFocusable( true );
		setFocusTraversalKeysEnabled( false );

		newApple();
	}

	public void paintComponent( java.awt.Graphics g )
	{
		super.paintComponent( g );

		// Bg
		g.drawImage( backgroundImg, 0, 0, null );

		// Pl
		g.drawImage( bowlImg, px, py, null );

		// Ap
		g.drawImage( appleImg, appleX, appleY, null );

		// heath
		g.setColor( java.awt.Color.RED );
		g.setFont( new java.awt.Font( "MS Gothic", java.awt.Font.PLAIN, 20 ) );
		g.drawString( "HEALTH " + health, 10, 20 );

		// time
		g.drawString( "TIME " + frameCount, 10, 40 );

		// Game over
		if ( isLose ) {
			g.setFont( new java.awt.Font( "MS Gothic", java.awt.Font.PLAIN, 40 ) );
			g.drawString( "GAME OVER", Window.WIDTH / 2 - 120, Window.HEIGHT / 2 );
		}
	}

	@Override public void actionPerformed( java.awt.event.ActionEvent e )
	{
		frameCount++;
		appleY += appleDY;

		if ( frameCount % 500 == 0 ) {
			dx += 1;
		}

		if ( frameCount % 300 == 0 ) {
			appleDY += 1;
		}

		if ( px < 0 ) px = Window.WIDTH;
		if ( px > Window.WIDTH ) px = 0;

		java.awt.Rectangle appleRect = new java.awt.Rectangle( appleX, appleY, 60, 60 );
		java.awt.Rectangle playerRect = new java.awt.Rectangle( px, py, 130, 60 );

		if ( appleRect.intersects( playerRect ) && appleX >= px && appleX + 60 <= px + 130 ) {
			newApple();
			health++;
		}

		if ( appleY > Window.HEIGHT - 60 ) {
			health--;
			newApple();
		}

		if ( health <= 0 ) {
			gTimer.stop();
			isLose = true;
		}
		
		repaint();
	}
	
	@Override public void keyPressed( java.awt.event.KeyEvent e )
	{
		switch( e.getKeyCode() ) {
		case java.awt.event.KeyEvent.VK_RIGHT:
			px += dx;
			break;
		case java.awt.event.KeyEvent.VK_LEFT:
			px -= dx;
			break;
		}
	}
	
	@Override public void keyTyped( java.awt.event.KeyEvent e ) {}
	@Override public void keyReleased( java.awt.event.KeyEvent e ) {}

}


class Main extends javax.swing.JFrame
{

	public Main()
	{
		pack();
		add( new Gameplay() );
		setTitle( "Catch The Apple" );
		setSize( Window.WIDTH, Window.HEIGHT );	
		setLocationRelativeTo( null );
		setDefaultCloseOperation( javax.swing.JFrame.EXIT_ON_CLOSE );
		setVisible( true );
	}
	
	public static void main( String[] args )
	{
		new Main();
	}
}
