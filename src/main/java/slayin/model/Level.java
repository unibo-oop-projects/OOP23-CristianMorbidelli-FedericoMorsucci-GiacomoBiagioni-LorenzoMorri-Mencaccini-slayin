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
    
    private final List<GameObject> enemyToDispatch;

    public Level(List<GameObject> enemies){
        this.enemyToDispatch = new ArrayList<>(enemies);
    }

    public Optional<GameObject> dispatchEnemy(){
        if(enemyToDispatch.isEmpty())   return Optional.empty();

        GameObject tmp = enemyToDispatch.get(0);
        enemyToDispatch.remove(0);

        return Optional.of(tmp);
    }
}
