package slayin.model;

import java.util.ArrayList;
import java.util.List;

import slayin.model.bounding.BoundingBoxImplRet;
import slayin.model.entities.GameObject;
import slayin.model.entities.character.Knight;
import slayin.model.entities.character.MeleeWeapon;
import slayin.model.utility.P2d;
import slayin.model.utility.Vector2d;

public class GameStatus {

    World world;
    
    
    GameObject character;
    List<GameObject> enemies;

    public GameStatus(){
        world = new World(1240, 720, 365);
        MeleeWeapon weapon = new MeleeWeapon(10, new BoundingBoxImplRet(new P2d(0, 0), 20, 15), 0, 30);
        character = new Knight(new P2d(500, 350), new Vector2d(1, 0), new BoundingBoxImplRet(new P2d(0, 0), 30, 30),world,10,weapon);
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

    public World getWorld(){
        return this.world;
    }

    public GameObject getCharacter(){
        return this.character;
    }
}
