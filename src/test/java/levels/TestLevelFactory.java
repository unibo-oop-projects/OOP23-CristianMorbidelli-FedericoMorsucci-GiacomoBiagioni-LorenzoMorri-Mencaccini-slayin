package levels;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import slayin.model.Level;
import slayin.model.World;
import slayin.model.entities.Dummy;
import slayin.model.entities.GameObject;
import slayin.model.utility.LevelFactory;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

public class TestLevelFactory {

    LevelFactory levelFactory;
    final int worldWidth = 1000;
    final int worldHeight = 1000;
    final int groundLevel = 800;

    @BeforeEach
    void setUp(){
        this.levelFactory = new LevelFactory(new World(worldWidth, worldHeight, groundLevel));
    }

    @Test
    void testGetEnemies(){
        Level test = levelFactory.buildLevel(0).get();

        List<GameObject> levelEnemies = new ArrayList<>();
        Optional<GameObject> obj;
        do{
            obj = test.dispatchEnemy();
            if(obj.isPresent()){
                levelEnemies.add(obj.get());
            }
        }while(obj.isPresent());

        assertTrue(levelEnemies.size() == 1);
        assertTrue(levelEnemies.get(0) instanceof Dummy);
    }
    
}
