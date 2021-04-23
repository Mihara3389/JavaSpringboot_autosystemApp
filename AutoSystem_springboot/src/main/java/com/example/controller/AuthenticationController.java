package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class AuthenticationController {

	@GetMapping("/login")//ログイン画面アクセス時はgetでリクエストが飛んでくる(デフォルト時も同様)
    public String getLogin(Model model) {
        return "login";
    }

    @GetMapping("/eroor")//errorが発生した際はエラーメッセージを表示したいので違う処理に入るように制御する
    public String getLoginError(Model model) {
    	model.addAttribute("ErrorMessage","*ユーザー名もしくはパスワードが一致しません");
        return "login";
    }
    
    //デフォルトではPostでリクエストが発生。カスタマイズ時はユーザーが指定したmethodに従う。
    @PostMapping("/login")
    public String postLogin(Model model) {
        return "top";
    }
    
	@GetMapping("/top")
    public String getTopPage(Model model) {
        return "top";
    }
}