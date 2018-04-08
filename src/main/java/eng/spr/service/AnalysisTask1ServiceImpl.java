package eng.spr.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eng.spr.model.AnalysisTask1;
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
}
