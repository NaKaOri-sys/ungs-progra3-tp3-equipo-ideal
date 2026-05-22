package equipoideal.model;

import equipoideal.model.event.IObserverNavigation;
import equipoideal.util.Observable;
import equipoideal.util.VentanaEnum;

public class Navigation extends Observable<IObserverNavigation> {
	private VentanaEnum ventana;
	
	public Navigation() {
		this.ventana = VentanaEnum.MENU;
	}
	
	public void updateView(VentanaEnum newView) {
		this.ventana = newView;
		notifyObservers(observer -> observer.onViewChanged(newView));
	}
	
	public VentanaEnum getView() {
		return this.ventana;
	}

}
