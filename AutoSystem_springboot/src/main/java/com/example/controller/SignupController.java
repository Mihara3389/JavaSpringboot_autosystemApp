package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.dto.SignupRequest;
import com.example.service.SignupService;

/**
 * ユーザー情報 Controller
 */
@Controller
public class SignupController {

  /**
   * ユーザー情報 Service
   */
  @Autowired
  SignupService userService;

  /**
   * ユーザー新規登録画面を表示
   * @param model Model
   * @return ユーザー情報一覧画面
   */
   @GetMapping("/signup")
   public String getSignUp(Model model) {

	   model.addAttribute("userRequest", new SignupRequest());

       // signup.htmlに画面遷移
       return "signup";
   } 
   
   
   @PostMapping(params="id=login_signup")
   public String postSignUp(Model model) {

       // login.htmlにリダイレクト
       return "redirect:/login";
   }

  /**
   * ユーザー新規登録
   * @param userRequest リクエストデータ
   * @param model Model
   * @return ユーザー情報一覧画面
   */
  @RequestMapping(value = "/signup", method = RequestMethod.POST)
  public String create(@Validated @ModelAttribute SignupRequest userRequest, BindingResult result, Model model) {
    if (result.hasErrors()) {
      List<String> errorList = new ArrayList<String>();
      for (ObjectError error : result.getAllErrors()) {
        errorList.add(error.getDefaultMessage());
      }

      model.addAttribute("validationError", errorList);
      return "signup";
    }else {
    	
    	
    }

    // ユーザー情報の登録
    userService.create(userRequest);
    return "redirect:/login";
  }
}