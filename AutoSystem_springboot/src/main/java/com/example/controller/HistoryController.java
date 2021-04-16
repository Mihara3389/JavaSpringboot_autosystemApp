package com.example.controller;



	import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.entity.HistoryEntity;
import com.example.repository.HistoryRepository;

	/**
	 * 採点結果得点履歴 Controller
	 */
	@Controller
	public class HistoryController {

	/**
	  * 採点結果得点履歴 Service
	  */
	@Autowired
	private HistoryRepository historyRepository;
	  /**
	   * 採点結果得点履歴を表示
	   * @param model Model
	   * @return 採点結果得点履歴
	   */
	  
	@RequestMapping(value="/top", method=RequestMethod.POST,params="action=history")
	public String postHistorylist(Integer Id, Model model) {

	        List<HistoryEntity> historylist =historyRepository.find(Id);
	        model.addAttribute("historylist", historylist);

	        return "history";
	  }
	}