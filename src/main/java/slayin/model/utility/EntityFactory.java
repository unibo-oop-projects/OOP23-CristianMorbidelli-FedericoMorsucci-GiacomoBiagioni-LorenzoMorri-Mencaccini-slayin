package slayin.model.utility;

import java.util.Random;

import slayin.model.World;
import slayin.model.bounding.BoundingBoxImplRet;
import slayin.model.entities.GameObject;
import slayin.model.entities.boss.Imp;
import slayin.model.entities.boss.Minotaur;
import slayin.model.entities.enemies.Couatl;
import slayin.model.entities.enemies.Fire;
import slayin.model.entities.enemies.Slime;
import slayin.model.events.GameEventListener;
import slayin.model.entities.Dummy;

/**
 * A class that generates the objects of the entities that need to be added to the scene.
 * In this class are described how different entities are created (starting positions, bounding boxes, etc...)
 */
public class EntityFactory {

    private final World world;
    private final Random rn;
    private final GameEventListener eventListener;

    public EntityFactory(World w, GameEventListener eventListener){
        this.world = w;
        rn = new Random();
        this.eventListener = eventListener;
    }

    public GameObject buildDummy(){
        final Vector2d DUMMY_STARTING_MOVEMENT = new Vector2d(0, 0);  // Starts with 0 speed

        final int DUMMY_HEIGHT = world.getHeight() / 20;
        final int DUMMY_LENGHT = DUMMY_HEIGHT;

        final int DUMMY_STARTING_X = (int) rn.nextInt(world.getWidth());    // Starts at a completely random X
        final int DUMMY_STARTING_Y = world.getGround() - DUMMY_HEIGHT/2;    // Starts at the ground level

        Dummy entity = new Dummy(new P2d(DUMMY_STARTING_X, DUMMY_STARTING_Y), DUMMY_STARTING_MOVEMENT, new BoundingBoxImplRet(new P2d(DUMMY_STARTING_X, DUMMY_STARTING_Y), DUMMY_LENGHT, DUMMY_HEIGHT), world);

        return entity;
    }

    public GameObject buildSlime(){

        final int SLIME_HEIGHT = world.getHeight() / 18;
        final int SLIME_LENGHT = SLIME_HEIGHT*2;

        final int DUMMY_STARTING_X = (int) rn.nextInt(world.getWidth());    // Starts at a completely random X
        final int DUMMY_STARTING_Y = world.getHeight() - SLIME_HEIGHT/2;    // Starts at the ground level

        Slime entity = new Slime(new P2d(DUMMY_STARTING_X, DUMMY_STARTING_Y),  new BoundingBoxImplRet(new P2d(DUMMY_STARTING_X, DUMMY_STARTING_Y), SLIME_LENGHT, SLIME_HEIGHT), world);

        return entity;
    }

    public GameObject buildFire(){
        final int FIRE_HEIGHT = world.getHeight() / 14;
        final int FIRE_LENGHT = FIRE_HEIGHT;

        final int DUMMY_STARTING_X = (int) rn.nextInt(world.getWidth());    // Starts at a completely random X
        final int DUMMY_STARTING_Y = world.getHeight()/2 - FIRE_HEIGHT/2;    // Starts at the ground level

        Fire entity = new Fire(new P2d(DUMMY_STARTING_X, DUMMY_STARTING_Y),  new BoundingBoxImplRet(new P2d(DUMMY_STARTING_X, DUMMY_STARTING_Y), FIRE_LENGHT, FIRE_HEIGHT), world);

        return entity;
    }

    public GameObject buildMinotaur(){
        BoundingBoxImplRet boundingBox= new BoundingBoxImplRet(null, world.getWidth()/12.8, world.getHeight()/4.8);
        
        return new Minotaur(null, boundingBox, this.world);
    }

    public GameObject buildImp(){
        BoundingBoxImplRet boundingBox=new BoundingBoxImplRet(null, world.getWidth()/18.29, world.getHeight()/10.29);
        
        return new Imp(null, boundingBox, this.world, this.eventListener);
    }
    public GameObject buildCouatl(){
        final int COUATL_HEIGHT = world.getHeight() / 18;
        final int COUATL_LENGHT = COUATL_HEIGHT;

        final int DUMMY_STARTING_X = (int) rn.nextInt(world.getWidth());    // Starts at a completely random X
        final int DUMMY_STARTING_Y = world.getHeight()/2 - COUATL_HEIGHT/2;    // Starts at the ground level

        Couatl entity = new Couatl(new P2d(DUMMY_STARTING_X, DUMMY_STARTING_Y),  new BoundingBoxImplRet(new P2d(DUMMY_STARTING_X, DUMMY_STARTING_Y), COUATL_LENGHT, COUATL_HEIGHT), world);

        return entity;
    }    
}
