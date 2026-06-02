package equipoideal.controller;

import equipoideal.model.Navigation;
import equipoideal.model.dto.ResultadoComparativoDto;
import equipoideal.model.listener.IListenerDashboardComparativo;
import equipoideal.util.VentanaEnum;
import equipoideal.view.DashboardComparativo;

public class WorkerResultController implements IListenerDashboardComparativo {
	private Navigation navigation;
	private DashboardComparativo view;
	
	public WorkerResultController(DashboardComparativo view, Navigation navigation, ResultadoComparativoDto resultadoComparativoDto) {
		this.navigation = navigation;
		this.view = view;
		this.view.getListener().addObserver(this);
		this.view.renderizarResultados(resultadoComparativoDto);
	}

	@Override
	public void onMenuPrincipalPress() {
		this.navigation.updateView(VentanaEnum.MENU);
	}

	public void dispose() {
		this.view.getListener().removeObserver(this);
	}
}
