package org.eclipse.hyperbola;

import org.eclipse.jface.action.*;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

/**
 * 创建窗体动作,使用createActionBarAdvisor()初始化
 *该advisor也是提供窗体层面上的建议，负责定义出现在菜单等处的建议，每个窗体
 * 都有一个ActionBarAdvisor实例
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

    //    makeActions()在窗体部件创建之前调用,因此创建动作时不能访问窗体的部件
    @Override
    protected void makeActions(IWorkbenchWindow window) {
//        ActionFactory定义了一系列可以被复用的通用动作
        exitAction = ActionFactory.QUIT.create(window);
//        register():注册动作,保证窗体关闭时,动作会被注销;同时支持按键绑定
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
//        hyperbolaMenu.add(helpMenu);  // 菜单可以嵌套
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

    //    可移动的工具栏
//    public void populateCoolBar(IActionBarConfigurer configurer) {
//        ICoolBarManager mgr = configurer.getCoolBarManager();
//        IToolBarManager toolBarManager = new ToolBarManager(mgr.getStyle());
//        mgr.add(toolBarManager);
//        toolBarManager.add(addContactAction);
//        toolBarManager.add(new Separator());
//        toolBarManager.add(addContactAction);
//    }
}

