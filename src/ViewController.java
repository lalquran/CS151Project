
public class ViewController {
	public ViewOption[] views;
	public int size = 0;
	public ViewController(){
		
		views= new ViewOption[5];
	}
	public void add(ViewOption newView){
		views[size] = newView;
		size++;
	}
	
}
