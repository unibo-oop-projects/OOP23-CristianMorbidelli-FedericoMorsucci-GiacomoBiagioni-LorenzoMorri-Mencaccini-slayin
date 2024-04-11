package slayin.core;

public class Engine {
    private long tickTime = 25; /* 40 fps */

    private SceneController sceneController;

    public Engine() {
        sceneController = new SceneController();
        sceneController.createSceneWindow();
    }

    public void switchScene(GameScene scene) {
        sceneController.switchScene(scene);
    }

    public void startGameLoop() {
        long startTime, timePassed;

        while (this.stopLoop()) { /* Game loop */
            startTime = System.currentTimeMillis();

            /* TODO: check input */

            /* TODO: update game status */

            /* TODO: render updates */
            sceneController.updateScene();

            timePassed = System.currentTimeMillis() - startTime;
            waitForNextTick(timePassed);
            // System.out.println(System.currentTimeMillis() - startTime);
        }
    }

    private void waitForNextTick(long timePassed) {
        if (timePassed < tickTime) { /* wait until tickTime before nextFrame */
            try {
                Thread.sleep(tickTime - timePassed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean stopLoop() {
        /* TODO: check delle condizioni per terminare la partita */
        return true;
    }
}
