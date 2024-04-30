package boss;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import slayin.model.entities.boss.Boss;
import slayin.model.entities.boss.Minotaur;

public class TestMinotaur {
    
    Boss minotaur; 
    
    @BeforeEach
    void setUp(){
        minotaur= new Minotaur(null, null, null);
    }

    @Test
    void testLogic(){
        
    }
}
