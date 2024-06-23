package slayin.model.entities.shots;

public interface Shot {

    /**
     * @return true if the shot is shooted from an enemy entity
     */
    public boolean isFromEnemy();
    
    /**
     * @return damage dealed to character on contact
     */
    public int getDamageOnHit();
}
