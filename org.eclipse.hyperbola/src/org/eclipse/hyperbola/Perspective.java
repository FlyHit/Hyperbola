package org.eclipse.hyperbola;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

// IPerspectiveFactory:���ɳ�ʼ��ҳ�沼�ֺͿ��ӵĶ�������action set��
public class Perspective implements IPerspectiveFactory {
    public final static String ID = "org.eclipse.hyperbola.perspective";

    /**
     * ����ҳ��ĳ�ʼ����
     * ��Ҫperspectiveʱ�����ᴴ��factory��Ȼ����ø÷�������IPageLayout��page��֮��factory������
     * perspective factoryֻ��perspective��һ�δ�����ʱ��Żᱻ����
     *
     * @param layout
     */
    @Override
    public void createInitialLayout(IPageLayout layout) {
        layout.setEditorAreaVisible(false);
//		editor area���ڷ���Contacts view��ratio��viewռreference area�İٷֱ�
//		editor������ӵ�perspective�У�����������perspective��ָ��editor��ʱ��λ��
//		layout.addView(ContactsView.ID,IPageLayout.LEFT,1.0f,layout.getEditorArea());
//		StandaloneView�������ر���������ôһ�����Է�ֹ��ͼ��view�����رա��ƶ�
        layout.addStandaloneView(ContactsView.ID, false, IPageLayout.LEFT, 0.3f, layout.getEditorArea());
    }
}
