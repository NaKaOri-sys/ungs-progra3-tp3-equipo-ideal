package equipoideal.model.event;

import equipoideal.util.VentanaEnum;

public interface IObserverNavigation {
	void onViewChanged(VentanaEnum viewName);
}
