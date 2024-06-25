package slayin.views;

import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import slayin.model.entities.character.PlayableCharacter;
import slayin.model.events.GameEventListener;
import slayin.model.events.menus.SimpleChangeSceneEvent;
import slayin.model.events.menus.StartGameEvent;
import slayin.model.utility.SceneType;
import slayin.model.utility.assets.Asset;
import slayin.model.utility.assets.AssetsManager;
import slayin.views.components.SlayinButton;
import slayin.views.components.SlayinLabel;
import slayin.views.components.SlayinPanel;

public class CharacterSelectionScene implements SimpleGameScene {
    private GameEventListener eventListener;

    public CharacterSelectionScene(GameEventListener eventListener) {
        this.eventListener = eventListener;
    }

    @Override
    public Container getContent() {
        Image backgroundImage = AssetsManager.getInstance().getImageAsset(Asset.MAIN_MENU_BG);
        SlayinPanel container = new SlayinPanel(backgroundImage);

        SlayinLabel title = new SlayinLabel("Selezione Personaggio", true);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Pannello per i personaggi
        JPanel charactersPanel = new JPanel();
        charactersPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        charactersPanel.setOpaque(false);

        SlayinButton charBtn1 = new SlayinButton("Knight",
                () -> eventListener.addEvent(new StartGameEvent(PlayableCharacter.KNIGHT)));
        SlayinButton charBtn2 = new SlayinButton("Wizard",
                () -> eventListener.addEvent(new StartGameEvent(PlayableCharacter.WIZARD)));
        SlayinButton charBtn3 = new SlayinButton("Knave",
                () -> eventListener.addEvent(new StartGameEvent(PlayableCharacter.KNAVE)));

        charactersPanel.add(charBtn1);
        charactersPanel.add(charBtn2);
        charactersPanel.add(charBtn3);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setOpaque(false);

        SlayinButton backBtn = new SlayinButton("Indietro",
                () -> eventListener.addEvent(new SimpleChangeSceneEvent(SceneType.MAIN_MENU)));
        backBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottomPanel.add(backBtn);

        container.addComponents(title, charactersPanel, backBtn);

        return container;
    }

    @Override
    public SceneType getSceneType() {
        return SceneType.CHARACTER_SELECTION;
    }

    @Override
    public boolean shouldRevalidate() {
        return false;
    }

}
