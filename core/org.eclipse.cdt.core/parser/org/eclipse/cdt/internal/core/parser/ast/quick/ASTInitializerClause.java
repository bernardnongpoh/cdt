/**********************************************************************
 * Copyright (c) 2002,2003 Rational Software Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Common Public License v0.5
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v05.html
 * 
 * Contributors: 
 * IBM Rational Software - Initial API and implementation
***********************************************************************/
package org.eclipse.cdt.internal.core.parser.ast.quick;

import java.util.Iterator;
import java.util.List;

import org.eclipse.cdt.core.parser.ISourceElementRequestor;
import org.eclipse.cdt.core.parser.ast.IASTExpression;
import org.eclipse.cdt.core.parser.ast.IASTInitializerClause;
import org.eclipse.cdt.core.parser.ast.IASTVariable;
import org.eclipse.cdt.internal.core.parser.ast.EmptyIterator;

/**
 * @author jcamelon
 */
public class ASTInitializerClause implements IASTInitializerClause {

	private final IASTInitializerClause.Kind kind; 
	private final IASTExpression assignmentExpression; 
	private final List initializerClauses; 
	private final List designators;
	private IASTVariable ownerDeclaration = null;
	/**
	 * @param kind
	 * @param assignmentExpression
	 * @param initializerClauses
	 */
	public ASTInitializerClause(Kind kind, IASTExpression assignmentExpression, List initializerClauses, List designators ) {
		this.kind = kind; 
		this.assignmentExpression = assignmentExpression;
		this.initializerClauses = initializerClauses; 
		this.designators = designators; 
	}

	/* (non-Javadoc)
	 * @see org.eclipse.cdt.core.parser.ast.IASTInitializerClause#getKind()
	 */
	public Kind getKind() {
		return kind;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.cdt.core.parser.ast.IASTInitializerClause#getInitializerList()
	 */
	public Iterator getInitializers() {
		if( initializerClauses == null )
			return new EmptyIterator();
		return initializerClauses.iterator();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.cdt.core.parser.ast.IASTInitializerClause#getAssigmentExpression()
	 */
	public IASTExpression getAssigmentExpression() {
		return assignmentExpression;
	}

    /* (non-Javadoc)
     * @see org.eclipse.cdt.core.parser.ISourceElementCallbackDelegate#acceptElement(org.eclipse.cdt.core.parser.ISourceElementRequestor)
     */
    public void acceptElement(ISourceElementRequestor requestor)
    {
    }

    /* (non-Javadoc)
     * @see org.eclipse.cdt.core.parser.ISourceElementCallbackDelegate#enterScope(org.eclipse.cdt.core.parser.ISourceElementRequestor)
     */
    public void enterScope(ISourceElementRequestor requestor)
    {
    }

    /* (non-Javadoc)
     * @see org.eclipse.cdt.core.parser.ISourceElementCallbackDelegate#exitScope(org.eclipse.cdt.core.parser.ISourceElementRequestor)
     */
    public void exitScope(ISourceElementRequestor requestor)
    {
    }

    /* (non-Javadoc)
     * @see org.eclipse.cdt.core.parser.ast.IASTInitializerClause#getDesignators()
     */
    public Iterator getDesignators()
    {
        return designators.iterator();
    }

    /* (non-Javadoc)
     * @see org.eclipse.cdt.core.parser.ast.IASTInitializerClause#setOwnerDeclaration(org.eclipse.cdt.core.parser.ast.IASTDeclaration)
     */
    public void setOwnerVariableDeclaration(IASTVariable declaration)
    {
        ownerDeclaration = declaration;
    }

    /* (non-Javadoc)
     * @see org.eclipse.cdt.core.parser.ast.IASTInitializerClause#getOwnerDeclaration()
     */
    public IASTVariable getOwnerVariableDeclaration()
    {
        return ownerDeclaration;
    }

}
