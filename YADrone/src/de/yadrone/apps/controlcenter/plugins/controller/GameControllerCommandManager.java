package de.yadrone.apps.controlcenter.plugins.controller;

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
        for (Controller c : controllers) {
            System.out.println("Found controller " + c.getName());
            if (c.getType().equals(Controller.Type.GAMEPAD)) {
                controller = c;
                break;
            }
        }

        controller.poll();
        System.out.println("Use the controller : " + controller.getName());
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

                if ("8".equals(comp.getName())) {
                    drone.takeOff();
                } else { if ("9".equals(comp.getName())) {
                    drone.landing();
                } else { if ("slider".equals(comp.getName())) {
                    if (event.getValue()>0) {
                        System.out.println("drone.goRight");
                        drone.goRight();
                    } else {
                        System.out.println("drone.goLeft");
                        drone.goLeft();
                    }
                } else {
                    if ("rz".equals(comp.getName())) {
                        if (event.getValue() > 0) {
                            System.out.println("drone.up");
                            drone.up();
                        } else {
                            System.out.println("drone.down");
                            drone.down();
                        }

                    } else {
                        if ("x".equals(comp.getName())) {
                            if (event.getValue() > 0) {
                                System.out.println("drone.forward");
                                drone.forward();
                            } else {
                                System.out.println("drone.backward");
                                drone.backward();
                            }
                        } else {
                            if ("y".equals(comp.getName())) {
                                if (event.getValue() > 0) {
                                    System.out.println("drone.setSpeed +1");
                                    drone.setSpeed(drone.getSpeed() + 1);
                                } else {
                                    System.out.println("drone.setSpeed -1");
                                    drone.setSpeed(drone.getSpeed() - 1);
                                }
                            }
                            else {
                                if ("5".equals(comp.getName())) {
                                    System.out.println("drone.spinLeft");
                                    drone.spinLeft();
                                }
                                else {
                                    if ("7".equals(comp.getName())) {
                                        System.out.println("drone.spinRight");
                                        drone.spinRight();
                                    }
                                    else {
                                        if ("0".equals(comp.getName())) {
                                            System.out.println("drone.toggleCamera");
                                            drone.toggleCamera();
                                        }
                                        else {
                                            if ("4".equals(comp.getName())) {
                                                drone.getCommandManager().animate(FlightAnimation.FLIP_LEFT);
                                            }  else {
                                                if ("6".equals(comp.getName())) {
                                                    drone.getCommandManager().animate(FlightAnimation.FLIP_RIGHT);
                                                }  else {
                                                    if ("3".equals(comp.getName())) {
                                                        drone.getCommandManager().animate(FlightAnimation.FLIP_AHEAD);
                                                    } else {
                                                        if ("0".equals(comp.getName())) {
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

               /*
                case KeyEvent.VK_S:
                    drone.stop();
                    break;
                case KeyEvent.VK_LEFT:
                    if (shiftflag)
                    {
                        drone.spinLeft();
                        shiftflag = false;
                    }
                    else
                        drone.goLeft();
                    break;
                case KeyEvent.VK_RIGHT:
                    if (shiftflag)
                    {
                        drone.spinRight();
                        shiftflag = false;
                    }
                    else
                        drone.goRight();
                    break;
                case KeyEvent.VK_UP:
                    if (shiftflag)
                    {
                        drone.up();
                        shiftflag = false;
                    }
                    else
                        drone.forward();
                    break;
                case KeyEvent.VK_DOWN:
                    if (shiftflag)
                    {
                        drone.down();
                        shiftflag = false;
                    }
                    else
                        drone.backward();
                    break;
                case KeyEvent.VK_1:
                    drone.setHorizontalCamera();
                    // System.out.println("1");
                    break;
                case KeyEvent.VK_2:
                    drone.setHorizontalCameraWithVertical();
                    // System.out.println("2");
                    break;
                case KeyEvent.VK_3:
                    drone.setVerticalCamera();
                    // System.out.println("3");
                    break;
                case KeyEvent.VK_4:
                    drone.setVerticalCameraWithHorizontal();
                    // System.out.println("4");
                    break;
                case KeyEvent.VK_5:
                    drone.toggleCamera();
                    // System.out.println("5");
                    break;
                case KeyEvent.VK_R:
                    drone.spinRight();
                    break;
                case KeyEvent.VK_L:
                    drone.spinLeft();
                    break;
                case KeyEvent.VK_U:
                    drone.up();
                    break;
                case KeyEvent.VK_D:
                    drone.down();
                    break;
                case KeyEvent.VK_E:
                    drone.reset();
                    break;
                case KeyEvent.VK_PLUS:
                    drone.setSpeed(drone.getSpeed()+1);
                    break;
                case KeyEvent.VK_MINUS:
                    drone.setSpeed(drone.getSpeed()-1);
                    break;

*/
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
