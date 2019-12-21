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

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.hyperbola.model.Contact;
import org.eclipse.hyperbola.model.ContactsEntry;
import org.eclipse.hyperbola.model.ContactsGroup;
import org.eclipse.hyperbola.model.Presence;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ResourceLocator;
import org.eclipse.ui.model.IWorkbenchAdapter;

/**
 * ������������ΪContactsGroup��ContactsEntry�ṩ��IWorkbenchAdapter���ڲ���ʵ��
 * ����ϵ���б��ContactsGroup��ContactsEntry�����ĸ��ڵ㡢�ӽڵ��Ӧ��model������Ҫʵ��
 * IWorkbenchAdapter�ӿ�����ʾ��viewer�ϣ�����ʹ�ù���ģʽ��Ϊÿ��ģ��Ԫ�ط���һ���ض�IWorkbenchAdapter�ӿ�
 * ���ContactsView����ʱ��Ҫע����������������ر�ʱ��Ҫע��������������
 */
public class HyperbolaAdapterFactory implements IAdapterFactory {
    //	IWorkbenchAdapter��һ���������ӿڣ�ΪworkbenchԪ���ṩ�˿��ӻ���ʾ�Ͳ�νṹ��
    // ������֪����ЩԪ�صľ������ͱ�ɽ�������ʾ��UI��
    private IWorkbenchAdapter groupAdapter = new IWorkbenchAdapter() {
        public Object getParent(Object o) {
            return ((ContactsGroup) o).getParent();
        }

        public String getLabel(Object o) {
            ContactsGroup group = ((ContactsGroup) o);
            int available = 0;
            Contact[] entries = group.getEntries();
            for (Contact contact : entries) {
                if (contact instanceof ContactsEntry) {
                    if (((ContactsEntry) contact).getPresence() != Presence.INVISIBLE)
                        available++;
                }
            }
            return group.getName() + " (" + available + "/" + entries.length
                    + ")";
        }

        //        ͼƬ�ı�ʾ��ʽ�����֣�image��Image descriptor.ǰ����������������Ҫһֱ���о����
//        ���Ҳ�ʹ��ʱ��Ҫdispose;������ͼƬ����������ʾ,ֻ������ҪͼƬ��ʱ��Ż�ȥ·��Ѱ�Ҳ�����ͼƬ
        public ImageDescriptor getImageDescriptor(Object object) {
//            ResourceLocator:����һЩ�����ڲ����Ѱ��jFace��Դ�ķ���(imageDescriptorFromBundle():�Ӳ���м���ͼƬ)
//            ����ʹ����Option��,�����ڽ���ж϶����Ƿ�Ϊnull������
            return ResourceLocator.imageDescriptorFromBundle(Application.PLUGIN_ID,
                    IImageKeys.GROUP).orElse(null);
        }

        public Object[] getChildren(Object o) {
            return ((ContactsGroup) o).getEntries();
        }
    };

    private IWorkbenchAdapter entryAdapter = new IWorkbenchAdapter() {
        public Object getParent(Object o) {
            return ((ContactsEntry) o).getParent();
        }

        public String getLabel(Object o) {
            ContactsEntry entry = ((ContactsEntry) o);
            return entry.getNickname() + " (" + entry.getName() + "@"
                    + entry.getServer() + ")";
        }

        public ImageDescriptor getImageDescriptor(Object object) {
            ContactsEntry entry = ((ContactsEntry) object);
            String key = presenceToKey(entry.getPresence());
            return ResourceLocator.imageDescriptorFromBundle(Application.PLUGIN_ID,
                    key).orElse(null);
        }

        public Object[] getChildren(Object o) {
            return new Object[0];
        }
    };

    /**
     * ���ݲ�ͬ������״̬������Ӧ��IImageKeys
     *
     * @param presence ����״̬
     * @return ��Ӧ��IImageKeys
     */
    private String presenceToKey(Presence presence) {
        if (presence == Presence.ONLINE)
            return IImageKeys.ONLINE;
        if (presence == Presence.AWAY)
            return IImageKeys.AWAY;
        if (presence == Presence.DO_NOT_DISTURB)
            return IImageKeys.DO_NOT_DISTURB;
        if (presence == Presence.INVISIBLE)
            return IImageKeys.OFFLINE;
        return "";
    }

    //	���������������ͣ�adapter type��ʵ������ص��ࣩ���õ��˹���ģʽ
    public Object getAdapter(Object adaptableObject, Class adapterType) {
        if (adapterType == IWorkbenchAdapter.class
                && adaptableObject instanceof ContactsGroup)
            return groupAdapter;
        if (adapterType == IWorkbenchAdapter.class
                && adaptableObject instanceof ContactsEntry)
            return entryAdapter;
        return null;
    }

    public Class[] getAdapterList() {
        return new Class[]{IWorkbenchAdapter.class};
    }
}
