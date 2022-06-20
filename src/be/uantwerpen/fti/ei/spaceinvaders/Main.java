package be.uantwerpen.fti.ei.spaceinvaders;

import be.uantwerpen.fti.ei.spaceinvadersjava2D.Java2DFactory;

public class Main {
    public static void main(String[] args) {
        AbstractFactory aFact = new Java2DFactory();
        Game game = new Game(aFact);
        game.run("src/resources/config");
    }
}

