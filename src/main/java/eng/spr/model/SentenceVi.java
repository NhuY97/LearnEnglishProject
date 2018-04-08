package eng.spr.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
@Entity
@Table(name = "sentencevi")
public class SentenceVi implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(name="sentence",unique=true)
	private String sentence;
	@OneToOne
	@PrimaryKeyJoinColumn
	private SentenceEng sentenceEng;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSentence() {
		return sentence;
	}
	public void setSentence(String sentence) {
		this.sentence = sentence;
	}
	public SentenceEng getSentenceEng() {
		return sentenceEng;
	}
	public void setSentenceEng(SentenceEng sentenceEng) {
		this.sentenceEng = sentenceEng;
	}
	public SentenceVi(int id, String sentence, SentenceEng sentenceEng) {
		super();
		this.id = id;
		this.sentence = sentence;
		this.sentenceEng = sentenceEng;
	}
	public SentenceVi() {
		super();
		// TODO Auto-generated constructor stub
	}

}
