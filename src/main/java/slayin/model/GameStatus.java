package slayin.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import slayin.model.entities.GameObject;
import slayin.model.entities.character.Character;
import slayin.model.entities.character.CharacterFactory;
import slayin.model.events.GameEventListener;
import slayin.model.events.collisions.CharacterCollisionEvent;
import slayin.model.score.GameScore;
import slayin.model.utility.Constants;

public class GameStatus {
    World world;
    private Level level;

    Character character;
    List<GameObject> enemies;
    private GameScore scoreManager;

    private GameEventListener eventListener;

    public GameStatus(GameEventListener eventListener){
        world = new World(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT, 600);
        character = CharacterFactory.getWizard(world);
        enemies = new ArrayList<>(); 
        scoreManager = new GameScore();
        this.eventListener = eventListener;
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


    public void setLevel(Optional<Level> level){
        if(level.isPresent())
            this.level = level.get();
        else{    
            // the Optional will be empty if no more levels can be read
            // TODO: to call the gameover scene, the character gets killed and then a collision event gets called
            character.decLife(character.getLife().getHealth());
            eventListener.addEvent(new CharacterCollisionEvent(character));
        }
    }


    public GameScore getScoreManager() {
        return this.scoreManager;
    }

    public Level getLevel(){
        return this.level;

    }
}
