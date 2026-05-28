package equipoideal.controller;

import equipoideal.model.Navigation;
import equipoideal.model.listener.IListenerDashboardComparativo;
import equipoideal.util.VentanaEnum;
import equipoideal.view.DashboardComparativo;

public class WorkerResultController implements IListenerDashboardComparativo {
	private Navigation navigation;
	private DashboardComparativo view;
	
	public WorkerResultController(DashboardComparativo view, Navigation navigation) {
		this.navigation = navigation;
		this.view = view;
		this.view.getListener().addObserver(this);
	}

	@Override
	public void onMenuPrincipalPress() {
		this.navigation.updateView(VentanaEnum.MENU);
	}
}
