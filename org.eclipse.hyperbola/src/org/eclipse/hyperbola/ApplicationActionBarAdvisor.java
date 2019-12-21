package org.eclipse.hyperbola;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

/**
 * 创建窗体动作,使用createActionBarAdvisor()初始化
 *
 * @author 11648
 */
public class ApplicationActionBarAdvisor extends ActionBarAdvisor {
    private ActionFactory.IWorkbenchAction exitAction;
    private ActionFactory.IWorkbenchAction aboutAction;

    public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
        super(configurer);
    }

    //    makeActions()在窗体部件创建之前调用,因此创建动作时不能访问窗体的部件
    @Override
    protected void makeActions(IWorkbenchWindow window) {
//        ActionFactory定义了一系列可以被复用的通用动作
        exitAction = ActionFactory.QUIT.create(window);
//        register():注册动作,保证窗体关闭时,动作会被注销;同时支持按键绑定
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
//        hyperbolaMenu.add(helpMenu);  // 菜单可以嵌套
    }
}

