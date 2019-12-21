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

/**
 * 该接口含保存了图片地址的变量，这样可以无需记住图片地址也方便修改
 */
public interface IImageKeys {
    //	接口中的变量默认是public static final
    String ONLINE = "icons/online.gif";
    String OFFLINE = "icons/offline.gif";
    String DO_NOT_DISTURB = "icons/dnd.gif";
    String GROUP = "icons/groups.gif";
    String AWAY = "icons/away.gif";
}
