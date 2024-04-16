package slayin.model.score;

import slayin.model.entities.graphics.DrawComponent;
import slayin.model.entities.graphics.DrawComponentFactory;
import slayin.model.utility.Constants;

/**
 * The GameScore class is responsible for keeping track of the player's score and combo factor.
 */
public class GameScore {
    private int score;
    private int comboFactor;

    private int remainingTime;
    private long startTimestamp;

    public GameScore() {
        this.score = 0;
        this.comboFactor = 0;
        this.remainingTime = Constants.COMBO_RESET_TIME;
    }

    /**
     * Increases the score by the given amount and updates the combo factor.
     * 
     * @param score - the amount to increase the score by
     */
    public void increaseScore(int score) {
        this.score += score + comboFactor;

        comboFactor++;
        remainingTime = Constants.COMBO_RESET_TIME;
        startTimestamp = System.currentTimeMillis();
    }

    /**
     * Updates the combo timer. If the combo timer reaches 0, the combo factor is set to 0
     * and the timer is set to the original value.
     */
    public void updateComboTimer() {
        if (comboFactor == 0)
            return;

        if (remainingTime <= 0) {
            comboFactor = 0;
            remainingTime = Constants.COMBO_RESET_TIME;
            return;
        }

        remainingTime -= (int) (System.currentTimeMillis() - startTimestamp);
        startTimestamp = System.currentTimeMillis();
    }

    /**
     * Resumes the combo timer.
     */
    public void resumeComboTimer() {
        startTimestamp = System.currentTimeMillis();
    }

    /**
     * Gets the current score.
     * 
     * @return the current score
     */
    public int getScore() {
        return this.score;
    }

    /**
     * Gets the remaining time of the combo timer.
     * 
     * @return the remaining time of the combo timer
     */
    public int getRemainingTime() {
        return remainingTime;
    }

    /**
     * Gets the combo factor.
     * 
     * @return the combo factor value
     */
    public int getComboFactor() {
        return comboFactor;
    }

    /**
     * Gets the DrawComponent to draw the score and the combo factor.
     * 
     * @return the DrawComponent to draw the score and the combo factor
     */
    public DrawComponent getDrawComponent() {
        return DrawComponentFactory.graphicsComponentScore(this);
    }
}
