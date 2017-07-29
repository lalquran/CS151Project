import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.*;

/**
 * CalendarModel class reads events from text file and uploads calendar with events
 */
public class CalendarModel {

    public CalendarModel()
    {
        try {
            Scheduler.loadCalendar();
        }
        catch(Exception e) {
        }
        l = new ArrayList();
    }


    /**
     * addChangeListener() adds listener to array list
     * @param: l
     */
    public void addChangeListener(ChangeListener l) {
        this.l.add(l);
    }

   /**
    * update()
    * updates listeners
    */
    public void update() {
        ChangeEvent e = new ChangeEvent(this);
        for (ChangeListener listener : l) {
            listener.stateChanged(e);
        }
    }

    /**
     * getMyEvents() method returns tree map of events
     * @param date String object
     * @return tree map
     */
    public TreeMap<String, Event> getMyEvents(String date){

        return Scheduler.viewEventByDate(date);
    }
    private ArrayList<ChangeListener> l;

}
