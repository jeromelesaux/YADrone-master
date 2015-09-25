package de.yadrone.apps.controlcenter.plugins.controller;

import de.yadrone.apps.controlcenter.CCPropertyManager;
import de.yadrone.base.IARDrone;
import de.yadrone.base.command.FlightAnimation;
import net.java.games.input.*;

import java.awt.event.KeyEvent;

/**
 * Created by jlesaux on 21/09/15.
 * File ${FILE}
 */
public class GameControllerCommandManager extends Thread {
    protected IARDrone drone;
    protected EventQueue queue;
    protected Controller controller;
    private String CONTROL_TAKEOFF = "8";
    private String CONTROL_LANDING = "9";
    private String CONTROL_GO_RIGHT_LEFT = "slider";
    private String CONTROL_UP_DOWN = "rz";
    private String CONTROL_FORWARD_BACKWARD = "x";
    private String CONTROL_SPEED = "y";
    private String CONTROL_SPIN_RIGHT = "7";
    private String CONTROL_SPIN_LEFT = "5";
    private String CONTROL_TOGGLE_CAMERA = "0";
    private String CONTROL_FLIP_RIGHT = "6";
    private String CONTROL_FLIP_LEFT = "4";
    private String CONTROL_FLIP_AHEAD = "3";
    private String CONTROL_FLIP_BEHIND = "0";

    public GameControllerCommandManager(IARDrone ardrone) {
        this.drone = ardrone;
        initiateController();
    }


    public void initiateController() {
        loadCommands();
        saveCommands();

        final Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
        if (controllers.length==0) {
            System.out.println("No controller found.");
            return;
        }
        for (Controller c : controllers) {
            System.out.println("Found controller " + c.getName());
            if (c.getType().equals(Controller.Type.GAMEPAD) || c.getType().equals(Controller.Type.FINGERSTICK)) {
                controller = c;
                break;
            }
        }
        if (controller!=null) {
            controller.poll();
            System.out.println("Use the controller : " + controller.getName());
            System.out.println("Type : " + String.valueOf(controller.getType()));
            queue = controller.getEventQueue();
        }
    }

    private void loadCommands(){
        String property = CCPropertyManager.getInstance().getProperty("gamepad_control_takeoff");
        if (property!=null && property.length() >0) {CONTROL_TAKEOFF= property;}
        property = CCPropertyManager.getInstance().getProperty("gamepad_control_landing");
        if (property!=null && property.length() >0){CONTROL_LANDING=property;}
        property=CCPropertyManager.getInstance().getProperty("gamepad_control_go_right_left");
        if (property!=null && property.length() >0) { CONTROL_GO_RIGHT_LEFT= property;}
        property=CCPropertyManager.getInstance().getProperty("gamepad_control_up_down");
        if(property!=null && property.length() >0) {CONTROL_UP_DOWN=property;}
        property=CCPropertyManager.getInstance().getProperty("gamepad_control_forward_backward");
        if(property!=null && property.length() >0) {CONTROL_FORWARD_BACKWARD=property;}
        property=CCPropertyManager.getInstance().getProperty("gamepad_control_speed");
        if(property!=null && property.length() >0) { CONTROL_SPEED=property;}
        property=CCPropertyManager.getInstance().getProperty("gamepad_control_spin_left");
        if (property!=null && property.length() >0) { CONTROL_SPIN_LEFT=property;}
        property=CCPropertyManager.getInstance().getProperty("gamepad_control_spin_right");
        if (property!=null && property.length() >0) { CONTROL_SPIN_RIGHT=property;}
        property=CCPropertyManager.getInstance().getProperty("gamepad_control_toggle_camera");
        if (property!=null && property.length() >0) { CONTROL_TOGGLE_CAMERA=property;}
        property=CCPropertyManager.getInstance().getProperty("gamepad_control_flip_right");
        if (property!=null && property.length() >0) { CONTROL_FLIP_RIGHT=property;}
        property=CCPropertyManager.getInstance().getProperty("gamepad_control_flip_left");
        if (property!=null && property.length() >0) { CONTROL_FLIP_LEFT=property;}
        property=CCPropertyManager.getInstance().getProperty("gamepad_control_flip_ahead");
        if (property!=null && property.length() >0) { CONTROL_FLIP_AHEAD=property;}
        property=CCPropertyManager.getInstance().getProperty("gamepad_control_flip_behind");
        if (property!=null && property.length() >0) { CONTROL_FLIP_BEHIND=property;}
    }
    private void saveCommands() {
        CCPropertyManager.getInstance().setProperty("gamepad_control_takeoff",CONTROL_TAKEOFF);
        CCPropertyManager.getInstance().setProperty("gamepad_control_landing",CONTROL_LANDING);
        CCPropertyManager.getInstance().setProperty("gamepad_control_go_right_left",CONTROL_GO_RIGHT_LEFT);
        CCPropertyManager.getInstance().setProperty("gamepad_control_up_down",CONTROL_UP_DOWN);
        CCPropertyManager.getInstance().setProperty("gamepad_control_forward_backward",CONTROL_FORWARD_BACKWARD);
        CCPropertyManager.getInstance().setProperty("gamepad_control_speed",CONTROL_SPEED);
        CCPropertyManager.getInstance().setProperty("gamepad_control_spin_left",CONTROL_SPIN_LEFT);
        CCPropertyManager.getInstance().setProperty("gamepad_control_spin_right",CONTROL_SPIN_RIGHT);
        CCPropertyManager.getInstance().setProperty("gamepad_control_toggle_camera",CONTROL_TOGGLE_CAMERA);
        CCPropertyManager.getInstance().setProperty("gamepad_control_flip_right",CONTROL_FLIP_RIGHT);
        CCPropertyManager.getInstance().setProperty("gamepad_control_flip_left",CONTROL_FLIP_LEFT);
        CCPropertyManager.getInstance().setProperty("gamepad_control_flip_ahead",CONTROL_FLIP_AHEAD);
        CCPropertyManager.getInstance().setProperty("gamepad_control_flip_behind",CONTROL_FLIP_BEHIND);
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

                if (CONTROL_TAKEOFF.equals(comp.getName())) {
                    drone.takeOff();
                } else { if (CONTROL_LANDING.equals(comp.getName())) {
                    drone.landing();
                } else { if (CONTROL_GO_RIGHT_LEFT.equals(comp.getName())) {
                    if (event.getValue()>0) {
                        System.out.println("drone.goRight");
                        drone.goRight();
                    } else {
                        System.out.println("drone.goLeft");
                        drone.goLeft();
                    }
                } else {
                    if (CONTROL_UP_DOWN.equals(comp.getName())) {
                        if (event.getValue() > 0) {
                            System.out.println("drone.up");
                            drone.up();
                        } else {
                            System.out.println("drone.down");
                            drone.down();
                        }

                    } else {
                        if (CONTROL_FORWARD_BACKWARD.equals(comp.getName())) {
                            if (event.getValue() > 0) {
                                System.out.println("drone.forward");
                                drone.forward();
                            } else {
                                System.out.println("drone.backward");
                                drone.backward();
                            }
                        } else {
                            if (CONTROL_SPEED.equals(comp.getName())) {
                                if (event.getValue() > 0) {
                                    System.out.println("drone.setSpeed +1");
                                    drone.setSpeed(drone.getSpeed() + 1);
                                } else {
                                    System.out.println("drone.setSpeed -1");
                                    drone.setSpeed(drone.getSpeed() - 1);
                                }
                            }
                            else {
                                if (CONTROL_SPIN_LEFT.equals(comp.getName())) {
                                    System.out.println("drone.spinLeft");
                                    drone.spinLeft();
                                }
                                else {
                                    if (CONTROL_SPIN_RIGHT.equals(comp.getName())) {
                                        System.out.println("drone.spinRight");
                                        drone.spinRight();
                                    }
                                    else {
                                        if (CONTROL_TOGGLE_CAMERA.equals(comp.getName())) {
                                            System.out.println("drone.toggleCamera");
                                            drone.toggleCamera();
                                        }
                                        else {
                                            if (CONTROL_FLIP_LEFT.equals(comp.getName())) {
                                                drone.getCommandManager().animate(FlightAnimation.FLIP_LEFT);
                                            }  else {
                                                if (CONTROL_FLIP_RIGHT.equals(comp.getName())) {
                                                    drone.getCommandManager().animate(FlightAnimation.FLIP_RIGHT);
                                                }  else {
                                                    if (CONTROL_FLIP_AHEAD.equals(comp.getName())) {
                                                        drone.getCommandManager().animate(FlightAnimation.FLIP_AHEAD);
                                                    } else {
                                                        if (CONTROL_FLIP_BEHIND.equals(comp.getName())) {
                                                            drone.getCommandManager().animate(FlightAnimation.FLIP_BEHIND);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
                }
                }

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
