package slayin.core;

public class Engine {
    private long tickTime = 25; /* 40 fps */

    public void startGameLoop() {
        long startTime, timePassed;

        while (this.stopLoop()) { /* Game loop */
            startTime = System.currentTimeMillis();

            /* TODO: check input */

            /* TODO: update game status */

            /* TODO: render updates */

            timePassed = System.currentTimeMillis() - startTime;
            if (timePassed < tickTime) { /* wait until tickTime before nextFrame */
                try {
                    Thread.sleep(tickTime - timePassed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            /* System.out.println(System.currentTimeMillis() - startTime); */
        }
    }

    public boolean stopLoop() {
        /* TODO: check delle condizioni per terminare la partita */
        return true;
    }

}
