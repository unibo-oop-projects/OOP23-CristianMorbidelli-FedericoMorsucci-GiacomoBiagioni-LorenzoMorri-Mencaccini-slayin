package slayin.views;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import slayin.core.GameScene;
import slayin.model.InputController;
import slayin.model.events.GameEventListener;
import slayin.model.utility.SceneType;

public class GameLevelScene implements GameScene {
    private GameEventListener eventListener;
    private InputController inputController;

    public GameLevelScene(GameEventListener eventListener, InputController inputController) {
        this.eventListener = eventListener;
        this.inputController = inputController;
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
