package eng.spr.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="synonym")
public class Synonym implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public Synonym() {
		super();
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	int id;
	@Column(name="synony_name",unique=true)
	private String synonymName;
	private String mean;
	@ManyToOne
	@JoinColumn(name="wordId")
	private Word word;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSynonymName() {
		return synonymName;
	}
	public void setSynonymName(String synonymName) {
		this.synonymName = synonymName;
	}
	public String getMean() {
		return mean;
	}
	public void setMean(String mean) {
		this.mean = mean;
	}
	public Word getWord() {
		return word;
	}
	public void setWord(Word word) {
		this.word = word;
	}
	public Synonym(int id, String synonymName, String mean, Word word) {
		super();
		this.id = id;
		this.synonymName = synonymName;
		this.mean = mean;
		this.word = word;
	}
	
}
