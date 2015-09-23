package de.yadrone.apps.controlcenter.plugins.controller;

import de.yadrone.base.IARDrone;
import net.java.games.input.*;

/**
 * Created by jlesaux on 21/09/15.
 * File ${FILE}
 */
public class GameControllerCommandManager extends Thread {
    protected IARDrone drone;
    protected EventQueue queue;
    protected Controller controller;

    public GameControllerCommandManager(IARDrone ardrone) {
        this.drone = ardrone;
        initiateController();
    }


    public void initiateController() {
        final Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
        if (controllers.length==0) {
            System.out.println("No controller found.");
            return;
        }
        controller = controllers[0];
        controller.poll();
        System.out.println("Found controller : " + controller.getName());
        System.out.println("Type : " + String.valueOf(controller.getType()));
        queue = controller.getEventQueue();
    }

    @Override
    public void run() {
        if (controller==null) {
            return;
        }
        while(true) {
            controller.poll();
            queue = controller.getEventQueue();
            Event event = new Event();
            while (queue.getNextEvent(event)) {
                StringBuffer buffer = new StringBuffer(controller.getName());
                buffer.append(" at ");
                buffer.append(event.getNanos()).append(", ");
                Component comp = event.getComponent();
                buffer.append(comp.getName()).append(" changed to ");
                float value = event.getValue();
                if (comp.isAnalog()) {
                    buffer.append(value);
                } else {
                    if (value == 1.0f) {
                        buffer.append("On");
                    } else {
                        buffer.append("Off");
                    }
                }
                System.out.println(buffer.toString());
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
