package be.uantwerpen.fti.ei.spaceinvadersjava2D;

import be.uantwerpen.fti.ei.entities.EnemyShip;

import java.awt.*;

/**
 * java2D grafiscal implementatios of enemy
 */
public class Java2DEnemy extends EnemyShip {
    Java2DContext context;

    public Java2DEnemy(Java2DContext context) {
        this.context = context;
    }

    /**
     * implements visualise method
     */
    @Override
    public void vis() {
        Graphics2D g2d = context.getG2d();
        g2d.drawImage(context.enemySprite, context.getSizeX()*super.getxCoordinate(),
                context.getSizeY() *super.getyCoordinate(), null);
    }
}
