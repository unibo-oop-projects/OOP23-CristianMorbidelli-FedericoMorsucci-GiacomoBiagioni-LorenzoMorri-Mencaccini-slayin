package slayin.core;

import slayin.model.entities.GameObject;
import slayin.model.GameStatus;
import slayin.model.InputController;
import slayin.model.Level;
import slayin.model.LevelFactory;
import slayin.model.events.GameEventListener;
import slayin.model.events.QuitGameEvent;
import slayin.model.events.StartGameEvent;
import slayin.model.events.collisions.WeaponCollisionEvent;
import slayin.views.MainMenuScene;

public class Engine {
    private long tickTime = 25; /* 40 fps */
    private boolean running = true;

    private SceneController sceneController;
    private GameStatus status;
    private InputController controllerInput;
    private GameEventListener eventListener;

    public Engine() {
        eventListener = new GameEventListener();
        sceneController = new SceneController(eventListener);
        sceneController.createWindow();
        controllerInput= new InputController();
    }

    private void initGame() {
        status = new GameStatus();
    }

    public void switchScene(GameScene scene) {
        sceneController.switchScene(scene);
    }

    public void startGameLoop() {
        long startTime, timePassed,previousTime;

        this.switchScene(new MainMenuScene(this.eventListener));
        this.initGame();

        previousTime=System.currentTimeMillis();
        while (this.running) { /* Game loop */
            startTime = System.currentTimeMillis();

            /* TODO: check input */
            this.InputController();

            /* TODO: update game status */
            this.updateGameStatus((int) (startTime-previousTime));
            this.processEvents();

            /* TODO: render updates */
            sceneController.updateScene();
            timePassed = System.currentTimeMillis() - startTime;
            waitForNextTick(timePassed);
            previousTime=startTime;
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
        // Temporary test, adding once a collision with an entity if there's one
        if(status.getObjects().size()>1){
            eventListener.addEvent(new WeaponCollisionEvent(status.getObjects().get(1)));
        }
    }
    
    private void InputController(){
        this.status.getCharacter().updateVel(controllerInput);
    }

    private void processEvents() {
        eventListener.getEvents().forEach(e -> {
            if (e instanceof StartGameEvent) {
                System.out.println("Start Game Event");

                this.status.setLevel(LevelFactory.buildLevel(0));   // setto il livello a 0; è un livello
                                                                          // di prova che ha soltanto un'entità immobile
            } else if (e instanceof QuitGameEvent) {
                System.out.println("Quit Game Event");
                this.running = false;
            } else if (e instanceof WeaponCollisionEvent) {
                System.out.println("Weapon Collision Event");
                System.out.println("With: " + ((WeaponCollisionEvent) e).getCollidedObject());
            }
        });

        eventListener.clearEvents();
    }
}
