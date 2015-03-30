package com.wind.easymock;
/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Aug 20, 2014  2:33:13 PM
 *@Description
 */
public interface Collaborator {

	void documentAdded(String title);
	void documentChanged(String title);
	void documentRemoved(String title);
	byte voteForRemoval(String title);
	byte voteForRemovals(String... title);
}
