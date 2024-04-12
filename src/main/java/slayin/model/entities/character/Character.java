package slayin.model.entities.character;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import slayin.model.World;
import slayin.model.bounding.BoundingBox;
import slayin.model.entities.GameObject;
import slayin.model.movement.MovementController;
import slayin.model.utility.P2d;
import slayin.model.utility.Vector2d;

public class Character extends GameObject{

    public static enum Direction { LEFT, RIGHT }
    private int life;
    private Direction dir;
    private List<MeleeWeapon> weapons;


    public Character(P2d pos, Vector2d vectorMouvement, BoundingBox boundingBox,int life, MeleeWeapon ... weapons) {
        super(pos, vectorMouvement, boundingBox);
        this.life=life;
        this.weapons= new ArrayList<>(Arrays.asList(weapons));
        //For now I'll default to LEFT, I'll probably change later
        this.dir=Direction.LEFT;
    }

    public List<MeleeWeapon> getWeapons(){
        return this.weapons;
    }

    public void setDir(Direction dir){
        this.dir=dir;
    }

    public Direction getDir(){
        return this.dir;
    }

    public boolean isAlive(){
        return life>0;
    }

    public void decLife(int damage){
        this.life= life-damage;
    }

    @Override
    public void updateVel(MovementController input) {
        if(input.isMovingLeft()){
            this.getVectorMouvement().setX(-150);
        }else if(input.isMovingRight()){
            this.getVectorMouvement().setX(150);
        }
    }

    @Override
    public void updatePos(int dt, World world) {
        this.setPos(this.getPos().sum(this.getVectorMouvement().mul(0.001*dt)));
        //update the boundingBox
        if(this.getDir()==Direction.LEFT){
            this.getWeapons().stream().forEach(t->t.updateBoxWeapon(new P2d(this.getPos().getX()-t.getWidthFromPlayer(),this.getPos().getY()+t.getHeightFromPlayer())));
        }else{
            this.getWeapons().stream().forEach(t->t.updateBoxWeapon(new P2d(this.getPos().getX()+t.getWidthFromPlayer(),this.getPos().getY()+t.getHeightFromPlayer())));
        }
    }

    
}
