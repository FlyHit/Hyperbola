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

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

/**
 * 聊天窗口（editor）
 * editor的生命周期：
 * 1.workbench初始化editor（使用默认的构造器），创建editor的地址（site），然后调用init()
 * （editor site允许editor访问workbench service)
 * 2.editor设为可见后，调用createControl方法创建editor的widget
 * 3.editor创建后，会调用setFocus()
 * 4.editor关闭后，如果需要保存，doSave()会被调用
 * 5.最后dispose()
 */
public class ChatEditor extends EditorPart {
    public static String ID = "org.eclipse.hyperbola.editors.chat";
    private Text transcript;  // 聊天内容显示的区域
    private Text entry;  // 打字区域

    public ChatEditor() {
    }

    public void doSave(IProgressMonitor monitor) {
        // Save not supported
    }

    public void doSaveAs() {
        // Save As not supported
    }

    //    实例化后马上调用该方法，该方法标志着editor生命周期的开始
    public void init(IEditorSite site, IEditorInput input)
            throws PartInitException {
        setSite(site);
        setInput(input);
//        setPartName():设置此部分（editor）的标题，注意设为""，不会不显示标题，而是显示默认名
//        使用该方法可以随时改变editor的标题
        setPartName(getUser());
    }

    public boolean isDirty() {
        return false;
    }

    public boolean isSaveAsAllowed() {
        // Save As not supported
        return false;
    }

    //    当该方法被调用时，editor启动
    public void createPartControl(Composite parent) {
        Composite top = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        top.setLayout(layout);

        transcript = new Text(top, SWT.BORDER | SWT.MULTI | SWT.WRAP);
        transcript.setLayoutData(new GridData(GridData.FILL, GridData.FILL,
                true, true));
        transcript.setEditable(false);  // 用于显示聊天内容，不可编辑
        transcript.setBackground(transcript.getDisplay().getSystemColor(
                SWT.COLOR_INFO_BACKGROUND));
        transcript.setForeground(transcript.getDisplay().getSystemColor(
                SWT.COLOR_INFO_FOREGROUND));

        entry = new Text(top, SWT.BORDER | SWT.WRAP);
        GridData gridData = new GridData(GridData.FILL, GridData.FILL, true,
                false);
        gridData.heightHint = entry.getLineHeight() * 2;  // 设置输入框的高度
        entry.setLayoutData(gridData);
//        按下enter发送消息
        entry.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent event) {
                if (event.character == SWT.CR) {
                    sendMessage();
                    event.doit = false;
                }
            }
        });
    }

    public void setFocus() {
        if (entry != null && !entry.isDisposed()) {
            entry.setFocus();
        }
    }

    private String getUser() {
        return getEditorInput().getName();
    }

    /**
     * 格式化消息
     *
     * @param from 发送消息的对象
     * @param body 消息内容
     * @return 格式化后的消息
     */
    private String renderMessage(String from, String body) {
        if (from == null)
            return body;
        int j = from.indexOf('@');
        if (j > 0)
            from = from.substring(0, j);  // from初始格式为name@xxx，这里提取出name
        return "<" + from + ">  " + body;
    }

    /**
     * 将光标设置到消息文本的最后
     */
    private void scrollToEnd() {
        int n = transcript.getCharCount();
        transcript.setSelection(n, n);
        transcript.showSelection();
    }

    /**
     * 发送输入框的消息到transcript（对话框？）
     */
    private void sendMessage() {
        String body = entry.getText();
        if (body.length() == 0)
            return;
        transcript.append(renderMessage(getUser(), body));
        transcript.append("\n");
        scrollToEnd();
        entry.setText("");
    }

    public void dispose() {
    }
}
