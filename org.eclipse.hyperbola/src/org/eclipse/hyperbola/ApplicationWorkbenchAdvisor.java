package org.eclipse.hyperbola;

import org.eclipse.ui.application.IWorkbenchConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

/**
 * ��advisor�ṩӦ�ò��棨application level���Ľ��飬����workbench�������Լ��ر�
 * ÿ�����е�applicationֻ��һ��workbench��ÿ��Advisor�����֣�����һ���������configurer��
 * configurerʹ����Щadvisor���Է���workbench��window��action bar��ÿ�����͵�advisor
 * ��Ӧ��Ӧ���͵�configurer������configurerΪadvisor�ṩ�˶�workbench�ķ�����Ȩ��
 * ��˾����ܽ�configurer���ݸ��������
 */
public class ApplicationWorkbenchAdvisor extends WorkbenchAdvisor {

    private static final String PERSPECTIVE_ID = "org.eclipse.hyperbola.perspective"; //$NON-NLS-1$

    @Override
    public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
        return new ApplicationWorkbenchWindowAdvisor(configurer);
    }

    //	һ��Ӧ�ÿ����ж��perspective,��workbenchAdvisorֻ�ܶ���һ����ʼperspective
//	Ӧ��֮�����ʹ��preference�޸ĳ�ʼperspective
    @Override
    public String getInitialWindowPerspectiveId() {
        return PERSPECTIVE_ID;
    }

    /**
     * �˳�����ʱ����workbench��״̬���ٴ�����ʱ����
     * ע�⣺run configuration���������ѡclear workspace����ôworkbench��״̬��һֱ���棬�÷���������
     *
     * @param configurer
     */
    @Override
    public void initialize(IWorkbenchConfigurer configurer) {
        configurer.setSaveAndRestore(true);
    }
}
