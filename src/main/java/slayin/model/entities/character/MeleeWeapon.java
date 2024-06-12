package slayin.model.entities.character;

import slayin.model.bounding.BoundingBox;
import slayin.model.utility.P2d;

/**
 * class to represent a melee weapon
 */
public class MeleeWeapon {
    private int damage;
    private String name;
    private BoundingBox boxWeapon;
    private int heightFromPlayer,widthFromPlayer;
    private int durationTime;
    private Long timeCreation;

    /**
     * The constructor of the MeleeWeapon class
     * @param damage - weapon damage
     * @param boxWeapon - bounding box weapon
     * @param heightFromPlayer - vertical distance of the weapon from the player's center
     * @param widthFromPlayer - horizontal distance of the weapon from the player's center
     * @param durationTime - the duration time of the weapon expressed in milliseconds
     */
    public MeleeWeapon(int damage, BoundingBox boxWeapon,int heightFromPlayer,int widthFromPlayer,String name,int durationTime) {
        this.damage = damage;
        this.boxWeapon = boxWeapon;
        this.heightFromPlayer= heightFromPlayer;
        this.widthFromPlayer= widthFromPlayer;
        this.name= name;
        this.durationTime=durationTime;
        this.timeCreation= System.currentTimeMillis();

    }
    
    /**
     * returns true if the weapon's duration is over and false otherwise
     * @return true if the weapon's duration is over and false otherwise
     */
    public Boolean isBroken(){
        int dt = (int) (System.currentTimeMillis() - this.timeCreation);
        //TODO: vedi se si trova un metodo migliore per geistire il -1
        if(durationTime==-1) return false;
        return dt>this.durationTime;
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
     * A getter for the name attribute
     * @return 
     */
    public String getName() {
        return name;
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
