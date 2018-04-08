package eng.spr.service;




import org.springframework.dao.DataIntegrityViolationException;

import eng.spr.model.Lesson;

public interface LessonService {
	public Iterable<Lesson> findAll();
	public void insertLesson(Lesson lesson) throws DataIntegrityViolationException;
	public boolean deleteLesson(int id);
	public boolean updateLesson(Lesson lesson);
	public Lesson findOne(int id);
	public String newWords(int id);
}
