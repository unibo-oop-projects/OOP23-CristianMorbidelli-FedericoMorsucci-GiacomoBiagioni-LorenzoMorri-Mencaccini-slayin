package slayin.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import slayin.model.bounding.BoundingBoxImplRet;
import slayin.model.entities.GameObject;
import slayin.model.entities.boss.Imp;
import slayin.model.entities.character.Character;
import slayin.model.entities.character.CharacterFactory;
import slayin.model.entities.shots.ShotObject;
import slayin.model.events.GameEventListener;
import slayin.model.events.GameOverEvent;
import slayin.model.score.GameScore;
import slayin.model.utility.Constants;

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

    public GameStatus(GameEventListener eventListener){
        world = new World(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT, 600);
        character = CharacterFactory.getKnave(world);
        enemies = new ArrayList<>();
        shots=new ArrayList<>();
        //enemies.add(new Minotaur(null, new BoundingBoxImplRet(null, 100, 150),world));
        enemies.add(new Imp(null, new BoundingBoxImplRet(null, 70, 70), world, eventListener));
        scoreManager = new GameScore();
        this.eventListener = eventListener;

        tickSinceLastEnemyAdded = 0;
    }

    public List<GameObject> getObjects(){   
        List<GameObject> all = new ArrayList<>();
        all.add(character);
        all.addAll(enemies);
        all.addAll(shots);
        return all;
    }

    public void addEnemy(Optional<GameObject> entity){
        if(entity.isPresent())
            enemies.add(entity.get());
    }

    public void removeEnemy(GameObject entity){
        enemies.remove(entity);
    }

    public void addShot(ShotObject shot){
        shots.add(shot);
    }

    public void removeShot(ShotObject shot){
        shots.remove(shot);
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

    public List<ShotObject> getShots(){
        return this.shots;
    }


    public void setLevel(Optional<Level> level){
        if(level.isPresent()){
            System.out.println("Starting level " + level.get().getID());
            this.level = level.get();
        }else{    
            // the Optional will be empty if no more levels can be read
            eventListener.addEvent(new GameOverEvent());
        }
    }


    public GameScore getScoreManager() {
        return this.scoreManager;
    }

    public Level getLevel(){
        return this.level;

    }

    public void addEnemiesToScene() {
        //System.out.println("The scene currently have " + enemies.size() + " enemies; can contain " + level.getCapacity());  
        double capacityReached = ((double) enemies.size()/ (double) level.getCapacity()) * 100;
        long currentTime = System.currentTimeMillis();

        if(capacityReached >= 100){
            System.out.println("NON AGGIUNGO");
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
