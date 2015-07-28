package undo.impl;

import java.util.ArrayList;
import java.util.List;

import undo.Change;
import undo.Document;
import undo.UndoManager;

/**
 * 
 * @author Joel Mata
 * 
 */
public class UndoManagerImpl implements UndoManager {

	private Document doc;
	private int bufferSize;

	// Points to the current change to apply
	private int changeIndex = 0;

	// ArrayList allows direct access to the index with the change to apply
	// , this behaviour is quite useful for cases where many undo(s) and redo(s) operations are performed.
	private List<Change> changes = new ArrayList<Change>();

	public UndoManagerImpl(Document doc, int bufferSize) {
		this.doc = doc;
		this.bufferSize = bufferSize;
	}

	@Override
	public void registerChange(Change change) {
		incChangeIndex();
		changes.add(changeIndex, change);
	}

	@Override
	public boolean canUndo() {
		// TODO we could use the new Optimal class of Java 8 to handle the nulls
		if (changes != null) {
			if (doc == null || bufferSize < 0 || changes.size() == 0)
				return false;
			else
				return true;
		} else {
			return false;
		}
	}

	@Override
	public void undo() {
		if (!canUndo()) {
			throw new IllegalStateException(
					"UndoManagerImpl.undo(), there are no changes to undo.");
		} else {
			decChangeIndex();
			try {
				changes.get(changeIndex).apply(doc);
			} catch (IllegalStateException illegalStateException) {
				throw new IllegalStateException(
						"UndoManagerImpl.undo(), error while applying this change: "
								+ illegalStateException.getMessage());
			}
		}
	}

	@Override
	public boolean canRedo() {
		// TODO we could use the new Optimal class of Java 8 to handle the nulls
		if (changes != null) {
			if (doc == null || bufferSize < 0 || changes.size() == 0)
				return false;
			else
				return true;
		} else {
			return false;
		}
	}

	@Override
	public void redo() {
		if (!canRedo()) {
			throw new IllegalStateException(
					"UndoManagerImpl.undo(), there are no more changes to redo.");
		} else {
			incChangeIndex();
			try {
				changes.get(changeIndex).revert(doc);
			} catch (IllegalStateException illegalStateException) {
				throw new IllegalStateException(
						"UndoManagerImpl.undo(), error while applying this change: "
								+ illegalStateException.getMessage());
			}
		}
	}

	/**
	 * Forces changeIndex to have values between 0 and undoManagerBufferSize. In
	 * the case where changeIndex goes higher than undoManagerBufferSize its
	 * value is reset back to zero
	 * 
	 */
	private void incChangeIndex() {
		changeIndex++;
		if (changeIndex > bufferSize)
			changeIndex = 0;
	}

	/**
	 * Forces changeIndex to have values between 0 and undoManagerBufferSize. In
	 * the case where changeIndex goes lower than zero its value is reset back
	 * to undoManagerBufferSize
	 * 
	 */
	private void decChangeIndex() {
		changeIndex++;
		if (changeIndex < 0)
			changeIndex = bufferSize;
	}
}
