package equipoideal.model.event;

import equipoideal.model.dto.ProgresoEventoDto;

public interface IWorkerObserver {
	void onFinish();
	void onProgress(ProgresoEventoDto progress);
	void onError(String error);
}
