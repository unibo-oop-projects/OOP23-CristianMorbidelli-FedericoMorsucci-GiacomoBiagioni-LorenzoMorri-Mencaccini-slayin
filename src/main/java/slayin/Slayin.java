package slayin;

import slayin.core.Engine;
import slayin.views.MainMenuScene;

public class Slayin {
    public static void main(String[] args) {
        Engine core = new Engine();

        core.switchScene(new MainMenuScene());
        core.startGameLoop();
    }

}
