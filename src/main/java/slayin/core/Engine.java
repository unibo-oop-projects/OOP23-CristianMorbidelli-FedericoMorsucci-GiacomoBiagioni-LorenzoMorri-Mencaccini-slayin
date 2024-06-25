package slayin.core;

import slayin.model.entities.GameObject;
import slayin.model.entities.character.CharacterImpl;
import slayin.model.entities.character.MeleeWeapon;
import slayin.model.entities.enemies.Enemy;
import slayin.model.entities.shots.ShotObject;

import java.util.List;

import slayin.model.GameStatus;
import slayin.model.events.ResolutionChangedEvent;
import slayin.model.events.GameEventListener;
import slayin.model.events.GameOverEvent;
import slayin.model.movement.InputController;
import slayin.model.utility.LevelFactory;
import slayin.model.utility.SceneType;
import slayin.model.utility.assets.AssetsManager;
import slayin.model.events.collisions.CharacterCollisionEvent;
import slayin.model.events.collisions.ShotCollisionWithWorldEvent;
import slayin.model.events.collisions.SpawnShotsEvent;
import slayin.model.events.collisions.WeaponCollisionEvent;
import slayin.model.events.menus.QuitGameEvent;
import slayin.model.events.menus.ShowPauseMenuEvent;
import slayin.model.events.menus.SimpleChangeSceneEvent;
import slayin.model.events.menus.StartGameEvent;

/**
 * The engine class represents the main core of the game. This is the class that takes care of
 * the Game Loop: inputs, updates and rendering.
 */
public class Engine {
    /** The {@code tickTime} is a final attribute that is used by the engine for the game loop; the tickTime is the minimum time (in milliseconds) between every loop*/
    private final long tickTime = 25; /* 40 fps */
    /** The {@code running} attribute tells when the engine must run the gameLoop and when it shouldn't. 
     * If this value is {@code True}, then the game is in his normal state and the gameLoop is runned;
     * if it's {@code False}, the gameLoop is temporarily stopped (the game is paused).*/
    private boolean running = true;

    private final SceneController sceneController;
    private final InputController inputController;
    private final GameEventListener eventListener;
    private GameStatus status;
    
    private LevelFactory levelFactory;

    /** The {@code Engine} class constructor doesn't need any parameters when instantiated. This is where the engine creates its controllers for the graphic
     * rendering and for the input handling, and a listener for in-game events.
    */
    public Engine() {
        eventListener = new GameEventListener();
        inputController = new InputController(eventListener);
        sceneController = new SceneController(eventListener, inputController);
    }

    /** The {@code initGame} function is where the Engine instantiate an object for the {@code status} of the game.
     * This is also where a factory that takes care of generating levels is created.*/
    private void initGame() {
        status = new GameStatus(eventListener);
        levelFactory = new LevelFactory(status.getWorld(), this.eventListener);
    }

    /** The effective game loop of the game */
    public void startGameLoop() {
        /** Numerical attributes for the tick time. The time passed in its loop is also used at every game status' update for numerical calculations. */
        long startTime, timePassed, previousTime;

        AssetsManager.loadAssets();
        sceneController.createWindow();
        sceneController.switchScene(SceneType.MAIN_MENU);

        previousTime = System.currentTimeMillis();  // previousTime shows at what value of System.currentTimeMillis the previous loop ended
        while (this.running) { /* Game loop */
            startTime = System.currentTimeMillis();

            this.processInputs();   // processing inputs means using the InputController to interact with the main character

            this.updateGameStatus((int) (startTime - previousTime)); // Updates the game status
            this.processEvents();   // Processing the events list

            sceneController.renderEntitiesInScene();    // Graphical rendering of entities

            timePassed = System.currentTimeMillis() - startTime;
            waitForNextTick(timePassed);    // The game must "sleep" if the current tick has not reached the tickTime yet
            previousTime = startTime;
        }

        sceneController.closeWindow();
    }

    /** A function that makes the gameLoop sleep in order to wait for the tickTime
     * 
     * @param timePassed - How long the current frame has lasted
     */
    private void waitForNextTick(long timePassed) {
        if (timePassed < tickTime) { /* wait until tickTime before next frame */
            try {
                Thread.sleep(tickTime - timePassed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /** A function that takes care of managing every aspect of the logical status of the game:
     * adding or removing entities, moving them in the scene, etc...
     * 
     * @param deltaTime - The difference in milliseconds between the last frame and the current one
     */
    private void updateGameStatus(int deltaTime) {
        if(sceneController.isInMenu()) return;  // If the game is at the menu frame, there is no status to update

        // if the scene's not full, regularly add new enemies (if the current level can provide more)
        status.addEnemiesToScene();
        
        // Update the logical position of the main character and the enemies on the
        // scene
        for (GameObject object : status.getObjects()) {
            object.updatePos(deltaTime);
        }

        status.getScoreManager().updateComboTimer();

        //controllo le collisioni
        checkCharacterCollisions();
        checkShotCollisions();
    }

    /** A function that checks the collisions that enemies have with Character's weapon (or shots)*/
    private void checkCharacterCollisions(){
        CharacterImpl character = status.getCharacter();
        List<MeleeWeapon> weapons = character.getWeapons();
        // collisioni con le weapon del personaggio
        status.getEnemies().forEach(enemy->{
            //controlo weapon da mischia
            weapons.forEach(weapon->{
                if(weapon.getBoxWeapon().isCollidedWith(enemy.getBoundingBox())) eventListener.addEvent(new WeaponCollisionEvent(enemy,weapon));
            });
            //controllo weapon in movimento
            this.status.getShots().stream().filter(s->!s.isFromEnemy())
            .filter(shot->shot.getBoundingBox().isCollidedWith(enemy.getBoundingBox()))
            .forEach(shot->eventListener.addEvent(new WeaponCollisionEvent(enemy,shot)));
            //
            if(character.getBoundingBox().isCollidedWith(enemy.getBoundingBox())) eventListener.addEvent(new CharacterCollisionEvent(enemy));
        });
    }

    /** A function that checks the collisions of shots with world boundaries, enemies or the character itself */
    private void checkShotCollisions(){
        //check if goes outside of the screen
        this.status.getShots().stream()
            .filter(shot->!(this.status.getWorld().collidingWithSides(shot).isEmpty()))
            .forEach(s->eventListener.addEvent(new ShotCollisionWithWorldEvent(s)));
        //check if an enemy shot hits the character
        this.status.getShots().stream()
            .filter(shot->shot.isFromEnemy() && shot.getBoundingBox().isCollidedWith(this.status.getCharacter().getBoundingBox()))
            .forEach(s->eventListener.addEvent(new CharacterCollisionEvent(s)));
    }


    /** Through this class, the Engine checks the inputs received from the player and process them */
    private void processInputs() {
        if(sceneController.isInMenu()) return;
        if(inputController.isJumping()){
            if(this.status.getCharacter().getShots().isPresent()) this.status.addShot(this.status.getCharacter().getShots().get());
        } 
        this.status.getCharacter().updateVectorMovement(inputController);
    }

    /** A function that asks the event listener for the list of events, and then resolve them according to their type */
    private void processEvents() {
        eventListener.getEvents().forEach(e -> {
            if (e instanceof StartGameEvent) {  // The event raised when the actual game is started
                var event = (StartGameEvent) e;

                //System.out.println("[EVENT] Starting game");
                this.initGame();
                this.status.setupCharacter(event.getPlayableCharacter());
                
                sceneController.showGameScene(status);
                this.status.setLevel(levelFactory.buildLevel(1));   // setto il livello a 1; il livello 0 non è accessibile, è pensato soltanto per dei test
                //this.status.addEnemy(this.status.getLevel().dispatchEnemy().get());
            } else if (e instanceof ShowPauseMenuEvent) {
                var event = (ShowPauseMenuEvent) e;
                sceneController.setPauseMenuOpen(event.shouldShowPauseMenu());

                if (!event.shouldShowPauseMenu())
                    status.getScoreManager().resumeComboTimer();
            } else if (e instanceof QuitGameEvent) {
                //System.out.println("[EVENT] Closing game");
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
                    status.removeEnemy((Enemy) collided);
                    // if the enemy has been defeated, the score gets increased
                    
                    status.getScoreManager().increaseScore(((Enemy) collided).getScorePerKill());

                    // since an enemy is dead, it needs to be checked if the level has been completed
                    if(isLevelCompleted()){
                        // current level has been completed
                        // if it was a boss level (max capacity=1) then the character gets healed (1/4 of maximum life)
                        if(status.getLevel().getCapacity() == 1)
                            status.getCharacter().getLife().heal(status.getCharacter().getLife().getMaxHealth()/4);
                        // read current level's id from the GameStatus, and tries to build the next one
                       status.setLevel(levelFactory.buildLevel(status.getLevel().getID()+1));     
                       // if the factory won't be able to get the level, the Game Over event will be raised
                    }

                    // if the current level is not completed yet, nothing more happens
                }
            } else if (e instanceof CharacterCollisionEvent) {
                CharacterCollisionEvent ev = (CharacterCollisionEvent) e;
                if(ev.getCollidedObject() instanceof ShotObject){   // Collision with a enemy shot
                    this.status.removeShot((ShotObject)ev.getCollidedObject());
                    status.getCharacter().decLife(((ShotObject) ev.getCollidedObject()).getDamageOnHit());
                }else{  // Collision with enemy
                    status.getCharacter().decLife(((Enemy) ev.getCollidedObject()).getDamageOnHit());
                }

                if (!status.getCharacter().isAlive()) {
                    eventListener.addEvent(new GameOverEvent());
                }           
            } else if (e instanceof GameOverEvent) {
                sceneController.switchScene(SceneType.GAME_OVER);
            } else if(e instanceof ShotCollisionWithWorldEvent){
                var event = (ShotCollisionWithWorldEvent) e;
                //System.out.println("tolgo");
                this.status.removeShot(event.getShot());
            } else if (e instanceof SimpleChangeSceneEvent) {
                SimpleChangeSceneEvent event = (SimpleChangeSceneEvent) e;
                sceneController.switchScene(event.getSceneType());
            } else if (e instanceof SpawnShotsEvent) {
                //add the shot to the world
                this.status.addShot(((SpawnShotsEvent)e).getShot());
            } else if (e instanceof ResolutionChangedEvent) {
                sceneController.updateResolution();
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
        if(status.getEnemies().size() > 0){
            return false;
        }

        return !status.getLevel().hasEnemiesLeft();
    }
}
