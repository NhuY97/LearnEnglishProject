package eng.spr.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eng.spr.model.Lesson;
import eng.spr.model.SentenceEng;
import eng.spr.model.SentenceVi;
import eng.spr.model.Word;
import eng.spr.repository.SentenceEngRepository;

@Service
@Transactional
public class SentenceEngServiceImpl implements SentenceEngService{
	@Autowired
	SentenceEngRepository sentenceEngRepository;
	@Override
	public Iterable<SentenceEng> findAll() {
		return sentenceEngRepository.findAll();
	}

	@Override
	public SentenceEng findOne(int id) {
		try {
			return sentenceEngRepository.findById(id).get();
		} catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void insertSentenceEng(SentenceEng sentenceEng) throws DataIntegrityViolationException{
		sentenceEngRepository.save(sentenceEng);
		
	}

	@Override
	public boolean deleteSentenceEng(int id) {
		try {
			sentenceEngRepository.delete(sentenceEngRepository.findById(id).get());
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public List<SentenceEng> findAllByWord(Word word) {
		List<SentenceEng> rs = new ArrayList<SentenceEng>();
		for(SentenceEng e : findAll()) {
			if(e.getWord().getId()==word.getId())
				rs.add(e);
		}
		return rs;
	}

	@Override
	public List<SentenceEng> findAllByLesson(Lesson lesson) {
		List<SentenceEng> rs = new ArrayList<SentenceEng>();
		for(SentenceEng e : findAll()) {
			if(e.getWord().getLesson().getId() == lesson.getId())
				rs.add(e);
		}
		return rs;
	}
	/*For TASK 1*/
	@Override
	public Map<SentenceVi,SentenceEng> mapSentence(Lesson lesson){
		Map<SentenceVi,SentenceEng> mapResult = new HashMap<SentenceVi,SentenceEng>();
		for(SentenceEng sentenceEng : findAllByLesson(lesson)) {
			mapResult.put(sentenceEng.getSentenceVi(),sentenceEng);
		}
		return mapResult;
	}
}
