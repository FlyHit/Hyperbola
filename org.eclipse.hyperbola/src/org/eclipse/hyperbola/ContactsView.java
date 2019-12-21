package org.eclipse.hyperbola;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.Platform;
import org.eclipse.hyperbola.model.Contact;
import org.eclipse.hyperbola.model.ContactsEntry;
import org.eclipse.hyperbola.model.ContactsGroup;
import org.eclipse.hyperbola.model.Session;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.part.ViewPart;

public class ContactsView extends ViewPart {
    //	view ID
    public static final String ID = "org.eclipse.hyperbola.views.contacts";
    private TreeViewer treeViewer;
    private Session session;
    private IAdapterFactory adapterFactory = new HyperbolaAdapterFactory();

    public ContactsView() {
        super();
    }

    //    创建控件
    @Override
    public void createPartControl(Composite parent) {
        initializeSession();
        treeViewer = new TreeViewer(parent, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
//        注册给定的adapter factory，对Contact的实例及其子类有效
        getSite().setSelectionProvider(treeViewer);
        Platform.getAdapterManager().registerAdapters(adapterFactory, Contact.class);
//        jFace的viewer是基于模型的SWT部件适配器（adaptor），可在rcp的view中显示内容，RCP的view则可以包含多个viewer。
//        content provider:提供树节点，实现了ITreeContentProvider
//        该接口允许viewer使用getChildren()和getParent()查询其输入对象的结构,然后将对象的元素显示在view上
//        label provider：为树节点生成易读的名称以及图片
//        setInput():通知treeViewer使用给定的对象创建树，并由content provider来解释这些对象，使其成为树
//        setInput()会依此使用ITreeContentProvider的getChildren()方法来将元素显示在view上，
//        这意味着根输入对象不会被显示（只作为创建树的起始点，类似个抽象的根节点，不可见）
        treeViewer.setLabelProvider(new WorkbenchLabelProvider());
        treeViewer.setContentProvider(new BaseWorkbenchContentProvider());
        treeViewer.setInput(session.getRoot());
        session.getRoot().addContactsListener((contacts, entry) -> treeViewer.refresh());
    }

    private void initializeSession() {
        session = new Session();
        ContactsGroup root = session.getRoot();
        ContactsGroup friendGroup = new ContactsGroup(root, "Friend");
        root.addEntry(friendGroup);
        friendGroup.addEntry(new ContactsEntry(friendGroup, "Alize", "aliz", "localhost"));
        friendGroup.addEntry(new ContactsEntry(friendGroup, "Sydney", "syd", "localhost"));
        ContactsGroup otherGroup = new ContactsGroup(root, "other");
        root.addEntry(otherGroup);
        otherGroup.addEntry(new ContactsEntry(otherGroup, "Nadine", "nad", "localhost"));
    }

    @Override
    public void setFocus() {
        treeViewer.getControl().setFocus();
    }

    /**
     * 关闭该view时，注销adapter factory
     */
    @Override
    public void dispose() {
        Platform.getAdapterManager().unregisterAdapters(adapterFactory);
        super.dispose();
    }
}
