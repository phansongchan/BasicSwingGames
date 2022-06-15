interface Window
{
	int WIDTH  = 400;
	int HEIGHT = 600;
	int DELAY  = 20;
}

class Gameplay extends javax.swing.JPanel implements java.awt.event.KeyListener, java.awt.event.ActionListener
{
	private javax.swing.Timer gTimer = new javax.swing.Timer( Window.DELAY, this );

	private int px = 25, py = 300, pr = 20, dy = 0;

	private int score = 0;
	
	private int pipeY = 0;
	private int pipeDistance = 150;
	private int pipe1X = 200;
	private int pipe2X = pipe1X * 2;
	private int pipe3X = pipe1X * 3;

	private int pipeWidth = 30;
	private int pipe1Height = (int)( Math.random() * 300 );
	private int pipe2Height = (int)( Math.random() * 300 );
	private int pipe3Height = (int)( Math.random() * 300 );
	
	
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
		g.fillRect( px, py, pr, pr );

		// PIPE
		g.fillRect( pipe1X, pipeY, pipeWidth, pipe1Height );
		g.fillRect( pipe2X, pipeY, pipeWidth, pipe2Height );
		g.fillRect( pipe3X, pipeY, pipeWidth, pipe3Height );

		g.fillRect( pipe1X, pipeDistance + pipe1Height, pipeWidth, Window.HEIGHT - pipeDistance - pipe1Height );
		g.fillRect( pipe2X, pipeDistance + pipe2Height, pipeWidth, Window.HEIGHT - pipeDistance - pipe2Height );
		g.fillRect( pipe3X, pipeDistance + pipe3Height, pipeWidth, Window.HEIGHT - pipeDistance - pipe3Height );

		// SCORE
		g.setColor( java.awt.Color.YELLOW );
		g.setFont( new java.awt.Font( "serif", java.awt.Font.PLAIN, 20 ) );
		g.drawString( "SCORE " + score, 10, 20 );
	}

	int pipeDX = 2;
	int count = 0;

	private boolean isTouched( java.awt.Rectangle r1,  java.awt.Rectangle r2 )
	{
		return r1.intersects( r2 );
	}
	
	@Override public void actionPerformed( java.awt.event.ActionEvent e )
	{
		count++;
		score++;
		dy += 1;
		py += dy;

		
		pipe1X -= pipeDX;
		pipe2X -= pipeDX;
		pipe3X -= pipeDX;

		if ( count % 300 == 0 ) pipeDX += 1;

		if ( pipe1X < -Window.WIDTH ) {
			pipe1X = Window.WIDTH;
			pipe1Height = (int)( Math.random() * 300 );
		} else if ( pipe2X < -Window.WIDTH ) {
			pipe2X = Window.WIDTH;
			pipe2Height = (int)( Math.random() * 300 );
		} else if ( pipe3X < -Window.WIDTH ) {
			pipe3X = Window.WIDTH;
			pipe3Height = (int)( Math.random() * 300 );
		}




		java.awt.Rectangle playerRect = new java.awt.Rectangle( px, py, pr, pr );
		
		
		if ( py > Window.HEIGHT || py < 0 ) gTimer.stop();

		// COLLISION
		if ( isTouched( playerRect, new java.awt.Rectangle( pipe1X, pipeY, pipeWidth, pipe1Height ) ) ) {
			gTimer.stop();
		}

		if ( isTouched( playerRect, new java.awt.Rectangle( pipe2X, pipeY, pipeWidth, pipe2Height ) ) ) {
			gTimer.stop();
		}

		if ( isTouched( playerRect, new java.awt.Rectangle( pipe3X, pipeY, pipeWidth, pipe3Height ) ) ) {
			gTimer.stop();
		}

		if ( isTouched( playerRect, new java.awt.Rectangle( pipe1X, pipeDistance + pipe1Height, pipeWidth, Window.HEIGHT - pipeDistance - pipe1Height ) ) ) {
			gTimer.stop();
		}

		if ( isTouched( playerRect, new java.awt.Rectangle( pipe2X, pipeDistance + pipe2Height, pipeWidth, Window.HEIGHT - pipeDistance - pipe2Height ) ) ) {
			gTimer.stop();
		}

		if ( isTouched( playerRect, new java.awt.Rectangle( pipe3X, pipeDistance + pipe3Height, pipeWidth, Window.HEIGHT - pipeDistance - pipe3Height ) ) ) {
			gTimer.stop();
		}

		repaint();
	}

	@Override public void keyPressed( java.awt.event.KeyEvent e )
	{
		switch( e.getKeyCode() ) {
		case java.awt.event.KeyEvent.VK_SPACE:
			dy = -10;
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
		setTitle( "Flappy Bird" );
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
