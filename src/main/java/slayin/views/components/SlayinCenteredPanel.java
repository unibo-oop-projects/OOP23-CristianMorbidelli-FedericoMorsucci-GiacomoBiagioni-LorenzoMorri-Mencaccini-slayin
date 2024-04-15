package slayin.views.components;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JComponent;

import slayin.model.utility.Constants;

/**
 * Generic panel that centers its components vertically and horizontally
 */
public class SlayinCenteredPanel extends JComponent {
    private GridBagConstraints centeredConstraints;

    /**
     * Creates a new fullscreen centered panel
     */
    public SlayinCenteredPanel() {
        this(true);
    }

    /**
     * Creates a new centered panel
     * 
     * @param fullscreen Whether the panel should be fullscreen or not
     */
    public SlayinCenteredPanel(boolean fullscreen) {
        if (fullscreen) {
            this.setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
            this.setPreferredSize(new Dimension(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
        }

        this.buildLayout();
    }

    private void buildLayout() {
        this.setLayout(new GridBagLayout());
        this.centeredConstraints = new GridBagConstraints();
        this.centeredConstraints.gridwidth = GridBagConstraints.REMAINDER;
        this.centeredConstraints.fill = GridBagConstraints.HORIZONTAL;
        this.centeredConstraints.weighty = 1;
    }

    /**
     * Adds the given components to the panel
     * 
     * @param components The list of components to add
     */
    public void addComponents(JComponent ...components) {
        for (JComponent component : components) {
            this.add(component, this.centeredConstraints);
        }
    }
}
