package eng.spr.service;

import org.springframework.dao.DataIntegrityViolationException;

import eng.spr.model.Synonym;

public interface SynonymService {
	public Iterable<Synonym> findAll();
	public Synonym findOne(int id);
	public void insertSynonym(Synonym synonym) throws DataIntegrityViolationException;
	public boolean deleteSynonym(int id);
}
