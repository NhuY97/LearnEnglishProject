package eng.spr.service;

import java.util.List;

import eng.spr.model.AnalysisTask2;
import eng.spr.model.Lesson;

public interface AnalysisTask2Service {
	public Iterable<AnalysisTask2> findAll();
	public AnalysisTask2 findOne(int id);
	public void insertAnalysisTask2(AnalysisTask2 analysisTask2);
	public boolean deleteAnalysisTask2(int id);
	public List<AnalysisTask2> findByLesson(Lesson lesson);
}
