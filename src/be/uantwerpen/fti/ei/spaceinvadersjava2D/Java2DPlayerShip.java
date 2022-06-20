package be.uantwerpen.fti.ei.spaceinvadersjava2D;

import be.uantwerpen.fti.ei.entities.PlayerShip;

import java.awt.*;

/**
 * grphical implementation of java2D playerShip
 */
public class Java2DPlayerShip extends PlayerShip {
    private Java2DContext context;
    public Java2DPlayerShip(Java2DContext fact) {
        this.context = fact;
    }

    @Override
    public void vis() {
        Graphics2D g2d = context.getG2d();
        g2d.drawImage(context.spaceShipSprite, context.getSizeX()*super.getxCoordinate(),
                context.getSizeY() *super.getyCoordinate(), null);
    }

}
