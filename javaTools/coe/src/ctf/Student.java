package ctf;

public class Student {
	private String name;
	private String team;
	
	Student(String name, String team){
		this.name=name;
		this.team=team;
	}
	
	public String getName(){
		return this.name;
	}
	public String getTeam(){
		return this.team;
	}
	public void setName(String name){
		this.name=name;
	}
	public void setTeam(String team){
		this.team=team;
	}
}
