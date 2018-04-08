package eng.spr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eng.spr.model.SentenceVi;
import eng.spr.repository.SentenceViRepository;
@Service
@Transactional
public class SentenceViServiceImpl implements SentenceViService {
	@Autowired
	SentenceViRepository sentenceViRepository;
	@Override
	public Iterable<SentenceVi> findAll() {
		return sentenceViRepository.findAll();
	}

	@Override
	public SentenceVi findOne(int id) {
		try {
			return sentenceViRepository.findById(id).get();
		} catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void insertSentenceVi(SentenceVi sentenceVi) throws DataIntegrityViolationException{
		sentenceViRepository.save(sentenceVi);
		
	}

	@Override
	public boolean deleteSentenceVi(int id) {
		try {
			sentenceViRepository.delete(sentenceViRepository.findById(id).get());
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public SentenceVi findBySentence(String sentence) {
		for(SentenceVi v : findAll()) {
			if(v.getSentence().trim().toLowerCase().equals(sentence.trim().toLowerCase()))
				return v;
		}
		return null;
	}
}
