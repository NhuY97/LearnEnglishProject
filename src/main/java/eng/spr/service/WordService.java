package eng.spr.service;

import org.springframework.dao.DataIntegrityViolationException;

import eng.spr.model.Word;

public interface WordService {
	public Iterable<Word> findAll();
	public Word findOne(int id);
	public void insertWord(Word word) throws DataIntegrityViolationException;
	public boolean deleteWord(int id);
}
