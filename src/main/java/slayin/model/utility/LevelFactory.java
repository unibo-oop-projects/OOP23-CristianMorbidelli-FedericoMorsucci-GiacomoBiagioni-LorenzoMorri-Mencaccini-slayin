package slayin.model.utility;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import slayin.model.Level;
import slayin.model.World;
import slayin.model.entities.GameObject;

public class LevelFactory {

    /* the path to the file which contains infos about the enemies contained in each level */
    private static final String enemiesConfigFile = "/configs/levels/enemies.json";

    private final World world;
    private JSONArray levels;

    /**
     * The constructor of a LevelFactory. It builds an object that can build as many levels as needed
     * @param world - the world of the actual game (needed to set the positions in which entities appear)
     */
    public LevelFactory(World world){
        this.world = world;
        try {
            levels = new JSONObject(Files.readString(Path.of(this.getClass().getResource(enemiesConfigFile).toURI()))).getJSONArray("levels");
        } catch (Exception e) {
            // Error while reading the json file will result in an empty array 
            levels = null;
        }
    }
    
    /**
     * Returns a Level object with the infos about the wanted level
     * @param level - the number of the level
     * @return An {@code Optional} containing the corrisponding Level object, or an {@code empty} one if some error occurs while building it
     */
    public Optional<Level> buildLevel(int level){
        // read from the array the JSON entry corresponding to the requested level
        JSONObject levelJSON;
        Level lvl = null;
        try {
            levelJSON = levels.getJSONObject(level);

            // gets the level capacity and the enemies list
            int capacity = getCapacity(levelJSON);
            // gets the list of enemies objects based on the current level
            List<GameObject> enemies = getEnemies(levelJSON);

            lvl = new Level(enemies, capacity);
        } catch (JSONException e) {
            System.out.println("Can't access the level configs file");
            e.printStackTrace();
        }

        return Optional.ofNullable(lvl);
    }

    /**
     * Returns the maximum number of enemies that will appear in the requested level at the same time
     * @param level - the JSON object of the level
     * @return the level capacity
     */
    private int getCapacity(JSONObject level){
        int capacity;
        try {
            capacity = level.getInt("capacity");
        } catch (JSONException e) {
            // Can't read the capacity from the JSON
            capacity = 0;
        }

        return capacity;
    }

    /**
     * Returns all the enemies that will appear in the requested level
     * @param level - the JSON object of the level
     * @return a list which contains all the GameObjects of the enemies of that level
     */
    private List<GameObject> getEnemies(JSONObject level){
        try {
            // the levels in the json are ordered in a JSON array that contains 
            // infos about the i-th level at its i-th position
            // from the JSON object corrisponding to the wanted level, it parse the infos about enemies
            JSONArray enemiesJSON = level.getJSONArray("enemies");
            // the enemies list gets parsed from JSON and put in a GameObject list
            List<GameObject> enemies = parseEnemies(enemiesJSON);
            
            return enemies;
        } catch (Exception e) {
            System.out.println("Can't read enemies from config file");
            e.printStackTrace();
            return List.of();
        }
        
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
    private List<GameObject> parseEnemies(JSONArray enemiesJSON) throws JSONException{
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
    private List<GameObject> buildEnemy(int id, int qnt){
        if(qnt<=0){
            return List.of();
        }

        EntityFactory entityFactory = new EntityFactory(world);
        List<GameObject> enemies = new ArrayList<>();

        for(int i=0; i<qnt; i++){
            switch(id){
                case 0: // Dummy entity; not an actual enemy in the final game
                    enemies.add(entityFactory.buildDummy());
                    break;
                case 1:
                    enemies.add(entityFactory.buildSlime());
                    break;
                default:
                    return List.of();
            }
        }

        return enemies;
    }
}
