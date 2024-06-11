package slayin.model.entities.character;

import slayin.model.bounding.BoundingBoxImplRet;
import slayin.model.entities.GameObject;
import slayin.model.entities.GameObject.Direction;
import slayin.model.entities.character.shots.RoundBullet;
import slayin.model.utility.Constants;
import slayin.model.utility.P2d;
import slayin.model.utility.Vector2d;
import slayin.model.World;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;


/**
 * Factory class to create different types of characters.
 */
public class CharacterFactory { 

    /**
     * Creates a Knight character.
     * 
     * @param w the game world
     * @return the created Knight character
     */
    public static Character getKnight(World w){
        // la widthFromPlayer la calcolo facendo widthPlayer/2 + BoundingBoxWeapon/2
        MeleeWeapon weapon = new MeleeWeapon(10, new BoundingBoxImplRet(new P2d(0, 0), 50, 35), 0, 53,"SwordL",-1);
        Consumer<Character> func= new Consumer<Character>() {

            @Override
            public void accept(Character t) {
                t.getVectorMovement().setY(Constants.FJUMP_CHARACTER);
            }
            
        };  
        Function<Character,Optional<GameObject>> getShot= (c)->{return Optional.empty();};
        return new Character(new P2d(500, 350), new Vector2d(1, 0), new BoundingBoxImplRet(new P2d(0, 0), 55, 70),new Health(10, 10),w,func,getShot,"Knight",weapon);
    }


    /**
     * Creates a modified Knight character.
     * 
     * @param w the game world
     * @return the created modified Knight character
     */
    public static Character getKnightModify(World w){
        MeleeWeapon weapon = new MeleeWeapon(10, new BoundingBoxImplRet(new P2d(0, 0), 50, 35), 0, 53,"SwordL",-1);
        Consumer<Character> func= new Consumer<Character>() {

            @Override
            public void accept(Character t) {
                //t.getVectorMovement().setY(Constants.FJUMP_CHARACTER);
                t.addWeapon(new MeleeWeapon(10, new BoundingBoxImplRet(new P2d(0, 0), 50, 35), 0, -53,"SwordR",2000));
                t.setTimeBlockedJump(1000);
                t.setTimeBlockedMove(100);
                if(t.getDir()==Direction.LEFT){
                    t.getVectorMovement().setX(-1200);
                }else{
                    t.getVectorMovement().setX(1200);
                }

            }
            
        };  
        Function<Character,Optional<GameObject>> getShot= (c)->{return Optional.empty();};
        return new Character(new P2d(500, 350), new Vector2d(1, 0), new BoundingBoxImplRet(new P2d(0, 0), 55, 70),new Health(10, 10),w,func,getShot,"Knight",weapon);
    }


    /**
     * Creates a Wizard character.
     * 
     * @param w the game world
     * @return the created Wizard character
     */
    public static Character getWizard(World w){
        Consumer<Character> func= new Consumer<Character>() {

            @Override
            public void accept(Character t) {
                //t.getVectorMovement().setY(Constants.FJUMP_CHARACTER);
                t.addWeapon(new MeleeWeapon(10, new BoundingBoxImplRet(new P2d(0, 0), 50, 200), 65, 0,"Vortex",1000));
                t.setTimeBlockedJump(2000);
                t.setTimeBlockedDecLife(1000);
            }
            
        };  

        Function<Character,Optional<GameObject>> getShot= (c)->{return Optional.of(new RoundBullet(c,w));};

        return new Character(new P2d(500, 350), new Vector2d(1, 0), new BoundingBoxImplRet(new P2d(0, 0), 55, 70),new Health(10, 10),w,func,getShot,"Wizard");
    }
}
