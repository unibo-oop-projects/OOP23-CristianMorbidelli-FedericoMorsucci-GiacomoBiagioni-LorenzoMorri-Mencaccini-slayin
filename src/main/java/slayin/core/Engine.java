package slayin.core;

import slayin.model.entities.GameObject;
import slayin.model.entities.character.Character;
import slayin.model.entities.character.MeleeWeapon;

import java.util.List;

import slayin.model.GameStatus;
import slayin.model.events.ChangeResolutionEvent;
import slayin.model.events.GameEventListener;
import slayin.model.events.GameOverEvent;
import slayin.model.movement.InputController;
import slayin.model.utility.LevelFactory;
import slayin.model.utility.assets.AssetsManager;
import slayin.model.events.collisions.CharacterCollisionEvent;
import slayin.model.events.collisions.ShotCollisionWithWorldEvent;
import slayin.model.events.collisions.WeaponCollisionEvent;
import slayin.model.events.menus.QuitGameEvent;
import slayin.model.events.menus.ShowMainMenuEvent;
import slayin.model.events.menus.ShowOptionsMenuEvent;
import slayin.model.events.menus.ShowPauseMenuEvent;
import slayin.model.events.menus.StartGameEvent;

public class Engine {
    private long tickTime = 25; /* 40 fps */
    private boolean running = true;

    private SceneController sceneController;
    private GameStatus status;
    private InputController inputController;
    private GameEventListener eventListener;
    
    private AssetsManager assetsManager;
    private LevelFactory levelFactory;

    public Engine() {
        eventListener = new GameEventListener();
        inputController = new InputController(eventListener);
        assetsManager = new AssetsManager();
        sceneController = new SceneController(eventListener, inputController, assetsManager);
    }

    private void initGame() {
        status = new GameStatus(eventListener);
        levelFactory = new LevelFactory(status.getWorld());
    }

    public void startGameLoop() {
        long startTime, timePassed, previousTime;

        assetsManager.loadAssets();
        sceneController.createWindow();
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

        // if the scene's not full, regularly add new enemies (if the current level can provide more)
        status.addEnemiesToScene();

        // Update the logical position of the main character and the enemies on the
        // scene
        for (GameObject object : status.getObjects()) {
            object.updatePos(deltaTime);
        }

        status.getScoreManager().updateComboTimer();

        /* TODO: check for collisions */
        checkCharacterCollisions();
    }

    private void checkCharacterCollisions(){
        Character character = status.getCharacter();
        List<MeleeWeapon> weapons = character.getWeapons();
        // collisioni con le weapon del personaggio
        status.getEnemies().forEach(enemy->{
            //controlo weapon da mischia
            weapons.forEach(weapon->{
                if(weapon.getBoxWeapon().isCollidedWith(enemy.getBoundingBox())) eventListener.addEvent(new WeaponCollisionEvent(enemy,weapon));
            });
            //controllo weapon in movimento
            this.status.getShots().forEach(shot->{
                if(shot.getBoundingBox().isCollidedWith(enemy.getBoundingBox())){ 
                    eventListener.addEvent(new WeaponCollisionEvent(enemy,shot));
                }
                if(!(this.status.getWorld().collidingWith(shot).isEmpty())) eventListener.addEvent(new ShotCollisionWithWorldEvent(shot));
            });
            if(character.getBoundingBox().isCollidedWith(enemy.getBoundingBox())) eventListener.addEvent(new CharacterCollisionEvent(enemy));
        });
    }


    private void processInputs() {
        if(sceneController.isInMenu()) return;
        if(inputController.isJumping()){
            if(this.status.getCharacter().getShots().isPresent()) this.status.addShot(this.status.getCharacter().getShots().get());
        } 
        this.status.getCharacter().updateVel(inputController);
    }

    private void processEvents() {
        eventListener.getEvents().forEach(e -> {
            if (e instanceof StartGameEvent) {
                System.out.println("[EVENT] Starting game");
                this.initGame();
                sceneController.showGameScene(status);

                this.status.setLevel(levelFactory.buildLevel(1));   // setto il livello a 1; il livello 0 non è accessibile, è pensato soltanto per dei test
                //this.status.addEnemy(this.status.getLevel().dispatchEnemy().get());
            } else if (e instanceof ShowPauseMenuEvent) {
                var event = (ShowPauseMenuEvent) e;
                sceneController.setPauseMenuOpen(event.shouldShowPauseMenu());

                if (!event.shouldShowPauseMenu())
                    status.getScoreManager().resumeComboTimer();
            } else if (e instanceof QuitGameEvent) {
                System.out.println("[EVENT] Closing game");
                this.running = false;
            } else if (e instanceof WeaponCollisionEvent) {
                WeaponCollisionEvent event = (WeaponCollisionEvent) e;
                GameObject collided = event.getCollidedObject();
                //rimuovo in caso ci siano proiettili in movimento
                if(event.getShot().isPresent()) status.removeShot(event.getShot().get());
                //System.out.println("Weapon Collision Event");
                //System.out.println("With: " + collided);

                if(collided.onHit()){
                    // if the GameObject that has been collided returns true; then it must be removed from the scene
                    status.removeEnemy(collided);
                    // if the enemy has been defeated, the score gets increased
                    // TODO: the fixed 5 must be changed with a value returned by the enemy
                    status.getScoreManager().increaseScore(5);

                    // since an enemy is dead, it needs to be checked if the level has been completed
                    if(isLevelCompleted()){
                        // current level has been completed
                        // read current level's id from the GameStatus, and tries to build the next one
                       status.setLevel(levelFactory.buildLevel(status.getLevel().getID()+1));
                       // if the factory won't be able to get the level, the Game Over event will be raised
                    }

                    // if the current level is not completed yet, nothing more happens
                }
            } else if (e instanceof CharacterCollisionEvent) {
                // TODO: change damage amount based on enemy
                status.getCharacter().decLife(1);

                if (!status.getCharacter().isAlive()) {
                    eventListener.addEvent(new GameOverEvent());
                }                    
            } else if (e instanceof GameOverEvent) {
                System.out.println("Game Over");
                sceneController.showGameOverScene();
            } else if(e instanceof ShotCollisionWithWorldEvent){
                var event = (ShotCollisionWithWorldEvent) e;
                System.out.println("tolgo colpo");
                this.status.removeShot(event.getShot());
            } else if (e instanceof ShowOptionsMenuEvent) {
                sceneController.showOptionsMenuScene();
            } else if (e instanceof ShowMainMenuEvent) {
                sceneController.showMainMenuScene();
            } else if (e instanceof ChangeResolutionEvent) {
                ChangeResolutionEvent event = (ChangeResolutionEvent) e;
                sceneController.changeResolution(event.getResolution());
            }
        });

        eventListener.clearEvents();
    }

    /**
     * a private method that checks whether the current level has been completed or not.
     * A level is said "completed" only when the character has succesfully killed all the enemies that had to be dispatched.
     * @return {@code true} if the enemyList is empty, and the Level object can't supply any more entities; {@code false} otherwise
     */
    private boolean isLevelCompleted(){
        if(status.getObjects().size() > 1){
            return false;
        }

        return !status.getLevel().hasEnemiesLeft();
    }
}
