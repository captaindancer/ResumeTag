package spring.xml.test;
/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Nov 24, 2014  10:50:22 AM
 *@Description
 */
public class SomeTest {

	private String name;

	public SomeTest(){
		
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "SomeTest [name=" + name + "]";
	}
}
