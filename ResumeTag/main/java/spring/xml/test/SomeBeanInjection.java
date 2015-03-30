package spring.xml.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Nov 24, 2014  10:51:37 AM
 *@Description
 */
public class SomeBeanInjection {

	private String[] stringArray;
	private SomeTest[] someObjArray;
	private List<Object> someList;
	private Map<Object, Object> someMap;
	private SomeTest someTest;
	
	public SomeTest getSomeTest() {
		return someTest;
	}

	public void setSomeTest(SomeTest someTest) {
		this.someTest = someTest;
	}

	public String[] getStringArray() {
		return stringArray;
	}

	public void setStringArray(String[] stringArray) {
		this.stringArray = stringArray;
	}

	public SomeTest[] getSomeObjArray() {
		return someObjArray;
	}

	public void setSomeObjArray(SomeTest[] someObjArray) {
		this.someObjArray = someObjArray;
	}

	public List<Object> getSomeList() {
		return someList;
	}

	public void setSomeList(List<Object> someList) {
		this.someList = someList;
	}

	public Map<Object, Object> getSomeMap() {
		return someMap;
	}

	public void setSomeMap(Map<Object, Object> someMap) {
		this.someMap = someMap;
	}

	public static void main(String[] args) {
		ApplicationContext applicationContext=new ClassPathXmlApplicationContext("applicationContext.xml");
		SomeBeanInjection bean=(SomeBeanInjection) applicationContext.getBean("someBean");
		String[] strs=bean.getStringArray();
		for(int i=0;i<strs.length;i++){
			System.out.println(strs[i]);
		}
		List<Object> someList=bean.getSomeList();
		System.out.println(someList.getClass().getName());
		System.out.println(someList.size());
		Map<Object, Object> someMap=bean.getSomeMap();
		System.out.println(someMap.getClass().getName());
		System.out.println(someMap.size());
	}

}
