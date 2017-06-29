package gameComponents;
//@author Calvin

public abstract class Participant {
	
	private String id;
	private String name;
	private String type;
	private int age;
	private String state;
	
	// constructor for Participant class
	public Participant(String id, String name, String type, int age, String state) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.age = age;
		this.state = state;
	}
	
	public String getID() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public String getType() {
		return type;
	}
	
	public int getAge() {
		return age;
	}

	public String getState() {
		return state;
	}	
}