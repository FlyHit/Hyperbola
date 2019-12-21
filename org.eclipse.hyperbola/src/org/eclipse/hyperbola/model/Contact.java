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
1. IWorkbenchAdapter��һ���������ӿڣ�ΪworkbenchԪ���ṩ�˿��ӻ���ʾ�Ͳ�νṹ��
 ������֪����ЩԪ�صľ������ͱ�ɽ�������ʾ��UI�ϡ�WorkbenchLabelProvider��
 BaseWorkbenchContentProvider���������label provider��content provider����������
 JFace��viewer����ʾ������ע����������Ԫ�أ�����
 2. ������IWorkBenchAdapter�Ķ����ܹ�ʹ�ñ�׼��content provider��ʾ��viewer��
 3. �����ǣ���1����ģ�Ͷ���ʵ��IAdapter�ӿڣ�2���ṩһ��������������Adapter Factory��
 notes��PlatformObjectʵ��IAdapter�ӿ�
 */
public abstract class Contact extends PlatformObject {
    public abstract String getName();

    public abstract ContactsGroup getParent();
}
