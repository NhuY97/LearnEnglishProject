package eng.spr.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
@Entity
@Table(name = "lesson")
public class Lesson implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(name="name",unique=true)
	private String name;
	private String mean;
	@Column(name="score")
	private int score = 0;
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	@OneToMany(mappedBy="lesson", cascade=CascadeType.ALL)
	private Set<Word> words;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMean() {
		return mean;
	}
	public void setMean(String mean) {
		this.mean = mean;
	}
	public Set<Word> getWords() {
		return words;
	}
	public void setWords(Set<Word> words) {
		this.words = words;
	}
	public Lesson() {
		super();
	}
	public Lesson(int id, String name, String mean,int score, Set<Word> words) {
		super();
		this.id = id;
		this.name = name;
		this.mean = mean;
		this.score = score;
		this.words = words;
	}
	

}
