package slayin.model.entities.enemies;

import slayin.model.World;
import slayin.model.bounding.BoundingBox;
import slayin.model.entities.GameObject;
import slayin.model.events.GameEventListener;
import slayin.model.utility.P2d;
import slayin.model.utility.Vector2d;

public abstract class Enemy extends GameObject {

    private GameEventListener eventListener;

    public Enemy(P2d pos, Vector2d vectorMovement, BoundingBox boundingBox, World world, GameEventListener eventListener) {
        super(pos, vectorMovement, boundingBox, world);
        this.eventListener = eventListener;
    }
    protected GameEventListener getEventListener(){
        return this.eventListener;
    }
    public void updateDir(){
    }
    public void updatePos(int dt){
    }
    public int getScorePerKill(){
        return 0;
    }
}
