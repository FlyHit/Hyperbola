package org.eclipse.hyperbola;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

/**
 * This class controls all aspects of the application's execution
 */
// Application类实现了IApplication接口，相当于main()，是程序的入口
//Application类必须关联application扩展点，且多个插件可共享一个Application
public class Application implements IApplication {
    public static final String PLUGIN_ID = "org.eclipse.hyperbola";

    @Override
    public Object start(IApplicationContext context) throws Exception {
        Display display = PlatformUI.createDisplay();
        try {
//			创建workbench，将它和display和workbenchAdvisor关联，并运行workbench UI
//			workbench并不知道怎么运作，WorkbenchAdvisor负责告知workbench做什么，怎么做
            int returnCode = PlatformUI.createAndRunWorkbench(display, new ApplicationWorkbenchAdvisor());
            if (returnCode == PlatformUI.RETURN_RESTART)
                return IApplication.EXIT_RESTART;
            else
                return IApplication.EXIT_OK;
        } finally {
            display.dispose();
        }

    }

    @Override
    public void stop() {
        if (!PlatformUI.isWorkbenchRunning())
            return;
        final IWorkbench workbench = PlatformUI.getWorkbench();
        final Display display = workbench.getDisplay();
        display.syncExec(() -> {
            if (!display.isDisposed())
                workbench.close();
        });
    }
}
