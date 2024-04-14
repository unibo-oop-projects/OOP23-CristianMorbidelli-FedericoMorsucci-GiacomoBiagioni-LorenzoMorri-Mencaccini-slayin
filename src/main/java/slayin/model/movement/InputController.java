package slayin.model.movement;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import slayin.model.events.GameEventListener;

/*
 * This class is used to control the input of the player
 */
public class InputController extends MovementController implements KeyListener {
    private GameEventListener eventListener;

    public InputController(GameEventListener eventListener) {
        super();
        this.eventListener = eventListener;

        // Set default moving direction (RIGHT)
        this.setMovingRight(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {

            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
            case KeyEvent.VK_SPACE:
                this.setJumping(false);
                break;

            default:
                break;
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                this.resetDirection();
                this.setMovingLeft(true);
                break;

            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                this.resetDirection();
                this.setMovingRight(true);
                break;

            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
            case KeyEvent.VK_SPACE:
                this.setJumping(true);
                break;

            case KeyEvent.VK_ESCAPE:
                // eventListener.addEvent();
                break;

            default:
                break;
        }
    }
}
