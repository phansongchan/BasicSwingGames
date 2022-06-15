interface Window
{
	int WIDTH  = 500;
	int HEIGHT = 500;
	int DELAY  = 50;
}


interface Size
{
	int TILE = 15;
	int GAME_TILES = ( Window.WIDTH * Window.HEIGHT ) / TILE;
}



class Gameplay extends javax.swing.JPanel implements java.awt.event.KeyListener, java.awt.event.ActionListener
{
	private javax.swing.Timer gTimer = new javax.swing.Timer( Window.DELAY, this );
	private int dir = 1;		// 1 is r, 2 is l, 3 is u, 4 is d
	private boolean isRunning;

	private int snakeBody = 5;


	private int[] snakeX = new int[ Size.GAME_TILES ];
	private int[] snakeY = new int[ Size.GAME_TILES ];

	private int foodX, foodY;

	public Gameplay()
	{
		gTimer.start();
		isRunning = true;
		
		setBackground( java.awt.Color.BLACK );
		addKeyListener( this );
		setFocusable( true );
		setFocusTraversalKeysEnabled( false );

		newFood();
	}

	public void paintComponent( java.awt.Graphics g )
	{
		super.paintComponent( g );

		// Snake
		for ( int i = 0; i < snakeBody; i++ ) {
			g.setColor( java.awt.Color.WHITE );
			g.fillRect( snakeX[ i ], snakeY[ i ], Size.TILE, Size.TILE );
		}

		// Food
		g.setColor( java.awt.Color.RED );
		g.fillRect( foodX, foodY, Size.TILE, Size.TILE );
	}

	private void newFood()
	{
		foodX = (int)( Math.random() * ( Window.WIDTH  / Size.TILE ) ) * Size.TILE;
		foodY = (int)( Math.random() * ( Window.HEIGHT / Size.TILE ) ) * Size.TILE;

		System.out.println( foodX + "," + foodY );

		if ( foodX >= 480 ) newFood();
		if ( foodY >= 450 ) newFood();
		if ( foodX % Size.TILE != 0 ) newFood();
		if ( foodY % Size.TILE != 0 ) newFood();
		
		for ( int i = 1; i < snakeBody; i++ ) {
			if ( snakeX[ i ] == foodX && snakeY[ i ] == foodY ) {
				newFood();
			}
		}
	}

	private void moveSnake()
	{
		for ( int i = snakeBody; i > 0; i-- ) {
			snakeX[ i ] = snakeX[ i - 1 ];
			snakeY[ i ] = snakeY[ i - 1 ];
		}

		
		switch ( dir ) {
		case 1:
			snakeX[ 0 ] += Size.TILE;
			break;
		case 2:
			snakeX[ 0 ] -= Size.TILE;
			break;
		case 3:
			snakeY[ 0 ] -= Size.TILE;
			break;
		case 4:
			snakeY[ 0 ] += Size.TILE;
			break;
		}

	}

	private void checkLogic()
	{
		if ( snakeX[ 0 ] < 0			)  snakeX[ 0 ] = Window.WIDTH;
		if ( snakeX[ 0 ] > Window.WIDTH  ) snakeX[ 0 ] = 0;
		if ( snakeY[ 0 ] < 0			 ) snakeY[ 0 ] = Window.HEIGHT;
		if ( snakeY[ 0 ] > Window.HEIGHT ) snakeY[ 0 ] = 0;

		if ( snakeX[ 0 ] == foodX && snakeY[ 0 ] == foodY ) {
			snakeBody++;
			newFood();
		}

		for ( int i = snakeBody; i > 0; i-- ) {
            if ( snakeX[ 0 ] == snakeX[ i ] && snakeY[ 0 ] == snakeY[ i ] ) {
                isRunning = false;
            }			
        }
	}
	

	@Override public void actionPerformed( java.awt.event.ActionEvent e )
	{
		if ( isRunning ) {
			moveSnake();
			checkLogic();
		}

		if ( !isRunning ) {
			gTimer.stop();
			System.out.println( "GAME OVER" );
		}
		repaint();
	}

	@Override public void keyPressed( java.awt.event.KeyEvent e )
	{
		switch ( e.getKeyCode() ) {
		case java.awt.event.KeyEvent.VK_RIGHT:
			if ( dir != 2 ) dir = 1;
			break;
		case java.awt.event.KeyEvent.VK_LEFT:
			if ( dir != 1 ) dir = 2;
			break;			
		case java.awt.event.KeyEvent.VK_UP:
			if ( dir != 4 ) dir = 3;
			break;
		case java.awt.event.KeyEvent.VK_DOWN:
			if ( dir != 3 ) dir = 4;
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
		setTitle( "Snake" );
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

