package eng.spr.modelform;

import java.util.ArrayList;
import java.util.List;

import eng.spr.model.SentenceEng;
import eng.spr.model.SentenceVi;
import eng.spr.model.Synonym;

public class WordForm{
	private String name;
	private List<SentenceEng> SentenceEngs = new ArrayList<SentenceEng>();
	private List<SentenceVi> SentenceVis = new ArrayList<SentenceVi>();
	private List<Synonym> Synonyms = new ArrayList<Synonym>();
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<SentenceEng> getSentenceEngs() {
		return SentenceEngs;
	}
	public void setSentenceEngs(List<SentenceEng> sentenceEngs) {
		SentenceEngs = sentenceEngs;
	}
	public List<SentenceVi> getSentenceVis() {
		return SentenceVis;
	}
	public void setSentenceVis(List<SentenceVi> sentenceVis) {
		SentenceVis = sentenceVis;
	}
	public List<Synonym> getSynonyms() {
		return Synonyms;
	}
	public void setSynonyms(List<Synonym> synonyms) {
		Synonyms = synonyms;
	}
}
