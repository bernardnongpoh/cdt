/*******************************************************************************
* Copyright (c) 2016 Institute for Software, HSR Hochschule fuer Technik 
* Rapperswil, University of applied sciences and others
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/
package org.eclipse.cdt.internal.core.dom.parser.cpp.semantics;

import org.eclipse.cdt.internal.core.dom.parser.ISerializableExecution;
import org.eclipse.cdt.internal.core.dom.parser.ITypeMarshalBuffer;
import org.eclipse.cdt.internal.core.dom.parser.cpp.ICPPEvaluation.ConstexprEvaluationContext;
import org.eclipse.cdt.internal.core.dom.parser.cpp.ICPPExecution;
import org.eclipse.cdt.internal.core.dom.parser.cpp.InstantiationContext;
import org.eclipse.core.runtime.CoreException;

public class ExecDeclarationStatement implements ICPPExecution {
	private final ICPPExecution declarationExec;

	public ExecDeclarationStatement(ICPPExecution declarationExec) {
		this.declarationExec = declarationExec;
	}

	@Override
	public ICPPExecution instantiate(InstantiationContext context, int maxDepth) {
		ICPPExecution newDeclarationExec = declarationExec.instantiate(context, maxDepth);
		if (newDeclarationExec == declarationExec) {
			return this;
		}
		return new ExecDeclarationStatement(newDeclarationExec);
	}

	@Override
	public ICPPExecution executeForFunctionCall(ActivationRecord record, ConstexprEvaluationContext context) {
		ICPPExecution newDeclarationExec = declarationExec.executeForFunctionCall(record, context);
		if (newDeclarationExec == declarationExec) {
			return this;
		}
		return new ExecDeclarationStatement(newDeclarationExec);
	}
	
	@Override
	public void marshal(ITypeMarshalBuffer buffer, boolean includeValue) throws CoreException {
		buffer.putShort(ITypeMarshalBuffer.EXEC_DECLARATION_STATEMENT);
		buffer.marshalExecution(declarationExec, includeValue);
	}
	
	public static ISerializableExecution unmarshal(short firstBytes, ITypeMarshalBuffer buffer) throws CoreException {
		ICPPExecution declarationExec = (ICPPExecution)buffer.unmarshalExecution();
		return new ExecDeclarationStatement(declarationExec);
	}
}
