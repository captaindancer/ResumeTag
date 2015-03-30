package spring.test;
/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Jul 24, 2014  8:06:06 PM
 *@Description
 */
public class Customer {

	private int id;
	private String firstName,lastName;
	
	
	public Customer(int id, String firstName, String lastName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@Override
	public String toString(){
		return String.format("Customer[id=%d,firstName='%s',lastName='%s']", id,firstName,lastName);
	}
}
