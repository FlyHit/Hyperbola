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
 * 适配器工厂，为ContactsGroup和ContactsEntry提供了IWorkbenchAdapter的内部类实现
 * （联系人列表的ContactsGroup和ContactsEntry是树的父节点、子节点对应的model，都需要实现
 * IWorkbenchAdapter接口来显示在viewer上，这里使用工厂模式来为每个模型元素返回一个特定IWorkbenchAdapter接口
 * 最后当ContactsView创建时需要注册该适配器工厂，关闭时需要注销该适配器工厂
 */
public class HyperbolaAdapterFactory implements IAdapterFactory {
    //	IWorkbenchAdapter是一个适配器接口，为workbench元素提供了可视化表示和层次结构，
    // 且无需知道这些元素的具体类型便可将它们显示在UI上
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

        //        图片的表示形式有两种：image和Image descriptor.前者是重量级对象，需要一直持有句柄，
//        并且不使用时需要dispose;后者是图片的轻量级表示,只有在需要图片的时候才会去路径寻找并创建图片
        public ImageDescriptor getImageDescriptor(Object object) {
//            ResourceLocator:包含一些用于在插件里寻找jFace资源的方法(imageDescriptorFromBundle():从插件中加载图片)
//            这里使用了Option类,可用于解决判断对象是否为null的问题
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
     * 根据不同的在线状态返回相应的IImageKeys
     *
     * @param presence 在线状态
     * @return 相应的IImageKeys
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

    //	根据适配器的类型（adapter type来实例化相关的类），用到了工厂模式
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
