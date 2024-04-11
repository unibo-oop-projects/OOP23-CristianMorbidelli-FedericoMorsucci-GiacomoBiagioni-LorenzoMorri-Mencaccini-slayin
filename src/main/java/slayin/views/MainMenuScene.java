package slayin.views;

import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;

import slayin.core.GameScene;
import slayin.model.events.GameEventListener;
import slayin.model.events.QuitGameEvent;
import slayin.model.events.StartGameEvent;
import slayin.model.utility.SceneType;
import slayin.views.utils.JBackgroundImage;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Dimension;

public class MainMenuScene implements GameScene {
    private GameEventListener eventListener;

    public MainMenuScene(GameEventListener eventListener) {
        this.eventListener = eventListener;
    }

    @Override
    public Container getContent() {
        JComponent container = new JBackgroundImage("/assets/backgrounds/mainmenu_bg.jpg");
        container.setLayout(new GridBagLayout());

        JLabel title = new JLabel("Slayin");
        title.setFont(title.getFont().deriveFont(64.0f));
        JButton playBtn = new JButton("Gioca");
        playBtn.addActionListener(e -> eventListener.addEvent(new StartGameEvent()));
        JButton button2 = new JButton("Button 2");
        JButton quitBtn = new JButton("Esci");
        quitBtn.addActionListener(e -> eventListener.addEvent(new QuitGameEvent()));

        Dimension buttonSize = new Dimension(200, 50);
        playBtn.setPreferredSize(buttonSize);
        button2.setPreferredSize(buttonSize);
        quitBtn.setPreferredSize(buttonSize);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 1; // Distribute extra space between components centering vertically

        container.add(title, gbc);
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
