package eng.spr.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import eng.spr.model.AnalysisTask1;
import eng.spr.model.AnalysisTask2;
import eng.spr.model.Lesson;
import eng.spr.model.SentenceEng;
import eng.spr.model.SentenceVi;
import eng.spr.model.Synonym;
import eng.spr.model.Word;
import eng.spr.modelform.AnswerTask2;
import eng.spr.modelform.WordForm;
import eng.spr.service.AnalysisTask1Service;
import eng.spr.service.AnalysisTask2Service;
import eng.spr.service.LessonService;
import eng.spr.service.SentenceEngService;
import eng.spr.service.SentenceViService;
import eng.spr.service.SynonymService;
import eng.spr.service.WordService;
/*
 * Author: Tran Nguyen Thanh Nhu Y - YZENNY97
 * Date: 07/10/2018 14:18
 * Email: nhuytnt1608@gmail.com
 * University: HCMUTE
 * */
@Controller
public class MainController {
	@Autowired
	private LessonService lessonService;
	@Autowired
	private WordService wordService;
	@Autowired
	private AnalysisTask1Service analysisTask1Service;
	@Autowired
	private AnalysisTask2Service analysisTask2Service;
	@Autowired
	private SentenceEngService sentenceEngService;
	@Autowired
	private SentenceViService sentenceViService;
	@Autowired
	private SynonymService synonymService;

	@Value("${message.invalidFormMessage}")
	private String invalidFormMessage;
	@Value("${message.deleteErr}")
	private String deleteErrMessage;
	@Value("${message.dupSentenceMess}")
	private String dupSentenceMess;
	@Value("${message.dupSynonymMess}")
	private String dupSynonymMess;

	private ArrayList<SentenceVi> listSentenceVi;

	/* Dynamic Field in Form  */
	// ADD AND REMOVE SENTENCE
	@RequestMapping(value = "/lesson/{id}/{order}", params = { "addRow" }, method = RequestMethod.POST)
	public String addRow(final WordForm wordForm, final BindingResult bindingResult, Model m,
			final HttpServletRequest req) {
		m.addAttribute("modify", "true");
		wordForm.getSentenceEngs().add(new SentenceEng());
		wordForm.getSentenceVis().add(new SentenceVi());
		return "main";
	}

	@RequestMapping(value = "/lesson/{id}/{order}", params = { "removeRow" }, method = RequestMethod.POST)
	public String removeRow(final WordForm wordForm, final BindingResult bindingResult, final HttpServletRequest req,
			Model m) {
		Integer index = Integer.valueOf(req.getParameter("removeRow"));
		wordForm.getSentenceEngs().remove(index.intValue());
		wordForm.getSentenceVis().remove(index.intValue());
		m.addAttribute("modify", "true");
		return "main";
	}
	
	//ADD AND REMOVE WORD FAMILY
	@RequestMapping(value = "/lesson/{id}/{order}", params = { "addWF" }, method = RequestMethod.POST)
	public String addWF(final WordForm wordForm, final BindingResult bindingResult, Model m,
			final HttpServletRequest req) {
		m.addAttribute("modify", "true");
		wordForm.getSynonyms().add(new Synonym());
		return "main";
	}

	@RequestMapping(value = "/lesson/{id}/{order}", params = { "removeWF" }, method = RequestMethod.POST)
	public String removeWF(final WordForm wordForm, final BindingResult bindingResult, final HttpServletRequest req,
			Model m) {
		Integer index = Integer.valueOf(req.getParameter("removeWF"));
		wordForm.getSynonyms().remove(index.intValue());
		m.addAttribute("modify", "true");
		return "main";
	}
	/* End */

	@RequestMapping(value = { "/", "/addWord", "/addLesson", "/index" }, method = RequestMethod.GET)
	public String home(@ModelAttribute("invalidForm") String invalidForm,
			@ModelAttribute("dup_synonym") String dupSynonym, @ModelAttribute("dup_sentence") String dupSentence,
			@ModelAttribute("deleteErr") String deleteErr, Model m, HttpSession session) {
		session.invalidate();
		m.addAttribute("lessons", lessonService.findAll());
		m.addAttribute("lessonService", lessonService);
		if (invalidForm.equals("true"))
			m.addAttribute("invalidFormMessage", invalidFormMessage);
		if (dupSynonym.equals("true"))
			m.addAttribute("invalidFormMessage", dupSynonymMess);
		if (dupSentence.equals("true"))
			m.addAttribute("invalidFormMessage", dupSentenceMess);
		if (deleteErr.equals("true"))
			m.addAttribute("deleteErrMessage", deleteErrMessage);
		m.addAttribute("lessonForm", new Lesson());
		return "index";
	}

	@RequestMapping(value = "/addLesson", method = RequestMethod.POST)
	public String saveNewLesson(@Valid @ModelAttribute("lessonForm") Lesson lessonForm, BindingResult br,
			RedirectAttributes ra) {
		if (br.hasErrors() || lessonForm.getName().trim().isEmpty() || lessonForm.getMean().trim().isEmpty()) {
			ra.addFlashAttribute("invalidForm", true);
		} else {
			Lesson lesson = new Lesson();
			lesson.setMean(lessonForm.getMean());
			lesson.setName(lessonForm.getName());
			try {
				lessonService.insertLesson(lesson);
			} catch (DataIntegrityViolationException e) {
				ra.addFlashAttribute("invalidForm", true);
			}
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/lesson/delete/{id}", method = RequestMethod.GET)
	public ModelAndView deleteLesson(@PathVariable("id") String strId, RedirectAttributes ra) {
		int id;
		try {
			id = Integer.parseInt(strId);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/");
		}
		if (!lessonService.deleteLesson(id))
			ra.addFlashAttribute("deleteErr", true);

		return new ModelAndView("forward:/");
	}

	@RequestMapping(value = "/lesson/{id}/{order}", method = RequestMethod.GET)
	public String performLesson(@ModelAttribute("invalidForm") String invalidForm,
			@PathVariable("order") String strOrder, @PathVariable("id") String strId, Model m, HttpSession session) {
		int id, order;
		m.addAttribute("modify", "");
		try {
			id = Integer.parseInt(strId);
			order = Integer.parseInt(strOrder);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return "redirect:/";
		}
		if (invalidForm.equals("true"))
			m.addAttribute("invalidFormMessage", invalidFormMessage);
		Lesson lesson = lessonService.findOne(id);
		session.setAttribute("lesson", lesson);
		session.setAttribute("order", order);
		session.removeAttribute("offsetT1");
		session.removeAttribute("complete");
		if (listSentenceVi != null)
			listSentenceVi = null;
		m.addAttribute("wordForm", new WordForm());
		return "main";
	}

	//

	@RequestMapping(value = "/lesson/{id}/{order}", params = { "save" }, method = RequestMethod.POST)
	public String saveNewWord(@Valid final WordForm wordForm, BindingResult br, HttpSession session,
			RedirectAttributes ra) {
		if (listSentenceVi != null)
			listSentenceVi = null;
		Lesson lesson = (Lesson) session.getAttribute("lesson");
		String order = session.getAttribute("order").toString();
		String redirect = "redirect:/lesson/" + lesson.getId() + "/" + order;
		if (br.hasErrors() || wordForm.getName().trim().isEmpty()) {
			ra.addFlashAttribute("invalidForm", true);
		} else {
			Word word = new Word();
			word.setName(wordForm.getName());
			word.setLesson(lesson);
			try {
				wordService.insertWord(word);
				AnalysisTask2 at2 = new AnalysisTask2();
				at2.setWord(word);
				analysisTask2Service.insertAnalysisTask2(at2);
			} catch (DataIntegrityViolationException e) {
				ra.addFlashAttribute("invalidForm", true);
				return redirect;
			}
			if (wordForm.getSynonyms() != null) {
				for (Synonym s : wordForm.getSynonyms()) {
					Synonym synonym = new Synonym();
					synonym.setSynonymName(s.getSynonymName());
					synonym.setWord(word);
					try {
						synonymService.insertSynonym(synonym);
					} catch (DataIntegrityViolationException e) {
						ra.addFlashAttribute("dup_synonym", true);
					}
				}
			}
			if (wordForm.getSentenceEngs() != null && wordForm.getSentenceVis() != null)
				for (int i = 0; i < wordForm.getSentenceEngs().size(); i++) {
					if (wordForm.getSentenceEngs().get(i) == null || wordForm.getSentenceVis().get(i) == null
							|| wordForm.getSentenceVis().get(i).getSentence().trim().isEmpty()
							|| wordForm.getSentenceEngs().get(i).getSentence().trim().isEmpty())
						continue;
					SentenceEng eng = wordForm.getSentenceEngs().get(i);
					SentenceVi vi = wordForm.getSentenceVis().get(i);
					SentenceEng sentenceEng = new SentenceEng();
					SentenceVi sentenceVi = new SentenceVi();
					sentenceEng.setSentence(formatSentence(eng.getSentence()));
					sentenceEng.setWord(word);
					sentenceVi.setSentence(vi.getSentence());
					sentenceVi.setSentenceEng(sentenceEng);
					sentenceEng.setSentenceVi(sentenceVi);
					try {
						sentenceEngService.insertSentenceEng(sentenceEng);
						sentenceViService.insertSentenceVi(sentenceVi);
						AnalysisTask1 at1 = new AnalysisTask1();
						at1.setSentenceEng(sentenceEng);
						analysisTask1Service.insertAnalysisTask1(at1);
					} catch (DataIntegrityViolationException e) {
						ra.addFlashAttribute("dup_sentence", true);
					}
				}
		}
		return redirect;
	}

	/* DO TASK 1 */

	@RequestMapping(value = "/do/lesson/{order}/task1", method = RequestMethod.GET)
	public String doTask1(@PathVariable("order") String order, HttpSession session, Model m, RedirectAttributes ra) {
		if (session.getAttribute("lesson") == null)
			return "redirect:/";
		Lesson lesson = (Lesson) session.getAttribute("lesson");
		if (sentenceEngService.mapSentence(lesson) != null && sentenceEngService.mapSentence(lesson).size() > 0) {
			int offset = 0;
			if (session.getAttribute("offsetT1") == null)
				session.setAttribute("offsetT1", 0);
			else {
				offset = Integer.valueOf(session.getAttribute("offsetT1").toString()).intValue();
			}

			if (listSentenceVi == null)
				{
				listSentenceVi = new ArrayList<SentenceVi>(sentenceEngService.mapSentence(lesson).keySet());
				Collections.shuffle(listSentenceVi);
				}
			m.addAttribute("total", listSentenceVi.size());
			if (offset >= listSentenceVi.size()) {
				offset = -1;
				int score = lesson.getScore();
				lesson.setScore(score + (listSentenceVi.size() * 100));
				lessonService.updateLesson(lesson);
				session.setAttribute("lesson", lesson);
				session.setAttribute("offsetT1", offset);
			} else if (offset != -1) {
				String sentenceVi = listSentenceVi.get(offset).getSentence();
				m.addAttribute("sentenceVi", sentenceVi);
				m.addAttribute("answerCorrect", sentenceEngService.mapSentence(lesson).get(sentenceViService.findBySentence(sentenceVi)).getSentence().split("```")[0]);
			}
			if (offset == -1)
				m.addAttribute("finish", true);
		} else {
			String s = "No sentence in this lesson! <a href='/lesson/" + lesson.getId() + "/"
					+ session.getAttribute("order") + "'><b>Go back</b></a> and create by yourself!";
			m.addAttribute("deleteErrMessage", s);
		}
		return "task1";
	}

	// submit answer
	@RequestMapping(value = "/do/lesson/{order}/task1", method = RequestMethod.POST)
	public String submitAnswer(@PathVariable("order") String order, @RequestParam("answer") String answer,
			@RequestParam("question") String question, HttpSession session, Model m, RedirectAttributes ra) {
		if (session.getAttribute("lesson") == null || session.getAttribute("offsetT1") == null)
			return "redirect:/";
		answer = answer.replaceAll("\\s+", " ");
		question = question.replaceAll("\\s+", " ");
		String redirect = "redirect:/do/lesson/" + session.getAttribute("order") + "/task1";
		Lesson lesson = (Lesson) session.getAttribute("lesson");
		SentenceVi sentenceVi = sentenceViService.findBySentence(question);
		if (sentenceVi == null) {
			ra.addFlashAttribute("deleteErrMessage", deleteErrMessage);
			return redirect;
		}
		SentenceEng sentenceEng = sentenceEngService.mapSentence(lesson).get(sentenceVi);
		if (sentenceEng == null) {
			ra.addFlashAttribute("deleteErrMessage", deleteErrMessage);
			return redirect;
		}
		String answerCheck = answer.replaceAll("[^\\w\\s]","");
		answerCheck = answerCheck.trim().toLowerCase();
		answerCheck = answerCheck.replaceAll("\\s+", " ");
		String[] arrAnswerCorrect = sentenceEng.getSentence().split("```");
		for(String answerCorrect : arrAnswerCorrect) {
			if(answerCorrect == null || answerCorrect.isEmpty())
				continue;
			answerCorrect = answerCorrect.replaceAll("[^\\w\\s]","");
			answerCorrect = answerCorrect.trim().toLowerCase();
			AnalysisTask1 at1 = analysisTask1Service.findBySentence(sentenceEng);
			at1.setTotalTimes(at1.getTotalTimes()+1);
			analysisTask1Service.updateAnalysisTask1(at1);
			if (answerCorrect.equals(answerCheck)) {
				int offset = Integer.valueOf(session.getAttribute("offsetT1").toString()).intValue();
				session.setAttribute("offsetT1", ++offset);
				ra.addFlashAttribute("progress", true);
				at1.setWrongTimes(at1.getWrongTimes()+1);
				analysisTask1Service.updateAnalysisTask1(at1);
				return redirect;
			}
		}
		ra.addFlashAttribute("answer", answer);
		return redirect;
	}
	/*END TASK 1 */
	
	/*BEGIN TASK 2 */
	
	@RequestMapping(value = "/do/lesson/{order}/task2", method = RequestMethod.GET)
	public String doTask2(@PathVariable("order") String order, HttpSession session, Model m) {
		if (session.getAttribute("lesson") == null)
			return "redirect:/";
		Lesson lesson = (Lesson) session.getAttribute("lesson");
		List<Word> words = wordService.findAllByLessonHasSynonym(lesson);
		m.addAttribute("words",words);
		AnswerTask2 answerTask2 =new AnswerTask2(words.size());
		AnswerTask2.arrCorrectAnswer = null;
		AnswerTask2.arrCorrectAnswer = new String[words.size()];
		for(int i=0;i<words.size();i++) {
			AnswerTask2.arrCorrectAnswer[i] = "";
			for(Synonym s : words.get(i).getListSynonym()) {
				if(s == null || s.getSynonymName().isEmpty()) continue;
				String answerCr = s.getSynonymName().trim().toLowerCase();
				answerCr = answerCr.replaceAll("\\s+", " ");
				AnswerTask2.arrCorrectAnswer[i] += answerCr + "; ";
			}
		}
		m.addAttribute("answerTask2",answerTask2);
		return "task2";
	}

	// submit answer
	@RequestMapping(value = "/do/lesson/{order}/task2", method = RequestMethod.POST)
	public String submitTask2(@PathVariable("order") String order,@Valid final AnswerTask2 answerTask2, BindingResult br, 
			HttpSession session, Model m, RedirectAttributes ra) {
		Lesson lesson =(Lesson)session.getAttribute("lesson"); 
		if (lesson == null)
			return "redirect:/";
		for(AnalysisTask2 at2 : analysisTask2Service.findByLesson(lesson)) {
			if(session.getAttribute(at2.getWord().getName()) == null) {
				at2.setTotalTimes(at2.getTotalTimes() + 1);
				analysisTask2Service.updateAnalysisTask2(at2);
			}
		}
		String redirect = "redirect:/do/lesson/"+session.getAttribute("order")+"/task2";
		Map<Integer,String> mapResult = new HashMap<Integer,String>();
		for(int i = 0;i<answerTask2.getArrAnswer().length;i++) {
			String answer = answerTask2.getArrAnswer()[i];
			answer = answer.trim().toLowerCase();
			answer = answer.replaceAll("\\s+", " ");
			String correctAnswer = AnswerTask2.arrCorrectAnswer[i];
			correctAnswer = correctAnswer.replaceAll("\\s+", " ");
			String[] answerSplit = answer.split(";");
			String f = "true";
			int count =0;
			for(String a : answerSplit) {
				if(correctAnswer.indexOf(a+";") == - 1)
					f = "false";
				else count++;
			}
			if(f.equals("true")) {
				if(count < correctAnswer.split(";").length - 1) f = "warning";
			}
			Word word = wordService.findAllByLessonHasSynonym(lesson).get(i);
			if(f != "true") {
				AnalysisTask2 at2 = analysisTask2Service.findByWord(word);
				at2.setWrongTimes(at2.getWrongTimes() +1);
				analysisTask2Service.updateAnalysisTask2(at2);
			} else {
				session.setAttribute(word.getName(), true);
			}
				
				
			mapResult.put(i+1,f);
		}
		ra.addFlashAttribute("mapResult",mapResult);
		ra.addFlashAttribute("arrAnswerFlash", answerTask2.getArrAnswer());
		if(session.getAttribute("complete") != null)
			session.removeAttribute("complete");
		
		if(!mapResult.containsValue("false") && !mapResult.containsValue("warning")) {
			session.setAttribute("complete", "true");
			lesson.setScore(lesson.getScore() + answerTask2.getArrAnswer().length*100);
			lessonService.updateLesson(lesson);
			
		}
		return redirect;
	}
	
	/*END TASK 2*/
	/*BEGIN ANALYSIS */
	
	@RequestMapping(value="lesson/{order}/analysis", method=RequestMethod.GET)
	public String analysis(@PathVariable("order") String order, HttpSession session, Model m) {
		if (session.getAttribute("lesson") == null)
			return "redirect:/";
		Lesson lesson = (Lesson)session.getAttribute("lesson");
		List<AnalysisTask1> listAT1ByLesson = analysisTask1Service.findByLesson(lesson);
		List<AnalysisTask2> listAT2ByLesson = analysisTask2Service.findByLesson(lesson);
		int totalTimesAT1 = 0;
		int wrongTimesAT1 = 0;
		int totalTimesAT2 = 0;
		int wrongTimesAT2 = 0;
		for(AnalysisTask1 at1 : listAT1ByLesson) {
			totalTimesAT1 += at1.getTotalTimes();
			wrongTimesAT1 += at1.getWrongTimes();
		}

		for(AnalysisTask2 at2 : listAT2ByLesson) {
			totalTimesAT2 += at2.getTotalTimes();
			wrongTimesAT2 += at2.getWrongTimes();
		}
		m.addAttribute("totalTimesAT1", totalTimesAT1);
		m.addAttribute("wrongTimesAT1", wrongTimesAT1);
		m.addAttribute("totalTimesAT2", totalTimesAT2);
		m.addAttribute("wrongTimesAT2", wrongTimesAT2);
		
		m.addAttribute("listAT2ByLesson", listAT2ByLesson);
		m.addAttribute("listAT1ByLesson", listAT1ByLesson);
		return "analysis";
	}
	
	/*END ANALYSIS */
	//
	private String formatSentence(String str) {
		str = str.trim().toLowerCase();
		String[] arrWords = { " am ", " is ", " are ", " will ", " shall ", " not ", " would ", " has ", " have ",
				" had ", " will not" };
		int[][] arrOne = { { 0 }, { 1 } };
		int[][] arrTwo = { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } };
		int[][] arrThree = { { 0, 0, 0 }, { 0, 0, 1 }, { 0, 1, 0 }, { 0, 1, 1 }, { 1, 0, 0 }, { 1, 0, 1 }, { 1, 1, 0 },
				{ 1, 1, 1 } };
		int[][] arrFour = { { 0, 0, 0, 0 }, { 0, 0, 0, 1 }, { 0, 0, 1, 0 }, { 0, 0, 1, 1 }, { 0, 1, 0, 0 },
				{ 0, 1, 0, 1 }, { 0, 1, 1, 0 }, { 0, 1, 1, 1 }, { 1, 0, 0, 0 }, { 1, 0, 0, 1 }, { 1, 0, 1, 0 },
				{ 1, 0, 1, 1 }, { 1, 1, 0, 0 }, { 1, 1, 0, 1 }, { 1, 1, 1, 0 }, { 1, 1, 1, 1 } };
		List<String> listWordsInSentence = new ArrayList<String>();
		for (String w : arrWords) {
			if (str.indexOf(w) != -1)
				listWordsInSentence.add(w);
		}
		String rs = "";
		switch (listWordsInSentence.size()) {
		case 1:
			rs = Cal(arrOne, listWordsInSentence, str,2);
			break;
		case 2:
			rs = Cal(arrTwo, listWordsInSentence, str,4);
			break;
		case 3:
			rs = Cal(arrThree, listWordsInSentence, str,8);
			break;
		case 4:
			rs = Cal(arrFour, listWordsInSentence, str,16);
			break;
		default:
			rs = str;
			break;
		}
		if (rs.trim().isEmpty())
			rs = str;
		return rs;
	}

	private String Cal(int[][] arr, List<String> listWords, String str,int length) {
		String rs = "";
		for (int i = 0; i < length; i++) {
			String elementStr = str;
			for (int j = 0; j < listWords.size(); j++) {
				if (arr[i][j] == 1) {
					String word = listWords.get(j);
					String newWord = changeWord(word);
					elementStr = elementStr.replaceAll(word, newWord);
				} else
					continue;
			}
			rs += elementStr + "```";
		}

		return rs;
	}

	private String changeWord(String word) {
		String newWord;
		switch (word) {
		case " am ":
			newWord = "'m ";
			break;
		case " is ":
			newWord = "'s ";
			break;
		case " are ":
			newWord = "'re ";
			break;
		case " will ":
			newWord = "'ll ";
			break;
		case " shall ":
			newWord = "'ll ";
			break;
		case " not ":
			newWord = "n't ";
			break;
		case " would ":
			newWord = "'d ";
			break;
		case " has ":
			newWord = "'s ";
			break;
		case " have ":
			newWord = "'ve ";
			break;
		case " had ":
			newWord = "'d ";
			break;
		case " will not":
			newWord = " won't";
			break;
		default:
			newWord = word;
			break;
		}
		return newWord;
	}
}
