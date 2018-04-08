package eng.spr.controller;


import java.util.ArrayList;

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

import eng.spr.model.Lesson;
import eng.spr.model.SentenceEng;
import eng.spr.model.SentenceVi;
import eng.spr.model.Synonym;
import eng.spr.model.Word;
import eng.spr.modelform.WordForm;
import eng.spr.service.AnalysisTask1Service;
import eng.spr.service.AnalysisTask2Service;
import eng.spr.service.LessonService;
import eng.spr.service.SentenceEngService;
import eng.spr.service.SentenceViService;
import eng.spr.service.SynonymService;
import eng.spr.service.WordService;

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

	
	/* Dynamic Field in Form */
	@RequestMapping(value="/lesson/{id}/{order}", params={"addRow"},method=RequestMethod.POST)
	public String addRow(final WordForm wordForm, final BindingResult bindingResult,Model m,final HttpServletRequest req) {
	    m.addAttribute("modify", "true");
	    wordForm.getSentenceEngs().add(new SentenceEng());
	    wordForm.getSentenceVis().add(new SentenceVi());
	    return "main";
	}

	@RequestMapping(value="/lesson/{id}/{order}", params={"removeRow"},method=RequestMethod.POST)
	public String removeRow(
			final WordForm wordForm, final BindingResult bindingResult, 
	        final HttpServletRequest req,Model m) {
		Integer index = Integer.valueOf(req.getParameter("removeRow"));
		wordForm.getSentenceEngs().remove(index.intValue());
		wordForm.getSentenceVis().remove(index.intValue());
	    m.addAttribute("modify", "true");
	    return "main";
	}
	/* End */
	
	@RequestMapping(value = { "/", "/addWord", "/addLesson", "/index" }, method = RequestMethod.GET)
	public String home(@ModelAttribute("invalidForm") String invalidForm,
			@ModelAttribute("dup_synonym") String dupSynonym,
			@ModelAttribute("dup_sentence") String dupSentence,
			@ModelAttribute("deleteErr") String deleteErr,
			Model m, HttpSession session) {
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
			} catch(DataIntegrityViolationException e) {
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
			@PathVariable("order") String strOrder, @PathVariable("id") String strId,
			Model m, HttpSession session) {
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
		if(listSentenceVi!=null) listSentenceVi = null;
		m.addAttribute("wordForm", new WordForm());
		return "main";
	}

	//

	@RequestMapping(value = "/lesson/{id}/{order}",params= {"save"}, method=RequestMethod.POST)
	public String saveNewWord(@Valid final WordForm wordForm, BindingResult br,
			HttpSession session, RedirectAttributes ra) {
		if(listSentenceVi!=null) listSentenceVi = null;
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
			} catch(DataIntegrityViolationException e) {
				ra.addFlashAttribute("invalidForm", true);
				return redirect;
			}
			if (wordForm.getSynonyms() != null) {
				for (Synonym s : wordForm.getSynonyms()) {
					Synonym synonym = new Synonym();
					synonym.setMean(s.getMean());
					synonym.setSynonymName(s.getSynonymName());
					synonym.setWord(word);
					try {
						synonymService.insertSynonym(synonym);
					} catch(DataIntegrityViolationException e) {
						ra.addFlashAttribute("dup_synonym", true);
					}
				}
			}
			if(wordForm.getSentenceEngs() != null && wordForm.getSentenceVis() != null)
				for(int i=0;i<wordForm.getSentenceEngs().size();i++) {
					if(wordForm.getSentenceEngs().get(i) == null || 
							wordForm.getSentenceVis().get(i) == null || 
							wordForm.getSentenceVis().get(i).getSentence().trim().isEmpty() ||
							wordForm.getSentenceEngs().get(i).getSentence().trim().isEmpty())
						continue;
					SentenceEng eng = wordForm.getSentenceEngs().get(i);
					SentenceVi vi = wordForm.getSentenceVis().get(i);
					SentenceEng sentenceEng = new SentenceEng();
					SentenceVi sentenceVi = new SentenceVi();
					sentenceEng.setSentence(eng.getSentence());
					sentenceEng.setWord(word);
					sentenceVi.setSentence(vi.getSentence());
					sentenceVi.setSentenceEng(sentenceEng);
					sentenceEng.setSentenceVi(sentenceVi);
					try {
						sentenceEngService.insertSentenceEng(sentenceEng);
						sentenceViService.insertSentenceVi(sentenceVi);
					} catch(DataIntegrityViolationException e) {
						ra.addFlashAttribute("dup_sentence", true);
					}
				}
		}
		return redirect;
	}
	
	/* DO TASK 1 */
	
	@RequestMapping(value="/do/lesson/{order}/task1", method=RequestMethod.GET)
	public String doTask1(@PathVariable("order") String order,HttpSession session,Model m,RedirectAttributes ra) {
		if(session.getAttribute("lesson") == null)
			return "redirect:/";
		Lesson lesson = (Lesson) session.getAttribute("lesson");
		if(sentenceEngService.mapSentence(lesson) != null && 
				sentenceEngService.mapSentence(lesson).size() > 0) {
			int offset = 0;
			if(session.getAttribute("offsetT1") == null)
				session.setAttribute("offsetT1", 0);
			else {
				offset = Integer.valueOf(session.getAttribute("offsetT1").toString()).intValue();
			}
			
			if(listSentenceVi == null)
			listSentenceVi = new ArrayList<SentenceVi>(sentenceEngService.mapSentence(lesson).keySet());
			m.addAttribute("total",listSentenceVi.size());
			if(offset>=listSentenceVi.size()) {
				offset = -1;
				int score = lesson.getScore();
				lesson.setScore(score + (listSentenceVi.size()*100));
				lessonService.updateLesson(lesson);
				session.setAttribute("lesson", lesson);
				session.setAttribute("offsetT1", offset);
			} else if(offset!=-1)
				m.addAttribute("sentenceVi", listSentenceVi.get(offset).getSentence());
			if(offset == -1)
				m.addAttribute("finish", true);
		}else {
			String s = "No sentence in this lesson! <a href='/lesson/"+lesson.getId()+"/"+session.getAttribute("order")+"'><b>Go back</b></a> and create by yourself!";
			m.addAttribute("deleteErrMessage", s);
		}
		return "task1";
	}
	
	//submit answer
	@RequestMapping(value="/do/lesson/{order}/task1", method=RequestMethod.POST)
	public String submitAnswer(@PathVariable("order") String order,
			@RequestParam("answer") String answer,@RequestParam("question") String question,
			HttpSession session,Model m,RedirectAttributes ra) {
		if(session.getAttribute("lesson") == null || session.getAttribute("offsetT1")==null)
			return "redirect:/";
		answer = answer.replaceAll("\\s+", " ");
		question = question.replaceAll("\\s+", " ");
		String redirect = "redirect:/do/lesson/"+session.getAttribute("order")+"/task1";
		Lesson lesson = (Lesson) session.getAttribute("lesson");
		SentenceVi sentenceVi = sentenceViService.findBySentence(question);
		if(sentenceVi == null) {
			ra.addAttribute("deleteErrMessage", deleteErrMessage);
			return redirect;
		}
		SentenceEng sentenceEng = sentenceEngService.mapSentence(lesson).get(sentenceVi);
		if(sentenceEng == null) {
			ra.addAttribute("deleteErrMessage", deleteErrMessage);
			return redirect;
		}
		if(sentenceEng.getSentence().trim().toLowerCase().equals(answer.trim().toLowerCase()))
		{
			int offset = Integer.valueOf(session.getAttribute("offsetT1").toString()).intValue();
			session.setAttribute("offsetT1", ++offset);
			ra.addFlashAttribute("progress", true);
		} else {
			ra.addFlashAttribute("answer", answer);
			return redirect;
		}
		
		return redirect;
	}
	
}
