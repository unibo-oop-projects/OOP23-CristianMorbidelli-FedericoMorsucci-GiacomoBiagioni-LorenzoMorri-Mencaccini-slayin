package slayin.model.entities.character;

import java.util.List;
import java.util.Optional;

import slayin.model.entities.shots.ShotObject;

/**
 * Interface representing a character in the game.
 */
public interface Character {

    /**
     * Gets the name of the character.
     *
     * @return the name of the character.
     */
    public String getName();

    /**
     * Checks if the character is alive.
     *
     * @return true if the character is alive, false otherwise.
     */
    public boolean isAlive();

    /**
     * Gets the life (health) of the character.
     *
     * @return the health of the character.
     */
    public Health getLife();

    /**
     * Decreases the life of the character by a specified amount of damage.
     *
     * @param damage the amount of damage to inflict.
     */
    public void decLife(int damage);

    /**
     * Adds a melee weapon to the character's arsenal.
     *
     * @param weapon the melee weapon to add.
     */
    public void addWeapon(MeleeWeapon weapon);

    /**
     * Gets the list of melee weapons the character possesses.
     *
     * @return the list of melee weapons.
     */
    public List<MeleeWeapon> getWeapons();

    /**
     * Gets the shots (projectiles) fired by the character.
     *
     * @return an optional containing the shots if available, or an empty optional otherwise.
     */
    public Optional<ShotObject> getShots();
}
