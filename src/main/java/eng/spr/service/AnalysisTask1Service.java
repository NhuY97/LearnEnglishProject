package eng.spr.service;

import java.util.List;

import eng.spr.model.AnalysisTask1;
import eng.spr.model.Lesson;
import eng.spr.model.Word;

public interface AnalysisTask1Service {
	public Iterable<AnalysisTask1> findAll();
	public AnalysisTask1 findOne(int id);
	public void insertAnalysisTask1(AnalysisTask1 analysisTask1);
	public boolean deleteAnalysisTask1(int id);
	public List<AnalysisTask1> findByLesson(Lesson lesson);
	public List<AnalysisTask1> findByWord(Word word);
	
}
