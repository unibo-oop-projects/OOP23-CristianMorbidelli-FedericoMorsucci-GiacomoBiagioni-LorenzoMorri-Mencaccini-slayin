package slayin.model.entities.character;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import slayin.model.World;
import slayin.model.bounding.BoundingBox;
import slayin.model.entities.GameObject;
import slayin.model.entities.graphics.DrawComponent;
import slayin.model.entities.graphics.DrawComponentFactory;
import slayin.model.movement.MovementController;
import slayin.model.utility.P2d;
import slayin.model.utility.Vector2d;

/**
 *  Base class for all characters.
 *  by default the character only moves left and right
 */
public class Character extends GameObject{
    private int life;
    private List<MeleeWeapon> weapons;

    /**
     * The constructor of the Character class
     * @param pos -initial position of the character
     * @param vectorMouvement - displacement vector
     * @param boundingBox - boundinf box of the character
     * @param life - initial value of the character's life
     * @param world - reference world used the character
     * @param weapons - melee weapons belonging to the character
     */
    public Character(P2d pos, Vector2d vectorMouvement, BoundingBox boundingBox,int life,World world, MeleeWeapon ... weapons) {
        super(pos, vectorMouvement, boundingBox,world);
        this.life=life;
        this.weapons= new ArrayList<>(Arrays.asList(weapons));
    }

    /**
     * A getter for the weapons attribute
     * @return list of melee weapons
     */
    public List<MeleeWeapon> getWeapons(){
        return this.weapons;
    }

    /**
     * A method that returns true if the life value is greater than zero
     * @return true if the life value is greater than zero and false otherwise
     */
    public boolean isAlive(){
        return life>0;
    }

    /**
     * A method decreases life by a defined value
     * @param damage - value that will decrease life, must be greater than zero otherwise it will not decrease life
     */
    public void decLife(int damage){
        if(damage>0){
            this.life= life-damage;
        }
    }

    @Override
    public DrawComponent getDrawComponent(){
        return DrawComponentFactory.graphicsComponentCharacter(this);
    }

    @Override
    public void updateVel(MovementController input) {
        if(input.isMovingLeft()){
            this.getVectorMovement().setX(-150);
        }else if(input.isMovingRight()){
            this.getVectorMovement().setX(150);
        }
    }

    @Override
    public void updatePos(int dt) {
        this.setPos(this.getPos().sum(this.getVectorMovement().mul(0.001*dt)));
        //update the boundingBox
        if(this.getDir()==Direction.LEFT){
            this.getWeapons().stream().forEach(t->t.updateBoxWeapon(new P2d(this.getPos().getX()-t.getWidthFromPlayer(),this.getPos().getY()+t.getHeightFromPlayer())));
        }else{
            this.getWeapons().stream().forEach(t->t.updateBoxWeapon(new P2d(this.getPos().getX()+t.getWidthFromPlayer(),this.getPos().getY()+t.getHeightFromPlayer())));
        }
    }

    
}
