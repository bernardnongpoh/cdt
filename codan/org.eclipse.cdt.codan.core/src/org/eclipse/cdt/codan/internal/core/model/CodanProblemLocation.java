/*******************************************************************************
 * Copyright (c) 2009 Alena Laskavaia 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Alena Laskavaia  - initial API and implementation
 *******************************************************************************/
package org.eclipse.cdt.codan.internal.core.model;

import org.eclipse.cdt.codan.core.model.ProblemLocation;
import org.eclipse.core.resources.IFile;

/**
 * Codan Problem Location, so far same as abstract class
 *
 */
public class CodanProblemLocation extends ProblemLocation {

	public CodanProblemLocation(IFile file, int startChar, int endChar) {
		super(file, startChar, endChar);
	}

	protected CodanProblemLocation(IFile file, int line) {
		super(file, line);
	}

}
