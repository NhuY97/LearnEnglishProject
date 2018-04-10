package eng.spr.service;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;

import eng.spr.model.Lesson;
import eng.spr.model.Word;

public interface WordService {
	public Iterable<Word> findAll();
	public List<Word> findAllByLessonHasSynonym(Lesson lesson);
	public Word findOne(int id);
	public void insertWord(Word word) throws DataIntegrityViolationException;
	public boolean deleteWord(int id);
}
