package be.uantwerpen.fti.ei.entities;

/**
 * Defines bonus object, inherits from entity
 */
public abstract class Bonus extends Entity {

    private int bonusType;
    private boolean bonusSpawnAble;
    private int bonusTimeCount;
    private int speed;
    private boolean movingRight;
    private boolean inUse;

    public boolean isInUse() {
        return inUse;
    }

    public void setInUse(boolean inUse) {
        this.inUse = inUse;
    }

    /**
     *
     * @param bonusType every number is an other type, naar keuze te implementeren in game(1=speedboost,2=freezing enemies, 3=invincible)
     * @param bonusSpawnAble a new bonus is permitted to spawn
     * @param bonusTimeCount counter to check bonususetime
     * @param speed speed for movement bonus
     * @param movingRight direction movement
     * @param inUse checks if bonus is used by player
     */
    public Bonus(int bonusType, boolean bonusSpawnAble, int bonusTimeCount, int speed, boolean movingRight, boolean inUse) {
        this.bonusType = bonusType;
        this.bonusSpawnAble = bonusSpawnAble;
        this.bonusTimeCount = bonusTimeCount;
        this.speed = speed;
        this.movingRight = movingRight;
        this.inUse = inUse;
    }

    public Bonus() {
        xCoordinate = 0;
        yCoordinate = 0;
        height = 0;
        width = 0;
        bonusSpawnAble = false;
        bonusType = 0;
        speed = 0;
        bonusTimeCount = 0;
    }


    /**
     * to set object specific parameters
     * @param bonusType type bonus
     * @param bonusSpawnAble set spawnable
     * @param bonusTimeCount set counter
     * @param speed set speed
     * @param movingRight set direction of movement
     */
    public void setBonus(int bonusType, boolean bonusSpawnAble, int bonusTimeCount, int speed, boolean movingRight) {
        this.bonusType = bonusType;
        this.bonusSpawnAble = bonusSpawnAble;
        this.speed = speed;
        this.bonusTimeCount = bonusTimeCount;
        this.movingRight = movingRight;
    }

    /**
     * move the bonus according to direction
     */
    public void move() {
        if(movingRight) {
            this.xCoordinate += this.speed;
        } else {
            this.xCoordinate -= this.speed;
        }
    }

    public boolean isMovingRight() {
        return movingRight;
    }

    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }

    public void timeCountIncrease() {
        this.bonusTimeCount++;
    }

    public int getBonusTimeCount() {
        return bonusTimeCount;
    }

    public void setBonusTimeCount(int bonusTimeCount) {
        this.bonusTimeCount = bonusTimeCount;
    }

    public boolean isBonusSpawnAble() {
        return bonusSpawnAble;
    }

    public void setBonusSpawnAble(boolean bonusSpawnAble) {
        this.bonusSpawnAble = bonusSpawnAble;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    public int getBonusType() {
        return bonusType;
    }

    public void setBonusType(int bonusType) {
        this.bonusType = bonusType;
    }

    /**
     * visualise method
     */
    public abstract void vis();
}
