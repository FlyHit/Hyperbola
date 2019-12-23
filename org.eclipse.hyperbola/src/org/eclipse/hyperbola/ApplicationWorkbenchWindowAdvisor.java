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
 * 配置窗体
 *
 * @author 11648
 */
public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {
	//	image和TrayItem作为实例域保存，因此在窗口关闭的时候可以被删除（释放）
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

	// 在窗体控件创建前调用,可设置窗体的初始尺寸,隐藏状态栏和工具栏
    @Override
	public void preWindowOpen() {
		IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
		configurer.setInitialSize(new Point(400, 300));
		configurer.setShowCoolBar(true);
		configurer.setShowStatusLine(true);
		configurer.setTitle("Hyperbola！");
		configurer.setShowMenuBar(true); // 显示菜单栏,如果没有菜单项仍不可见
	}

	/**
	 * 在窗体打开后执行任意动作
	 */
	@Override
	public void postWindowOpen() {
//		TODO 状态栏的message不显示，在18版的eclipse似乎运行正常
		initStatusLine();
		final IWorkbenchWindow window = getWindowConfigurer().getWindow();
		trayItem = initTaskItem(window);
		if (trayItem != null) {
			hookPopupMenu(window);
			hookMinimize(window);
		}
	}

	/**
	 * 窗口最小化时不在任务栏显示图标，双击托盘图标恢复
	 *
	 * @param window
	 */
	private void hookMinimize(final IWorkbenchWindow window) {
		window.getShell().addShellListener(new ShellAdapter() {
			// shell最小化时触发
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
	 * 托盘图标添加右键菜单
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
//    	获取系统托盘需要display，display通过调用PlatformUI.createDisplay()在Workbench创建之前创建
//		窗口创建后，workbenchWindowAdvisor便可以通过shell获取display
		final Tray tray = window.getShell().getDisplay().getSystemTray();
		if (tray == null)
			return null;
//        Tray——TrayItem
		trayItem = new TrayItem(tray, SWT.NONE);
		Optional<ImageDescriptor> imageDescriptorOption = ResourceLocator.imageDescriptorFromBundle(
				Application.PLUGIN_ID, IImageKeys.ONLINE);
		imageDescriptorOption.ifPresent(imageDescriptor -> trayImage = imageDescriptor.createImage());
		trayItem.setImage(trayImage);
		trayItem.setToolTipText("Hyperbola");
		return trayItem;
	}

	/**
	 * 初始化状态栏 由于setMessage等方法只能在状态栏控件创建之后调用,因此不能在ActionBarAdvisor里的fillStatusLine()
	 * 中调用(该方法在状态栏调用之前创建).可在postWindowOpen()中调用这些方法
	 */
	private void initStatusLine() {
//		注意ImageDescriptor可能为null，不能从null创建image
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
