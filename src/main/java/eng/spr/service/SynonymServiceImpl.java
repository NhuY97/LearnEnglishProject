package eng.spr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eng.spr.model.Synonym;
import eng.spr.repository.SynonymRepository;

@Service
@Transactional
public class SynonymServiceImpl implements SynonymService {
	@Autowired
	SynonymRepository synonymRepository;
	@Override
	public Iterable<Synonym> findAll() {
		return synonymRepository.findAll();
	}

	@Override
	public Synonym findOne(int id) {
		try {
			return synonymRepository.findById(id).get();
		} catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void insertSynonym(Synonym synonym) throws DataIntegrityViolationException{
		synonymRepository.save(synonym);
		
	}

	@Override
	public boolean deleteSynonym(int id) {
		try {
			synonymRepository.delete(synonymRepository.findById(id).get());
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
