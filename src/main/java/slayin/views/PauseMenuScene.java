package slayin.views;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import slayin.core.GameScene;
import slayin.model.GameStatus;
import slayin.model.events.GameEventListener;
import slayin.model.events.menus.QuitGameEvent;
import slayin.model.events.menus.ShowPauseMenuEvent;
import slayin.model.utility.Constants;
import slayin.model.utility.SceneType;

public class PauseMenuScene implements GameScene {
    private GameEventListener eventListener;
    private GameStatus gameStatus;

    public PauseMenuScene(GameEventListener eventListener, GameStatus gameStatus) {
        this.eventListener = eventListener;
        this.gameStatus = gameStatus;
    }

    @Override
    public Container getContent() {
        JPanel container = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawGameSnapshot(g);
            }
        };

        JLayeredPane menuContainer = new JLayeredPane() {
            @Override
            public void paint(Graphics g) {
                drawGlassBackground(g);
                
                super.paint(g);
            }
        };
        menuContainer.setLayout(new GridBagLayout());
        menuContainer.setPreferredSize(new Dimension(gameStatus.getWorld().getWidth(), gameStatus.getWorld().getHeight()));

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

        menuContainer.add(title, gbc);
        menuContainer.add(playBtn, gbc);
        menuContainer.add(quitBtn, gbc);

        container.add(menuContainer);

        return container;
    }

    @Override
    public void drawGraphics() {}

    @Override
    public SceneType getSceneType() {
        return SceneType.PAUSE_MENU;
    }
    
    private void drawGameSnapshot(Graphics g) {
        g.drawLine(0,365,Constants.WINDOW_WIDTH,365);
        gameStatus.getObjects().forEach(e -> e.getDrawComponent().draw(g));
    }

    private void drawGlassBackground(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setComposite(java.awt.AlphaComposite.SrcOver.derive(0.8f));
        g2d.setPaint(Color.GRAY);
        g2d.fillRect(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        g2d.dispose();
    }
}
