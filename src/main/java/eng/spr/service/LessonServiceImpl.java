package eng.spr.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eng.spr.model.Lesson;
import eng.spr.model.Word;
import eng.spr.repository.LessonRepository;

@Service
@Transactional
public class LessonServiceImpl implements LessonService{
	
	@Autowired
	private LessonRepository lessonRepository;
	@Override
	public Iterable<Lesson> findAll() {
		return lessonRepository.findAll();
	}

	@Override
	public void insertLesson(Lesson lesson) throws DataIntegrityViolationException{
		lessonRepository.save(lesson);
	}

	@Override
	public boolean deleteLesson(int id) {
		try {
			lessonRepository.delete(lessonRepository.findById(id).get());
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean updateLesson(Lesson lessonNew) {
		Optional<Lesson> optLessonOld = lessonRepository.findById(lessonNew.getId());
		if(optLessonOld == null || optLessonOld.get() == null)
			return false;
		Lesson lessonUpdate = optLessonOld.get();
		lessonUpdate.setMean(lessonNew.getMean());
		lessonUpdate.setName(lessonNew.getName());
		lessonUpdate.setWords(lessonNew.getWords());
		lessonUpdate.setScore(lessonNew.getScore());
		lessonRepository.save(lessonUpdate);
		return true;
		
	}

	@Override
	public Lesson findOne(int id) {
		try {
			return lessonRepository.findById(id).get();
		} catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String newWords(int id) {
		Lesson lesson;
		String result = "";
		if(lessonRepository.findById(id) == null || (lesson = lessonRepository.findById(id).get()) == null || lesson.getWords() == null || lesson.getWords().isEmpty())
			return "<span style='color:orange'>empty</span>";
		for(Word word : lesson.getWords()) {
			if(word != null && !word.getName().isEmpty())
				if(result == "") result += "<b>"+word.getName()+"</b>";
				else result += ", " + "<b>" +word.getName()+"</b>";
		}
			
		return result;
	}
}
