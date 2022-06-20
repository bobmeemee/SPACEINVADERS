package be.uantwerpen.fti.ei.entities;

/**
 * enemy ship, inherits from entity
 */
public abstract class EnemyShip extends Entity {
    public int value, speed;
    public boolean movingRight;


    public EnemyShip() {
        value = 1;
        speed = 1;
        movingRight = true;
    }

    /**
     * constructor
     * @param value enemy points worth
     * @param speed enemies move at this speed
     * @param movingRight direction of sideways movement
     */
    public EnemyShip(int value, int speed, boolean movingRight) {
        this.value = value;
        this.speed = speed;
        this.movingRight = movingRight;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isMovingRight() {
        return movingRight;
    }

    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }

    /**
     * moves the object left or right based on value of movingRight parameter
     */
    public void move() {
        if(movingRight) {
            this.xCoordinate += this.speed;
        } else {
            this.xCoordinate -= this.speed;
        }
    }

    /**
     * moves the object down
     * @param amount amount of coordinates you want to go down
     */
    public void moveDown(int amount) {
        this.yCoordinate += amount;
    }

    /**
     * moves the object up
     * @param amount amount of coordinates you want to move up
     */
    public void moveUp(int amount) {this.yCoordinate -= amount; }
    public abstract void vis();
}
