/*******************************************************************************
 * Copyright (c) 2000, 2010 QNX Software Systems and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     QNX Software Systems - Initial API and implementation
 *******************************************************************************/
package org.eclipse.cdt.debug.internal.ui;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

/**
 * Color manager for C/C++ Debug UI.
 * 
 * @since Jul 23, 2002
 */
public class ColorManager {
	private static ColorManager gfColorManager;

	private ColorManager() {
	}

	public static ColorManager getDefault() {
		if (gfColorManager == null) {
			gfColorManager = new ColorManager();
		}
		return gfColorManager;
	}

	protected Map<RGB, Color> fColorTable = new HashMap<RGB, Color>(10);

	public Color getColor(RGB rgb) {
		Color color = fColorTable.get(rgb);
		if (color == null) {
			color = new Color(Display.getCurrent(), rgb);
			fColorTable.put(rgb, color);
		}
		return color;
	}

	public void dispose() {
		Iterator<Color> e = fColorTable.values().iterator();
		while (e.hasNext())
			 e.next().dispose();
	}
}
