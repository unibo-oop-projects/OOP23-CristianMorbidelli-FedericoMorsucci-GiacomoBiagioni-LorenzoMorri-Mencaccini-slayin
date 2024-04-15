package slayin.views;

import java.awt.Container;

import slayin.core.GameScene;
import slayin.model.events.GameEventListener;
import slayin.model.events.menus.QuitGameEvent;
import slayin.model.events.menus.StartGameEvent;
import slayin.model.utility.AssetsManager;
import slayin.model.utility.SceneType;
import slayin.views.components.SlayinButton;
import slayin.views.components.SlayinPanel;
import slayin.views.components.SlayinLabel;

import java.awt.Image;

public class MainMenuScene implements GameScene {
    private GameEventListener eventListener;

    public MainMenuScene(GameEventListener eventListener) {
        this.eventListener = eventListener;
    }

    @Override
    public Container getContent() {
        Image backgroundImage = AssetsManager.loadImage("/assets/backgrounds/mainmenu_bg.jpg");

        SlayinLabel title = new SlayinLabel("Slayin", true);
        SlayinButton playBtn = new SlayinButton("Gioca", () -> eventListener.addEvent(new StartGameEvent()));
        SlayinButton optionsBtn = new SlayinButton("Opzioni", () -> System.out.println("Opzioni"));
        SlayinButton quitBtn = new SlayinButton("Esci", () -> eventListener.addEvent(new QuitGameEvent()));
        
        SlayinPanel container = new SlayinPanel(backgroundImage);
        container.addComponents(title, playBtn, optionsBtn, quitBtn);

        return container;
    }

    @Override
    public void drawGraphics() {}

    @Override
    public SceneType getSceneType() {
        return SceneType.MAIN_MENU;
    }

    @Override
    public boolean shouldRevalidate() {
        return false;
    }
}
