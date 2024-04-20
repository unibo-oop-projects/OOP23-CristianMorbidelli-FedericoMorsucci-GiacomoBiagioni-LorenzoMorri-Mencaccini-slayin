package slayin.model.entities.character;

import slayin.model.World;
import slayin.model.bounding.BoundingBox;
import slayin.model.utility.P2d;
import slayin.model.utility.Vector2d;
/**
 * class of the knight, 
 * compared to a normal character he can jump
 */
public class Knight extends Character{
     /**
     * The constructor of the Knight class
     * @param pos -initial position of the character
     * @param vectorMouvement - displacement vector
     * @param boundingBox - boundinf box of the character
     * @param life - initial value of the character's life
     * @param world - reference world used the character
     * @param weapons - melee weapons belonging to the character
     */
    public Knight(P2d pos,Vector2d VectorMouvement,BoundingBox boundingBox,World world, Health life,MeleeWeapon ... weapons) {
        super(pos,VectorMouvement,boundingBox,life,world, weapons);      
    }
    
}
