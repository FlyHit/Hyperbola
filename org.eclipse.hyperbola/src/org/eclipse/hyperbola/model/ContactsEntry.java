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
package org.eclipse.hyperbola.model;

/**
 * 联系人列表（树）的联系人条目（子节点的模型）
 */
public class ContactsEntry extends Contact {
    private final String name;
    private final String nickname;
    private final String server;
    private Presence presence;  // 联系人的在线状态
    private final ContactsGroup group;  // 联系人组（父节点）

    public ContactsEntry(ContactsGroup group, String name, String nickname,
                         String server) {
        this.group = group;
        this.name = name;
        this.nickname = nickname;
        this.server = server;
        this.presence = Presence.INVISIBLE;
    }

    /**
     * 获取在线状态
     *
     * @return 联系人的在线状态
     */
    public Presence getPresence() {
        return presence;
    }

    public void setPresence(Presence presence) {
        this.presence = presence;
        group.fireContactsChanged(this);
    }

    public String getName() {
        return name;
    }

    public String getNickname() {
        return nickname;
    }

    public String getServer() {
        return server;
    }

    public ContactsGroup getParent() {
        return group;
    }
}
