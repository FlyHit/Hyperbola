/*******************************************************************************
 * Copyright (c) 2005 Jean-Michel Lemieux, Jeff McAffer and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Hyperbola is an RCP application developed for the book
 *     Eclipse Rich Client Platform - 
 *         Designing, Coding, and Packaging Java Applications
 * See http://eclipsercp.org
 *
 * Contributors:
 *     Jean-Michel Lemieux and Jeff McAffer - initial API and implementation
 *******************************************************************************/
package org.eclipse.hyperbola;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.console.IConsoleConstants;

public class PerspectiveDebug implements IPerspectiveFactory {
    public static final String ID = "org.eclipsercp.hyperbola.perspectives.debug";

    public void createInitialLayout(IPageLayout layout) {
        layout.addStandaloneView(ContactsView.ID, false, IPageLayout.LEFT,
                0.33f, layout.getEditorArea());
        layout.addView(IConsoleConstants.ID_CONSOLE_VIEW, IPageLayout.BOTTOM,
                0.70f, layout.getEditorArea());
        layout.addPerspectiveShortcut(Perspective.ID);
        layout.addPerspectiveShortcut(PerspectiveFreeMoving.ID);
    }
}
