package slayin.model.score;

import slayin.model.entities.graphics.DrawComponent;
import slayin.model.entities.graphics.DrawComponentFactory;
import slayin.model.utility.Globals;

/**
 * Implementation of the {@code ScoreManager} interface.
 */
public class GameScore implements ScoreManager {
    private int score;
    private int comboFactor;

    private int remainingTime;
    private long startTimestamp;

    public GameScore() {
        this.score = 0;
        this.comboFactor = 0;
        this.remainingTime = Globals.COMBO_RESET_TIME;
    }

    @Override
    public void increaseScore(int score) {
        this.score += score + comboFactor;
        comboFactor++;
        remainingTime = Globals.COMBO_RESET_TIME;
        startTimestamp = System.currentTimeMillis();
    }

    @Override
    public void updateComboTimer() {
        if (comboFactor == 0)
            return;
        if (remainingTime <= 0) {
            comboFactor = 0;
            remainingTime = Globals.COMBO_RESET_TIME;
            return;
        }
        remainingTime -= (int) (System.currentTimeMillis() - startTimestamp);
        startTimestamp = System.currentTimeMillis();
    }

    @Override
    public void resumeComboTimer() {
        startTimestamp = System.currentTimeMillis();
    }

    @Override
    public DrawComponent getDrawComponent() {
        return DrawComponentFactory.graphicsComponentScore(this);
    }

    @Override
    public int getScore() {
        return this.score;
    }

    @Override
    public int getRemainingTime() {
        return remainingTime;
    }

    @Override
    public int getComboFactor() {
        return comboFactor;
    }

}
