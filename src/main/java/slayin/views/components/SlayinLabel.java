package slayin.views.components;

import javax.swing.JLabel;

/**
 * The default label for the game menus.
 * Has some default settings for the title label.
 */
public class SlayinLabel extends JLabel {
    private final float DEFAULT_TITLE_FONT_SIZE = 64.0f;

    /**
     * Creates a new basic JLabel with the given text.
     * 
     * @param text The text to display on the label.
     */
    public SlayinLabel(String text) {
        super(text);
    }

    /**
     * Creates a new JLabel with the given text and a custom font size.
     * 
     * @param text The text to display on the label.
     * @param fontSize The font size to use for the label.
     */
    public SlayinLabel(String text, float fontSize) {
        this(text);
        
        this.setFont(this.getFont().deriveFont(fontSize));
    }

    /**
     * Creates a new JLabel with the given text and the default size if is a title.
     * 
     * @param text The text to display on the label.
     * @param isTitle Whether the label should be a title label or not.
     */
    public SlayinLabel(String text, boolean isTitle) {
        this(text);
        
        if (isTitle)
            this.setFont(this.getFont().deriveFont(DEFAULT_TITLE_FONT_SIZE));
    }
}
