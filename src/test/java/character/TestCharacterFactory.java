package character;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import slayin.model.World;
import slayin.model.entities.character.Character;
import slayin.model.entities.character.CharacterFactory;
import slayin.model.movement.MovementController;

public class TestCharacterFactory {

    Character knight,wizard;
    int widthWorld,heighWorld,ground;
    MovementController inpuController;
    
    @BeforeEach
    void setUp(){
        widthWorld=1280;
        heighWorld=720;
        ground=600;
        inpuController= new MovementController();
        knight= CharacterFactory.getKnight(new World(widthWorld, heighWorld, ground));
        wizard= CharacterFactory.getWizard(new World(widthWorld, heighWorld, ground));
    }

    @Test
    void testCollisonWithEdgeKnight(){
        //per questo test considero che le misure del mondo sono 1280x702
        //l'altezza del personaggio sara 70 e 55 di lunghezza
        inpuController.setMovingRight(true);
        knight.updateVectorMovement(inpuController);
        //faccio passare 50 secondi almeno sono sicuro che arrivi nel bordo a destra
        knight.updatePos(50000);
        System.out.println(knight.getPos().getX());
        // lo confronto a (int)widthWorld-(55/2) perchè gli tolgo la meta della lunghezza del personaggio visto che la pos
        //del personaggio è la sua posizione centrale
        assertEquals((int)knight.getPos().getX(), (int)widthWorld-(55/2)-1);
        inpuController.setMovingRight(false);
        inpuController.setMovingLeft(true);
        knight.updateVectorMovement(inpuController);
        //faccio passare 50 secondi almeno sono sicuro che arrivi nel bordo a destra
        knight.updatePos(50000);
        System.out.println(knight.getPos().getX());
        assertEquals((int)knight.getPos().getX(), (int)(0+(55/2)));
    }

    @Test
    void testCollisonWithEdgeWizard(){
        //per questo test considero che le misure del mondo sono 1280x702
        //l'altezza del personaggio sara 70 e 55 di lunghezza
        inpuController.setMovingRight(true);
        wizard.updateVectorMovement(inpuController);
        //faccio passare 50 secondi almeno sono sicuro che arrivi nel bordo a destra
        wizard.updatePos(50000);
        System.out.println(wizard.getPos().getX());
        // lo confronto a (int)widthWorld-(55/2) perchè gli tolgo la meta della lunghezza del personaggio visto che la pos
        //del personaggio è la sua posizione centrale
        assertEquals((int)wizard.getPos().getX(), (int)widthWorld-(55/2)-1);
        inpuController.setMovingRight(false);
        inpuController.setMovingLeft(true);
        wizard.updateVectorMovement(inpuController);
        //faccio passare 50 secondi almeno sono sicuro che arrivi nel bordo a destra
        wizard.updatePos(50000);
        System.out.println(wizard.getPos().getX());
        assertEquals((int)wizard.getPos().getX(), (int)(0+(55/2)));
    }

    @Test
    void testJumpKnight(){
        //TODO: da sistemare
        //per questo test considero che le misure del mondo sono 1280x702
        //l'altezza del personaggio sara 70 e 55 di lunghezza
        inpuController.setJumping(true);
        knight.updateVectorMovement(inpuController);
        System.out.println(knight.getPos().getY());
        //dopo due secondi il wizzard dovrebbe avere una posizione maggiore di quella inziale
        knight.updatePos(100);
        //metto minore perchè nel nostro gioco più le y minori più il personaggio sta salendo
        System.out.println(knight.getPos().getY());
        assertTrue(knight.getPos().getY()<ground-(70/2));
    }

    void testWizzard(){
        //TODO: fare i test per il wizzard
    }

    
}
