package slayin.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import slayin.model.entities.enemies.Enemy;

/**
 * A class to represent the infos about each level: it contains a list
 * of the enemies that have yet to appear in that level.
 */
public class Level {

    /** The ID of a level is its appearing order (EX: first level has id=1; second level has id=2...) */
    private final int identifier;
    
    /** every level has a set of enemies that will be periodically dispatched in the scene,
     * this list keep track of the ones that has yet to be added to the game
     */
    private final List<Enemy> enemyToDispatch;
    /** The capacity of a level is the maximum number of enemies that will be able to join the scene at the same time */
    private final int capacity;

    /**
     * The constructor of the Level class that is used by the LevelFactory class to build the levels
     * @param id - the identifier of the level
     * @param enemies - the list of enemies
     * @param capacity - the capacity of the level
     */
    public Level(int id, List<Enemy> enemies, int capacity){
        this.identifier = id;
        this.enemyToDispatch = new ArrayList<>(enemies);
        this.capacity = capacity;
    }

    /**
     * Getter method for the {@code identifier} attribute
     * @return the integer value of the level's id
     */
    public int getID(){
        return this.identifier;
    }

    /**
     * Getter method for the {@code capacity} attribute
     * @return the integer value of the level's capacity
     */
    public int getCapacity(){
        return this.capacity;
    }

    /**
     * A method that returns an element from the list of enemies (if there is at least one), and then removes it immediately after.
     * @return an Optional containing the first element of the list, or an empty one if the list has no elements
     */
    public Optional<Enemy> dispatchEnemy(){
        // if the list has no more elements, an empty optional is returned
        if(!this.hasEnemiesLeft())   return Optional.empty();

        // the first element of the list is extracted and then returned inside an Optional
        Enemy tmp = enemyToDispatch.get(0);
        enemyToDispatch.remove(0);

        return Optional.of(tmp);
    }

    /**
     * A method that tells if the current level has yet to dispatch enemies or has already given all of them.
     * @return {@code true} if this level still have Entities in its list; {@code false} otherwise (so its dispatchEnemy() method would
     * return an empty Optional)
     */
    public boolean hasEnemiesLeft(){
        return !this.enemyToDispatch.isEmpty();
    }
}
