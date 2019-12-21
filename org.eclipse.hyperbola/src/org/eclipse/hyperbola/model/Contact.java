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

import org.eclipse.core.runtime.PlatformObject;

/*
1. IWorkbenchAdapter是一个适配器接口，为workbench元素提供了可视化表示和层次结构，
 且无需知道这些元素的具体类型便可将它们显示在UI上。WorkbenchLabelProvider和
 BaseWorkbenchContentProvider是相关联的label provider和content provider，可用于在
 JFace的viewer中显示带有已注册适配器的元素（对象）
 2. 适配了IWorkBenchAdapter的对象能够使用标准的content provider显示在viewer上
 3. 做法是：（1）让模型对象实现IAdapter接口（2）提供一个适配器工厂（Adapter Factory）
 notes：PlatformObject实现IAdapter接口
 */
public abstract class Contact extends PlatformObject {
    public abstract String getName();

    public abstract ContactsGroup getParent();
}
