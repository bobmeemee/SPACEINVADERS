package be.uantwerpen.fti.ei.spaceinvadersjava2D;

import be.uantwerpen.fti.ei.entities.Bonus;
import be.uantwerpen.fti.ei.entities.Bullet;
import be.uantwerpen.fti.ei.entities.EnemyShip;
import be.uantwerpen.fti.ei.entities.PlayerShip;
import be.uantwerpen.fti.ei.spaceinvaders.AbstractFactory;
import be.uantwerpen.fti.ei.spaceinvaders.AbstractInput;

/**
 * factory implementatie van java2D
 */
public class Java2DFactory extends AbstractFactory {
    Java2DContext j2DContext = new Java2DContext("src/resources/config");


    /**
     * gives the game dimensions to the context
     * @param GameWidth width of the game
     * @param GameHeight height of the game
     */
    @Override
    public void setGameDimensions(int GameWidth, int GameHeight) {
        j2DContext.setGameDimensions(GameWidth,GameHeight);
    }

    /**
     * render the context
     */
    @Override
    public void render() {
        j2DContext.render();
    }

    /**
     * implementation of getplayership
     * @return java2Dplayership
     */
    @Override
    public PlayerShip getPlayerShip() {
        return new Java2DPlayerShip(j2DContext);
    }

    /**
     * implementation of getInput
     * @return keylistener object
     */
    @Override
    public AbstractInput getInput() {
        return new Java2DInput(j2DContext);
    }

    /**
     *implementation of getbullet
     * @return java2D bullet
     */
    @Override
    public Bullet getBullet() {
        return new Java2DBullet(j2DContext);
    }

    /**
     * implementation of getenemyship
     * @return java2Denemyship
     */
    @Override
    public EnemyShip getEnemyShip() {
        return new Java2DEnemy(j2DContext);
    }

    /**
     * implementation of getbonus
     * @return bonus object
     */
    @Override
    public Bonus getBonus() { return new Java2DBonus(j2DContext); };


    @Override
    public void setScore(int score) {
        j2DContext.setScore(score);
    }

    @Override
    public void setLives(int lives) {
        j2DContext.setLives(lives);
    }

    @Override
    public void setLevel(int level) {
        j2DContext.setLevel(level);
    }

    @Override
    public void gameOverScreen(boolean visible) {
        j2DContext.setGameOverScreen(visible);
    }

    @Override
    public void pauseScreen(boolean visible) {
        j2DContext.setPauseScreen(visible);
    }

    /**
     * displays bonus in use
     *
     */
    @Override
    public void bonusInUse(int bonus, boolean visible) {
        j2DContext.setBonus(bonus);
        j2DContext.setBonusInUse(visible);
    }

    /**
     * displays bonus in posession of player
     * @param bonus bonus to display
     * @param visible true if the player has one in inventory
     */
    @Override
    public void bonusInPosession(int bonus, boolean visible) {
        j2DContext.setBonus(bonus);
        j2DContext.setBonusAvailable(visible);
    }

}
