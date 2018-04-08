package eng.spr.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
@Entity
@Table(name = "word")
public class Word implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(name="name",unique=true)
	private String name;
	@ManyToOne
	@JoinColumn(name="lessonId")
	private Lesson lesson;
	@OneToMany(mappedBy="word",cascade=CascadeType.ALL)
	private Set<SentenceEng> listSentenceEng;
	@OneToOne
	@PrimaryKeyJoinColumn
	private AnalysisTask2 listAnalysisTask2;
	@OneToMany(mappedBy="word",cascade=CascadeType.ALL)
	private Set<Synonym> listSynonym;
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
	public Lesson getLesson() {
		return lesson;
	}
	public void setLesson(Lesson lesson) {
		this.lesson = lesson;
	}
	public Set<SentenceEng> getListSentenceEng() {
		return listSentenceEng;
	}
	public void setListSentenceEng(Set<SentenceEng> listSentenceEng) {
		this.listSentenceEng = listSentenceEng;
	}
	public AnalysisTask2 getListAnalysisTask2() {
		return listAnalysisTask2;
	}
	public void setListAnalysisTask2(AnalysisTask2 listAnalysisTask2) {
		this.listAnalysisTask2 = listAnalysisTask2;
	}
	public Set<Synonym> getListSynonym() {
		return listSynonym;
	}
	public void setListSynonym(Set<Synonym> listSynonym) {
		this.listSynonym = listSynonym;
	}
	public Word(int id, String name, Lesson lesson, Set<SentenceEng> listSentenceEng,
			AnalysisTask2 listAnalysisTask2, Set<Synonym> listSynonym) {
		super();
		this.id = id;
		this.name = name;
		this.lesson = lesson;
		this.listSentenceEng = listSentenceEng;
		this.listAnalysisTask2 = listAnalysisTask2;
		this.listSynonym = listSynonym;
	}
	public Word() {
		super();
	}
	
	
}
