package slayin.model;

import java.util.Optional;

public class World {

    public static enum Edge { LEFT_BORDER, RIGHT_BORDER , TOP_BORDER , BOTTOM_BORDER  }
    private int width,height,ground;

    public World(int width, int height, int ground) {
        this.width = width;
        this.height = height;
        this.ground=ground;
    }

    public Optional<Edge> collidingWith(GameObject obj){
        return Optional.empty();
    }

    public int getGround(){
        return this.ground;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }


}
