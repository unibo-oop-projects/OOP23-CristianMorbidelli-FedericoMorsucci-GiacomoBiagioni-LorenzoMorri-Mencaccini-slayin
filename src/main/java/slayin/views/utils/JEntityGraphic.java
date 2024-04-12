package slayin.views.utils;

import java.awt.Graphics;

import javax.swing.JPanel;

import slayin.model.utility.P2d;

public class JEntityGraphic extends JPanel {
    private P2d pos;

    public JEntityGraphic() {
        this.pos = new P2d(0, 0);
    }

    public void updatePos(P2d pos) {
        this.pos = pos;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawRect((int) pos.getX(), (int) pos.getY(), 50, 50);
    }
}
