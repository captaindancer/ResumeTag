package com.wind.easymock;

import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;


/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Aug 20, 2014  2:41:18 PM
 *@Description
 */
public class ExampleTest extends EasyMockSupport {

	@Mock
	private Collaborator collaborator;  //创建mock
	
	@TestSubject
	private final ClassTested classUnderTest=new ClassTested();  //设置测试的类
	
	@Test
	public void addDocument(){
		collaborator.documentAdded("New Document");  //希望mock做的
		replayAll();  //告诉所有的mock,我们正在做真实的测试
		classUnderTest.addDocument("New Document", "content");  //测试
		verifyAll();  //确保应该被调用的调用了
	}
}
