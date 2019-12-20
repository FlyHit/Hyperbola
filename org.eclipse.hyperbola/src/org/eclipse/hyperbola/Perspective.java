package org.eclipse.hyperbola;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

//perspective类必须实现IPespectivej接口
public class Perspective implements IPerspectiveFactory {

//	创建页面的初始布局
	@Override	
	public void createInitialLayout(IPageLayout layout) {
	}
}
