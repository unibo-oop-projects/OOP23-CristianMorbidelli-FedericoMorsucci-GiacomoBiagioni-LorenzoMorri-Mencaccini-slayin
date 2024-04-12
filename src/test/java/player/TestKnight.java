package player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import slayin.model.InputController;
import slayin.model.World;
import slayin.model.bounding.BoundingBoxImplRet;
import slayin.model.character.Knight;
import slayin.model.character.MeleeWeapon;
import slayin.model.utility.P2d;
import slayin.model.utility.Vector2d;
import java.awt.*;

import java.awt.event.WindowAdapter; 
import java.awt.event.WindowEvent; 
  
class canva extends Frame { 
    public canva() 
    { 
        setVisible(true); 
        //setSize(500, 600); 
        setSize(1000, 1000); 
        addWindowListener(new WindowAdapter() { 
            @Override
            public void windowClosing(WindowEvent e) 
            { 
                System.exit(0); 
            } 
        }); 
    } 
    public void paint(Graphics g) 
    { 
        //g.drawRect(100, 100, 100, 50); 
        g.drawLine(0,660 , 1000, 660);
    } 
}

public class TestKnight {
    
    Knight k;
    InputController controller;
    canva c;
    int prevX,prevY;
    World w;
    MeleeWeapon m;

    @BeforeEach                                         
    void setUp() {
        P2d point = new P2d(600, 590);
        m= new MeleeWeapon(1, new BoundingBoxImplRet(new P2d(620,590), 5, 5),0,20);
        k = new Knight(point, new Vector2d(1, 0), new BoundingBoxImplRet(point, 40, 50),10,m);
        controller= new InputController();
        c = new canva();
        prevX=0;
        prevY=0;
        w = new World(1000, 1000, 610);
    }

    @Test
    public void testVel(){
        controller.setMoveLeft();
        k.updateVel(controller);
        controller.unSetMoveLeft();
        controller.setMoveUp();
        k.updateVel(controller);

        long startTime, timePassed, lastTime;
		long tickTime = 5;	/* 200 fps */
        lastTime=System.currentTimeMillis();
		for(int i=0;i<1000;i++){/* Game loop */
			startTime = System.currentTimeMillis();
            //System.out.println((int)(startTime-lastTime));
            k.updatePos((int)(startTime-lastTime),w);
            this.render(k);
            System.out.println(k.getPos());
            k.getWeapons().stream().forEach(t->{
                BoundingBoxImplRet r=(BoundingBoxImplRet)t.getBoxWeapon();
                System.out.println("il punto della spada è:"+r.getPoint());
            });
            lastTime=System.currentTimeMillis();
			timePassed = lastTime - startTime;
			if(timePassed < tickTime){	/* wait until tickTime before nextFrame */
				try {
					Thread.sleep(tickTime-timePassed);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
            lastTime=startTime;
		}
    }

    public void render(Knight k){
        Graphics g = c.getGraphics();
        g.clearRect(prevX, prevY, 200, 200);
        g.drawLine(0,660 , 1000, 660);
        prevX=(int)k.getPos().getX();
        prevY=(int)k.getPos().getY();
        g.drawOval(prevX, prevY, 40, 50); 
        for(var w : k.getWeapons()){
            var bBox = w.getBoxWeapon();            
            g.drawOval((int)bBox.getPoint().getX(),(int) bBox.getPoint().getY(), 50, 20); 
        }

    }
}
