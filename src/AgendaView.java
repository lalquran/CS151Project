import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * AgendaView class is a JPanel with strings of times and JTables
 */
public class AgendaView extends JPanel{

	///////////////////////////////////////
	//Right side of the Application frame//
	///////////////////////////////////////
	
	private JLabel date;
	private int sYear = 0;
	private int sMonth = 0;
	private int sDay = 0;
	private int eYear = 4000;
	private int eMonth = 13;
	private int eDay = 32;
    private JScrollPane p;
    private JTable leftTable, rightTable;
    private JPanel p2;
    private int totalEvents;
    private ArrayList<String> allEvents = new ArrayList<>();
    private CalendarController eventController;
    private CalendarModel calendarModelEvents;
    private Color color;
    public String option = "AgendaView";
   /**
    * AgendaView constructor with CalendarModel parameter
    * @param: events
    */
    public AgendaView(CalendarModel events) throws IOException {
        date = new JLabel();
        color = new Color(152, 217, 233);
        p2 = new JPanel(new BorderLayout());
        p = new JScrollPane(p2);
        eventController = new CalendarController();
        this.calendarModelEvents = events;
        this.setLayout(new BorderLayout());
        //today();

    }
    
    /**
     * countAllEvents() counts all events within range
     */
    public void countAllEvents(){
    	TreeMap<String, TreeMap<String, Event>> outer = calendarModelEvents.tm;
    	totalEvents = 0;
    	allEvents.clear();
    	for(String outerKeys : outer.keySet()){
    		TreeMap<String, Event> inner = outer.get(outerKeys);
    		String[] dateComponents = outerKeys.split("/");
    		int year = Integer.parseInt(dateComponents[2].replace("/", ""));
    		int month = Integer.parseInt(dateComponents[0].replace("/", ""));
    		int day = Integer.parseInt(dateComponents[1].replace("/", ""));
    		if(year >= sYear && year <=eYear){
    			
    			if(month >= sMonth && month <= eMonth){
    				if(day >= sDay && day <= eDay){
    		    		for(String innerKeys : inner.keySet()){
    		    			totalEvents++;
    		    			allEvents.add(inner.get(innerKeys).toString());
    		    		}
    					
    				}
    			}
    		}
    	}
    	
    }
    

   /**
    * format() method creates grid style JTable
    * @param: ArrayList events
    */
    private void formatGrids(TreeMap<String, Event> events)
    {
        countAllEvents();
    	Object[][] o = new Object[totalEvents][1];
        Object[] t = {""};

        if (events != null) {
            final int[] h = new int[totalEvents];
            for (int i = 0; i < totalEvents; i++)
            {

                o[i][0] = allEvents.get(i);
            }

            rightTable = new JTable(o, t)
            {
                @Override
                public Component prepareRenderer(TableCellRenderer r, int i, int ic) {
                    Component comp = super.prepareRenderer(r, i, ic);
                    
                    if (h[i] == 1) {
                        comp.setBackground(Color.PINK);
                    } else {
                        comp.setBackground(Color.white);
                    }
                    return comp;
                }
            };
        } 
        else 
        {
            rightTable = new JTable(o, t);
        }

        rightTable.setTableHeader(null);
        rightTable.setRowHeight(40);
        rightTable.setGridColor(Color.black);
        rightTable.setEnabled(false);
    }

    /**
     * Shows the entire Right Side
     * show()
     * @param: ArrayList events
     */
    private void show(TreeMap<String, Event> events) {

        this.invalidate();
        p2.removeAll();
        formatGrids(events);
        headerDate("Agenda");

        p2.add(rightTable, BorderLayout.CENTER);

        this.add(date, BorderLayout.NORTH);
        this.add(p, BorderLayout.CENTER);
        this.validate();
        this.repaint();
    }

    /**
     * headerDate() sets the date panel header
     * @param d
     */
    private void headerDate(String d) {
        this.date.setText(d);
    }

    /**
     * today() method
     * sets current date
     */
    public void today() 
    {
    	eventController.currentDate();
        System.out.println("today: "+eventController.date2MMYYDD());
        
    }

   /**
    * view() method
    * @param: year, month and day
    */
    public void view(int y, int m, int d) {
    	eventController.setCalendar(y, m, d); //return gc
    	System.out.println("view: "+eventController.date2MMYYDD());
    	//Show right side
        show(calendarModelEvents.getMyEvents(eventController.date2MMYYDD()));
    }

    public CalendarModel getModel(){
        return calendarModelEvents;
    }
    
    public void setStartYear(int y){
    	this.sYear = y;
    }
    
    public int getStartYear(){
    	return this.sYear;
    }
    public void setStartMonth(int m){
    	this.sMonth = m;
    }
    
    public int getStartMonth(){
    	return this.sMonth;
    }
    public void setStartDay(int d){
    	this.sDay = d;
    }
    
    public int getStartDay(){
    	return this.sDay;
    }
    
    public void setEndYear(int y){
    	this.eYear = y;
    }
    
    public int getEndYear(){
    	return this.eYear;
    }
    public void setEndMonth(int m){
    	this.eMonth = m;
    }
    
    public int getEndMonth(){
    	return this.eMonth;
    }
    public void setEndDay(int d){
    	this.eDay = d;
    }
    
    public int getEndDay(){
    	return this.eDay;
    }


    
}

