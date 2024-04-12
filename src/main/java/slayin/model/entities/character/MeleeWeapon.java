package slayin.model.entities.character;

import slayin.model.bounding.BoundingBox;
import slayin.model.utility.P2d;

public class MeleeWeapon {
    private int damage;
    private BoundingBox boxWeapon;
    private int heightFromPlayer,widthFromPlayer;


    public MeleeWeapon(int damage, BoundingBox boxWeapon,int heightFromPlayer,int widthFromPlayer) {
        this.damage = damage;
        this.boxWeapon = boxWeapon;
        this.heightFromPlayer= heightFromPlayer;
        this.widthFromPlayer= widthFromPlayer;
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

    public int getHeightFromPlayer() {
        return this.heightFromPlayer;
    }

    public int getWidthFromPlayer() {
        return this.widthFromPlayer;
    }

    
}
