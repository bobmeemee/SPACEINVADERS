package be.uantwerpen.fti.ei.entities;

/**
 * defines bullet object, inherits from entity
 */
public abstract class Bullet extends Entity {
    private int speed;
    private boolean movingUp;

    public Bullet() {
        this.xCoordinate = 1;
        this.yCoordinate = 1;
        this.speed = 1;
        this.movingUp = true;
    }

    /**
     * default constructor
     * @param speed set movement speed of bullet
     * @param movingUp sets direction of bullet movement
     */
    public Bullet(int speed, boolean movingUp) {
        this.speed = speed;
        this.movingUp = movingUp;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isMovingUp() {
        return movingUp;
    }

    public void setMovingUp(boolean movingUp) {
        this.movingUp = movingUp;
    }

    /**
     * lets the bullet move up or down according to movingUp parameter
     */
    public void fly() {
        if (movingUp) {
            yCoordinate -= speed;
        } else {
            yCoordinate += speed;
        }
    }

    /**
     * visualise method
     */
    public abstract void vis();
}
