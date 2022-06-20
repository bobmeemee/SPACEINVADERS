package be.uantwerpen.fti.ei.spaceinvaders;

import be.uantwerpen.fti.ei.entities.Bonus;
import be.uantwerpen.fti.ei.entities.Bullet;
import be.uantwerpen.fti.ei.entities.EnemyShip;
import be.uantwerpen.fti.ei.entities.PlayerShip;

/**
 * abstract factory to implement graphics
 */
public abstract class AbstractFactory {

    /**
     * sets the dimensions
     * @param GameWidth width of the game
     * @param GameHeight height of the game
     */
    public abstract void setGameDimensions(int GameWidth, int GameHeight);

    /**
     * visualises the context
     */
    public abstract void render();

    // all the get methods
    public abstract PlayerShip getPlayerShip();
    public abstract AbstractInput getInput();
    public abstract Bullet getBullet();
    public abstract EnemyShip getEnemyShip();
    public abstract Bonus getBonus();

    /**
     * display score
     * @param score
     */
    public abstract void setScore(int score);

    /**
     * display lives
     * @param lives
     */
    public abstract void setLives(int lives);

    /**
     * display level
     * @param level
     */
    public abstract void setLevel(int level);

    /**
     * display gameoverscreen
     * @param visible true is visible, false is not visible
     */
    public abstract void gameOverScreen(boolean visible);

    /**
     * make pausescreen visible
     * @param visible true is visible, false is not visible
     */
    public abstract void pauseScreen(boolean visible);

    public abstract void bonusInUse(int bonus, boolean visible);

    public abstract void bonusInPosession(int bonus, boolean visible);








}
