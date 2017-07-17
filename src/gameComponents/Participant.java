package gameComponents;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

//@author Calvin

public abstract class Participant {
	
	private SimpleStringProperty id;
	private SimpleStringProperty name;
	private SimpleStringProperty type;
	private SimpleIntegerProperty age;
	private SimpleStringProperty state;
	
	// constructor for Participant class
	public Participant(String id, String name, String type, int age, String state) {
		super();
		this.id = new SimpleStringProperty(id);
		this.name = new SimpleStringProperty(name);
		this.type = new SimpleStringProperty(type);
		this.age = new SimpleIntegerProperty(age);
		this.state = new SimpleStringProperty(state);
	}
	
	public String getID() {
		return id.get();
	}

	public String getName() {
		return name.get();
	}
	
	public String getType() {
		return type.get();
	}
	
	public int getAge() {
		return age.get();
	}

	public String getState() {
		return state.get();
	}	
}