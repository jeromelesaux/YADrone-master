package de.yadrone.apps.controlcenter.plugins.controller;

import de.yadrone.apps.controlcenter.ICCPlugin;
import de.yadrone.base.IARDrone;

import javax.swing.*;
import java.awt.*;

/**
 * Created by jls on 30/09/15.
 */
public class GameControllerPanel extends JPanel implements ICCPlugin {

    IARDrone drone;


    GameControllerCommandManager gameControllerCommandManager;
    private Image originalImage;
    private Image scaledImage;
    private int width = 0;
    private int height = 0;

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (originalImage == null)
        { // called only once
            ImageIcon icon = new ImageIcon(GameControllerPanel.class.getResource("/controller-game.png"));
            originalImage = icon.getImage();
            scaledImage = originalImage;
        }

        if ((width != getWidth()) || (height != getHeight()))
        { // called once the user changes the frame size
            width = getWidth();
            height = getHeight();
            scaledImage = originalImage.getScaledInstance(getWidth(), getHeight(), Image.SCALE_AREA_AVERAGING);
        }

        g.drawImage(scaledImage, 0, 0, this);
    }

    public void activate(IARDrone drone) {
        this.drone = drone;
        gameControllerCommandManager = new GameControllerCommandManager(this.drone);
    }

    public void deactivate() {

    }

    public String getTitle() {
        return "GamePad";
    }

    public String getDescription() {
        return "Gamepad controller enable";
    }

    public boolean isVisual() {
        return true;
    }

    public Dimension getScreenSize() {
        return new Dimension(400, 180);
    }

    public Point getScreenLocation() {
        return new Point(330, 260);
    }

    public JPanel getPanel() {
        return this;
    }
}
