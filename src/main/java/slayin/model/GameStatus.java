package slayin.model;

import java.util.ArrayList;
import java.util.List;

import slayin.model.entities.GameObject;
import slayin.model.entities.character.Character;
import slayin.model.entities.character.CharacterFactory;
import slayin.model.score.GameScore;
import slayin.model.utility.Constants;

public class GameStatus {
    World world;
    private Level level;

    Character character;
    List<GameObject> enemies;
    List<GameObject> shots;
    private GameScore scoreManager;

    public GameStatus(){
        world = new World(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT, 600);
        character = CharacterFactory.getKnight(world);
        enemies = new ArrayList<>();
        //enemies.add(new Minotaur(null, new BoundingBoxImplRet(null, 100, 150),world));
        scoreManager = new GameScore();
    }

    public List<GameObject> getObjects(){   
        List<GameObject> all = new ArrayList<>();
        all.add(character);
        all.addAll(enemies);
        all.addAll(shots);
        return all;
    }

    public void addShot(GameObject shot){
        shots.add(shot);
    }

    public void removeShot(GameObject shot){
        shots.remove(shot);
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

    public List<GameObject> getShots(){
        return this.shots;
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
