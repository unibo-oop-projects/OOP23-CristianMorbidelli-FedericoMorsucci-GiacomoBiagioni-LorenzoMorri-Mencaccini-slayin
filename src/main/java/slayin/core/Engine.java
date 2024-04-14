package slayin.core;

import slayin.model.entities.GameObject;
import slayin.model.GameStatus;
import slayin.model.events.GameEventListener;
import slayin.model.movement.InputController;
import slayin.model.utility.LevelFactory;
import slayin.model.events.collisions.WeaponCollisionEvent;
import slayin.model.events.menus.QuitGameEvent;
import slayin.model.events.menus.ShowPauseMenuEvent;
import slayin.model.events.menus.StartGameEvent;

public class Engine {
    private long tickTime = 25; /* 40 fps */
    private boolean running = true;

    private SceneController sceneController;
    private GameStatus status;
    private InputController inputController;
    private GameEventListener eventListener;

    private LevelFactory levelFactory;

    public Engine() {
        eventListener = new GameEventListener();
        inputController = new InputController(eventListener);
    }

    private void initGame() {
        status = new GameStatus();
        levelFactory = new LevelFactory(status.getWorld());
        sceneController = new SceneController(eventListener, inputController, status);
        
        sceneController.createWindow();
    }

    public void startGameLoop() {
        long startTime, timePassed, previousTime;

        this.initGame();
        sceneController.showMainMenuScene();

        previousTime = System.currentTimeMillis();
        while (this.running) { /* Game loop */
            startTime = System.currentTimeMillis();

            this.processInputs();

            this.updateGameStatus((int) (startTime - previousTime));
            this.processEvents();

            sceneController.renderEntitiesInScene();
            
            timePassed = System.currentTimeMillis() - startTime;
            waitForNextTick(timePassed);
            previousTime = startTime;
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
                sceneController.showGameScene();
                this.status.setLevel(levelFactory.buildLevel(0));   // setto il livello a 0; è un livello
                                                                          // di prova che ha soltanto un'entità immobile
            } else if (e instanceof QuitGameEvent) {
                System.out.println("[EVENT] Closing game");
                this.running = false;
            } else if (e instanceof WeaponCollisionEvent) {
                System.out.println("Weapon Collision Event");
                System.out.println("With: " + ((WeaponCollisionEvent) e).getCollidedObject());
            } else if (e instanceof ShowPauseMenuEvent) {
                var event = (ShowPauseMenuEvent) e;
                sceneController.setPauseMenuOpen(event.shouldShowPauseMenu());
            }
        });

        eventListener.clearEvents();
    }
}
