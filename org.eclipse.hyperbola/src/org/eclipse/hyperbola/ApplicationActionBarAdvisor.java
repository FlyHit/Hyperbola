package org.eclipse.hyperbola;

import org.eclipse.jface.action.*;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

/**
 * �������嶯��,ʹ��createActionBarAdvisor()��ʼ��
 *
 * @author 11648
 */
public class ApplicationActionBarAdvisor extends ActionBarAdvisor {
    private ActionFactory.IWorkbenchAction exitAction;
    private ActionFactory.IWorkbenchAction aboutAction;
    private ActionFactory.IWorkbenchAction addContactAction;
    private ActionFactory.IWorkbenchAction chatAction;

    public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
        super(configurer);
    }

    //    makeActions()�ڴ��岿������֮ǰ����,��˴�������ʱ���ܷ��ʴ���Ĳ���
    @Override
    protected void makeActions(IWorkbenchWindow window) {
//        ActionFactory������һϵ�п��Ա����õ�ͨ�ö���
        exitAction = ActionFactory.QUIT.create(window);
//        register():ע�ᶯ��,��֤����ر�ʱ,�����ᱻע��;ͬʱ֧�ְ�����
        register(exitAction);
        aboutAction = ActionFactory.ABOUT.create(window);
        register(aboutAction);
        addContactAction = new AddContactAction(window);
        register(addContactAction);
        chatAction = new ChatAction(window);
        register(chatAction);
    }

    @Override
    protected void fillMenuBar(IMenuManager menuBar) {
        MenuManager hyperbolaMenu = new MenuManager("&Hyperbola", "hyperbola");
        hyperbolaMenu.add(addContactAction);
        hyperbolaMenu.add(exitAction);
        hyperbolaMenu.add(chatAction);
//        hyperbolaMenu.add(new GroupMarker("other-actions"));
        MenuManager helpMenu = new MenuManager("&Help", "help");
        helpMenu.add(aboutAction);
        menuBar.add(hyperbolaMenu);
        menuBar.add(helpMenu);
//        hyperbolaMenu.add(helpMenu);  // �˵�����Ƕ��
    }

    @Override
    protected void fillCoolBar(ICoolBarManager coolBar) {
        IToolBarManager toolBar = new ToolBarManager(coolBar.getStyle());
        coolBar.add(toolBar);
        toolBar.add(addContactAction);
        toolBar.add(chatAction);
    }

    protected void fillTrayItem(IMenuManager trayItem) {
        trayItem.add(aboutAction);
        trayItem.add(exitAction);
    }

    //    ���ƶ��Ĺ�����
//    public void populateCoolBar(IActionBarConfigurer configurer) {
//        ICoolBarManager mgr = configurer.getCoolBarManager();
//        IToolBarManager toolBarManager = new ToolBarManager(mgr.getStyle());
//        mgr.add(toolBarManager);
//        toolBarManager.add(addContactAction);
//        toolBarManager.add(new Separator());
//        toolBarManager.add(addContactAction);
//    }
}

