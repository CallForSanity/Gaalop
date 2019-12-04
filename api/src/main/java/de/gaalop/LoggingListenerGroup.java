package de.gaalop;

import java.util.*;
import de.gaalop.LoggingListener;


public class LoggingListenerGroup extends LinkedList<LoggingListener>
{
	public void logNote(String topic, Object args) {
		ListIterator listIterator = this.listIterator(0);
        while(listIterator.hasNext()){ 
        	LoggingListener listener = (LoggingListener) listIterator.next();
        	listener.logNote(topic, args);
        } 
	}
}