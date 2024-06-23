package slayin.model.entities.character;

import slayin.model.entities.graphics.DrawComponent;
import slayin.model.entities.graphics.DrawComponentFactory;


/**
 * Represents the health of a character with current and maximum health values.
 */
public class Health {
    private int health;
    private int maxHealth;

    /**
     * Constructs a Health object with specified initial and maximum health values.
     *
     * @param health    the initial health value
     * @param maxHealth the maximum health value
     */
    public Health(int health, int maxHealth) {
        this.health = health;
        this.maxHealth = maxHealth;
    }

    public DrawComponent getDrawComponent(){
        return DrawComponentFactory.graphicsComponentHealth(this);
    }


    /**
     * Gets the current health value.
     *
     * @return the current health value
     */
    public int getHealth() {
        return health;
    }

    /**
     * Gets the maximum health value.
     *
     * @return the maximum health value
     */
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * Decreases the health by a specified damage value.
     * The health will only be decreased if the damage value is greater than zero.
     *
     * @param damage the value by which to decrease the health, must be greater than zero
     */
    public void decLife(int damage){
        if(damage>0){
            this.health= health-damage;
        }
    }

    /**
     * Decreases the health by a specified damage value.
     * The health will only be increased to the maximum life possible.
     *
     * @param amount - the value by which to increase the health, must be greater than zero
     */
    public void heal(int amount){
        if(amount>0){
            this.health += amount;
            if(this.health>maxHealth) this.health = maxHealth;
        }
    }
    
}
