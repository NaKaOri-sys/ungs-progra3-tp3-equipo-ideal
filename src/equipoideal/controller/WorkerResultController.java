package equipoideal.controller;

import equipoideal.model.WorkerResultModel;
import equipoideal.model.event.IObserverWorkerResult;
import equipoideal.view.WorkerResultPanel;

public class WorkerResultController implements IObserverWorkerResult {
	private WorkerResultPanel view;
	private WorkerResultModel model;
	
	public WorkerResultController(WorkerResultPanel view, WorkerResultModel model) {
		this.view = view;
		this.model = model;
	}
}
