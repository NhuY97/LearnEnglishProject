package eng.spr.repository;

import org.springframework.data.repository.CrudRepository;

import eng.spr.model.Word;

public interface WordRepository extends CrudRepository<Word,Integer> {

}