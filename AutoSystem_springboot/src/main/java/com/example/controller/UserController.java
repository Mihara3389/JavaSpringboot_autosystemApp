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

import com.example.dto.UserRequest;
import com.example.entity.UserEntity;
import com.example.service.UserService;

/**
 * ユーザー情報 Controller
 */
@Controller
public class UserController {

  /**
   * ユーザー情報 Service
   */
  @Autowired
  UserService userService;

  /**
   * ユーザー情報一覧画面を表示
   * @param model Model
   * @return ユーザー情報一覧画面
   */
  @GetMapping(value = "/userlist")
  public String displayList(Model model) {
    List<UserEntity> userlist = userService.searchAll();
    model.addAttribute("userlist", userlist);
    return "userlist";
  }

  /**
   * ユーザー新規登録画面を表示
   * @param model Model
   * @return ユーザー情報一覧画面
   */
   @GetMapping("/signup")
   public String getSignUp(Model model) {

	   model.addAttribute("userRequest", new UserRequest());

       // signup.htmlに画面遷移
       return "login/signup";
   } 
   
   
   @PostMapping("/signup")
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
  @RequestMapping(value = "/user/create", method = RequestMethod.POST)
  public String create(@Validated @ModelAttribute UserRequest userRequest, BindingResult result, Model model) {
    if (result.hasErrors()) {
      List<String> errorList = new ArrayList<String>();
      for (ObjectError error : result.getAllErrors()) {
        errorList.add(error.getDefaultMessage());
      }

      model.addAttribute("validationError", errorList);
      return "signup";
    }

    // ユーザー情報の登録
    userService.create(userRequest);
    return "redirect:/userlist";
  }
}