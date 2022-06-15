interface Window
{
	int WIDTH  = 600;
	int HEIGHT = 600;
	int DELAY  = 20;
}


class Gameplay extends javax.swing.JPanel implements java.awt.event.KeyListener, java.awt.event.ActionListener
{

	public boolean isTouched( java.awt.Rectangle r1, java.awt.Rectangle r2 )		
	{
		if ( r1.intersects( r2 ) ) return true;
		return false;
	}
	
	private int ballX = 400, ballY = 300, ballDX = -4, ballDY = -4;

	private int pAX = 30, pAY = Window.HEIGHT / 2;
	private int pBX = Window.WIDTH - 30 - 10, pBY = Window.HEIGHT / 2;

	private int dy = 20;

	private javax.swing.Timer gTimer = new javax.swing.Timer( Window.DELAY, this );
	
	public Gameplay()
	{

		gTimer.start();
		
		setBackground( java.awt.Color.BLACK );
		addKeyListener( this );
		setFocusable( true );
		setFocusTraversalKeysEnabled( false );
	}

	public void paintComponent( java.awt.Graphics g )
	{
		super.paintComponent( g );

		g.setColor( java.awt.Color.WHITE );

		g.fillRect( ballX, ballY, 10, 10 );

		g.fillRect( pAX, pAY, 10, 100 );
		g.fillRect( pBX, pBY, 10, 100 );
	}

	@Override public void actionPerformed( java.awt.event.ActionEvent e )
	{
		ballX += ballDX;
		ballY += ballDY;

		if ( ballY < 0 ) ballDY *= -1;
		if ( ballY > Window.HEIGHT - 20 * 2 - 20 ) ballDY *= -1;

		if ( ballX < 0 || ballX > Window.HEIGHT ) {
			ballX = 400;
			ballY = 300;
		}

		if ( isTouched( new java.awt.Rectangle( ballX, ballY, 10, 10 ), new java.awt.Rectangle( pAX, pAY, 10, 100 ) ) ) {
			ballDX *= -1;
		}

		if ( isTouched( new java.awt.Rectangle( ballX, ballY, 10, 10 ), new java.awt.Rectangle( pBX, pBY, 10, 100 ) ) ) {
			ballDX *= -1;
		}

		repaint();
	}

	@Override public void keyPressed( java.awt.event.KeyEvent e )
	{
		switch( e.getKeyCode() ) {
		case java.awt.event.KeyEvent.VK_UP:
			pBY -= dy;
			break;
		case java.awt.event.KeyEvent.VK_DOWN:
			pBY += dy;
			break;
		case java.awt.event.KeyEvent.VK_W:
			pAY -= dy;
			break;
		case java.awt.event.KeyEvent.VK_S:
			pAY += dy;
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
		setTitle( "Pong" );
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
