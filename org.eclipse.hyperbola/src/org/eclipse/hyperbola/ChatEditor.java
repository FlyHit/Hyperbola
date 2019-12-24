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
 * ���촰�ڣ�editor��
 * editor���������ڣ�
 * 1.workbench��ʼ��editor��ʹ��Ĭ�ϵĹ�������������editor�ĵ�ַ��site����Ȼ�����init()
 * ��editor site����editor����workbench service)
 * 2.editor��Ϊ�ɼ��󣬵���createControl��������editor��widget
 * 3.editor�����󣬻����setFocus()
 * 4.editor�رպ������Ҫ���棬doSave()�ᱻ����
 * 5.���dispose()
 */
public class ChatEditor extends EditorPart {
    public static String ID = "org.eclipse.hyperbola.editors.chat";
    private Text transcript;  // ����������ʾ������
    private Text entry;  // ��������

    public ChatEditor() {
    }

    public void doSave(IProgressMonitor monitor) {
        // Save not supported
    }

    public void doSaveAs() {
        // Save As not supported
    }

    //    ʵ���������ϵ��ø÷������÷�����־��editor�������ڵĿ�ʼ
    public void init(IEditorSite site, IEditorInput input)
            throws PartInitException {
        setSite(site);
        setInput(input);
//        setPartName():���ô˲��֣�editor���ı��⣬ע����Ϊ""�����᲻��ʾ���⣬������ʾĬ����
//        ʹ�ø÷���������ʱ�ı�editor�ı���
        setPartName(getUser());
    }

    public boolean isDirty() {
        return false;
    }

    public boolean isSaveAsAllowed() {
        // Save As not supported
        return false;
    }

    //    ���÷���������ʱ��editor����
    public void createPartControl(Composite parent) {
        Composite top = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        top.setLayout(layout);

        transcript = new Text(top, SWT.BORDER | SWT.MULTI | SWT.WRAP);
        transcript.setLayoutData(new GridData(GridData.FILL, GridData.FILL,
                true, true));
        transcript.setEditable(false);  // ������ʾ�������ݣ����ɱ༭
        transcript.setBackground(transcript.getDisplay().getSystemColor(
                SWT.COLOR_INFO_BACKGROUND));
        transcript.setForeground(transcript.getDisplay().getSystemColor(
                SWT.COLOR_INFO_FOREGROUND));

        entry = new Text(top, SWT.BORDER | SWT.WRAP);
        GridData gridData = new GridData(GridData.FILL, GridData.FILL, true,
                false);
        gridData.heightHint = entry.getLineHeight() * 2;  // ���������ĸ߶�
        entry.setLayoutData(gridData);
//        ����enter������Ϣ
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
     * ��ʽ����Ϣ
     *
     * @param from ������Ϣ�Ķ���
     * @param body ��Ϣ����
     * @return ��ʽ�������Ϣ
     */
    private String renderMessage(String from, String body) {
        if (from == null)
            return body;
        int j = from.indexOf('@');
        if (j > 0)
            from = from.substring(0, j);  // from��ʼ��ʽΪname@xxx��������ȡ��name
        return "<" + from + ">  " + body;
    }

    /**
     * ��������õ���Ϣ�ı������
     */
    private void scrollToEnd() {
        int n = transcript.getCharCount();
        transcript.setSelection(n, n);
        transcript.showSelection();
    }

    /**
     * ������������Ϣ��transcript���Ի��򣿣�
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
