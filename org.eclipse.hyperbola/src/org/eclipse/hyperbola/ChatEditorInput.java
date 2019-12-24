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

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

/**
 * editorͨ��IEditorInput��
 * IEditorInput��editor input�����������������������ļ�������������
 * ������ģ�͡�����IEditorPart��ģ��Դ��������
 * ʵ�ִ�editor input�ӿڵĿͻ���Ӧ��дObject.equals��Object���Զ�������ͬ������ش�true��
 * IWorkbenchPage.openEditor API�����ڴ������Ҿ�����ͬ����ı༭����
 * clientӦ��չ�˽ӿ������������͵ı༭�����롣
 * editor inputͨ��IEditorPart.init�������ݸ�editor��
 */
public class ChatEditorInput implements IEditorInput {
    private String participant;  // ʹ��participant�ж����������Ƿ���ͬ

    public ChatEditorInput(String participant) {
        super();
        Assert.isNotNull(participant);  // participant���null
        this.participant = participant;
    }

    /**
     * editor input�Ƿ����
     *
     * @return
     */
    @Override
    public boolean exists() {
        return false;
    }

    @Override
    public ImageDescriptor getImageDescriptor() {
        return null;
    }


    @Override
    public String getName() {
        return participant;
    }

    //    �����ܹ���������״̬�Ķ���
    @Override
    public IPersistableElement getPersistable() {
        return null;
    }

    //    ����������ͬ�򷵻�true
//    IWorkbenchPage.openEditor API�����ڴ������Ҿ�����ͬ�����editor
    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj))
            return true;
        if (!(obj instanceof ChatEditorInput))
            return false;
        ChatEditorInput other = (ChatEditorInput) obj;
        return this.participant.equals(other.participant);
    }

    @Override
    public int hashCode() {
        return participant.hashCode();
    }

    @Override
    public String getToolTipText() {
        return participant;
    }

    @Override
    public Object getAdapter(Class adapter) {
        return null;
    }
}
