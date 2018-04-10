package eng.spr.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eng.spr.model.Lesson;
import eng.spr.model.Word;
import eng.spr.repository.WordRepository;

@Service
@Transactional
public class WordServiceImpl implements WordService{

	@Autowired
	WordRepository wordRepository;
	@Override
	public Iterable<Word> findAll() {
		return wordRepository.findAll();
	}

	@Override
	public Word findOne(int id) {
		try {
			return wordRepository.findById(id).get();
		} catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void insertWord(Word word) throws DataIntegrityViolationException{
		wordRepository.save(word);
		
	}

	@Override
	public boolean deleteWord(int id) {
		try {
			wordRepository.delete(wordRepository.findById(id).get());
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public List<Word> findAllByLessonHasSynonym(Lesson lesson) {
		List<Word> rs = new ArrayList<Word>();
		for(Word w : findAll()) {
			if(w.getLesson().getId() == lesson.getId() && !w.getListSynonym().isEmpty())
				rs.add(w);
		}
		return rs;
	}

}
