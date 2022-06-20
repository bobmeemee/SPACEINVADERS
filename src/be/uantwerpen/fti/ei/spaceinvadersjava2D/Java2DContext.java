package be.uantwerpen.fti.ei.spaceinvadersjava2D;

import be.uantwerpen.fti.ei.spaceinvaders.AbstractInput;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

/**
 * java2D grafische implementatie van het kader en de achtergrond
 */
public class Java2DContext {
    private int ScreenWidth;
    private int ScreenHeight;
    private int GameHeight;
    private int GameWidth;
    private JFrame frame;
    private JPanel panel;
    private BufferedImage g2dimage;     // used for drawing
    private Graphics2D g2d;             // always draw in this one
    public BufferedImage backgroundImg;
    public BufferedImage spaceShipSprite;
    public BufferedImage playerBulletSprite;
    public BufferedImage enemySprite;
    public BufferedImage letterSprite;
    public BufferedImage blueOrbSprite;
    public BufferedImage redOrbSprite;
    public BufferedImage greenOrbSprite;
    public BufferedImage gameOverSprite;


    private int SizeX;
    private int SizeY;
    private int score;
    private int lives;
    private int level;
    private boolean gameOverScreen;
    private boolean pauseScreen;
    private boolean bonusInUse;
    private int bonus;
    private String[] bonuses = {"none","speed", "freeze", "invincible"};
    private boolean bonusAvailable;

    public void setBonusAvailable(boolean bonusAvailable) { this.bonusAvailable = bonusAvailable; }

    private final Font font1;

    public void setBonusInUse(boolean bonusInUse) { this.bonusInUse = bonusInUse; }

    public void setBonus(int bonus) { this.bonus = bonus; }



    public void setLevel(int level) {
        this.level = level;
    }

    public void setGameOverScreen(boolean gameOverScreen) {
        this.gameOverScreen = gameOverScreen;
    }

    public void setPauseScreen(boolean pauseScreen) {
        this.pauseScreen = pauseScreen;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }


    public Graphics2D getG2d() {
        return g2d;
    }
    public JFrame getFrame() {
        return frame;
    }
    public int getSizeX() {
        return SizeX;
    }
    public int getSizeY() {return SizeY; }

    /**
     * loads the images into the variables
     */
    private void loadImages() {
        try{
            backgroundImg = null;
            backgroundImg = ImageIO.read(new File("src/resources/space-wallpaper.png"));
            spaceShipSprite = ImageIO.read(new File("src/resources/Spaceship.png"));
            playerBulletSprite = ImageIO.read(new File("src/resources/bullet.png"));
            enemySprite = ImageIO.read(new File("src/resources/enemy.png"));
            letterSprite = ImageIO.read(new File("src/resources/letters.png"));
            blueOrbSprite = ImageIO.read(new File("src/resources/blueorb.png"));
            redOrbSprite = ImageIO.read(new File("src/resources/redorb.png"));
            greenOrbSprite = ImageIO.read(new File("src/resources/greenorb.png"));
            gameOverSprite = ImageIO.read(new File("src/resources/gameover.png"));
        }
        catch(IOException e){
            System.out.println("Error");
        }
    }


    /**
     * default empty constructor
     */
    public Java2DContext(String filename) {
        Properties prop = new Properties();
        InputStream iS = null;
        try {
            iS = new FileInputStream(filename);
        } catch (FileNotFoundException ex) {
            System.out.println("config file not found");
        }
        try {
            prop.load(iS);
        } catch (IOException ex) {
        }
        ScreenWidth = Integer.parseInt(prop.getProperty("screenWidth")); //grafische screenwidth
        ScreenHeight = Integer.parseInt(prop.getProperty("screenHeight"));
        frame = new JFrame();
        panel = new JPanel(true) {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                doDrawing(g);
            }
        };
        frame.setFocusable(true);
        frame.add(panel);
        frame.setTitle("Space Invaders");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(ScreenWidth, ScreenHeight);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        font1 = new Font("Courier", Font.BOLD, 26);

    }

    /**
     * render the context
     */
    public void render() {
        panel.repaint();
    } //  repaint context

    /**
     * draw objects of context and wallpaper
     * @param g Graphicsobject to draw on
     */
    private void doDrawing(Graphics g) {
        Graphics2D graph2d = (Graphics2D) g;
        Toolkit.getDefaultToolkit().sync();
        graph2d.drawImage(g2dimage, 0, 0, null);   // copy buffered image
        graph2d.dispose();


        if (g2d != null) {
            g2d.drawImage(backgroundImg,0, 0, null);
            g2d.setFont(font1);
            Font font2 = new Font("Courier", Font.ITALIC, 16);;
            if(bonusInUse) {
                g2d.drawString("bonus in use : " +bonuses[bonus], ScreenWidth * 7 / 10, ScreenHeight * 9 / 10);
            }
            if(bonusAvailable) {
                g2d.drawString("bonus available : " +bonuses[bonus], ScreenWidth * 7 / 10, ScreenHeight * 9 / 10);
            }

            if(gameOverScreen) {
                g2d.drawImage(gameOverSprite, ScreenWidth / 4, 0, null);
                g2d.drawString("SCORE: " + score, ScreenWidth * 7 / 20, ScreenHeight * 5 / 10);
                g2d.drawString("LEVEL: " + level, ScreenWidth * 11 / 20, ScreenHeight * 5 / 10);
                g2d.drawString("press any key to restart", ScreenWidth * 4 / 10, ScreenHeight * 6 / 10);
            } else if(pauseScreen) {
                g2d.drawString("SCORE: " + score,10, 25);
                g2d.drawString("LIVES: " + lives, 200, 25);
                g2d.drawString("LEVEL: " + level, 1100, 25);
                g2d.drawString("game paused", ScreenWidth * 4 / 10, ScreenHeight * 5 / 10);
                g2d.drawString("press any key to restart", ScreenWidth * 4 / 10, ScreenHeight * 6 / 10);
            } else {
                g2d.drawString("SCORE: " + score,10, 25);
                g2d.drawString("LIVES: " + lives, 200, 25);
                g2d.drawString("LEVEL: " + level, 1100, 25);
            }
        }

    }

    /**
     * sets game dimensions
     * @param GameWidth breedte van het spel
     * @param GameHeight hoogte van het spel
     */
    public void setGameDimensions(int GameWidth, int GameHeight) {
        SizeX = ScreenWidth/GameWidth;
        SizeY = ScreenHeight/GameHeight;
        this.GameHeight = GameHeight;
        this.GameWidth = GameWidth;
        frame.setLocation(50,50);
        frame.setSize(ScreenWidth, ScreenHeight);
        loadImages();
        try {
            backgroundImg = resizeImage(backgroundImg, frame.getWidth(), frame.getHeight());
            spaceShipSprite = resizeImage(spaceShipSprite, SizeX*5, SizeY*5);
            playerBulletSprite = resizeImage(playerBulletSprite,SizeX,SizeY*4);
            enemySprite = resizeImage(enemySprite, SizeX*10,SizeY*10);
            greenOrbSprite = resizeImage(greenOrbSprite, SizeX*3, SizeY*3);
            redOrbSprite = resizeImage(redOrbSprite, SizeX*3, SizeY*3);
            blueOrbSprite = resizeImage(blueOrbSprite, SizeX*3, SizeY*3);
            gameOverSprite = resizeImage(gameOverSprite, SizeX*70, SizeY*70);
        } catch(Exception e) {
            System.out.println(e.getStackTrace());
        }
        g2dimage = new BufferedImage(frame.getWidth(), frame.getHeight(), BufferedImage.TYPE_4BYTE_ABGR_PRE);
        g2d = g2dimage.createGraphics();
        g2d.drawImage(backgroundImg,0, 0, null);
    }


    /**
     * a function that resizes images
     * @param originalImage original image from file
     * @param targetWidth desired width
     * @param targetHeight desired height
     * @return resizedimage
     */
    public BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight){
        Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_DEFAULT);
        BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_4BYTE_ABGR_PRE);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
        return outputImage;
    }
}

