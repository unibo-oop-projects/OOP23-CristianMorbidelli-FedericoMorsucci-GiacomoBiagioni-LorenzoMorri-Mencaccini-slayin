package slayin.model.entities.character;

import java.util.function.Consumer;

import slayin.model.World;
import slayin.model.bounding.BoundingBox;
import slayin.model.utility.P2d;
import slayin.model.utility.Vector2d;

public class Wizard extends Character {

    public Wizard(P2d pos, Vector2d vectorMouvement, BoundingBox boundingBox, Health life, World world,
            Consumer<Character> jumpFunc, MeleeWeapon... weapons) {
        super(pos, vectorMouvement, boundingBox, life, world, jumpFunc, weapons);
    }
    
}
