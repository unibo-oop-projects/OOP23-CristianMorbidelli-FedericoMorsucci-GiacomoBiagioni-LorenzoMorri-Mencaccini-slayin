package slayin.model.score;

import slayin.model.entities.graphics.DrawComponent;

/**
 * The ScoreManager class is responsible for keeping track of the player's score
 * and modifying it based on a combo factor.
 */
public interface ScoreManager {
    /**
     * Increases the score by the given amount and updates the combo factor.
     * 
     * @param score - the amount to increase the score by
     */
    public void increaseScore(int score);

    /**
     * Updates the combo timer. If the combo timer reaches 0, the combo factor is
     * set to 0
     * and the timer is set to the original value.
     */
    public void updateComboTimer();

    /**
     * Resumes the combo timer.
     */
    public void resumeComboTimer();

    /**
     * Gets the DrawComponent to draw the score and the combo factor.
     * 
     * @return the DrawComponent to draw the score and the combo factor
     */
    public DrawComponent getDrawComponent();

    /**
     * Gets the current score.
     * 
     * @return the current score
     */
    public int getScore();

    /**
     * Gets the remaining time of the combo timer.
     * 
     * @return the remaining time of the combo timer
     */
    public int getRemainingTime();

    /**
     * Gets the combo factor.
     * 
     * @return the combo factor
     */
    public int getComboFactor();
}
