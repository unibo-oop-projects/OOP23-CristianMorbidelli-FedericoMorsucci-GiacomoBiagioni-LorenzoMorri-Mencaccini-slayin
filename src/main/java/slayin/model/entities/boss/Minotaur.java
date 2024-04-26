package slayin.model.entities.boss;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import slayin.model.World;
import slayin.model.bounding.BoundingBox;
import slayin.model.utility.P2d;
import slayin.model.utility.Vector2d;

public class Minotaur extends Boss  {
    
    private List<String> sprites = new ArrayList<String>();
    private int state;
    final static int START=0, RUN=1, STUNNED=2, HITTED=3;
    
    /**
     * Minotaur constructor, set initial health, sprites
     * @param pos - intial position
     * @param vectorMovement - displacement vector
     * @param boundingBox -
     * @param world - reference world used the character
     * 
     */
    public Minotaur(P2d pos, Vector2d vectorMovement, BoundingBox boundingBox, World world) {
        //TODO: impostare set iniziale completo
        super(pos, vectorMovement, boundingBox, world);
        this.setHealth(5); //The Minotaur must receive 5 hits to be defeated
        this.setSprites();
        this.state=START;
    }

    @Override
    public void updatePos(int dt) {
        // TODO: impostare movimenti in base a vita o colpi subiti
        switch(this.state) {
            case START:
                //TODO: impostare cambio immagine (muove gamba per caricare colpo)
                if(this.secondDifference(5)){
                    this.state=RUN;
                }
                break;
            case RUN:
                //TODO: impostare vettore spostamento e se tocca muro diventa STUNNED
                break;
            case STUNNED:
                //TODO: alternare sprite
                //TOOD: se viene colpito devi cambiare stato e aggiornare timeFlag con resetTimeFlag
                if(this.secondDifference(5)){
                    this.state=START;
                }
                break;
            case HITTED:
                //TODO: alternare sprite
                if(this.secondDifference(2)){
                    this.state=START;
                }
                break;
            default:
                System.out.println("ERROR: Minotuar.state = "+ this.state);
        }
    }

    //TODO: sistemare path e renderlo generico, aggiungere gli altri sprite
    private void setSprites() {
        String path = Paths.get("assets", "boss", "minotaur","Minotaur.jpg").toString();
        
        this.sprites.add(path);
    }
    
}
