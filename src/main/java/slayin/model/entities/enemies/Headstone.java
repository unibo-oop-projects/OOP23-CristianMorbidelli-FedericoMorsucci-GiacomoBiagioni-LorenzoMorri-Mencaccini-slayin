package slayin.model.entities.enemies;

import slayin.model.World;
import slayin.model.bounding.BoundingBox;
import slayin.model.bounding.BoundingBoxImplCirc;
import slayin.model.bounding.BoundingBoxImplRet;
import slayin.model.entities.graphics.DrawComponent;
import slayin.model.entities.graphics.DrawComponentFactory;
import slayin.model.entities.shots.HeadstoneShot;
import slayin.model.events.GameEventListener;
import slayin.model.events.collisions.SpawnShotsEvent;
import slayin.model.utility.P2d;
import slayin.model.utility.Vector2d;

public class Headstone extends Enemy {

    private static final int damageOnHit = 2;
    private static final int scorePerKill = 3;
    private int oldDt = 0;

    public Headstone(P2d pos, BoundingBox boundingBox, World world, GameEventListener eventListener) {
        super(pos, new Vector2d(0, 0), boundingBox, world, eventListener);
        if(this.getPos().getX()>world.getWidth()/2){
            this.setDir(Direction.LEFT);
        }else{
            this.setDir(Direction.RIGHT);
        }
    }
    
    @Override
    public void updatePos(int dt) {

        oldDt += dt;
        if(oldDt>3500){
            this.shot();
            oldDt = 0;
        }
    }

    private void shot(){
        BoundingBoxImplRet bBox = (BoundingBoxImplRet)this.getBoundingBox();
        int direction=1;
        double x = bBox.getWidth()/2;
        if(this.getDir()==(Direction.LEFT)){
            direction = -1;
        }
        P2d shotSpawn = new P2d(this.getPos().getX()+x*direction,this.getPos().getY()-bBox.getHeight()/4);
        HeadstoneShot skull = new HeadstoneShot(shotSpawn, new Vector2d(this.getWorld().getWidth()/3.5*direction, 0), new BoundingBoxImplCirc(shotSpawn, bBox.getWidth()/3), this.getWorld());

        this.getEventListener().addEvent(new SpawnShotsEvent(skull));
    }

    @Override
    public int getScorePerKill(){
        return scorePerKill;
    }

    @Override
    public DrawComponent getDrawComponent(){
        return DrawComponentFactory.graphicsComponentEnemy((Enemy)this);
    }

    @Override
    public void updateDir() {

    }

    @Override
    public int getDamageOnHit() {
        return Headstone.damageOnHit;
    }
}
