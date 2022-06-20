package be.uantwerpen.fti.ei.spaceinvaders;
import java.lang.Math;

/**
 * gameloop timer
 */
public class Timer {
    private int desiredTime;

    private final int NANOSECONDS = 1000000000; // 1 second

    private boolean timerStarted;
    private long beginTime, timeDifference;

    /**
     * default constructor
     * @param milliseconds time of one gamecycle in milliseconds
     */
    public Timer(int milliseconds) {
        this.desiredTime = milliseconds* 1000000;
        timerStarted = false;
        beginTime = 0;
        timeDifference = 0;
    }

    // start timer
    public void start() {
        timerStarted = true;
        beginTime = System.nanoTime();
    }

    //stop timer
    public void stop() {
        timerStarted = false;
        beginTime = 0;
    }

    /**
     * lets game sleep for time left after gameloop
     */
    public void sleep() {
        if (timerStarted == true) {
            timeDifference = System.nanoTime() - beginTime;
        }
        else {
            timeDifference = 0;
        }

        if ( (timerStarted == true) && (timeDifference < this.desiredTime) ) {
            try {
                Thread.sleep((desiredTime -timeDifference)/1000000);
            }
            catch (InterruptedException e) {
                System.out.println("Exception e");
            }
        }
        timerStarted = true;
        beginTime = System.nanoTime();
        timeDifference = 0;
    }

}
