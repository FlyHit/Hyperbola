package org.eclipse.hyperbola;

import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ResourceLocator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tray;
import org.eclipse.swt.widgets.TrayItem;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

import java.util.Optional;

/**
 * ���ô���
 *
 * @author 11648
 */
public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {
	//	image��TrayItem��Ϊʵ���򱣴棬����ڴ��ڹرյ�ʱ����Ա�ɾ�����ͷţ�
	private Image statusImage;
	private TrayItem trayItem;
	private Image trayImage;
	private ApplicationActionBarAdvisor actionBarAdvisor;

    public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
        super(configurer);
    }

    @Override
    public ActionBarAdvisor createActionBarAdvisor(IActionBarConfigurer configurer) {
		actionBarAdvisor = new ApplicationActionBarAdvisor(configurer);
		return actionBarAdvisor;
    }

	// �ڴ���ؼ�����ǰ����,�����ô���ĳ�ʼ�ߴ�,����״̬���͹�����
    @Override
	public void preWindowOpen() {
		IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
		configurer.setInitialSize(new Point(400, 300));
		configurer.setShowCoolBar(true);
		configurer.setShowStatusLine(true);
		configurer.setTitle("Hyperbola��");
		configurer.setShowMenuBar(true); // ��ʾ�˵���,���û�в˵����Բ��ɼ�
	}

	/**
	 * �ڴ���򿪺�ִ�����⶯��
	 */
	@Override
	public void postWindowOpen() {
//		TODO ״̬����message����ʾ����18���eclipse�ƺ���������
		initStatusLine();
		final IWorkbenchWindow window = getWindowConfigurer().getWindow();
		trayItem = initTaskItem(window);
		if (trayItem != null) {
			hookPopupMenu(window);
			hookMinimize(window);
		}
	}

	/**
	 * ������С��ʱ������������ʾͼ�꣬˫������ͼ��ָ�
	 *
	 * @param window
	 */
	private void hookMinimize(final IWorkbenchWindow window) {
		window.getShell().addShellListener(new ShellAdapter() {
			// shell��С��ʱ����
			@Override
			public void shellIconified(ShellEvent e) {
				window.getShell().setVisible(false);
			}
		});

		trayItem.addListener(SWT.DefaultSelection, event -> {
			Shell shell = window.getShell();
			if (!shell.isVisible()) {
				shell.setVisible(true);
				window.getShell().setMinimized(false);
			}
		});
	}

	/**
	 * ����ͼ������Ҽ��˵�
	 *
	 * @param window
	 */
	private void hookPopupMenu(final IWorkbenchWindow window) {
		// Add listener for menu pop-up
		trayItem.addListener(SWT.MenuDetect, event -> {
			MenuManager trayMenu = new MenuManager();
			Menu menu = trayMenu.createContextMenu(window.getShell());
			actionBarAdvisor.fillTrayItem(trayMenu);
			menu.setVisible(true);
		});
	}

	private TrayItem initTaskItem(IWorkbenchWindow window) {
//    	��ȡϵͳ������Ҫdisplay��displayͨ������PlatformUI.createDisplay()��Workbench����֮ǰ����
//		���ڴ�����workbenchWindowAdvisor�����ͨ��shell��ȡdisplay
		final Tray tray = window.getShell().getDisplay().getSystemTray();
		if (tray == null)
			return null;
//        Tray����TrayItem
		trayItem = new TrayItem(tray, SWT.NONE);
		Optional<ImageDescriptor> imageDescriptorOption = ResourceLocator.imageDescriptorFromBundle(
				Application.PLUGIN_ID, IImageKeys.ONLINE);
		imageDescriptorOption.ifPresent(imageDescriptor -> trayImage = imageDescriptor.createImage());
		trayItem.setImage(trayImage);
		trayItem.setToolTipText("Hyperbola");
		return trayItem;
	}

	/**
	 * ��ʼ��״̬�� ����setMessage�ȷ���ֻ����״̬���ؼ�����֮�����,��˲�����ActionBarAdvisor���fillStatusLine()
	 * �е���(�÷�����״̬������֮ǰ����).����postWindowOpen()�е�����Щ����
	 */
	private void initStatusLine() {
//		ע��ImageDescriptor����Ϊnull�����ܴ�null����image
		Optional<ImageDescriptor> imageDescriptorOption = ResourceLocator.imageDescriptorFromBundle(
				Application.PLUGIN_ID, IImageKeys.GROUP);
		imageDescriptorOption.ifPresent(imageDescriptor -> statusImage = imageDescriptor.createImage());
		IStatusLineManager statusLine = getWindowConfigurer().getActionBarConfigurer().getStatusLineManager();
		statusLine.setMessage(statusImage, "Online");
	}

	@Override
	public void dispose() {
		if (statusImage != null)
			statusImage.dispose();
		if (trayImage != null)
			trayImage.dispose();
		if (trayItem != null)
			trayItem.dispose();
	}
}
