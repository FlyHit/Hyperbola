package org.eclipse.hyperbola;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

// IPerspectiveFactory:生成初始的页面布局和可视的动作集（action set）
public class Perspective implements IPerspectiveFactory {
    public final static String ID = "org.eclipse.hyperbola.perspective";

    /**
     * 创建页面的初始布局
     * 需要perspective时，将会创建factory，然后调用该方法传递IPageLayout给page，之后factory将废弃
     * perspective factory只在perspective第一次创建的时候才会被创建
     *
     * @param layout
     */
    @Override
    public void createInitialLayout(IPageLayout layout) {
        layout.setEditorAreaVisible(false);
//		editor area用于放置Contacts view，ratio：view占reference area的百分比
//		editor不能添加到perspective中，不过可以在perspective中指定editor打开时的位置
//		layout.addView(ContactsView.ID,IPageLayout.LEFT,1.0f,layout.getEditorArea());
//		StandaloneView可以隐藏标题区域，这么一来可以防止视图（view）被关闭、移动
        layout.addStandaloneView(ContactsView.ID, false, IPageLayout.LEFT, 0.3f, layout.getEditorArea());
    }
}
