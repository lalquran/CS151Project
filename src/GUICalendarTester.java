import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JFrame;



public class GUICalendarTester  {
	
	public static void main(String[] args) throws IOException{
		SimpleCalendarFrame calendarFrame = new SimpleCalendarFrame();
        calendarFrame.setSize(new Dimension(1000, 600));
        calendarFrame.setResizable(true);
        calendarFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        calendarFrame.setVisible(true);
	}
}
