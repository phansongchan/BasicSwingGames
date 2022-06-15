interface Window
{
	int WIDTH  = 600;
	int HEIGHT = 600;
	int DELAY  = 20;
}


class BlockGenerator
{
	int[][] blocks;
	int bWidth;
	int bHeight;
	
	public BlockGenerator( int row, int col )
	{
		blocks = new int[ row ][ col ];

		for ( int i = 0; i < blocks.length; i++ ) {
			for ( int j = 0; j < blocks[ 0 ].length; j++ ) {
				blocks[ i ][ j ] = 1;
			}
		}

		bWidth  = 100;
		bHeight = 20;
	}

	public void draw( java.awt.Graphics g )
	{
		g.setColor( new java.awt.Color(
									   (int)( Math.random() * 255 ),
									   (int)( Math.random() * 255 ),
									   (int)( Math.random() * 255 )
									   ) );

		for ( int i = 0; i < blocks.length; i++ ) {
			for ( int j = 0; j < blocks[ 0 ].length; j++ ) {
				if ( blocks[ i ][ j ] > 0 ) {
					g.drawRect( bWidth * i + 40, bHeight * j + 30, bWidth, bHeight ); 
				}
			}
		}
	}

	public void setValue( byte n, int ro, int co )
	{
		blocks[ ro ][ co ] = n;
	}
}


class Gameplay extends javax.swing.JPanel implements java.awt.event.KeyListener, java.awt.event.ActionListener
{

	private boolean isTouched( java.awt.Rectangle r1, java.awt.Rectangle r2 )
	{
		return ( r1.intersects( r2 ) );
	}


	private BlockGenerator bg = new BlockGenerator( 5, 10 );
	
	private javax.swing.Timer gTimer = new javax.swing.Timer( Window.DELAY, this );

	private int px = Window.WIDTH / 2, py = Window.HEIGHT - 80, pw = 100, ph = 10;
	private int dx = 14;

	private int totalBlocks = 50;

	private int ballX = 400, ballY = 300, ballR = 15, ballDX = -4, ballDY = -4;
	
	public Gameplay()
	{
		setBackground( java.awt.Color.BLACK );
		addKeyListener( this );
		setFocusable( true );
		setFocusTraversalKeysEnabled( false );

		gTimer.start();
	}

	public void paintComponent( java.awt.Graphics g )
	{
		super.paintComponent( g );

		// Pl
		g.setColor( java.awt.Color.WHITE );
		g.fillRect( px, py, pw, ph );

		// Ball
		g.fillRect( ballX, ballY, ballR, ballR );

		// Block
		bg.draw( g );
	}

	@Override public void actionPerformed( java.awt.event.ActionEvent e )
	{
		ballX += ballDX;
		ballY += ballDY;

		if ( ballX < 0 || ballX > Window.WIDTH - ballR ) ballDX *= -1;
		if ( ballY < 0 ) ballDY *= -1;
		if ( ballY > Window.HEIGHT - ballR ) {
			gTimer.stop();
			System.out.println( "GAME OVER" );
		}

		if ( totalBlocks == 0 ) {
			gTimer.stop();
			System.out.println( "GAME CLEAR" );
		}

		java.awt.Rectangle ballRect = new java.awt.Rectangle( ballX, ballY, ballR, ballR );
		java.awt.Rectangle playerRect = new java.awt.Rectangle( px, py, pw, ph );

		if ( isTouched( ballRect, playerRect ) ) {
			ballDY *= -1;			
		}

		for ( int i = 0; i < bg.blocks.length; i++ ) {
			for ( int j = 0; j < bg.blocks[ 0 ].length; j++ ) {
				if ( bg.blocks[ i ][ j ] > 0 ) {
					java.awt.Rectangle blockRect = new java.awt.Rectangle( bg.bWidth * i + 40, bg.bHeight * j + 30, bg.bWidth, bg.bHeight );

					if ( isTouched( blockRect, ballRect ) ) {
						bg.setValue( (byte)0, i, j );
						ballDY *= -1;
						totalBlocks--;
						// ballDX *= -1;
					}
				}
			}
		}
		
		repaint();
	}

	@Override public void keyPressed( java.awt.event.KeyEvent e )
	{
		switch ( e.getKeyCode() ) {
		case java.awt.event.KeyEvent.VK_RIGHT:
			px += dx;
			break;
		case java.awt.event.KeyEvent.VK_LEFT:
			px -= dx;
			break;
		}
	}
	@Override public void keyReleased( java.awt.event.KeyEvent e ) {}
	@Override public void keyTyped( java.awt.event.KeyEvent e ) {}

}

class Main extends javax.swing.JFrame
{
	public Main()
	{
		pack();
		add( new Gameplay() );
		setTitle( "Breakout" );
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
