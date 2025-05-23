package cz.fel.cvut.pjv.semestral.model.utils;


/**
 * A simple timer class that can be used to measure elapsed time.
 * It can be started, stopped, and the elapsed time can be retrieved in seconds.
 */
public class Timer implements Runnable {
    private long startTime;
    private long endTime;
    private boolean running;


    /**
     * Constructor for the Timer class.
     * Initializes the timer to not running state.
     */
    public void start() {
        running = true;
        startTime = System.currentTimeMillis();
        Thread timerThread = new Thread(this);
        timerThread.setDaemon(true); // set this to be able to exit
        timerThread.start();
    }

    /**
     * Stops the timer and records the end time.
     */
    public void stop() {
        running = false;
        endTime = System.currentTimeMillis();
    }

    /**
     * Returns the elapsed time in seconds.
     *
     * @return elapsed time in seconds
     */
    public long getElapsedSeconds() {
        if (running) {
            return (System.currentTimeMillis() - startTime) / 1000;
        } else {
            return (endTime - startTime) / 1000;
        }
    }

    /**
     * Returns the elapsed time in a formatted string (MM:SS).
     * TODO - Possible to add hours
     *
     * @return formatted time string
     */
    public String getFormattedTime() {
        long seconds = getElapsedSeconds();
        long minutes = seconds / 60;
        long remainingSeconds = seconds % 60;
        return String.format("%d:%02d", minutes, remainingSeconds);
    }

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(1000); // sleep 1 second
                long elapsed = getElapsedSeconds();
                //System.out.println("Elapsed time: " + elapsed + "s");
            } catch (InterruptedException e) {
               // System.out.println("Timer interrupted");
            }
        }
    }
}
