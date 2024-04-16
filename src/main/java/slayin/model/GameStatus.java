package slayin.model;

import java.util.ArrayList;
import java.util.List;

import slayin.model.bounding.BoundingBoxImplRet;
import slayin.model.entities.Dummy;
import slayin.model.entities.GameObject;
import slayin.model.entities.character.Knight;
import slayin.model.entities.character.Character;
import slayin.model.entities.character.MeleeWeapon;
import slayin.model.score.GameScore;
import slayin.model.utility.Constants;
import slayin.model.utility.P2d;
import slayin.model.utility.Vector2d;

public class GameStatus {
    World world;
    private Level level;

    Character character;
    List<GameObject> enemies;
    private GameScore scoreManager;

    public GameStatus(){
        world = new World(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT, 365);
        MeleeWeapon weapon = new MeleeWeapon(10, new BoundingBoxImplRet(new P2d(0, 0), 20, 15), 0, 30);
        character = new Knight(new P2d(500, world.getGround() - 15), new Vector2d(1, 0), new BoundingBoxImplRet(new P2d(0, 0), 30, 30),world,10,weapon);
        enemies = new ArrayList<>();
        enemies.add(new Dummy(new P2d(100, world.getGround() - 15), null, new BoundingBoxImplRet(new P2d(100, world.getGround() - 15), 40, 30), world));
        enemies.add(new Dummy(new P2d(500, world.getGround() - 15), null, new BoundingBoxImplRet(new P2d(500, world.getGround() - 15), 40, 30), world));
        enemies.add(new Dummy(new P2d(1000, world.getGround() - 15), null, new BoundingBoxImplRet(new P2d(1000, world.getGround() - 15), 40, 30), world));

        scoreManager = new GameScore();
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

    public Character getCharacter(){
        return this.character;
    }

    public List<GameObject> getEnemies(){
        return this.enemies;
    }


    public void setLevel(Level level){
        this.level = level;
    }


    public GameScore getScoreManager() {
        return this.scoreManager;
    }

    public Level getLevel(){
        return this.level;

    }
}
