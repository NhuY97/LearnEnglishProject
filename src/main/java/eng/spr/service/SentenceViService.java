package eng.spr.service;

import org.springframework.dao.DataIntegrityViolationException;

import eng.spr.model.SentenceVi;

public interface SentenceViService {
	public Iterable<SentenceVi> findAll();
	public SentenceVi findOne(int id);
	public SentenceVi findBySentence(String sentence);
	public void insertSentenceVi(SentenceVi sentenceVi) throws DataIntegrityViolationException;
	public boolean deleteSentenceVi(int id);
}
