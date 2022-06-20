package be.uantwerpen.fti.ei.entities;

/**
 * player controlled object, the spaceship at the bottom of the screen
 */
public abstract class PlayerShip extends Entity{
    private int speed;
    private boolean bonusAvailable;

    public PlayerShip() {
        xCoordinate = 0;
        yCoordinate = 0;
        speed = 1;
        bonusAvailable = false;
    }

    public boolean isBonusAvailable() {
        return bonusAvailable;
    }

    public void setBonusAvailable(boolean bonusAvailable) {
        this.bonusAvailable = bonusAvailable;
    }

    public PlayerShip(int speed) {
        this.speed = speed;
    }

    /**
     * moves the object to the chosen direction with given speed from speed parameter
     * @param direction 1= to the right, -1=to the left
     */
    public void move(int direction) {
        super.xCoordinate += speed * direction;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * visualises object
     */
    public abstract void vis();
}
