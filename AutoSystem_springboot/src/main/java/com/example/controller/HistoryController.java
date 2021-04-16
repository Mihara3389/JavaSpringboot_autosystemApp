package com.example.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.entity.HistoryEntity;
import com.example.entity.UserEntity;
import com.example.service.HistoryService;

	/**
	 * 採点結果得点履歴 Controller
	 */
	@Controller
	public class HistoryController {
	/**
	 * 採点結果得点履歴 Service
	 */
	@Autowired
	private HistoryService historyService;
	/**
	 * 採点結果得点履歴を表示
	 * @param model Model
	 * @return 採点結果得点履歴
	 */
	@RequestMapping(value="/top", method=RequestMethod.POST,params="action=history")
	public String postHistorylist(Model model) {
		//SpringSecurityに格納されているログイン中ユーザのIDを取得
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int loginId = ((UserEntity)principal).getId();
		//採点結果履歴を取得
		List<HistoryEntity> histories = historyService.searchAll();
		//採点結果履歴画面へ値を渡すためのリスト
		List<Object> historylist = new ArrayList<Object>();
		//同じユーザIDの履歴すべて取得するまでループ
		for(int i = 0; i < histories.size(); i++) {
			//リスト内のデータを取得
			int historyEntityId = histories.get(i).getUserid();
			int historyEntityPoint = histories.get(i).getPoint();
			Timestamp historyEntityCreatedat = histories.get(i).getCreatedAt();
			//ログイン中のidと採点結果履歴DB内のuser_idと照合
			if(loginId == historyEntityId) {
				//一致するデータがある場合は、得点と登録日を取得
				historylist.add(historyEntityPoint);
				historylist.add(historyEntityCreatedat);
			    model.addAttribute("historylist", histories);
			}
		}
		//一致する採点結果履歴がなかったらデータなしで画面へ
		if(historylist.isEmpty()){
			model.addAttribute("historylist", historylist);
		}
	   return "history";
	}
}