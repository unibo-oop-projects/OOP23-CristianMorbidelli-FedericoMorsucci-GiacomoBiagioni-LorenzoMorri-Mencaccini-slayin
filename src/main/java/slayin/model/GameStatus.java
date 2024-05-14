package slayin.model;

import java.util.ArrayList;
import java.util.List;

import slayin.model.bounding.BoundingBoxImplRet;
import slayin.model.entities.GameObject;
import slayin.model.entities.boss.Minotaur;
import slayin.model.entities.character.Knight;
import slayin.model.entities.character.Character;
import slayin.model.entities.character.Health;
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
        world = new World(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT, 600);
        MeleeWeapon weapon = new MeleeWeapon(10, new BoundingBoxImplRet(new P2d(0, 0), 50, 35), 0, 55,"Sword");
        character = new Knight(new P2d(500, 350), new Vector2d(1, 0), new BoundingBoxImplRet(new P2d(0, 0), 55, 70),world,new Health(10, 10),weapon);
        enemies = new ArrayList<>();
        //enemies.add(new Minotaur(null, new BoundingBoxImplRet(null, 100, 150),world));
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
