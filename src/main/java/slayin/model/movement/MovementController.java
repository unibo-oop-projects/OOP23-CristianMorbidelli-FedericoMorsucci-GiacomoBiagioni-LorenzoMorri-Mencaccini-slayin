package slayin.model.movement;

public class MovementController {
    private boolean jumping;
    private boolean movingLeft;
    private boolean movingRight;
    private boolean movingDown;

    public MovementController() {
        this.jumping = false;
        this.movingLeft = false;
        this.movingRight = false;
        this.movingDown = false;
    }

    public boolean isJumping() {
        return jumping;
    }

    public boolean isMovingLeft() {
        return movingLeft;
    }

    public boolean isMovingRight() {
        return movingRight;
    }

    public boolean isMovingDown() {
        return movingDown;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }

    public void setMovingDown(boolean movingDown) {
        this.movingDown = movingDown;
    }

    public void resetDirection() {
        this.movingLeft = false;
        this.movingRight = false;
    }
}
