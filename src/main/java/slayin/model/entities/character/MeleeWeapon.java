package slayin.model.entities.character;

import slayin.model.bounding.BoundingBox;
import slayin.model.utility.P2d;

/**
 * class to represent a melee weapon
 */
public class MeleeWeapon {
    private int damage;
    private BoundingBox boxWeapon;
    private int heightFromPlayer,widthFromPlayer;

    /**
     * The constructor of the MeleeWeapon class
     * @param damage - weapon damage
     * @param boxWeapon - bounding box weapon
     * @param heightFromPlayer - vertical distance of the weapon from the player's center
     * @param widthFromPlayer - horizontal distance of the weapon from the player's center
     */
    public MeleeWeapon(int damage, BoundingBox boxWeapon,int heightFromPlayer,int widthFromPlayer) {
        this.damage = damage;
        this.boxWeapon = boxWeapon;
        this.heightFromPlayer= heightFromPlayer;
        this.widthFromPlayer= widthFromPlayer;
    }

    /**
     * A getter for the boxWeapon attribute
     * @return bounding Box weapons
     */
    public BoundingBox getBoxWeapon() {
        return boxWeapon;
    }
    
    /**
     * A getter for the damage attribute
     * @return value of the damage
     */
    public int getDamage(){
        return this.damage;
    }

    /**
     * updates the center point of the weapon's bounding box
     * @param p - new point of the bounding box
     */
    public void updateBoxWeapon(P2d p){
        this.boxWeapon.updatePoint(p);
    }

    /**
     * A getter for the heightFromPlayer attribute
     * @return - vertical distance of the weapon from the player's center
     */
    public int getHeightFromPlayer() {
        return this.heightFromPlayer;
    }

    /**
     * A getter for the widthFromPlayer attribute
     * @return - horizontal distance of the weapon from the player's center
     */
    public int getWidthFromPlayer() {
        return this.widthFromPlayer;
    }

    
}
