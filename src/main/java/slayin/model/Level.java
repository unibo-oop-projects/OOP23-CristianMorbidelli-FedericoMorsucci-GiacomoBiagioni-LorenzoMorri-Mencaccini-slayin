package slayin.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import slayin.model.entities.GameObject;

/**
 * A class to represent the infos about each level: it contains a list
 * of the enemies that have yet to appear in that level.
 */
public class Level {
    
    /** every level has a set of enemies that will be periodically dispatched in the scene,
     * this list keep track of the ones that has yet to be added to the game
     */
    private final List<GameObject> enemyToDispatch;

    /**
     * The constructor of the Level class that is used by the LevelFactory class to build the levels
     * @param enemies - the list of enemies
     */
    public Level(List<GameObject> enemies){
        this.enemyToDispatch = new ArrayList<>(enemies);
    }

    /**
     * A method that returns an element from the list of enemies (if there is at least one), and then removes it immediately after.
     * @return an Optional containing the first element of the list, or an empty one if the list has no elements
     */
    public Optional<GameObject> dispatchEnemy(){
        // if the list has no more elements, an empty optional is returned
        if(!this.hasEnemiesLeft())   return Optional.empty();

        // the first element of the list is extracted and then returned inside an Optional
        GameObject tmp = enemyToDispatch.get(0);
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
