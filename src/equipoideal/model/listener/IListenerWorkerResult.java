package equipoideal.model.listener;

public interface IListenerWorkerResult {
	<T> void onViewDetailSelected(T equipo);
	<T> void onTeamSelected(T equipo);
}
