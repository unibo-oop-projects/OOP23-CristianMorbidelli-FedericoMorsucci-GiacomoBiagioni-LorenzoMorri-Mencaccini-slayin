package slayin.model.entities.graphics;

import java.awt.Graphics;

/**
 * Interface representing a drawable component.
 * Implementing classes should provide a concrete implementation of the draw method
 * to render graphical components on the screen.
 */
public interface DrawComponent {

    /**
     * Draws the graphical representation of the component.
     *
     * @param g The Graphics object used for drawing.
     */
    public void draw(Graphics g);
} 
