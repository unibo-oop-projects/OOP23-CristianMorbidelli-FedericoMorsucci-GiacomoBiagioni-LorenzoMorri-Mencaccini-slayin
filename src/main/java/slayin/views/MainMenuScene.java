package slayin.views;

import java.awt.Container;

import javax.swing.JButton;

import slayin.core.GameScene;
import slayin.model.utility.SceneType;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Dimension;

public class MainMenuScene implements GameScene {
    @Override
    public Container getContent() {
        Container container = new Container();
        container.setLayout(new GridBagLayout());

        JButton playBtn = new JButton("Gioca");
        JButton button2 = new JButton("Button 2");
        JButton quitBtn = new JButton("Esci");

        Dimension buttonSize = new Dimension(200, 50);
        playBtn.setPreferredSize(buttonSize);
        button2.setPreferredSize(buttonSize);
        quitBtn.setPreferredSize(buttonSize);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 1; // This will center the buttons vertically

        container.add(playBtn, gbc);
        container.add(button2, gbc);
        container.add(quitBtn, gbc);

        return container;
    }

    @Override
    public SceneType getSceneType() {
        return SceneType.MAIN_MENU;
    }
}
