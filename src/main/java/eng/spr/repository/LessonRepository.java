package eng.spr.repository;

import org.springframework.data.repository.CrudRepository;

import eng.spr.model.Lesson;

public interface LessonRepository extends CrudRepository<Lesson,Integer> {

}
