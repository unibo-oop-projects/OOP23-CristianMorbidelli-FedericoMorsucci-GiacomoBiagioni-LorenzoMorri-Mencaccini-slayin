package slayin.model.character;

import slayin.model.bounding.BoundingBox;
import slayin.model.utility.P2d;

public class MeleeWeapon {
    private int damage;
    private BoundingBox boxWeapon;


    public MeleeWeapon(int damage, BoundingBox boxWeapon) {
        this.damage = damage;
        this.boxWeapon = boxWeapon;
    }

    public BoundingBox getBoxWeapon() {
        return boxWeapon;
    }
    
    public int getDamage(){
        return this.damage;
    }

    public void updateBoxWeapon(P2d p){
        this.boxWeapon.updatePoint(p);
    }

    
}
