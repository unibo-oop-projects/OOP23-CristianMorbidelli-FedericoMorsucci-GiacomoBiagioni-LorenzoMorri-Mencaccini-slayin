package levels;

import org.junit.jupiter.api.Test;

import slayin.model.Level;
import slayin.model.entities.GameObject;
import slayin.model.utility.LevelFactory;

import java.util.List;
import java.util.ArrayList;

public class TestLevelFactory {

    @Test
    void testGetEnemies(){
        Level test = LevelFactory.buildLevel(1);

        List<GameObject> levelEnemies = new ArrayList<>();
        GameObject obj;
        do{
            obj = test.dispatchEnemy().get();
            if(obj!=null){
                levelEnemies.add(obj);
            }
        }while(obj!=null);

        System.out.println(levelEnemies);
    }
    
}
