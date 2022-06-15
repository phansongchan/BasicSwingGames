interface Window
{
	int WIDTH  = 500;
	int HEIGHT = 600;
	int DELAY  = 20;
}

class Gameplay extends javax.swing.JPanel implements java.awt.event.KeyListener, java.awt.event.ActionListener
{
	private javax.swing.Timer gTimer = new javax.swing.Timer( Window.DELAY, this );

	private java.awt.Image backgroundImg = new javax.swing.ImageIcon( "img/background.png" ).getImage();
	private java.awt.Image doodleImg = new javax.swing.ImageIcon( "img/doodle.png" ).getImage();
	private java.awt.Image platImg = new javax.swing.ImageIcon( "img/plat.png" ).getImage();

	private int platAmount = 6;

	private int[] platX = new int[ platAmount ];
	private int[] platY = new int[ platAmount ];
	

	private int px = 100, py = 100, dx = 7, dy = 0;
	
	public Gameplay()
	{
		gTimer.start();
		
		setBackground( java.awt.Color.WHITE );
		addKeyListener( this );
		setFocusable( true );
		setFocusTraversalKeysEnabled( false );

		newPlat();
	}

	private void newPlat()
	{
		for ( int i = 0; i < platAmount; i++ ) {
			platX[ i ] = (int)( Math.random() * Window.WIDTH / 2 );
			platY[ i ] = (int)( Math.random() * Window.HEIGHT ) - 10;
		}
	}
	
	public void paintComponent( java.awt.Graphics g )
	{
		super.paintComponent( g );

		// Bg
		g.drawImage( backgroundImg, 0, 0, null );

		// Pl
		g.drawImage( doodleImg, px, py, null );

		// Plat
		for ( int i = 0; i < platAmount; i++ ) {
			g.drawImage( platImg, platX[ i ], platY[ i ], null );
		}
	}

	@Override public void actionPerformed( java.awt.event.ActionEvent e )
	{
		dy += 1;
		py += dy;

		if ( py > Window.HEIGHT - 100 ) dy = -20;

		java.awt.Rectangle doodleRect = new java.awt.Rectangle( px, py, 100, 100 );
		
		for ( int i = 0; i < platAmount; i++ ) {
			java.awt.Rectangle platRect = new java.awt.Rectangle( platX[ i ], platY[ i ], 170, 30 );

			if ( doodleRect.intersects( platRect ) && py + 170 > platY[ i ] ) {
				dy = -20;
			}
		}

		if ( py < Window.HEIGHT / 2 ) {
			for ( int i = 0; i < platAmount; i++ ) {
				py = Window.HEIGHT / 2;
				platY[ i ] = platY[ i ] - dy;

				if ( platY[ i ] > Window.HEIGHT ) {
					platY[ i ] = 0;
					platX[ i ] = (int)( Math.random() * Window.WIDTH  );
				}
			}			
		}
		
		
		repaint();
	}

	@Override public void keyPressed( java.awt.event.KeyEvent e )
	{
		if ( e.getKeyCode() == java.awt.event.KeyEvent.VK_LEFT ) {
			px -= dx;
		}

		if ( e.getKeyCode() == java.awt.event.KeyEvent.VK_RIGHT ) {
			px += dx;
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
		setTitle( "Doodle Jump" );
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
