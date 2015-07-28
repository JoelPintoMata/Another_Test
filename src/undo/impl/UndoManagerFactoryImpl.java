package undo.impl;

import undo.Document;
import undo.UndoManager;
import undo.UndoManagerFactory;

/**
 * 
 * @author Joel Mata
 *
 */
public class UndoManagerFactoryImpl implements UndoManagerFactory{
	
	@Override
	// TODO then factory method should be static
	public UndoManager createUndoManager(Document doc, int bufferSize) {
		return new UndoManagerImpl(doc, bufferSize);
	}
}
