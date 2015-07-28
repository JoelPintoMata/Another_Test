package undo.test;

import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import undo.UndoManager;
import undo.impl.UndoManagerFactoryImpl;

public class AllTests {

	private static boolean setUpIsDone = false;
	private static UndoManagerFactoryImpl undoManagerFactoryImpl;
	private static UndoManager undoManager;

	@BeforeClass
	public static void setUp() {
		if (setUpIsDone) {
			return;
		}
		undoManagerFactoryImpl = new UndoManagerFactoryImpl();
	}

	@Before
	public void beforeTest1() {
		undoManager = undoManagerFactoryImpl.createUndoManager(null, 0);
	}

	@Test
	public void testEmpyUndoManagerCanUndo() {
		assertFalse(
				"The undoManager is newly created, there is nothing to undo",
				undoManager.canUndo());
	}

	@Test
	public void testEmpyUndoManagerCanRedo() {
		assertFalse(
				"The undoManager is newly created, there is nothing to redo",
				undoManager.canRedo());
	}

	@Test(expected = IllegalStateException.class)
	public void testEmpyUndoManagerUndo() {
		undoManager.undo();
	}

	@Test(expected = IllegalStateException.class)
	public void testEmpyUndoManagerRedo() {
		undoManager.redo();
	}

	@Before
	public void beforeTest2() {
		undoManager = undoManagerFactoryImpl.createUndoManager(null, 10);
	}
}
