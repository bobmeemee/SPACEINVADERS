package be.uantwerpen.fti.ei.spaceinvadersjava2D;

import be.uantwerpen.fti.ei.spaceinvaders.AbstractInput;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

/**
 * de java input implementation
 */
public class Java2DInput extends AbstractInput {
    private Java2DContext context;
    private LinkedList<Inputs> keyInputs;
    private LinkedList<Inputs> keyRemovals;

    public Java2DInput(Java2DContext context) {
        this.context = context;
        keyInputs = new LinkedList<Inputs>();
        keyRemovals = new LinkedList<Inputs>();
        context.getFrame().addKeyListener(new KeyInputAdapter());
    }

    public boolean inputAvailable() {
        return keyInputs.size() > 0;
    }
    public Inputs getInput() {
        return keyInputs.poll();
    }

    public boolean removalAvailable() {return keyRemovals.size() > 0;}
    public Inputs getRemoval() {return  keyRemovals.poll(); }

    /**
     * keylistener
     */
    class KeyInputAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int keycode = e.getKeyCode();
            switch (keycode) {
                case KeyEvent.VK_LEFT:
                    keyInputs.add(Inputs.LEFT);
                    break;
                case KeyEvent.VK_RIGHT:
                    keyInputs.add(Inputs.RIGHT);
                    break;
                case KeyEvent.VK_DOWN:
                    keyInputs.add(Inputs.DOWN);
                    break;
                case KeyEvent.VK_UP:
                    keyInputs.add(Inputs.UP);
                    break;
                case KeyEvent.VK_SPACE:
                    keyInputs.add(Inputs.SPACE);
                    break;
                case KeyEvent.VK_ESCAPE:
                    keyInputs.add(Inputs.ESCAPE);
                    break;
            }
        }

        /**
         * keylistener for removing objects
         * @param e key released
         */
        @Override
        public void keyReleased(KeyEvent e) {
            int keycode = e.getKeyCode();
            switch (keycode) {
                case KeyEvent.VK_LEFT:
                    keyRemovals.add(Inputs.LEFT);
                    break;
                case KeyEvent.VK_RIGHT:
                    keyRemovals.add(Inputs.RIGHT);
                    break;
                case KeyEvent.VK_DOWN:
                    keyRemovals.add(Inputs.DOWN);
                    break;
                case KeyEvent.VK_UP:
                    keyRemovals.add(Inputs.UP);
                    break;
                case KeyEvent.VK_SPACE:
                    keyRemovals.add(Inputs.SPACE);
                    break;
                case KeyEvent.VK_ESCAPE:
                    keyRemovals.add(Inputs.ESCAPE);
                    break;
            }

        }
    }
}
