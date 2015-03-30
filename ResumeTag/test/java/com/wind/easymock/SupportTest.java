package com.wind.easymock;

import static org.junit.Assert.*;

import org.easymock.EasyMockSupport;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;

/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Aug 20, 2014  3:03:20 PM
 *@Description
 */
public class SupportTest extends EasyMockSupport {
	 private Collaborator collaborator;

	    private ClassTested classUnderTest;

	    @Before
	    public void setup() {
	        classUnderTest = new ClassTested();
	    }

	    @Test
	    public void addDocument() {
	        collaborator = createMock(Collaborator.class);
	        classUnderTest.setListener(collaborator);
	        collaborator.documentAdded("New Document");
	        replayAll();
	        classUnderTest.addDocument("New Document", "content");
	        verifyAll();
	    }

	    @Test
	    public void voteForRemovals() {

	        final IMocksControl ctrl = createControl();
	        collaborator = ctrl.createMock(Collaborator.class);
	        classUnderTest.setListener(collaborator);

	        collaborator.documentAdded("Document 1");

//	        expect(collaborator.voteForRemovals(new String[]{"Document 1"})).andReturn((byte) 20);

	        collaborator.documentRemoved("Document 1");

	        replayAll();

	        classUnderTest.addDocument("Document 1", "content");
	        assertTrue(classUnderTest.removeDocuments(new String[]{"Document 1"}));

	        verifyAll();
	    }
}
