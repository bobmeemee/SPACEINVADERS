package be.uantwerpen.fti.ei.spaceinvadersjava2D;

import be.uantwerpen.fti.ei.entities.Bonus;

import java.awt.*;

/**
 * java2D graphic bonus object
 */
public class Java2DBonus extends Bonus {
    Java2DContext context;
    public Java2DBonus(Java2DContext context) {
        this.context = context;
    }

    /**
     * implements visualise method, checks also which bonus is called
     */
    @Override
    public void vis() {
        Graphics2D g2d = context.getG2d();
        int type = super.getBonusType();
        switch (type) {
            case 1:
                g2d.drawImage(context.blueOrbSprite, context.getSizeX() * super.getxCoordinate(),
                        context.getSizeY() * super.getyCoordinate(), null);

                break;
            case 2:
                g2d.drawImage(context.greenOrbSprite, context.getSizeX()*super.getxCoordinate(),
                        context.getSizeY() *super.getyCoordinate(), null);
                break;
            case 3:
                g2d.drawImage(context.redOrbSprite, context.getSizeX()*super.getxCoordinate(),
                        context.getSizeY() *super.getyCoordinate(), null);
                break;
        }
    }
}
