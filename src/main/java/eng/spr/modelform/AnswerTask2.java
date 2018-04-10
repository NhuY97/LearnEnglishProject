package eng.spr.modelform;

public class AnswerTask2 {
	private String[] arrAnswer;
	public static String[] arrCorrectAnswer;
	public String[] getArrAnswer() {
		return arrAnswer;
	}
	public void setArrAnswer(String[] arrAnswer) {
		this.arrAnswer = arrAnswer;
	}
	public AnswerTask2(int n) {
		arrAnswer = new String[n];
	}
	public AnswerTask2() {
		
	}
}
