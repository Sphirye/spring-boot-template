package com.sphirye.spring.boot.template.security

import com.sphirye.spring.boot.template.config.CorsConfig
import com.sphirye.spring.boot.template.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import javax.servlet.http.HttpServletResponse

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig {

    @Autowired lateinit var jwtTokenFilter: JwtTokenFilter
    @Autowired lateinit var corsFilter: CorsConfig

    @Bean
    @Throws(Exception::class)
    fun filterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        httpSecurity
            .cors().and().csrf().disable()
            .addFilterBefore(corsFilter.corsFilter(), UsernamePasswordAuthenticationFilter::class.java)

            .exceptionHandling()
            .authenticationEntryPoint { _, response, ex ->
                response.sendError(HttpServletResponse.SC_FORBIDDEN, ex.message)
            }

            .accessDeniedHandler { _, response, ex ->
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.message)
            }

            // Enable h2-console
            .and().headers().frameOptions().sameOrigin()

            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            // Set secure endpoints
            .and()
            .authorizeRequests()
            .antMatchers("/api/hello").permitAll()
            .antMatchers("/api/authenticate").permitAll()
            .antMatchers("/api/signup").permitAll()
            .antMatchers("/public/**").permitAll()
            .antMatchers("/h2-console/**").permitAll()

            .anyRequest().authenticated()

            .and()
            .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter::class.java)
        return httpSecurity.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder { return CustomPasswordEncoder() }

    @Bean
    fun authenticationManager(): AuthenticationManager { return CustomAuthenticationManager() }
}