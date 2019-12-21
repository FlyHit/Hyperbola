package org.eclipse.hyperbola;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
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
    }

    @Override
    protected void fillMenuBar(IMenuManager menuBar) {
        MenuManager hyperbolaMenu = new MenuManager("&Hyperbola", "hyperbola");
        hyperbolaMenu.add(exitAction);
//        hyperbolaMenu.add(new GroupMarker("other-actions"));
        MenuManager helpMenu = new MenuManager("&Help", "help");
        helpMenu.add(aboutAction);
        menuBar.add(hyperbolaMenu);
        menuBar.add(helpMenu);
//        hyperbolaMenu.add(helpMenu);  // �˵�����Ƕ��
    }
}

