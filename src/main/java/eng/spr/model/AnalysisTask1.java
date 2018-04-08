package eng.spr.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
@Entity
@Table(name = "analysistask1")
public class AnalysisTask1 implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private int totalTimes=0;
	private int wrongTimes=0;
	@OneToOne
	@JoinColumn(name = "sentenceEngId")
	private SentenceEng sentenceEng;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTotalTimes() {
		return totalTimes;
	}
	public void setTotalTimes(int totalTimes) {
		this.totalTimes = totalTimes;
	}
	public int getWrongTimes() {
		return wrongTimes;
	}
	public void setWrongTimes(int wrongTimes) {
		this.wrongTimes = wrongTimes;
	}
	public SentenceEng getSentenceEng() {
		return sentenceEng;
	}
	public void setSentenceEng(SentenceEng sentenceEng) {
		this.sentenceEng = sentenceEng;
	}
	public AnalysisTask1(int id, int totalTimes, int wrongTimes, SentenceEng sentenceEng) {
		super();
		this.id = id;
		this.totalTimes = totalTimes;
		this.wrongTimes = wrongTimes;
		this.sentenceEng = sentenceEng;
	}
	public AnalysisTask1() {
	}
	
}
