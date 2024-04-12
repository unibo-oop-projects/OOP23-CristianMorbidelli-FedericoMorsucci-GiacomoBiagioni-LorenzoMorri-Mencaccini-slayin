package slayin.views;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JLabel;

import slayin.core.GameScene;
import slayin.model.events.GameEventListener;
import slayin.model.utility.SceneType;

public class GameLevelScene implements GameScene {
    private GameEventListener eventListener;

    public GameLevelScene(GameEventListener eventListener) {
        this.eventListener = eventListener;
    }

    @Override
    public Container getContent() {
        Container container = new Container();
        container.setLayout(new BorderLayout());

        // Create a test label
        JLabel testLabel = new JLabel("I'm inside the game level scene!");
        container.add(testLabel, BorderLayout.CENTER);

        return container;
    }

    @Override
    public SceneType getSceneType() {
        return SceneType.GAME_LEVEL;
    }
}
