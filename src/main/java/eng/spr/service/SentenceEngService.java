package eng.spr.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;

import eng.spr.model.Lesson;
import eng.spr.model.SentenceEng;
import eng.spr.model.SentenceVi;
import eng.spr.model.Word;

public interface SentenceEngService {
	public Iterable<SentenceEng> findAll();
	public SentenceEng findOne(int id);
	public void insertSentenceEng(SentenceEng sentenceEng) throws DataIntegrityViolationException;
	public boolean deleteSentenceEng(int id);
	public List<SentenceEng> findAllByWord(Word word);
	public List<SentenceEng> findAllByLesson(Lesson lesson);
	public Map<SentenceVi,SentenceEng> mapSentence(Lesson lesson);
}
