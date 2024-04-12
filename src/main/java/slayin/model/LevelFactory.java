package slayin.model;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import slayin.model.entities.GameObject;

public class LevelFactory {

    private static final String enemiesConfigFile = "config/levels/enemies.json";
    
    public static Level buildLevel(int level){
        List<GameObject> enemies = getEnemies(level);
        Level lev = new Level(null);

        return lev;
    }

    private static List<GameObject> getEnemies(int level){
        URL path = LevelFactory.class.getClass().getResource(enemiesConfigFile);
        try {
            JSONArray levels = new JSONArray(Files.readString(Path.of(path.toURI())));

        } catch (Exception e) { }
        
        return null;
    }
}
