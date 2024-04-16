package slayin.model.entities.character;

import slayin.model.entities.graphics.DrawComponent;

public class Health {
    private int health;
    private int maxHealth;

    public Health(int health, int maxHealth) {
        this.health = health;
        this.maxHealth = maxHealth;
    }

    public DrawComponent getDrawComponent(){
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updatePos'");
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }
    
}
