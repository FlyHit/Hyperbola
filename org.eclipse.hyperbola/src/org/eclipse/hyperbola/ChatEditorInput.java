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
 * editor通过IEditorInput打开
 * IEditorInput是editor input的轻量级描述符，类似于文件名，但更抽象。
 * 它不是模型。它是IEditorPart的模型源的描述。
 * 实现此editor input接口的客户端应重写Object.equals（Object）以对两个相同的输入回答true。
 * IWorkbenchPage.openEditor API依赖于此来查找具有相同输入的编辑器。
 * client应扩展此接口以声明新类型的编辑器输入。
 * editor input通过IEditorPart.init方法传递给editor。
 */
public class ChatEditorInput implements IEditorInput {
    private String participant;  // 使用participant判断两个输入是否相同

    public ChatEditorInput(String participant) {
        super();
        Assert.isNotNull(participant);  // participant需非null
        this.participant = participant;
    }

    /**
     * editor input是否存在
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

    //    返回能够保存输入状态的对象
    @Override
    public IPersistableElement getPersistable() {
        return null;
    }

    //    两个输入相同则返回true
//    IWorkbenchPage.openEditor API依赖于此来查找具有相同输入的editor
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
