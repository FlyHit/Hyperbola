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

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.IViewLayout;

public class PerspectiveFreeMoving implements IPerspectiveFactory {
    public static final String ID = "org.eclipse.hyperbola.perspectives.freeMoving";

    public void createInitialLayout(IPageLayout layout) {
        IFolderLayout folder = layout.createFolder("contacts",
                IPageLayout.LEFT, 0.33f, layout.getEditorArea());
        folder.addPlaceholder(ContactsView.ID + ":*");
        folder.addView(ContactsView.ID);
//		view默认是可以关闭的，这里设为不可关闭
        IViewLayout l = layout.getViewLayout(ContactsView.ID);
        l.setCloseable(false);

        layout.addPerspectiveShortcut(Perspective.ID);
        layout.addPerspectiveShortcut(PerspectiveFreeMoving.ID);
    }
}
