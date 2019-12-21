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

    //    �����ؼ�
    @Override
    public void createPartControl(Composite parent) {
        initializeSession();
        treeViewer = new TreeViewer(parent, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
//        ע�������adapter factory����Contact��ʵ������������Ч
        getSite().setSelectionProvider(treeViewer);
        Platform.getAdapterManager().registerAdapters(adapterFactory, Contact.class);
//        jFace��viewer�ǻ���ģ�͵�SWT������������adaptor��������rcp��view����ʾ���ݣ�RCP��view����԰������viewer��
//        content provider:�ṩ���ڵ㣬ʵ����ITreeContentProvider
//        �ýӿ�����viewerʹ��getChildren()��getParent()��ѯ���������Ľṹ,Ȼ�󽫶����Ԫ����ʾ��view��
//        label provider��Ϊ���ڵ������׶��������Լ�ͼƬ
//        setInput():֪ͨtreeViewerʹ�ø����Ķ��󴴽���������content provider��������Щ����ʹ���Ϊ��
//        setInput()������ʹ��ITreeContentProvider��getChildren()��������Ԫ����ʾ��view�ϣ�
//        ����ζ�Ÿ�������󲻻ᱻ��ʾ��ֻ��Ϊ����������ʼ�㣬���Ƹ�����ĸ��ڵ㣬���ɼ���
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
     * �رո�viewʱ��ע��adapter factory
     */
    @Override
    public void dispose() {
        Platform.getAdapterManager().unregisterAdapters(adapterFactory);
        super.dispose();
    }
}
