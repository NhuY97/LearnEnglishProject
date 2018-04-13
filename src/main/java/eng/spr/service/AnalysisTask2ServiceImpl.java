package eng.spr.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eng.spr.model.AnalysisTask2;
import eng.spr.model.Lesson;
import eng.spr.model.Word;
import eng.spr.repository.AnalysisTask2Repository;

@Service
@Transactional
public class AnalysisTask2ServiceImpl implements AnalysisTask2Service {
	@Autowired
	AnalysisTask2Repository analysisTask2Repository;
	@Override
	public Iterable<AnalysisTask2> findAll() {
		return analysisTask2Repository.findAll();
	}

	@Override
	public AnalysisTask2 findOne(int id) {
		try {
			return analysisTask2Repository.findById(id).get();
		} catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void insertAnalysisTask2(AnalysisTask2 analysisTask2) {
		analysisTask2Repository.save(analysisTask2);
		
	}

	@Override
	public boolean deleteAnalysisTask2(int id) {
		try {
			analysisTask2Repository.delete(analysisTask2Repository.findById(id).get());
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public List<AnalysisTask2> findByLesson(Lesson lesson) {
		List<AnalysisTask2> rs = new ArrayList<AnalysisTask2>();
		for(AnalysisTask2 a : findAll()) {
			if(a.getWord().getLesson().getId() == lesson.getId())
				rs.add(a);
		}
		return rs;
	}

	@Override
	public AnalysisTask2 findByWord(Word word) {
		for(AnalysisTask2 a : findAll()) {
			if(a.getWord().getId() == word.getId())
				return a;
		}
		return null;
	}

	@Override
	public boolean updateAnalysisTask2(AnalysisTask2 at2) {
		AnalysisTask2 at2Update = findOne(at2.getId());
		if(at2Update == null)
			return false;
		at2Update.setWord(at2.getWord());
		at2Update.setTotalTimes(at2.getTotalTimes());
		at2Update.setWrongTimes(at2.getWrongTimes());
		analysisTask2Repository.save(at2Update);
		return true;
	}
}
