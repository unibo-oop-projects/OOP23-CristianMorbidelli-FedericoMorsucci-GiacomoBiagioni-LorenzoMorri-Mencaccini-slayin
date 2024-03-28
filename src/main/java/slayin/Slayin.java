package slayin;

import slayin.core.Engine;

public class Slayin {

	static long tickTime = 25;	/* 40 fps */

    public static void main(String[] args) {
		Engine core = new Engine();
		long startTime, timePassed;
		
		while(core.stopLoop()){		/* Game loop */
			startTime = System.currentTimeMillis();

			/* TODO: check input */

			/* TODO: update game status */

			/* TODO: render updates */

			timePassed = System.currentTimeMillis() - startTime;
			if(timePassed < tickTime){	/* wait until tickTime before nextFrame */
				try {
					Thread.sleep(tickTime-timePassed);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			/*System.out.println(System.currentTimeMillis() - startTime);*/
		}
	}

}

