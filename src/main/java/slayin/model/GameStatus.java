package slayin.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import slayin.model.entities.GameObject;
import slayin.model.entities.character.Character;
import slayin.model.entities.character.CharacterFactory;
import slayin.model.entities.character.PlayableCharacter;
import slayin.model.entities.enemies.Enemy;
import slayin.model.entities.shots.ShotObject;
import slayin.model.events.GameEventListener;
import slayin.model.events.GameOverEvent;
import slayin.model.score.GameScore;
import slayin.model.utility.Globals;

/**
 * The {@code GameStatus} manages the current status of the game, including the world, character, enemies, shots,
 * and other game elements. It also handles the game events and the level progression.
 */
public class GameStatus {
    World world;
    private Level level;

    Character character;
    List<GameObject> enemies;
    List<ShotObject> shots;
    private GameScore scoreManager;

    private GameEventListener eventListener;

    /** Keeps track of how many ticks are passed since the last time an {@code enemy} has been added. This helps
     * regulating the enemies' dispatching, in order to avoid to fill the scene to its maximum capacity too fast.
     */
    private long tickSinceLastEnemyAdded;

    /**
     * Constructor to initialize the game status with a given event listener.
     *
     * @param eventListener the event listener for handling game events.
     */
    public GameStatus(GameEventListener eventListener){
        world = new World(Globals.RESOLUTION.getWidth(), Globals.RESOLUTION.getHeight());
        enemies = new ArrayList<>();
        shots=new ArrayList<>();
        scoreManager = new GameScore();
        this.eventListener = eventListener;

        tickSinceLastEnemyAdded = 0;
    }

    /**
     * Gets the list of all game objects including the character, enemies, and shots.
     *
     * @return a list of all game objects.
     */
    public List<GameObject> getObjects(){   
        List<GameObject> all = new ArrayList<>();
        all.add(character);
        all.addAll(enemies);
        all.addAll(shots);
        return all;
    }

    /**
     * Adds an enemy to the scene
     *
     * @param entity - the enemy to be added.
     */
    public void addEnemy(Optional<Enemy> entity){
        if(entity.isPresent())
            enemies.add(entity.get());
    }

    /**
     * Removes an enemy from the scene
     *
     * @param entity - the enemy to be removed.
     */
    public void removeEnemy(Enemy entity){
        enemies.remove(entity);
    }

    /**
     * Adds a shot to the scene.
     *
     * @param shot - the shot to be added.
     */
    public void addShot(ShotObject shot){
        shots.add(shot);
    }

    /**
     * Removes a shot from the scene
     *
     * @param shot - the shot to be removed
     */
    public void removeShot(ShotObject shot){
        shots.remove(shot);
    }

    /**
     * Return the world of the game.
     *
     * @return the World object
     */
    public World getWorld(){
        return this.world;
    }

     /**
     * Sets up the playable character based on the specified type.
     *
     * @param typeCharacter - the type of the character to be set up.
     */
    public void setupCharacter(PlayableCharacter typeCharacter){
        switch (typeCharacter) {
            case KNIGHT:
                character= CharacterFactory.getKnight(world);
                break;
            case KNAVE:
                character= CharacterFactory.getKnave(world);
                break;
            case WIZARD:
                character= CharacterFactory.getWizard(world);
                break;
        
            default:
                break;
        }
    }

    /**
     * Gets the current character.
     *
     * @return the current character.
     */
    public Character getCharacter() {
        return this.character;
    }

    /**
     * Gets the list of enemies in the game.
     *
     * @return the list of enemies.
     */
    public List<GameObject> getEnemies() {
        return this.enemies;
    }

    /**
     * Gets the list of shots in the game.
     *
     * @return the list of shots.
     */
    public List<ShotObject> getShots() {
        return this.shots;
    }

    /**
     * Sets the current level of the game. Raise a {@code GameOverEvent} if no more levels are present.
     *
     * @param level - the Level object to be set.
     */
    public void setLevel(Optional<Level> level){
        if(level.isPresent()){
            System.out.println("Starting level " + level.get().getID());
            this.level = level.get();
        }else{    
            // the Optional will be empty if no more levels can be read
            eventListener.addEvent(new GameOverEvent());
        }
    }

    /**
     * Gets the score manager.
     *
     * @return the score manager.
     */
    public GameScore getScoreManager() {
        return this.scoreManager;
    }

    /**
     * Gets the current level.
     *
     * @return the current level.
     */
    public Level getLevel() {
        return this.level;
    }

    /**
     * Adds enemies to the scene based on the level capacity and elapsed time since the last addition.
     */
    public void addEnemiesToScene() {
        //System.out.println("The scene currently have " + enemies.size() + " enemies; can contain " + level.getCapacity());  
        double capacityReached = ((double) enemies.size()/ (double) level.getCapacity()) * 100;
        long currentTime = System.currentTimeMillis();

        if(capacityReached >= 100){
            //System.out.println("NON AGGIUNGO");
            return;     // 100% of level capacity reached; no need to add more enemies
        }

        if(capacityReached >= 80){              // 80% of level capacity reached; will add more enemies every 2 seconds
            if(currentTime - tickSinceLastEnemyAdded < 2000)  return;
        }else if(capacityReached >= 40){        // 40% of level capacity reached; will add more enemies every 1,5 seconds
            if(currentTime - tickSinceLastEnemyAdded < 1500)  return;
        }else if(capacityReached >= 20){        // 20% of level capacity reached; will add more enemies every 0,5 seconds
            if(currentTime - tickSinceLastEnemyAdded < 500)  return;
        }                                       // below 20% of level capacity, will add more enemies every tick

        addEnemy(level.dispatchEnemy());
        tickSinceLastEnemyAdded = System.currentTimeMillis();
    }
}
