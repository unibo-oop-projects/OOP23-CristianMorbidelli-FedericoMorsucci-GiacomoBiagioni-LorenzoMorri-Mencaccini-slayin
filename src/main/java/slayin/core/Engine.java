package slayin.core;

import slayin.model.entities.GameObject;
import slayin.model.GameStatus;
import slayin.model.events.GameEventListener;
import slayin.model.events.QuitGameEvent;
import slayin.model.events.StartGameEvent;
import slayin.model.movement.InputController;
import slayin.model.utility.SceneType;

import slayin.model.LevelFactory;
import slayin.model.events.collisions.WeaponCollisionEvent;

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

            this.processInputs();

            this.updateGameStatus((int) (startTime - previousTime));
            this.processEvents();

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
        if(sceneController.isInMenu()) return;
        
        // Update the logical position of the main character and the enemies on the
        // scene
        for (GameObject object : status.getObjects()) {
            object.updatePos(deltaTime);
            System.out.println(object.getPos());
        }

        /* TODO: check for collisions */
        // Temporary test, adding once a collision with an entity if there's one
        if(status.getObjects().size()>1){
            eventListener.addEvent(new WeaponCollisionEvent(status.getObjects().get(1)));
        }
    }

    private void processInputs() {
        if(sceneController.isInMenu()) return;
        this.status.getCharacter().updateVel(inputController);
    }

    private void processEvents() {
        eventListener.getEvents().forEach(e -> {
            if (e instanceof StartGameEvent) {
                System.out.println("[EVENT] Starting game");
                sceneController.switchScene(SceneType.GAME_LEVEL);
                this.status.setLevel(LevelFactory.buildLevel(0));   // setto il livello a 0; è un livello
                                                                          // di prova che ha soltanto un'entità immobile
            } else if (e instanceof QuitGameEvent) {
                System.out.println("[EVENT] Closing game");
                this.running = false;
            } else if (e instanceof WeaponCollisionEvent) {
                System.out.println("Weapon Collision Event");
                System.out.println("With: " + ((WeaponCollisionEvent) e).getCollidedObject());
            }
        });

        eventListener.clearEvents();
    }
}
