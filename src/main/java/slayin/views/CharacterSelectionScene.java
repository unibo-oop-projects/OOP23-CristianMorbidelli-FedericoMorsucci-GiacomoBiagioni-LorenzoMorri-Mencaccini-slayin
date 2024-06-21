package slayin.views;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import slayin.core.SimpleGameScene;
import slayin.model.entities.character.PlayableCharacter;
import slayin.model.events.GameEventListener;
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
        Image backgroundImage = AssetsManager.getImageAsset(Asset.MAIN_MENU_BG);

        SlayinPanel container = new SlayinPanel(backgroundImage);
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        SlayinLabel title = new SlayinLabel("Selezione Personaggio", true);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Pannello per i personaggi
        JPanel charactersPanel = new JPanel();
        charactersPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        charactersPanel.setOpaque(false);

        // Aggiunta dei personaggi
        charactersPanel.add(createCharacterPanel("Knight", PlayableCharacter.KNIGHT));
        charactersPanel.add(createCharacterPanel("Wizard", PlayableCharacter.WIZARD));
        charactersPanel.add(createCharacterPanel("Knave", PlayableCharacter.KNAVE));
        
        container.addComponents(title, charactersPanel);
        return container;
    }

    private JPanel createCharacterPanel(String characterName, PlayableCharacter character) {
        // Pannello del personaggio
        JPanel characterPanel = new JPanel();
        characterPanel.setLayout(new BorderLayout(10, 10));
        characterPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        characterPanel.setOpaque(true);

        // Immagine del personaggio
        JLabel imageLabel = new JLabel(new ImageIcon(AssetsManager.getImageAsset(Asset.DUMMY_ENEMY)));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        characterPanel.add(imageLabel, BorderLayout.CENTER);

        // Bottone di selezione
        SlayinButton charBtn = new SlayinButton(characterName, () -> eventListener.addEvent(new StartGameEvent(character)));
        characterPanel.add(charBtn, BorderLayout.SOUTH);

        return characterPanel;
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
