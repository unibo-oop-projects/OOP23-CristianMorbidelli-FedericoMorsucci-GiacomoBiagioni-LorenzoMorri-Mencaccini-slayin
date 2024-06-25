package slayin.views;

import java.awt.Container;
import java.awt.Image;
import java.util.Arrays;

import slayin.model.events.ResolutionChangedEvent;
import slayin.model.events.GameEventListener;
import slayin.model.events.menus.SimpleChangeSceneEvent;
import slayin.model.utility.GameResolution;
import slayin.model.utility.Globals;
import slayin.model.utility.SceneType;
import slayin.model.utility.assets.Asset;
import slayin.model.utility.assets.AssetsManager;
import slayin.views.components.SlayinButton;
import slayin.views.components.SlayinLabel;
import slayin.views.components.SlayinPanel;
import slayin.views.components.SlayinSliderMenu;

public class OptionMenuScene implements SimpleGameScene {
    private final GameEventListener eventListener;

    public OptionMenuScene(GameEventListener eventListener) {
        this.eventListener = eventListener;
    }

    @Override
    public Container getContent() {
        Image backgroundImage = AssetsManager.getInstance().getImageAsset(Asset.MAIN_MENU_BG);

        SlayinLabel title = new SlayinLabel("Impostazioni", true);
        SlayinSliderMenu<GameResolution> sliderMenu = new SlayinSliderMenu<GameResolution>(Globals.RESOLUTION,
                Arrays.asList(GameResolution.values()), (res) -> res.getShowText());
        sliderMenu.addChangeListener(newResolution -> {
            Globals.RESOLUTION = newResolution;
            eventListener.addEvent(new ResolutionChangedEvent());
        });
        SlayinButton backBtn = new SlayinButton("Indietro",
                () -> eventListener.addEvent(new SimpleChangeSceneEvent(SceneType.MAIN_MENU)));

        SlayinPanel container = new SlayinPanel(backgroundImage);
        container.addComponents(title, sliderMenu, backBtn);

        return container;
    }

    @Override
    public SceneType getSceneType() {
        return SceneType.OPTION_MENU;
    }

    @Override
    public boolean shouldRevalidate() {
        return true;
    }

}
