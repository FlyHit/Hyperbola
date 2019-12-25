package org.eclipse.hyperbola;

import org.eclipse.ui.application.IWorkbenchConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

/**
 * 该advisor提供应用层面（application level）的建议，参与workbench的启动以及关闭
 * 每个运行的application只有一个workbench。每个Advisor（三种）都有一个相关联的configurer，
 * configurer使得这些advisor可以访问workbench、window、action bar。每种类型的advisor
 * 对应相应类型的configurer，由于configurer为advisor提供了对workbench的访问特权，
 * 因此绝不能将configurer传递给其他插件
 */
public class ApplicationWorkbenchAdvisor extends WorkbenchAdvisor {

    private static final String PERSPECTIVE_ID = "org.eclipse.hyperbola.perspective"; //$NON-NLS-1$

    @Override
    public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
        return new ApplicationWorkbenchWindowAdvisor(configurer);
    }

    //	一个应用可能有多个perspective,但workbenchAdvisor只能定义一个初始perspective
//	应用之后可以使用preference修改初始perspective
    @Override
    public String getInitialWindowPerspectiveId() {
        return PERSPECTIVE_ID;
    }

    /**
     * 退出程序时保存workbench的状态，再次启动时加载
     * 注意：run configuration中如果不勾选clear workspace，那么workbench的状态会一直保存，该方法不管用
     *
     * @param configurer
     */
    @Override
    public void initialize(IWorkbenchConfigurer configurer) {
        configurer.setSaveAndRestore(true);
    }
}
