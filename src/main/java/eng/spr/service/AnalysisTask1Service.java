package eng.spr.service;

import eng.spr.model.AnalysisTask1;

public interface AnalysisTask1Service {
	public Iterable<AnalysisTask1> findAll();
	public AnalysisTask1 findOne(int id);
	public void insertAnalysisTask1(AnalysisTask1 analysisTask1);
	public boolean deleteAnalysisTask1(int id);
}
