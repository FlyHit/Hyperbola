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
     * ��������ϵ�˵Ķ���
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
//        (��ֱ�Ӽ���һ���ض����¼�Դ)��������(window)����ʹaction��view����,�����������龳(��ͼ)��ʹ�ø�action
//		����������ӵ�window's selection service,���Լ�����window���е�ѡ��ı��¼�
//		��selection service�����Լ�����selection,�����Ҫ��view��editorע��Ϊselection provider,
//		Ȼ��selection������������.������Contact view��ѡ���¼�������window
        window.getSelectionService().addSelectionListener(this);
    }

    @Override
    public void dispose() {
        window.getSelectionService().removeSelectionListener(this);
    }

    /**
     * ѡ��ı��ʱ�����(��ı�Ϊһ����nullѡ��)
     *
     * @param part     ������ѡ���workbench part
     * @param incoming ��ǰ��ѡ��
     */
    @Override
    public void selectionChanged(IWorkbenchPart part, ISelection incoming) {
        // IStructuredSelection: a selection containing elements
        if (incoming instanceof IStructuredSelection) {
            selection = (IStructuredSelection) incoming;
//            ֻ��ѡ����ϵ����(ContactsGroup)��ֻѡ��һ��,���ܴ����ö���
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
