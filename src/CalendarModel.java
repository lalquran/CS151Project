import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.ParseException;
import java.util.*;

/**
 * CalendarModel class reads events from text file 
 * and uploads calendar with events
 */
public class CalendarModel {
	///////////////////
	//Deleting Events//
	//Creating Events//
	///////////////////
	
	private ArrayList<ChangeListener> l;
	// inside TreeMap holds all the events. The first tree map sorts the dates
	static public TreeMap<String, Event> listOfEvents = new TreeMap<>();
	//outside TreeMaps holds all the dates. The second tree map sorts the times of multiple events in the same day
	static public TreeMap<String, TreeMap<String, Event>> tm = new TreeMap<>();
	static Scanner scanner = new Scanner(System.in);

	public CalendarModel()
	{
		try {
			loadCalendar();
		}
		catch(Exception e) {
		}
		l = new ArrayList<>();
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

		return viewEventByDate(date);
	}

	/** creatEvent method prompts user for all information needed to create a
	 * new event and add it to the tree map
	 *
	 * @throws ParseException
	 * @throws IOException 
	 */
	public static boolean createEvent(String title, String eventDate, String startingTime, String endingTime) throws ParseException, IOException {
		listOfEvents = new TreeMap<>();

		boolean flag = false;

		Event createdEvent = new Event(title, Event.modifyStringToSort(eventDate), startingTime, endingTime);

		// if the outer tree map is empty for that 
		// specific date, reserve spot for that day. 
		// if the day is already made, it will skip this
		// if statement
		if (tm.get(createdEvent.getEventDate()) == null) {
			tm.put(createdEvent.getEventDate(), new TreeMap<String, Event>());
		}


		
		listOfEvents = tm.get(createdEvent.getEventDate());

		if(listOfEvents.size()==0){
			listOfEvents.put(startingTime, createdEvent);
			flag = true;
		}

		for(Map.Entry<String, Event> e : listOfEvents.entrySet()) {


			if (!e.getValue().isConflict(startingTime, endingTime)) {
				listOfEvents.put(startingTime, createdEvent);

				tm.put(createdEvent.getEventDate(), listOfEvents);
			
				flag = true;
			}
		}
		return flag;
	}

	/** loadCalendar fills the event tree map with 
	 * events listed in a text file: events.txt
	 * @throws IOException
	 */
	public static void loadCalendar() throws IOException {
		FileReader file = new FileReader("events.txt");
		BufferedReader br = new BufferedReader(file);

		String title, eventDate, startingTime, endingTime;

		String line = "";
		//set line equal to line read from createdEvent.txt
		while ((line = br.readLine()) != null) {
			eventDate = line.split("\\|")[0];

			// this time variable is going to catch the entire time frame
			String time = line.split("\\|")[1];

			startingTime = time.split("-")[0];

			if (time.split("-").length == 1) {
				endingTime = null;
			} else {
				endingTime = time.split("-")[1];
			}
			title = line.split("\\|")[2];


			Event createdEvent = new Event(title, eventDate, startingTime, endingTime);

			if (tm.get(createdEvent.getEventDate()) == null) {
				tm.put(createdEvent.getEventDate(), new TreeMap<String, Event>());
			}

			listOfEvents = tm.get(createdEvent.getEventDate());

			listOfEvents.put(startingTime, createdEvent);

			tm.put(createdEvent.getEventDate(), listOfEvents);
		}
	}

	public static TreeMap<String, Event> viewEventByDate(String option) {
		if (tm.get(option) != null) {
			return tm.get(option);
		}
		return null;
	}

	/** deleteEvent allows the user two options in deleting events
	 * First option is delete an event by specific date.
	 * Second option is deleting all events
	 */
	public static void deleteEvent() {
		System.out.println("Delete options: [S]elect (To select a date) or [A]ll (To delete all events)");
		String option = scanner.nextLine().toUpperCase();

		switch (option) {
		case "S":
			System.out.println("Enter the date of the event you wish to delete (MM/DD/YYYY)");
			String input = scanner.nextLine();
			if (tm.get(input) != null) {
				if(tm.get(input).size() != 0){
					tm.get(input).clear();
					System.out.println("All events on this date have been deleted \n");
				}
				else
					System.out.println("Event not found.\n");
			} else {
				System.out.println("Event not found.\n");
			}
			break;
		case "A":
			tm.clear();
			System.out.println("All events have been deleted\n");
			break;
		}


	}


	/** saveEventsToTxtFile saves all events created to a txt file events.txt
	 */
	public static void saveEventsToTxtFile(){
		try {
			PrintWriter filewriter = new PrintWriter(new FileWriter(new File("events.txt"), false));

			for (Map.Entry<String, TreeMap<String, Event>> outTM : tm.entrySet()) {

				for (Map.Entry<String, Event> events : outTM.getValue().entrySet()) {

					filewriter.println(events.getValue().toStringOutput());
				}
			}filewriter.close();
		} catch(IOException e){
			System.err.println("error writing to file");
		}
	}

}