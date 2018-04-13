package eng.spr.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eng.spr.model.AnalysisTask1;
import eng.spr.model.Lesson;
import eng.spr.model.SentenceEng;
import eng.spr.model.Word;
import eng.spr.repository.AnalysisTask1Repository;

@Service
@Transactional
public class AnalysisTask1ServiceImpl implements AnalysisTask1Service {
	@Autowired
	AnalysisTask1Repository analysisTask1Repository;
	@Override
	public Iterable<AnalysisTask1> findAll() {
		return analysisTask1Repository.findAll();
	}

	@Override
	public AnalysisTask1 findOne(int id) {
		try {
			return analysisTask1Repository.findById(id).get();
		} catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void insertAnalysisTask1(AnalysisTask1 analysisTask1) {
		analysisTask1Repository.save(analysisTask1);
		
	}

	@Override
	public boolean deleteAnalysisTask1(int id) {
		try {
			analysisTask1Repository.delete(analysisTask1Repository.findById(id).get());
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public List<AnalysisTask1> findByLesson(Lesson lesson) {
		List<AnalysisTask1> rs = new ArrayList<AnalysisTask1>();
		for(AnalysisTask1 a : findAll()) {
			if(a.getSentenceEng().getWord().getLesson().getId() == lesson.getId())
				rs.add(a);
		}
		return rs;
	}

	@Override
	public List<AnalysisTask1> findByWord(Word word) {
		List<AnalysisTask1> rs = new ArrayList<AnalysisTask1>();
		for(AnalysisTask1 a : findAll()) {
			if(a.getSentenceEng().getWord().getId() == word.getId())
				rs.add(a);
		}
		return rs;
	}

	@Override
	public AnalysisTask1 findBySentence(SentenceEng sentence) {
		for(AnalysisTask1 a : findAll()) {
			if(a.getSentenceEng().getId() == sentence.getId())
				return a;
		}
		return null;
	}

	@Override
	public boolean updateAnalysisTask1(AnalysisTask1 at1) {
		AnalysisTask1 at1Update = findOne(at1.getId());
		if(at1Update == null)
			return false;
		at1Update.setSentenceEng(at1.getSentenceEng());
		at1Update.setTotalTimes(at1.getTotalTimes());
		at1Update.setWrongTimes(at1.getWrongTimes());
		analysisTask1Repository.save(at1Update);
		return true;
	}
}
