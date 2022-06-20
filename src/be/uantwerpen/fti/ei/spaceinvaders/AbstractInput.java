package be.uantwerpen.fti.ei.spaceinvaders;

/**
 * abstracte inputklasse
 */
public abstract class AbstractInput {
    /**
     * mogelijke inputs voor het spel
     */
    public enum Inputs {LEFT, RIGHT, UP, DOWN, SPACE, ESCAPE}

    /**
     *
     * @return de ingedrukte knop
     */
    public abstract Inputs getInput();
    public abstract boolean inputAvailable();


    public abstract boolean removalAvailable();
    /**
     *
     * @return de losgelaten knop
     */
    public abstract Inputs getRemoval();
}
