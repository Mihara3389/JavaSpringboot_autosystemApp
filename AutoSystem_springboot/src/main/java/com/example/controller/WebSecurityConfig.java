package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.service.UserDetailsServiceImpl;

/**
 * SpringSecurityを利用するための設定クラス
 * ログイン処理でのパラメータ、画面遷移や認証処理でのデータアクセス先を設定する
 */
@Configuration
//SpringSecurityのConfigurationクラスが
//インポートされ利用するのに必要なコンポート定義が自動実施
@EnableWebSecurity
//WebSecurityConfigurerAdapterを継承する。
//これによってデフォルトで適用されるBean定義を簡単にカスタマイズ出来る  
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	//UserDetailsServiceを利用出来るように＠Autowiredしておく
	@Autowired
    private UserDetailsServiceImpl userDetailsService;

	//認証用パスワードはハッシュ化して扱うためPasswordをハッシュ化する際に必要なBCryptPasswordEncoder()を返すメソッドを作成しておく。
    @Bean
    public PasswordEncoder passwordEncoder() {

    	BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    	
	//これはハッシュ化済みの値をDBに登録する確認用に出力させるコード//
	String password = "1234";
        String digest = bCryptPasswordEncoder.encode(password);
        System.out.println("ハッシュ値 = " + digest);
	///////////////////////////////////////////////////////////////

        return new BCryptPasswordEncoder();
    }

    /**
     * 認可設定を無視するリクエストを設定
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
        		//セキュリティ対策が不要なリソースがある場合は
        		//SpringSecurityの処理を適用しないように設定
        		.antMatchers("/resources/**");
    }

    /**
     * 認証・認可の情報を設定する
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            	//ログインformをすべてのユーザーがアクセス可能に
            	.antMatchers("/login").permitAll()
            	//全てのURLリクエストは認証されているユーザーしかアクセスできないように
                .anyRequest().authenticated();
        http
        	//formLoginメソッドを呼び出しフォーム認証を有効に
        	.formLogin()
        		.loginPage("/login")
        		.usernameParameter("username")
				.passwordParameter("password")
				//ログイン成功時の遷移先指定
                .defaultSuccessUrl("/sample", true)
                .failureUrl("/eroor").permitAll();
    }

    /**
     * 認証時に利用するデータソースを定義する設定メソッド
     * ここではDBから取得したユーザ情報をuserDetailsServiceへセットすることで認証時の比較情報としている
     */
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
}