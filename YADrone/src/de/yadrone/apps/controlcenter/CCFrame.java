package de.yadrone.apps.controlcenter;


import de.yadrone.apps.controlcenter.plugins.controller.GameControllerCommandManager;
import de.yadrone.apps.controlcenter.plugins.pluginmanager.PluginManager;
import de.yadrone.base.ARDrone;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class CCFrame extends JFrame
{
	private ARDrone drone;
	private GameControllerCommandManager controllerCommand;
	
	public CCFrame(ARDrone ardrone)
	{
		super("YADrone Control Center");
		
		setSize(1024, 768);
		
		this.drone = ardrone;
		
		try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e) {}

		JDesktopPane desktop = new JDesktopPane() {
			private Image originalImage;
			private Image scaledImage;
			private int width = 0;
		    private int height = 0;
		    
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                
                if (originalImage == null)
                { // called only once
	                ImageIcon icon = new ImageIcon(CCFrame.class.getResource("/desktop.jpg"));
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
        };
        
        // the plugin manager is a mandatory component, because it is used to start all other plugins
        
        final PluginManager pluginManager = new PluginManager();
        pluginManager.setDesktop(desktop);
        pluginManager.activate(ardrone);
        
        JInternalFrame frame = new JInternalFrame(pluginManager.getTitle(), true, false, true, true);
	    frame.setSize(500, 300);
	    frame.setLocation(0, getSize().height - frame.getSize().height - 40);
	    frame.setContentPane(pluginManager);
	    frame.setVisible(true);
        
	    desktop.add(frame);
	    
	    setContentPane(desktop);
	    
		setVisible(true);
		
		addWindowListener(new WindowListener() {

			public void windowOpened(WindowEvent e) {
			}

			public void windowIconified(WindowEvent e) {
			}

			public void windowDeiconified(WindowEvent e) {
			}

			public void windowActivated(WindowEvent e) {
			}

			public void windowDeactivated(WindowEvent e) {
			}

			public void windowClosing(WindowEvent e) {
				pluginManager.deactivate();
				drone.stop();
				System.exit(0);
			}

			public void windowClosed(WindowEvent e) {
			}
		});
//		String path=null;
//		try {
//			path = CCFrame.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
//			System.out.println("Found jar path : " + path);
//		} catch (URISyntaxException e) {
//			e.printStackTrace();
//		}
//		// load dll files from the jar
//		System.setProperty("java.library.path", path);
//		Field fieldSysPath = null;
//		try {
//			fieldSysPath = ClassLoader.class.getDeclaredField( "sys_paths" );
//		} catch (NoSuchFieldException e) {
//			e.printStackTrace();
//		}
//		fieldSysPath.setAccessible(true);
//		try {
//			fieldSysPath.set(null, null);
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//		}

		// call backend thread for the game controller.
		controllerCommand = new GameControllerCommandManager(this.drone);
		controllerCommand.start();
	}
}
