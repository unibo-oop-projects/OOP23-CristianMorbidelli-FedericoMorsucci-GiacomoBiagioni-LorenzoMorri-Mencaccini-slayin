package slayin.views;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import slayin.core.GameScene;
import slayin.model.events.GameEventListener;
import slayin.model.events.menus.QuitGameEvent;
import slayin.model.events.menus.ShowPauseMenuEvent;
import slayin.model.utility.SceneType;

public class PauseMenuScene implements GameScene {
    private GameEventListener eventListener;

    public PauseMenuScene(GameEventListener eventListener) {
        this.eventListener = eventListener;
    }

    @Override
    public Container getContent() {
        JPanel container = new JPanel();
        container.setLayout(new GridBagLayout());
        container.setPreferredSize(new Dimension(1280, 360));

        JLabel title = new JLabel("In Pausa");
        title.setFont(title.getFont().deriveFont(64.0f));
        JButton playBtn = new JButton("Riprendi");
        playBtn.addActionListener(e -> eventListener.addEvent(new ShowPauseMenuEvent(false)));
        JButton quitBtn = new JButton("Esci");
        quitBtn.addActionListener(e -> eventListener.addEvent(new QuitGameEvent()));

        Dimension buttonSize = new Dimension(200, 50);
        playBtn.setPreferredSize(buttonSize);
        quitBtn.setPreferredSize(buttonSize);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 1; // Distribute extra space between components centering vertically

        container.add(title, gbc);
        container.add(playBtn, gbc);
        container.add(quitBtn, gbc);

        return container;
    }

    @Override
    public void drawGraphics() {
    }

    @Override
    public SceneType getSceneType() {
        return SceneType.PAUSE_MENU;
    }
    
}
