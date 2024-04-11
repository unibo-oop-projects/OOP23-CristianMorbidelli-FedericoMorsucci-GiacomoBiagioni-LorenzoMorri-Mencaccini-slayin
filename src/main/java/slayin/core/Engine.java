package slayin.core;

import slayin.model.GameObject;
import slayin.model.GameStatus;

public class Engine {
    private long tickTime = 25; /* 40 fps */
    private SceneController sceneController;

    GameStatus status;

    public Engine() {
        sceneController = new SceneController();
        sceneController.createSceneWindow();
    }

    private void initGame() {
        status = new GameStatus();
    }

    public void switchScene(GameScene scene) {
        sceneController.switchScene(scene);
    }

    public void startGameLoop() {
        long startTime, timePassed;

        this.initGame();

        while (this.stopLoop()) { /* Game loop */
            startTime = System.currentTimeMillis();

            /* TODO: check input */

            /* TODO: update game status */
            this.updateGameStatus((int) startTime);

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

    private boolean stopLoop() {
        /* TODO: check delle condizioni per terminare la partita */
        return true;
    }

    private void updateGameStatus(int startTime) {

        // Update the logical position of the main character and the enemies on the
        // scene
        for (GameObject object : status.getObjects()) {
            object.updatePos(startTime, status.getWorld());
        }

        /* TODO: check for collisions */
    }
}
