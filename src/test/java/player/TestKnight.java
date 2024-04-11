package player;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import slayin.model.InputController;
import slayin.model.bounding.BoundingBox;
import slayin.model.bounding.BoundingBoxImplRet;
import slayin.model.character.Knight;
import slayin.model.utility.P2d;
import slayin.model.utility.Vector2d;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


import java.awt.*; 
import java.awt.event.WindowAdapter; 
import java.awt.event.WindowEvent; 
  
class canva extends Frame { 
    public canva() 
    { 
        setVisible(true); 
        setSize(500, 600); 
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
        g.drawLine(0,400 , 500, 400);
    } 
}

public class TestKnight {
    
    Knight k;
    InputController controller;
    canva c;
    int prevX,prevY;

    @BeforeEach                                         
    void setUp() {
        k = new Knight(new P2d(300, 370), new Vector2d(1, 0), new BoundingBoxImplRet(new P2d(0, 0), 5, 5),10);
        controller= new InputController();
        c = new canva();
        prevX=0;
        prevY=0;
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
            System.out.println((int)(startTime-lastTime));
            k.updatePos((int)(startTime-lastTime));
            //if(k.getPos().getY()<350){
            //}
            this.render(k);
            System.out.println(k.getPos());
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
        //g.clearRect(prevX, prevY, 50, 50);
        g.drawLine(0,400 , 500, 400);
        prevX=(int)k.getPos().getX();
        prevY=(int)k.getPos().getY();
        g.drawOval(prevX, prevY, 20, 30); 

    }
}
