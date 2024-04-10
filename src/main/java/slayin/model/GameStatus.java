package slayin.model;

import java.util.ArrayList;
import java.util.List;

public class GameStatus {
    
    List<GameObject> objects;

    public GameStatus(){
        objects = new ArrayList<>();
    }

    public List<GameObject> getObjects(){   return this.objects;    }
}
