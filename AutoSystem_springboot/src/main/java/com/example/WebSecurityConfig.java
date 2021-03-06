package com.example;

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
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.service.UserDetailsServiceImpl;

/**
 * SpringSecurityを利用するための設定クラス
 * ログイン処理でのパラメータ、画面遷移や認証処理でのデータアクセス先を設定する
 */
//SpringSecurityのConfigurationクラスが
//インポートされ利用するのに必要なコンポート定義が自動実施
@Configuration
//WebSecurityConfigurerAdapterを継承する。
//これによってデフォルトで適用されるBean定義を簡単にカスタマイズ出来る 
@EnableWebSecurity
// Spring Securityの基本設定
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	//UserDetailsServiceを利用出来るように＠Autowiredしておく
	@Autowired
    private UserDetailsServiceImpl userDetailsService;
	
	//認証用パスワードはハッシュ化して扱うためPasswordをハッシュ化する際に
	//必要なBCryptPasswordEncoder()を返すメソッドを作成しておく。
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

	/**
     * 認可設定を無視するリクエストを設定
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
       // セキュリティ設定を無視するリクエスト設定
        // 静的リソース(images、css、javascript)に対するアクセスはセキュリティ設定を無視する
        web.ignoring().antMatchers(
                            "/images/**",
                            "/css/**",
                            "/javascript/**",
                            "/webjars/**");
    }

    /**
     * 認証・認可の情報を設定する
     */
  
   	  @Override
    	  protected void configure(HttpSecurity http) throws Exception {
    		  http
    		  .authorizeRequests()
    		  //すべてのユーザーがアクセス可能にしたいURL
    		  .antMatchers("/login","/signup").permitAll()
    		  //上記に記載したURL以外は認証後のみ閲覧可能
    		  .anyRequest().authenticated()
    		  //CSRF対策機能
    		  .and().csrf().csrfTokenRepository(new HttpSessionCsrfTokenRepository());
    		  http
    		  //formLoginメソッドを呼び出しフォーム認証を有効に
    		  .formLogin()
    		  .loginPage("/login")
    		  .usernameParameter("username")
    		  .passwordParameter("password")
    		  //ログイン成功時の遷移先指定
    		  .defaultSuccessUrl("/top", true)
    		  .failureUrl("/eroor").permitAll();
    		  //logoutメソッドを呼び出しlogoutを有効に
    		  http
    		  .logout()
    		  //セッションの破棄
    		  .invalidateHttpSession(true)
    		  //JSESSIONIDというクッキーを削除
    		  .deleteCookies("JSESSIONID")
    		  .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
    	      //ログアウト成功後のURL
    	      .logoutSuccessUrl("/login").permitAll(); 
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