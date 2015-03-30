package com.wind.innerclass;
/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Aug 21, 2014  2:17:47 PM
 *@Description
 */
public class InnerInstance {
	
	private String name="outer";
	private String password;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void display(){
		new InnerClass().display();
		System.out.println(new InnerClass().name);
	}
	
	public class InnerClass{
		private String name="inner";
//		private static final String xx="1";
		
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
		public void display(){
			System.out.println(getName());
			System.out.println(InnerInstance.this.getName());
		}
	}
	
	public static class InnerStatic{
		private String name="static";

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
	
	public static void main(String[] args) {
		InnerInstance innerInstance=new InnerInstance();
		InnerClass innerClass=innerInstance.new InnerClass();
//		System.out.println(innerClass.getName());
		innerClass.display();
		InnerStatic innerStatic=new InnerStatic();
		System.out.println(innerStatic.getName());
	}
}
