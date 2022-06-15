interface Window
{
	int WIDTH  = 700;
	int HEIGHT = 500;
	int DELAY  = 20;
}


class Gameplay extends javax.swing.JPanel implements java.awt.event.KeyListener, java.awt.event.ActionListener
{

	private javax.swing.Timer gTimer = new javax.swing.Timer( Window.DELAY, this );


	private java.awt.Image bgImg = new javax.swing.ImageIcon( "img/background.png" ).getImage();
	private java.awt.Image bg2Img = new javax.swing.ImageIcon( "img/background.png" ).getImage();
	private java.awt.Image cloudImg = new javax.swing.ImageIcon( "img/cloud.png" ).getImage();
	private java.awt.Image dinoImg = new javax.swing.ImageIcon( "img/dino.png" ).getImage();
	private java.awt.Image cactusImg = new javax.swing.ImageIcon( "img/cactus.png" ).getImage();

	private int cloudX = Window.WIDTH - 100;

	private int px = 25, py = Window.HEIGHT - 180, dx = 160, dy = 170;

	private int cx = Window.WIDTH, cy = py, cdx = 8;

	private int bgX = 0;

	private boolean canJump = false;

	private int score = 0;
	private int pushBack = 7;


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

		java.awt.Graphics2D g2d = (java.awt.Graphics2D)g;

        g2d.setRenderingHint(
                             java.awt.RenderingHints.KEY_ANTIALIASING,
                             java.awt.RenderingHints.VALUE_ANTIALIAS_ON );

        // You can also enable antialiasing for text:

        g2d.setRenderingHint(
                             java.awt.RenderingHints.KEY_TEXT_ANTIALIASING,
                             java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_ON );
		
		g.drawImage( bgImg, bgX, 0, null );
		g.drawImage( cloudImg, cloudX, 20, null );
		g.drawImage( dinoImg, px, py, null );
		g.drawImage( cactusImg, cx, cy, null );

		g.setColor( java.awt.Color.BLACK );
		g.setFont( new java.awt.Font( "serif", java.awt.Font.PLAIN, 30 ) );
		g.drawString( "SCORE " + score, 10, 40);
				   
	}

	@Override public void actionPerformed( java.awt.event.ActionEvent e )
	{
		score++;
		cloudX -= 10;
		cx -= cdx;
		
		if ( px > 30 ) px -= pushBack;
		if ( cloudX < -100 ) cloudX = Window.WIDTH - 100;
		if ( cx < -150 ) cx = Window.WIDTH - (int)( Math.random() * 60 ) + 10;
		
		if ( new java.awt.Rectangle( px, py, 100, 100 ).intersects( new java.awt.Rectangle( cx, cy, 80, 80 ) ) ) {
			if ( cx >= px && cx + 30 <= px + 100 && cy <= py && cy + 100 >= py + 100 ) {
				gTimer.stop();
				System.out.println( "Game over!" );
			}
		}

		if ( score % 100 == 0 ) {
			pushBack++;
			cdx += 1;
		}
		
		if ( py == Window.HEIGHT - 180 ) {
			canJump = true;
		}

		repaint();
	}

	@Override public void keyPressed( java.awt.event.KeyEvent e )
	{
		if ( e.getKeyCode() == java.awt.event.KeyEvent.VK_SPACE ) {
			if ( canJump ) {
				py -= dy;	
				px += dx;
				canJump = false;
			}
		}
	}
	
	@Override public void keyTyped( java.awt.event.KeyEvent e ) {}	

	@Override public void keyReleased( java.awt.event.KeyEvent e ) {
		if ( e.getKeyCode() == java.awt.event.KeyEvent.VK_SPACE ) {
			if ( !canJump ) {
				if ( py < Window.HEIGHT - 180 ) {
					py += dy;
					canJump = true;
				}
			}
		}		
	}
}


class Main extends javax.swing.JFrame
{

	public Main()
	{
		pack();
		add( new Gameplay() );
		setTitle( "Dinosaur" );
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
