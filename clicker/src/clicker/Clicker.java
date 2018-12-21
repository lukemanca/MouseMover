package clicker;

import java.awt.Robot;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PopupMenu;

public class Clicker {

	private static Robot robo;
	private static Timer timer;
	private static int currentState = 0;
	private static int delayTime = 120000;
	private static int pixelJump = 1;
	private static JFrame frame;

	public static void main(String[] args) throws AWTException, IOException {
		runSplash();
		setTray();
		robo = new Robot();
		timer = new Timer();
		timer.schedule(new TimerTask() {
            @Override
            public void run() {
                mouseMove();
            }
        }, delayTime, delayTime);
	}
	
	
	public static void mouseMove() {
			if((currentState + 1 ) > 3){
				currentState = 0;
			}
		    else {
			currentState += 1;
		    }

			Point finishPoint = locationSwitch(currentState);
			robo.mouseMove((int) finishPoint.getX(), (int) finishPoint.getY());
	}
	
	public static Point locationSwitch(int currentState) {
		Point p = MouseInfo.getPointerInfo().getLocation();
		
		switch (currentState) {	
			case 0:
				p.setLocation(p.getX() + pixelJump, p.getY()); //Right 1
				break;
		
			case 1:
				p.setLocation(p.getX(), p.getY() - pixelJump); //Right 1
				break;
			
			case 2:
				p.setLocation(p.getX() - pixelJump, p.getY()); //Right 1
				break;
				
			case 3:
				p.setLocation(p.getX(), p.getY() + pixelJump); //Right 1
				break;
		}	
		return p;
	}
	

	public static void setTray() throws IOException {
		if (!SystemTray.isSupported()) {
            setFrame();
        }
		//From clicker Package
		Image image = ImageIO.read(Clicker.class.getResourceAsStream("MoveMouseWSmaller.png"));
        final TrayIcon trayIcon = new TrayIcon(image, "MouseMover");
        
        //Make PopUp Menu
        final PopupMenu popup = new PopupMenu();
        MenuItem quitItem = new MenuItem("Quit");
        popup.add(quitItem);
        trayIcon.setPopupMenu(popup);
        
        //Add Listeners (Anonymous Class)
        ActionListener listener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	System.exit(0); 
            }
        };
        
        quitItem.addActionListener(listener);
        
        final SystemTray tray = SystemTray.getSystemTray();
        try {
            tray.add(trayIcon);
            
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
        }
	}

	public static void setFrame() {
		frame = new JFrame("MouseMover");
		frame.setUndecorated (true);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
            frame.setIconImage(ImageIO.read(Clicker.class.getResourceAsStream("MoveMouseW.png")));
        } catch (final IOException exc) {
            System.out.println("NoWOrk for frame Image");
        }
	}
	
	public static void runSplash() {
		
	}
	
}