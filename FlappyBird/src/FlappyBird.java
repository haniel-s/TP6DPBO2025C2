import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    int frameWidth = 360;

    int frameHeight = 640;

    // image attributes
    Image backgroundImage;
    Image birdImage;
    Image lowerPipeImage;
    Image upperPipeImage;

    // player
    int playerStartPosX = frameWidth / 8;
    int playerStartPosY = frameHeight / 2;
    int playerWidth = 34;
    int playerHeight = 24;
    Player player;

    //pipes attributes
    int pipeStartPosX = frameWidth;
    int pipeStartPosY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;
    ArrayList<Pipe> pipes;

    Timer gameLoop;
    Timer pipesCooldown;
    int gravity = 1;

    // buat bikin gameover
    boolean gameOver = false;

    // track score-nya
    private int score = 0;
    private JLabel scoreLabel;
    // constructor
    public FlappyBird(){
        setPreferredSize(new Dimension(frameWidth,frameHeight));
        setFocusable(true);
        addKeyListener(this);
        //setBackground(Color.blue);

        backgroundImage = new ImageIcon(getClass().getResource("assets/background.png")).getImage();
        birdImage = new ImageIcon(getClass().getResource("assets/bird.png")).getImage();
        lowerPipeImage = new ImageIcon(getClass().getResource("assets/lowerPipe.png")).getImage();
        upperPipeImage = new ImageIcon(getClass().getResource("assets/upperPipe.png")).getImage();

        player = new Player(playerStartPosX,playerStartPosY,playerWidth,playerHeight,birdImage);
        pipes = new ArrayList<Pipe>();

        //tampilin score
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setBounds(10, 10, 100, 30);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 18));
        scoreLabel.setForeground(Color.WHITE);
        setLayout(null); // Use absolute positioning
        add(scoreLabel);

        pipesCooldown = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("pipa");
                if (gameOver!=true){
                    placePipes();
                }
            }
        });
        pipesCooldown.start();

        gameLoop = new Timer(1000/60,this);
        gameLoop.start();
    }

    private void updateScore() {
        score++;
        scoreLabel.setText("Score: " + score);
    }

    public void restartGame(){
        player = new Player(playerStartPosX, playerStartPosY, playerWidth, playerHeight, birdImage);
        pipes.clear();
        score = 0; // Reset score
        scoreLabel.setText("Score: 0");
        gameOver = false;
        gameLoop.start();
        pipesCooldown.start();
        for(int i = 0; i < pipes.size(); i++){
            Pipe pipe = pipes.get(i);
            pipe.setPosX(pipe.getPosX() + pipe.getVelocityX());

            // If this is an upper pipe (assuming these are added in pairs)
            if (pipe.getPosY() <= 0) {
                // Check if player just passed this pipe
                if (player.getPosX() > pipe.getPosX() + pipe.getWidth() &&
                        pipe.getpassed() == false) {
                    pipe.setpassed(true);
                    updateScore();
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        g.drawImage(backgroundImage,0,0,frameWidth,frameHeight,null);

        g.drawImage(player.getImage(),player.getPosX(),player.getPosY(),player.getWidth(),player.getHeight(), null);

        for (int i = 0;i<pipes.size();i++){
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.getImage(),pipe.getPosX(),pipe.getPosY(),pipe.getWidth(),pipe.getHeight(),null);
        }

        if (gameOver==true) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("Game Over!", frameWidth/2 - 80, frameHeight/2);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("Press R to restart", frameWidth/2 - 80, frameHeight/2 + 40);
        }
    }




    public void move(){
        if (gameOver==true) return;
        player.setvelocityY(player.getvelocityY()+gravity);
        player.setPosY(player.getPosY()+player.getvelocityY());
        if (player.getPosY() + player.getHeight() > frameHeight) {
            gameOver = true;
            gameLoop.stop();
            pipesCooldown.stop();
            return;
        }
        player.setPosY(Math.max(player.getPosY(),0));

        for(int i = 0;i<pipes.size();i++){
            Pipe pipe = pipes.get(i);
            pipe.setPosX(pipe.getPosX()+pipe.getVelocityX());
        }
    }

    public void placePipes(){
        int randomPosY=(int)(pipeStartPosY-pipeHeight/4-Math.random()*(pipeHeight/2));
        int openingSpace = frameHeight/4;

        Pipe upperPipe = new Pipe(pipeStartPosX,pipeStartPosY,pipeWidth,pipeHeight,upperPipeImage);
        pipes.add(upperPipe);

        Pipe lowerPipe = new Pipe(pipeStartPosX,(randomPosY+openingSpace+pipeHeight),pipeWidth,pipeHeight,lowerPipeImage);
        pipes.add(lowerPipe);

    }

    @Override
    public void actionPerformed(ActionEvent e){
        move();
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e){

    }

    @Override
    public void keyPressed(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_SPACE && !gameOver){
            player.setvelocityY(-10);
        }
        if(e.getKeyCode() == KeyEvent.VK_R && gameOver){
            restartGame();
        }
    }

    @Override
    public void keyReleased(KeyEvent e){

    }

}
