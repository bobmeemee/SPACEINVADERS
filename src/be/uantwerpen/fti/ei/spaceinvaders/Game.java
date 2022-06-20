package be.uantwerpen.fti.ei.spaceinvaders;

import be.uantwerpen.fti.ei.entities.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

/**
 * main game class that contains the game logic
 */
public class Game {
    private AbstractFactory factory;
    private int GameWidth;
    private int GameHeight;
    private boolean isRunning =  false;
    private Timer timer = new Timer(30);
    private ArrayList<AbstractInput.Inputs> buttonsPressed = new ArrayList<>();

    private PlayerShip playership;
    private Bonus bonus;

    private AbstractInput input;
    private ArrayList<EnemyShip> enemyList= new ArrayList<>();
    private ArrayList<Bullet> playerBullets = new ArrayList<>();
    private ArrayList<Bullet> enemyBullets = new ArrayList<>();
    boolean isPaused;

    private int randomEnemyBulletTime;
    private boolean randomEnemyBulletInitialised;
    private int randomEnemyBulletCounter;

    private int score;
    private int lives;
    private int level;
    private boolean gameOver;

    /**
     * constructor
     * @param fact factory to visualise game
     */
    public Game(AbstractFactory fact) {this.factory = fact;}

    /**
     * initialises game, sets parameters and gets objects
     * @param filename config file
     */
    public void initGame(String filename) {
        Properties prop = new Properties();
        String fileName = filename;
        InputStream iS = null;
        try {
            iS = new FileInputStream(fileName);
        } catch (FileNotFoundException ex) {
            System.out.println("config file not found");
        }
        try {
            prop.load(iS);
        } catch (IOException ex) {
        }

        score = 0;
        lives = 3;
        level =1;
        GameWidth =Integer.parseInt(prop.getProperty("gameWidth"));
        GameHeight = Integer.parseInt(prop.getProperty("gameHeight"));
        factory.setGameDimensions(GameWidth,GameHeight);
        playership = factory.getPlayerShip();
        playership.setxCoordinate(GameWidth/2);
        playership.setyCoordinate(GameHeight*9/10);
        playership.setSpeed(1);
        playership.setSize(2, 2);
        playership.setBonusAvailable(false);
        input = factory.getInput();
        spawnEnemies(1);
        bonus = factory.getBonus();
        bonus.setBonusSpawnAble(true);
        bonus.setBonusTimeCount(0);
        bonus.setSize(2,1);
        bonus.setInUse(false);
        factory.gameOverScreen(false);
        randomEnemyBulletInitialised = false;


        timer.start();
    }

    /**
     * game loop
     * the game starts running when this function is called
     */
    public void run(String filename) {
        initGame(filename);
        isRunning = true;
        isPaused = false;
        while (isRunning) {
            while (!isPaused) {
                playerInputHandler();
                handleEntities();
                handleCollisions();
                handleHUD();
                visualiseElements();
                // next level
                if(enemyList.isEmpty()) {
                    level+=1;
                    spawnEnemies(1);
                    playerBullets.clear();
                }
                //you died
                if(lives == 0) {
                    isPaused = true;
                    gameOver = true;
                }
                timer.sleep();
            }
            if(gameOver) {
                factory.gameOverScreen(true);
            } else {
                factory.pauseScreen(true);
            }
            pauseInputHandler();
            factory.render();
            timer.sleep();
        }
        isRunning = false;
        timer.stop();
    }

    /**
     * visualises the context and all entity objects
     */
    public void visualiseElements() {
        factory.render();
        for(EnemyShip elem: enemyList) { elem.vis(); }
        for(Bullet elem: playerBullets) { elem.vis(); }
        for(Bullet elem: enemyBullets) {elem.vis();}
        playership.vis();
        if(!bonus.isBonusSpawnAble() && !playership.isBonusAvailable()) { bonus.vis(); }
    }

    /**
     * handles inputs while game is paused or over, deactivates pause mode when a
     * button is pressed
     */
    public void pauseInputHandler() {
        if(input.inputAvailable()) {
            isPaused = false;
            if (gameOver) {
                gameOver = false;
                resetGame();
                initGame("src/resources/config");
            }

            // removes input used to restart game so no in game action is taken by restarting
            AbstractInput.Inputs direction = input.getInput();
            if(input.removalAvailable()) {
                AbstractInput.Inputs toRemove = input.getRemoval();
                buttonsPressed.remove(toRemove);
            }
        }
    }

    /**
     * resets game
     */
    public void resetGame() {
        enemyList.clear();
        enemyBullets.clear();
        playerBullets.clear();
        playership.setBonusAvailable(false);
        bonus.setBonusSpawnAble(true);
        bonus.setInUse(false);
        bonus.setBonusTimeCount(0);
    }

    /// takes care of all inputs player can do

    /**
     * handles all inputs player can do while playing the game:
     * moving the ship
     * shooting
     * pausing the game
     * using a bonus
     */
    public void playerInputHandler() {
        // get input keypressed
        if(input.inputAvailable()) {
            AbstractInput.Inputs direction = input.getInput();
            if(!buttonsPressed.contains(direction)) {
                buttonsPressed.add(direction);
            }
        }

        // check list and do movements/actions accordingly
        if (!buttonsPressed.isEmpty()) {
            for( int i=0; i<buttonsPressed.size(); i++) {
                if ((buttonsPressed.get(i) ==  AbstractInput.Inputs.RIGHT ) && playership.getxCoordinate() < GameWidth*0.95) {
                    playership.move(1);
                }
                if (buttonsPressed.get(i) == AbstractInput.Inputs.LEFT && playership.getxCoordinate() > 0) {
                    playership.move(-1);
                }
                if (buttonsPressed.get(i) == AbstractInput.Inputs.UP && playership.isBonusAvailable() && !bonus.isInUse()) {
                    bonus.setInUse(true);
                    bonus.setBonusTimeCount(0);
                }
                if(buttonsPressed.get(i) ==  AbstractInput.Inputs.ESCAPE) {
                    isPaused = true;
                }

                if(buttonsPressed.get(i) == AbstractInput.Inputs.SPACE) {
                    playerShoot(1);
                    buttonsPressed.remove(AbstractInput.Inputs.SPACE); // avoid multiple bullets
                }

            }
        }

        // remove if key released
        if(input.removalAvailable()) {
            AbstractInput.Inputs toRemove = input.getRemoval();
            buttonsPressed.remove(toRemove);
        }
    }

    /**
     * handles all movements and actions of non player objects:
     * moving bullets
     * moving enemies
     * enemies shooting
     * spawning and moving bonuses
     */
    public void handleEntities() {
        //player bullet movement
        Iterator<Bullet> b = playerBullets.iterator();
        while(b.hasNext()) {
            Bullet bullet =b.next();
            bullet.fly();
            if(bullet.getyCoordinate() <0) {
                b.remove();
            }
        }

        // enemy bullet movement
        Iterator<Bullet> eb = enemyBullets.iterator();
        while(eb.hasNext()) {
            Bullet bullet =eb.next();
            bullet.fly();
            if(bullet.getyCoordinate() > GameHeight) {
                eb.remove();
            }
        }
        // enemy movement
        if (!(bonus.isInUse() && bonus.getBonusType() == 2)) {
            enemiesMove();
        }

        // enemy shooting
        randomEnemyShoot( 40 -level*4,  120 - level *4); // 30ms * mintime

        // bonus

        // spawning bonus
        int bonusSpawnMinimumTime = 450;
        spawnBonus(bonusSpawnMinimumTime);

        // removing bonus if too long in game
        int bonusDisappearTime = 2200;
        removeBonusFromGame(bonusDisappearTime);

        // removing bonus after use
        int bonusUseTime = 160;
        removeBonusFromPlayer(bonusUseTime);

        //
        if((bonus.isInUse() && bonus.getBonusType()==1)) {
            playership.setSpeed(4);
        } else {
            playership.setSpeed(2);
        }

    }

    /**
     * function that removes a bonus from the player after using it for a given time
     * @param bonusUseTime use time in ticks
     */
    public void removeBonusFromPlayer(int bonusUseTime) {
        if(bonus.isInUse() && bonus.getBonusTimeCount() < bonusUseTime) {
            bonus.timeCountIncrease();
            System.out.println("bonus in use" );
        } else if (bonus.isInUse() && bonus.getBonusTimeCount() >= bonusUseTime) {
            bonus.setBonusSpawnAble(true);
            bonus.setBonusTimeCount(0);
            bonus.setInUse(false);
            playership.setBonusAvailable(false);
        }
    }

    /**
     * function that removes a bonus after a max time of flying around in the game
     * @param bonusDisappearTime disappear time in ticks
     */
    public void removeBonusFromGame(int bonusDisappearTime) {
        if(!bonus.isBonusSpawnAble() && !playership.isBonusAvailable() && bonus.getBonusTimeCount() < bonusDisappearTime) {
            bonusMove();
            bonus.timeCountIncrease();
        } else if (!bonus.isBonusSpawnAble() && !playership.isBonusAvailable() && bonus.getBonusTimeCount() >= bonusDisappearTime) {
            bonus.setBonus(1,true,0,1,true);
        }

    }

    /**
     * spawns bonus after interval
     * @param bonusSpawnMinimumTime respawn time in ticks
     */
    public void spawnBonus(int bonusSpawnMinimumTime) {
        if(!playership.isBonusAvailable() && bonus.isBonusSpawnAble() && bonus.getBonusTimeCount() >= bonusSpawnMinimumTime) {
            bonus.setPosition(GameWidth/2, GameHeight/10);
            int bonusType = ThreadLocalRandom.current().nextInt(1, 4);
            bonus.setBonus(bonusType,false,0,1, true);
            bonus.setInUse(false);
        } else if(!playership.isBonusAvailable() && bonus.isBonusSpawnAble() && bonus.getBonusTimeCount() < bonusSpawnMinimumTime) {
            bonus.timeCountIncrease();
        }
    }

    /**
     * moves the enemies, also checks direction they have to move to
     */
    public void enemiesMove() {
        boolean edgeReachedRight=false;
        boolean edgeReachedLeft =false;
        for (EnemyShip elem: enemyList) {
            edgeReachedRight = (elem.getxCoordinate() > GameWidth*0.95);
            edgeReachedLeft = (elem.getxCoordinate() <=0) | edgeReachedLeft;
        }
        for(EnemyShip elem: enemyList) {
            if(edgeReachedRight) {
                elem.setMovingRight(false);
                elem.moveDown(level/4);
            } else if(edgeReachedLeft) {
                elem.setMovingRight(true);
                elem.moveDown(level/4);
            }
            elem.move();
        }
    }

    /**
     * moves the bonus
     */
    public void bonusMove() {
        if(bonus.getxCoordinate() >= GameWidth*0.96) {
            bonus.setMovingRight(false);
        }
        if (bonus.getxCoordinate() <= 0) {
            bonus.setMovingRight(true);
        }
        bonus.move();
    }



    /**
     * chooses random enemy to shoot from
     * @param minimumTime minimum time before new shot
     * @param maximumTime maximum time before which the enemies have to shoot
     */
    // picks random time en enemy to shoot with given time interval
    public void randomEnemyShoot(int minimumTime, int maximumTime) {
        // picks a random time interval between enemies shooting w min en max time
        if (!randomEnemyBulletInitialised) {
            randomEnemyBulletCounter = 0;
            randomEnemyBulletTime = ThreadLocalRandom.current().nextInt(minimumTime, maximumTime);
            randomEnemyBulletInitialised = true;
        }

        //chooses a random enemy to shoot from
        if((randomEnemyBulletCounter >= randomEnemyBulletTime) && !enemyList.isEmpty()) {
            int randomEnemy = ThreadLocalRandom.current().nextInt(0, enemyList.size());
            enemyShoot(1, enemyList.get(randomEnemy));
            randomEnemyBulletCounter = 0;
            randomEnemyBulletInitialised =false;
        } else {
            randomEnemyBulletCounter++;
        }
    }

    /**
     * spawns enemies
     * @param enemySpeed speed of spawned enemies
     */
    public void spawnEnemies(int enemySpeed) {
        for(int x = GameWidth * 20/100; x< GameWidth*80/100;x += GameWidth*10/100 ) {
            for(int y = GameHeight /10; y < GameHeight * 6/10; y += GameHeight *2/10) {
                EnemyShip enemyShip = factory.getEnemyShip();
                enemyShip.setSpeed(enemySpeed);
                enemyShip.setPosition(x,y);
                enemyShip.setSize(5,4);
                enemyList.add(enemyShip);
            }
        }
    }

    /**
     * fires a bullet from the players position
     * @param bulletSpeed speed of bullet fired
     */
    public void playerShoot(int bulletSpeed) {
        if (playerBullets.size() < 10) {
            Bullet bullet = factory.getBullet();
            bullet.setPosition(playership.getxCoordinate(), GameHeight*9/10);
            bullet.setSpeed(bulletSpeed);
            bullet.setMovingUp(true);
            bullet.setSize(1,4);
            playerBullets.add(bullet);
        }
    }

    /**
     * shoots bullet from given enemy
     * @param bulletSpeed speed of fired bullet
     * @param enemyShip enemy that shoots the bullet
     */
    public void enemyShoot(int bulletSpeed, EnemyShip enemyShip) {
        Bullet bullet = factory.getBullet();
        bullet.setPosition(enemyShip.getxCoordinate(), enemyShip.getyCoordinate());
        bullet.setSpeed(bulletSpeed);
        bullet.setMovingUp(false);
        bullet.setSize(1,4);
        enemyBullets.add(bullet);
    }

    /**
     * handles all possible collisions:
     * player-bullet
     * enemy-player
     * enemy-bullet
     * bonus-bullet
     */
    public void handleCollisions() {
        // enemies/bonus and playerbullets
        int bow = bonus.getWidth();
        int boh = bonus.getHeight();
        int box = bonus.getxCoordinate();
        int boy = bonus.getyCoordinate();
        Iterator<Bullet> b = playerBullets.iterator();
        while (b.hasNext()) {
            Bullet bull = b.next();
            int bw = bull.getWidth();
            int bh = bull.getHeight();
            int bx = bull.getxCoordinate();
            int by = bull.getyCoordinate();
            // collision with bonus
            if(((by-bh)<=(boy+boh)) && ((by + bh)>=(boy - boh)) && ((bx+bw)>=(box-bow)) && ((bx-bw)<=(bow+box)) && !bonus.isBonusSpawnAble() &&
            !playership.isBonusAvailable() ) {
                b.remove();
                score += 50;
                bonus.setBonusSpawnAble(false);
                bonus.setBonusTimeCount(0);
                bonus.setInUse(false);
                playership.setBonusAvailable(true);
            }
            Iterator<EnemyShip> e = enemyList.iterator();

            while (e.hasNext()) {
                EnemyShip enemy = e.next();

                int ew = enemy.getWidth();
                int eh = enemy.getHeight();
                int ex = enemy.getxCoordinate();
                int ey = enemy.getyCoordinate();
                // collision with enemies
                if(((by-bh)<=(ey+eh)) && ((by + bh)>=(ey - eh)) && ((bx+bw)>=(ex-ew)) && ((bx-bw)<=(ew+ex))) {
                    b.remove();
                    e.remove();
                    score += 100;
                }
            }
        }
        // enemy bullets and player
        int pw = playership.getWidth();
        int ph = playership.getHeight();
        int px = playership.getxCoordinate();
        int py = playership.getyCoordinate();
        Iterator<Bullet> eb = enemyBullets.iterator();
        while (eb.hasNext()) {
            Bullet bull = eb.next();
            int bw = bull.getWidth();
            int bh = bull.getHeight();
            int bx = bull.getxCoordinate();
            int by = bull.getyCoordinate();

            if (((by - bh) <= (py + ph)) && ((by + bh) >= (py - ph)) && ((bx + bw) >= (px - pw)) && ((bx - bw) <= (pw + px))) {
                eb.remove();
                if(!(bonus.isInUse() && bonus.getBonusType() == 3)) {
                    lives -=1;
                }

                System.out.println("hit");
            }
        }
        //player and enemies
        for (EnemyShip enemy : enemyList) {
            int eh = enemy.getHeight();
            int ey = enemy.getyCoordinate();
            // collision with player
            if (((py - ph) <= (ey + eh)) && ((py + ph) >= (ey - eh))) {
                enemiesReachedPlayer();
            }
        }
    }


    /**
     *  handles the actions taken when the enemies reach the player height:
     *  lives minus one and moving enemies back up
     */
    public void enemiesReachedPlayer() {
        lives-=1;
        for(EnemyShip elem: enemyList) {
            elem.moveUp(GameHeight/3);
        }
    }

    /**
     * gives the factory the HUD values to display
     */
    // gives the HUD the hud values
    public void handleHUD(){
        factory.setLives(lives);
        factory.setScore(score);
        factory.setLevel(level);
        factory.pauseScreen(false);
        factory.bonusInUse(bonus.getBonusType(), bonus.isInUse());
        factory.bonusInPosession(bonus.getBonusType(), playership.isBonusAvailable() && !bonus.isInUse());
    }
}
