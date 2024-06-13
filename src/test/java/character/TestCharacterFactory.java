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

    Character knight,wizard,knave;
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
        knave= CharacterFactory.getKnave(new World(widthWorld, heighWorld, ground));
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
        //per questo test considero che le misure del mondo sono 1280x702
        //l'altezza del personaggio sara 70 e 55 di lunghezza
        //faccio un primo update pos per posizonare bene il personaggio con i limite del ground
        knight.updatePos(10);
        //imposto come comando il jump
        inpuController.setJumping(true);
        //aggiorndo il vectorMovement
        knight.updateVectorMovement(inpuController);
        //dopo duecento millisecondi il wizzard dovrebbe avere una y minore di quella inziale
        knight.updatePos(200);
        //metto minore perchè nel nostro gioco più le y sono minori più il personaggio sta salendo
        assertTrue(knight.getPos().getY()<ground-(70/2));
        //dopo 3 secondi il personaggio dovrebbe essere ricarede a terra
        knight.updatePos(3000);
        assertEquals((int)knight.getPos().getY(), (int)(ground-(70/2))-1);
    }
    
    @Test
    void testWizzard(){
        //TODO: fare i test per il wizzard
    }

    @Test
    void testJumpKnave(){
        //per questo test considero che le misure del mondo sono 1280x702
        //l'altezza del personaggio sara 70 e 55 di lunghezza
        //faccio un primo update pos per posizonare bene il personaggio con i limite del ground
        knave.updatePos(10);
        //imposto come comando il jump
        inpuController.setJumping(true);
        //aggiorndo il vectorMovement
        knave.updateVectorMovement(inpuController);
        //dopo duecento millisecondi il wizzard dovrebbe avere una y minore di quella inziale
        knave.updatePos(200);
        //metto minore perchè nel nostro gioco più le y sono minori più il personaggio sta salendo
        assertTrue(knave.getPos().getY()<ground-(70/2));
        //dopo 3 secondi il personaggio dovrebbe essere ricarede a terra
        knave.updatePos(3000);
        assertEquals((int)knave.getPos().getY(), (int)(ground-(70/2))-1);
    }

    
}
