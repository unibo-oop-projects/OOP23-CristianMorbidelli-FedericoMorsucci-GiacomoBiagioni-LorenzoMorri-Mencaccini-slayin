package slayin.model.utility;

import java.util.Random;

import slayin.model.World;
import slayin.model.bounding.BoundingBoxImplRet;
import slayin.model.entities.GameObject;
import slayin.model.entities.Dummy;

/**
 * A class that generates the objects of the entities that need to be added to the scene.
 * In this class are described how different entities are created (starting positions, bounding boxes, etc...)
 */
public class EntityFactory {

    private final World world;
    private final Random rn;

    public EntityFactory(World w){
        this.world = w;
        rn = new Random();
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
}
