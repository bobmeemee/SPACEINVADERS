package be.uantwerpen.fti.ei.spaceinvadersjava2D;

import be.uantwerpen.fti.ei.entities.Bullet;

import java.awt.*;

/**
 * java2D graphical implementation of bullet
 */
public class Java2DBullet extends Bullet {
    Java2DContext context;
    public Java2DBullet(Java2DContext context) {
        this.context = context;
    }


    /**
     * implements visualise method
     */
    @Override
    public void vis() {
        Graphics2D g2d = context.getG2d();
        g2d.drawImage(context.playerBulletSprite, context.getSizeX()*super.getxCoordinate(),
                context.getSizeY() *super.getyCoordinate(), null);
    }
}
