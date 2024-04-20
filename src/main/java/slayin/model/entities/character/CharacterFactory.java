package slayin.model.entities.character;

import slayin.model.bounding.BoundingBoxImplRet;
import slayin.model.entities.GameObject.Direction;
import slayin.model.utility.Constants;
import slayin.model.utility.P2d;
import slayin.model.utility.Vector2d;
import slayin.model.World;

import java.util.function.Consumer;

public class CharacterFactory {
    

    public static Character getKnight(World w){
        MeleeWeapon weapon = new MeleeWeapon(10, new BoundingBoxImplRet(new P2d(0, 0), 50, 35), 0, 55,"Sword",-1);
        Consumer<Character> func= new Consumer<Character>() {

            @Override
            public void accept(Character t) {
                t.getVectorMovement().setY(Constants.FJUMP_CHARACTER);
            }
            
        };  
        return new Knight(new P2d(500, 350), new Vector2d(1, 0), new BoundingBoxImplRet(new P2d(0, 0), 55, 70),new Health(10, 10),w,func,weapon);
    }

    public static Character getKnightModify(World w){
        MeleeWeapon weapon = new MeleeWeapon(10, new BoundingBoxImplRet(new P2d(0, 0), 50, 35), 0, 55,"SwordL",-1);
        Consumer<Character> func= new Consumer<Character>() {

            @Override
            public void accept(Character t) {
                //t.getVectorMovement().setY(Constants.FJUMP_CHARACTER);
                t.addWeapon(new MeleeWeapon(10, new BoundingBoxImplRet(new P2d(0, 0), 50, 35), 0, -55,"SwordR",2000));
                t.setTimeBlockedJump(1000);
                t.setTimeBlockedMove(100);
                if(t.getDir()==Direction.LEFT){
                    t.getVectorMovement().setX(-1200);
                }else{
                    t.getVectorMovement().setX(1200);
                }

            }
            
        };  
        return new Knight(new P2d(500, 350), new Vector2d(1, 0), new BoundingBoxImplRet(new P2d(0, 0), 55, 70),new Health(10, 10),w,func,weapon);
    }
}
