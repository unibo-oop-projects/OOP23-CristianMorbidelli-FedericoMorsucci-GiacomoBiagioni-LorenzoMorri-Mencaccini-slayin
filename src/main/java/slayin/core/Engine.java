package slayin.core;

import slayin.model.entities.GameObject;
import slayin.model.entities.character.Knight;
import slayin.model.GameStatus;
import slayin.model.InputController;
import slayin.model.events.GameEventListener;
import slayin.model.events.QuitGameEvent;
import slayin.model.events.StartGameEvent;
import slayin.model.utility.SceneType;

public class Engine {
    private long tickTime = 25; /* 40 fps */
    private boolean running = true;

    private SceneController sceneController;
    private GameStatus status;
    private InputController inputController;
    private GameEventListener eventListener;

    public Engine() {
        eventListener = new GameEventListener();
        inputController = new InputController();

        sceneController = new SceneController(eventListener, inputController);
        sceneController.createWindow();
    }

    private void initGame() {
        status = new GameStatus();
    }

    public void startGameLoop() {
        long startTime, timePassed, previousTime;

        sceneController.switchScene(SceneType.MAIN_MENU);
        this.initGame();

        previousTime = System.currentTimeMillis();
        while (this.running) { /* Game loop */
            startTime = System.currentTimeMillis();

            /* TODO: check input */
            this.processInputs();

            /* TODO: update game status */
            this.updateGameStatus((int) (startTime - previousTime));
            this.processEvents();

            /* TODO: render updates */
            sceneController.updateScene();
            timePassed = System.currentTimeMillis() - startTime;
            waitForNextTick(timePassed);
            previousTime = startTime;
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

    private void updateGameStatus(int deltaTime) {

        // Update the logical position of the main character and the enemies on the
        // scene
        for (GameObject object : status.getObjects()) {
            object.updatePos(deltaTime, status.getWorld());
        }

        /* TODO: check for collisions */
    }

    private void processInputs() {
        this.status.getCharacter().updateVel(inputController);
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
