/*******************************************************************************
 * Copyright (c) 2010 Jean-Michel Lemieux, Jeff McAffer, Chris Aniszczyk and others.
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
 *     Chris Aniszczyk - edits for the second edition
 *******************************************************************************/
package org.eclipse.hyperbola;

import org.eclipse.hyperbola.model.ContactsEntry;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ResourceLocator;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.*;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;

public class ChatAction extends Action implements ISelectionListener, IWorkbenchAction {
    private final IWorkbenchWindow window;
    public final static String ID = "org.eclipse.hyperbola.chat";
    private IStructuredSelection selection;

    public ChatAction(IWorkbenchWindow window) {
        this.window = window;
        setId(ID);
        setText("&Chat");
        setToolTipText("Chat with the selected contact.");
        setImageDescriptor(
                ResourceLocator.imageDescriptorFromBundle(Application.PLUGIN_ID, IImageKeys.CHAT).orElse(null));
        window.getSelectionService().addSelectionListener(this);
    }

    public void dispose() {
        window.getSelectionService().removeSelectionListener(this);
    }

    public void selectionChanged(IWorkbenchPart part, ISelection incoming) {
        if (incoming instanceof IStructuredSelection) {
            selection = (IStructuredSelection) incoming;
            setEnabled(selection.size() == 1 && selection.getFirstElement() instanceof ContactsEntry);
        } else {
            // Other selections, for example containing text or of other kinds.
            setEnabled(false);
        }
    }

    public void run() {
        Object item = selection.getFirstElement();
        ContactsEntry entry = (ContactsEntry) item;
        IWorkbenchPage page = window.getActivePage();
        ChatEditorInput input = new ChatEditorInput(entry.getName());
        try {
//        	使用IWorkbenchPage.openEditor打开editor，如果editor已经打开，
//			则激活该editor；反之，新创建一个editor（使用ChatEditorInput.equal判断）
            page.openEditor(input, ChatEditor.ID);
        } catch (PartInitException e) {
            // handle error
        }
    }
}
