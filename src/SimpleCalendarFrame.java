import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class SimpleCalendarFrame extends JFrame implements ChangeListener {

	
    private CalendarController eventController;
    private CalendarModel CalendarModel;
    //day view is the right side of the panel
    private DayView DayView;
    private MonthView MonthView;
    private AgendaView AgendaView;
    final JPanel rightPanel;
    private JPanel lastView = new JPanel();
    private JPanel rightView = new JPanel();
    

    /**
     * CalendarFrame constructor creates a CalendarFrame object
     *
     */
    public SimpleCalendarFrame() throws IOException
    {
    	
    	//has the day view
        CalendarModel = new CalendarModel();

        DayView = new DayView(CalendarModel);
        
        rightPanel = new JPanel(new BorderLayout());
        JPanel rightChoicePanel = new JPanel(new FlowLayout());
       
        JButton dayChoice = new JButton("Day");
        JButton weekChoice = new JButton("Week");
        JButton monthChoice = new JButton("Month");
        JButton fourDaysChoice = new JButton("4 Days");
        JButton agendaChoice = new JButton("Agenda");
        rightChoicePanel.add(dayChoice);
        rightChoicePanel.add(weekChoice);
        rightChoicePanel.add(monthChoice);
        rightChoicePanel.add(fourDaysChoice);
        rightChoicePanel.add(agendaChoice);
        
        
       
       
        rightPanel.add(rightChoicePanel, BorderLayout.NORTH);
       
        CalendarModel.addChangeListener(this);

        eventController = new CalendarController(CalendarModel);

        eventController.set(DayView);
    

        MonthView = new MonthView(eventController, CalendarModel);
        AgendaView = new AgendaView(CalendarModel);
        eventController.setAgendaView(AgendaView);
        
        final CalendarView calendar = new CalendarView(eventController, CalendarModel);
        JPanel left = new JPanel();
        left.setLayout(new BorderLayout());

        JPanel p = new JPanel(new FlowLayout());
        p.add(calendar);
        calendar.setPreferredSize(new Dimension(300, 300));
   
        left.add(p, BorderLayout.CENTER);

        

        setLayout(new BorderLayout());
        add(left, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);
        setTitle("CALENDAR");

        rightPanel.add(DayView, BorderLayout.CENTER);
        rightPanel.validate();
        eventController.get().view(eventController.getYear(), eventController.getMonth(), eventController.getDay());
        rightPanel.repaint();
        
        
        
        
        
        dayChoice.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				rightPanel.remove(rightView);
				rightPanel.add(DayView, BorderLayout.CENTER);
				add(rightPanel);
				lastView = rightView;
				rightView = DayView;
				rightPanel.validate();
				repaint();
			}
        });
        
        monthChoice.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				rightPanel.remove(rightView);
				rightPanel.add(MonthView, BorderLayout.CENTER);
				add(rightPanel);
				lastView = rightView;
				rightView = MonthView;
				rightPanel.validate();
				repaint();
			}
        });
        agendaChoice.addActionListener(new ActionListener(){
    		@Override
    		public void actionPerformed(ActionEvent e) {
    			rightPanel.remove(rightView);
    			rightPanel.add(AgendaView, BorderLayout.CENTER);
    			add(rightPanel);
    			lastView = rightView;
    			rightView = AgendaView;
    			String sYear, sMonth, sDay, eYear, eMonth, eDay;
    			sYear = JOptionPane.showInputDialog("Please Provide a Range for the agenda. Whats's the range's start year? ");
    			sMonth = JOptionPane.showInputDialog("Start Month? ");
    			sDay = JOptionPane.showInputDialog("Start Day? ");
    			eYear = JOptionPane.showInputDialog("End Year? ");
    			eMonth = JOptionPane.showInputDialog("End Month? ");
    			eDay = JOptionPane.showInputDialog("End Day? ");
    			AgendaView.setStartYear(Integer.parseInt(sYear));
    	        AgendaView.setStartMonth(Integer.parseInt(sMonth));
    	        AgendaView.setStartDay(Integer.parseInt(sDay));
    	        AgendaView.setEndYear(Integer.parseInt(eYear));
    	        AgendaView.setEndMonth(Integer.parseInt(eMonth));
    	        AgendaView.setEndDay(Integer.parseInt(eDay));
    			AgendaView.view(eventController.getYear(), eventController.getMonth(), eventController.getDay());
    			rightPanel.validate();
    			repaint();
    		}
        });
    }
    

  
 
    /**
     * stateChanged() repaints dayView
     * @param: CalendarModel
     */
    public void stateChanged(ChangeEvent e) 
    {
    	rightPanel.remove(lastView);
    	
        rightPanel.add(rightView, BorderLayout.CENTER);
        rightPanel.validate();
        rightPanel.repaint();
        this.repaint();
    }
}
