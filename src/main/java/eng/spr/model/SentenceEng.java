package eng.spr.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
@Entity
@Table(name = "sentenceeng")
public class SentenceEng implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(name="sentence",unique=true)
	private String sentence;
	@ManyToOne
	@JoinColumn(name="wordId")
	private Word word;
	@OneToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name="sentenceViId")
	private SentenceVi sentenceVi;
	@OneToOne
	@PrimaryKeyJoinColumn
	private AnalysisTask1 analysisTask1;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSentence() {
		return sentence;
	}
	public String getMainSentence() {
		String s = sentence.split("```")[0];
		return s;
	}
	public void setSentence(String sentence) {
		this.sentence = sentence;
	}
	public Word getWord() {
		return word;
	}
	public void setWord(Word word) {
		this.word = word;
	}
	public SentenceVi getSentenceVi() {
		return sentenceVi;
	}
	public void setSentenceVi(SentenceVi sentenceVi) {
		this.sentenceVi = sentenceVi;
	}
	public AnalysisTask1 getAnalysisTask1() {
		return analysisTask1;
	}
	public void setAnalysisTask1(AnalysisTask1 analysisTask1) {
		this.analysisTask1 = analysisTask1;
	}
	public SentenceEng(int id, String sentence, Word word, SentenceVi sentenceVi, AnalysisTask1 analysisTask1) {
		super();
		this.id = id;
		this.sentence = sentence;
		this.word = word;
		this.sentenceVi = sentenceVi;
		this.analysisTask1 = analysisTask1;
	}
	public SentenceEng() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
