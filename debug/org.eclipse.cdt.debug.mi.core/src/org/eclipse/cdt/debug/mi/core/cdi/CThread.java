/*
 * (c) Copyright QNX Software Systems Ltd. 2002.
 * All Rights Reserved.
 *
 */
package org.eclipse.cdt.debug.mi.core.cdi;

import org.eclipse.cdt.debug.core.cdi.CDIException;
import org.eclipse.cdt.debug.core.cdi.model.ICDIStackFrame;
import org.eclipse.cdt.debug.core.cdi.model.ICDIThread;
import org.eclipse.cdt.debug.mi.core.MIException;
import org.eclipse.cdt.debug.mi.core.MISession;
import org.eclipse.cdt.debug.mi.core.command.CommandFactory;
import org.eclipse.cdt.debug.mi.core.command.MIStackListFrames;
import org.eclipse.cdt.debug.mi.core.command.MIStackSelectFrame;
import org.eclipse.cdt.debug.mi.core.output.MIFrame;
import org.eclipse.cdt.debug.mi.core.output.MIInfo;
import org.eclipse.cdt.debug.mi.core.output.MIStackListFramesInfo;

/**
 */
public class CThread extends CObject implements ICDIThread {

	int id;
	static StackFrame[] noStack = new StackFrame[0];
	StackFrame currentFrame;
	
	public CThread(CTarget target, int threadId) {
		super(target);
		id = threadId;
	}
	
	int getId() {
		return id;
	}

	public String toString() {
		return Integer.toString(id);
	}

	StackFrame getCurrentStackFrame() throws CDIException {
		if (currentFrame == null) {
			ICDIStackFrame[] frames = getStackFrames();
			if (frames.length > 0) {
				currentFrame = (StackFrame)frames[0];
			}
		}
		return currentFrame;
	}

	/**
	 * @see org.eclipse.cdt.debug.core.cdi.model.ICDIThread#getStackFrames()
	 */
	public ICDIStackFrame[] getStackFrames() throws CDIException {

		StackFrame[] stack = noStack;
		CSession session = getCTarget().getCSession();
		VariableManager mgr = (VariableManager)session.getVariableManager();

		MISession mi = session.getMISession();
		CommandFactory factory = mi.getCommandFactory();
		MIStackListFrames frames = factory.createMIStackListFrames();
		try {
			getCTarget().setCurrentThread(this);
			mi.postCommand(frames);
			MIStackListFramesInfo info = frames.getMIStackListFramesInfo();
			if (info == null) {
				throw new CDIException("No answer");
			}
			MIFrame[] miFrames = info.getMIFrames();
			stack = new StackFrame[miFrames.length];
			for (int i = 0; i < stack.length; i++) {
				stack[i] = new StackFrame(this, miFrames[i]);
			}
			return stack;
		} catch (MIException e) {
			//throw new CDIException(e.getMessage());
			//System.out.println(e);
		} catch (CDIException e) {
			//throw e;
			//System.out.println(e);
		}
		return stack;
	}

	/**
	 * @see org.eclipse.cdt.debug.core.cdi.model.ICDIThread#setCurrentStackFrame(ICDIStackFrame)
	 */
	public void setCurrentStackFrame(ICDIStackFrame stackframe) throws CDIException {
		if (stackframe instanceof  StackFrame) {
			setCurrentStackFrame((StackFrame)stackframe);
		}
	}

	public void setCurrentStackFrame(StackFrame stackframe) throws CDIException {
		CSession session = getCTarget().getCSession();
		MISession mi = session.getMISession();
		CommandFactory factory = mi.getCommandFactory();
		int frameNum = 0;
		if (stackframe != null) {
			frameNum = stackframe.getLevel();
		}
		MIStackSelectFrame frame = factory.createMIStackSelectFrame(frameNum);
		try {
			// Set ourself as the current thread first.
			getCTarget().setCurrentThread(this);
			mi.postCommand(frame);
			MIInfo info = frame.getMIInfo();
			if (info == null) {
				throw new CDIException("No answer");
			}
			currentFrame = stackframe;

			// Resetting threads may change the value of
			// some variables like Register.  Send an update
			// To generate changeEvents.
			VariableManager varMgr = session.getVariableManager();
			varMgr.update();

		} catch (MIException e) {
			throw new CDIException(e.getMessage());
		}
	}

	/**
	 * @see org.eclipse.cdt.debug.core.cdi.model.ICDIThread#isSuspended()
	 */
	public boolean isSuspended() {
		return getTarget().isSuspended();
	}

	/**
	 * @see org.eclipse.cdt.debug.core.cdi.model.ICDIThread#finish()
	 */
	public void finish() throws CDIException {
		getCTarget().setCurrentThread(this);
		getTarget().finish();
	}

	/**
	 * @see org.eclipse.cdt.debug.core.cdi.model.ICDIThread#resume()
	 */
	public void resume() throws CDIException {
		getTarget().resume();
	}

	/**
	 * @see org.eclipse.cdt.debug.core.cdi.model.ICDIThread#stepInto()
	 */
	public void stepInto() throws CDIException {
		getCTarget().setCurrentThread(this);
		getTarget().stepInto();
	}

	/**
	 * @see org.eclipse.cdt.debug.core.cdi.model.ICDIThread#stepIntoInstruction()
	 */
	public void stepIntoInstruction() throws CDIException {
		getCTarget().setCurrentThread(this);
		getTarget().stepIntoInstruction();
	}

	/**
	 * @see org.eclipse.cdt.debug.core.cdi.model.ICDIThread#stepOver()
	 */
	public void stepOver() throws CDIException {
		getCTarget().setCurrentThread(this);
		getTarget().stepOver();
	}

	/**
	 * @see org.eclipse.cdt.debug.core.cdi.model.ICDIThread#stepOverInstruction()
	 */
	public void stepOverInstruction() throws CDIException {
		getCTarget().setCurrentThread(this);
		getTarget().stepOverInstruction();
	}

	/**
	 * @see org.eclipse.cdt.debug.core.cdi.model.ICDIThread#stepReturn()
	 */
	public void stepReturn() throws CDIException {
		getCTarget().setCurrentThread(this);
		getTarget().stepReturn();
	}

	/**
	 * @see org.eclipse.cdt.debug.core.cdi.model.ICDIThread#suspend()
	 */
	public void suspend() throws CDIException {
		getCTarget().setCurrentThread(this);
		getTarget().suspend();
	}

	/**
	 * @see org.eclipse.cdt.debug.core.cdi.model.ICDIThread#equals(ICDIThread)
	 */
	public boolean equals(ICDIThread thread) {
		if (thread instanceof CThread) {
			CThread cthread = (CThread) thread;
			return id == cthread.getId();
		}
		return super.equals(thread);
	}

}
