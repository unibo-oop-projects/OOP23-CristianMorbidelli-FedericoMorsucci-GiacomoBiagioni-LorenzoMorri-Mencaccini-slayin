package slayin.model;

import java.util.ArrayList;
import java.util.List;

import slayin.model.bounding.BoundingBoxImplRet;
import slayin.model.character.Knight;
import slayin.model.utility.P2d;
import slayin.model.utility.Vector2d;

public class GameStatus {
    
    GameObject character;
    List<GameObject> enemies;

    public GameStatus(){
        character = new Knight(new P2d(0, 0), new Vector2d(1, 0), new BoundingBoxImplRet(new P2d(0, 0), 30, 30));
        enemies = new ArrayList<>();
    }

    public List<GameObject> getObjects(){   
        List<GameObject> all = new ArrayList<>();
        all.add(character);
        all.addAll(enemies);

        return all;
    }

    public void addEnemy(GameObject entity){
        enemies.add(entity);
    }

    public void removeEnemy(GameObject entity){
        enemies.remove(entity);
    }
}
