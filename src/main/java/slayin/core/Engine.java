package slayin.core;

import slayin.model.GameObject;
import slayin.model.GameStatus;
import slayin.model.events.GameEventListener;
import slayin.model.events.QuitGameEvent;
import slayin.model.events.StartGameEvent;
import slayin.model.utility.SceneType;

public class Engine {
    private long tickTime = 25; /* 40 fps */
    private boolean running = true;

    private SceneController sceneController;
    private GameStatus status;

    private GameEventListener eventListener;

    public Engine() {
        eventListener = new GameEventListener();
        sceneController = new SceneController(eventListener);
        sceneController.createWindow();
    }

    private void initGame() {
        status = new GameStatus();
    }

    public void startGameLoop() {
        long startTime, timePassed;

        sceneController.switchScene(SceneType.MAIN_MENU);
        this.initGame();

        while (this.running) { /* Game loop */
            startTime = System.currentTimeMillis();

            /* TODO: check input */

            /* TODO: update game status */
            this.updateGameStatus((int) startTime);
            this.processEvents();

            /* TODO: render updates */
            sceneController.updateScene();

            timePassed = System.currentTimeMillis() - startTime;
            waitForNextTick(timePassed);
            // System.out.println(System.currentTimeMillis() - startTime);
        }

        sceneController.closeWindow();
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

    private void updateGameStatus(int startTime) {

        // Update the logical position of the main character and the enemies on the
        // scene
        for (GameObject object : status.getObjects()) {
            object.updatePos(startTime, status.getWorld());
        }

        /* TODO: check for collisions */
    }

    private void processEvents() {
        eventListener.getEvents().forEach(e -> {
            if (e instanceof StartGameEvent) {
                System.out.println("[EVENT] Starting game");
                sceneController.switchScene(SceneType.GAME_LEVEL);
            } else if (e instanceof QuitGameEvent) {
                System.out.println("[EVENT] Closing game");
                this.running = false;
            }
        });

        eventListener.clearEvents();
    }
}
