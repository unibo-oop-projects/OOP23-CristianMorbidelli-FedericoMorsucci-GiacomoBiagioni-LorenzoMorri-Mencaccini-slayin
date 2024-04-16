package slayin.views.components;

import java.awt.Dimension;

import javax.swing.JButton;

/**
 * The default button for the game menus.
 */
public class SlayinButton extends JButton {
    private final Dimension DEFAULT_SIZE = new Dimension(200, 60);

    /**
     * Creates a new JButton with the given text and click action.
     * 
     * @param text The text to display on the button.
     * @param clickAction The action to perform when the button is clicked.
     */
    public SlayinButton(String text, Runnable clickAction) {
        super();

        this.setText(text);
        this.addActionListener(e -> clickAction.run());
        this.setPreferredSize(DEFAULT_SIZE);
    }
}
