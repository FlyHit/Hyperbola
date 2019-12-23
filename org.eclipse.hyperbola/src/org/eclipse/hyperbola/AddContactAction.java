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
import org.eclipse.hyperbola.model.ContactsGroup;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ResourceLocator;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;

public class AddContactAction extends Action implements ISelectionListener,
        ActionFactory.IWorkbenchAction {
    private final IWorkbenchWindow window;
    public final static String ID = "org.eclipse.hyperbola.addContact";
    private IStructuredSelection selection;

    /**
     * 添加添加联系人的动作
     *
     * @param window
     */
    public AddContactAction(IWorkbenchWindow window) {
        this.window = window;
        setId(ID);
        setText("&Add Contact...");
        setToolTipText("Add a contact to your contacts list.");
        setImageDescriptor(ResourceLocator.imageDescriptorFromBundle("org.eclipse.hyperbola",
                IImageKeys.ADD_CONTACT).orElse(null));
//        (不直接监听一个特定的事件源)监听窗口(window)可以使action和view分离,并可在其他情境(视图)中使用该action
//		将监听器添加到window's selection service,可以监听该window所有的选择改变事件
//		但selection service不会自己产生selection,因此需要将view或editor注册为selection provider,
//		然后将selection发布给监听器.这里是Contact view将选择事件发布给window
        window.getSelectionService().addSelectionListener(this);
    }

    @Override
    public void dispose() {
        window.getSelectionService().removeSelectionListener(this);
    }

    /**
     * 选择改变的时候调用(需改变为一个非null选择)
     *
     * @param part     包含该选择的workbench part
     * @param incoming 当前的选择
     */
    @Override
    public void selectionChanged(IWorkbenchPart part, ISelection incoming) {
        // IStructuredSelection: a selection containing elements
        if (incoming instanceof IStructuredSelection) {
            selection = (IStructuredSelection) incoming;
//            只有选中联系人组(ContactsGroup)且只选中一个,才能触发该动作
            setEnabled(selection.size() == 1
                    && selection.getFirstElement() instanceof ContactsGroup);
        } else {
            // Other selections, for example containing text or of other kinds.
            setEnabled(false);
        }
    }

    public void run() {
        AddContactDialog d = new AddContactDialog(window.getShell());
        int code = d.open();
        if (code == Window.OK) {
            Object item = selection.getFirstElement();
            ContactsGroup group = (ContactsGroup) item;
            ContactsEntry entry = new ContactsEntry(group, d.getUserId(), d
                    .getNickname(), d.getServer());
            group.addEntry(entry);
        }
    }

}
