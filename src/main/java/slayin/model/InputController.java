package slayin.model;

/**
 * Input
 */
public class InputController {

    boolean moveUp,moveDown,moveLeft,moveRight;

    public boolean isMoveUp() {
        return moveUp;
    }

    public void setMoveUp() {
        this.moveUp = true;
    }

    public void unSetMoveUp() {
        this.moveUp = false;
    }

    public boolean isMoveDown() {
        return moveDown;
    }

    public void setMoveDown() {
        this.moveDown = true;
    }

    public void unSetMoveDown() {
        this.moveDown = false;
    }

    public boolean isMoveLeft() {
        return moveLeft;
    }

    public void setMoveLeft() {
        this.moveLeft = true;
    }

    public void unSetMoveLeft() {
        this.moveLeft = false;
    }

    public boolean isMoveRight() {
        return moveRight;
    }

    public void setMoveRight() {
        this.moveRight = true;
    }

    public void unSetMoveRight() {
        this.moveRight = false;
    }

    
}
