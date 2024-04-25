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
    }

    @Override
    public void updatePos(int dt) {
        // TODO: impostare stati boss e movimenti in base a vita o colpi subiti
    }

    //TODO: sistemare path e renderlo generico, aggiungere gli altri sprite
    private void setSprites() {
        String path = Paths.get("assets", "boss", "minotaur","Minotaur.jpg").toString();
        
        this.sprites.add(path);
    }
    
}
