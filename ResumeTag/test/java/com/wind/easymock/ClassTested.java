package com.wind.easymock;

import java.util.HashMap;
import java.util.Map;

/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Aug 20, 2014  2:35:01 PM
 *@Description
 */
public class ClassTested {

	private Collaborator listener;

    private final Map<String, String> documents = new HashMap<String, String>();

    public void setListener(final Collaborator listener) {
        this.listener = listener;
    }

    public void addDocument(final String title, final String content) {
        final boolean documentChange = documents.containsKey(title);
        documents.put(title, content);
        if (documentChange) {
            notifyListenersDocumentChanged(title);
        } else {
            notifyListenersDocumentAdded(title);
        }
    }

    public boolean removeDocument(final String title) {
        if (!documents.containsKey(title)) {
            return true;
        }

        if (!listenersAllowRemoval(title)) {
            return false;
        }

        documents.remove(title);
        notifyListenersDocumentRemoved(title);

        return true;
    }

    public boolean removeDocuments(final String... titles) {
        if (!listenersAllowRemovals(titles)) {
            return false;
        }

        for (final String title : titles) {
            documents.remove(title);
            notifyListenersDocumentRemoved(title);
        }
        return true;
    }

    private void notifyListenersDocumentAdded(final String title) {
        listener.documentAdded(title);
    }

    private void notifyListenersDocumentChanged(final String title) {
        listener.documentChanged(title);
    }

    private void notifyListenersDocumentRemoved(final String title) {
        listener.documentRemoved(title);
    }

    private boolean listenersAllowRemoval(final String title) {
        return listener.voteForRemoval(title) > 0;
    }

    private boolean listenersAllowRemovals(final String... titles) {
        return listener.voteForRemovals(titles) > 0;
    }

}
