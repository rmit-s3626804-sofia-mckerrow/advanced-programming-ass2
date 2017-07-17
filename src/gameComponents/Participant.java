package gameComponents;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

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
	
	public StringProperty idProperty() {
		return id;
	}

	public String getName() {
		return name.get();
	}
	
	public StringProperty nameProperty() {
		return name;
	}
	
	public String getType() {
		return type.get();
	}
	
	public StringProperty typeProperty() {
		return type;
	}
	
	public int getAge() {
		return age.get();
	}
	
	public IntegerProperty ageProperty() {
		return age;
	}

	public String getState() {
		return state.get();
	}
	
	public StringProperty stateProperty() {
		return state;
	}
}