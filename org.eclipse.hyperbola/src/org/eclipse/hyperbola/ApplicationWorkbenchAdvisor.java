package org.eclipse.hyperbola;

import org.eclipse.ui.application.IWorkbenchConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

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
