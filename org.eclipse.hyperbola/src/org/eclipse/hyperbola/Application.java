package org.eclipse.hyperbola;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

/**
 * This class controls all aspects of the application's execution
 */
// Application��ʵ����IApplication�ӿڣ��൱��main()���ǳ�������
//Application��������application��չ�㣬�Ҷ������ɹ���һ��Application
public class Application implements IApplication {
    public static final String PLUGIN_ID = "org.eclipse.hyperbola";

    @Override
    public Object start(IApplicationContext context) throws Exception {
        Display display = PlatformUI.createDisplay();
        try {
//			����workbench��������display��workbenchAdvisor������������workbench UI
//			workbench����֪����ô������WorkbenchAdvisor�����֪workbench��ʲô����ô��
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
