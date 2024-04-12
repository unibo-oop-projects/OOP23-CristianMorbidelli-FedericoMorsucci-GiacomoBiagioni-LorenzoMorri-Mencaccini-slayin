package slayin.model.utility;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import slayin.model.Level;
import slayin.model.entities.GameObject;

public class LevelFactory {

    /* the path to the file which contains infos about the enemies contained in each level */
    private static final String enemiesConfigFile = "config/levels/enemies.json";
    
    /**
     * Returns a Level object with the infos about the wanted level
     * @param level - the number of the level
     * @return the corrisponding Level object
     */
    public static Level buildLevel(int level){
        // it gets the list of enemies objects based on the current level
        List<GameObject> enemies = getEnemies(level);

        Level lvl = new Level(enemies);
        return lvl;
    }

    /**
     * Returns all the enemies that will appear in the requested level
     * @param level - the number of the level
     * @return a list which contains all the GameObjects of the enemies of that level
     */
    private static List<GameObject> getEnemies(int level){
        // access the resource and reads the path of the config file
        URL path = LevelFactory.class.getClass().getResource(enemiesConfigFile);
        try {
            // the levels in the json are ordered in a JSON array that contains 
            // infos about the i-th level at its i-th position
            JSONArray levels = new JSONArray(Files.readString(Path.of(path.toURI())));
            JSONObject levelJSON = levels.getJSONObject(level);

            // from the JSON object corrisponding to the wanted level, it parse the infos about enemies
            JSONArray enemiesJSON = levelJSON.getJSONArray("enemies");
            // the enemies list gets parsed from JSON and put in a GameObject list
            List<GameObject> enemies = parseEnemies(enemiesJSON);
            
            return enemies;
        } catch (Exception e) {return List.of();}
        
    }

    /**
     * Return a list of enemies from a JSON Array. This method check each type
     * of enemy and the quantity that it appears in; then it fills a list with 
     * objects representing each type of monster.
     * @param enemiesJSON - the array of monsters in JSON format, each element
     * has an "id" to differ every type of monster, and a "qnt" that means how many
     * enemies of that type are needed
     * @return a list which contains the GameObjects of the enemies in the JSON array
     * @throws JSONException
     */
    private static List<GameObject> parseEnemies(JSONArray enemiesJSON) throws JSONException{
        List<GameObject> enemies = new ArrayList<>();
        for(int i=0; i<enemiesJSON.length(); i++){
            JSONObject enemy = enemiesJSON.getJSONObject(i);
            // for each element of the JSON Array, it builds "qnt" enemies corrisponding to "id"
            enemies.addAll(buildEnemy(enemy.getInt("id"), enemy.getInt("qnt")));
        }

        return enemies;
    }

    /**
     * Return a list of enemy of the same type. The lenght of the list is
     * given as a parameter. If the id doesn't corrispond to an existing enemy,
     * or the asked quantity is 0 or below, it returns an empty immutable list.
     * @param id - the id of the enemy to build
     * @param qnt - the quantity of enemy of the wanted type; also the
     * final lenght of the returned list
     * @return the list of enemies
     */
    private static List<GameObject> buildEnemy(int id, int qnt){
        if(qnt<=0){
            return List.of();
        }

        switch(id){
            case 0: // Dummy entity; not an actual enemy in the final game
                return List.of();   // TODO: implement an EnemyFactory to build the enemies
            default:
                return List.of();
        }
    }
}
