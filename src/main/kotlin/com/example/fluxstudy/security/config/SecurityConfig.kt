package com.example.fluxstudy.security.config

import com.example.fluxstudy.security.handler.AccessDeniedHandler
import com.example.fluxstudy.security.handler.AuthenticationEntryPointHandler
import com.example.fluxstudy.security.jwt.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
@EnableWebFluxSecurity
class SecurityConfig (
    private val jwtAuthenticationFilter: JwtAuthenticationFilter,
    private val authenticationEntryPointHandler: AuthenticationEntryPointHandler,
    private val accessDeniedHandler: AccessDeniedHandler,
){
    @Bean
    fun filterChain(http: ServerHttpSecurity): SecurityWebFilterChain =
        http
            .authorizeExchange { auth ->
                auth
                    .pathMatchers("/api/v1/auth/**").permitAll()
                    .anyExchange().authenticated()
            }
            .addFilterBefore(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
            .exceptionHandling { exception ->
                exception
                    .authenticationEntryPoint(authenticationEntryPointHandler)
                    .accessDeniedHandler(accessDeniedHandler)
            }
            .csrf { it.disable() }
            .httpBasic { it.disable() }
            .formLogin { it.disable() }
            .build()

    @Bean
    fun passwordEncoder() : PasswordEncoder = BCryptPasswordEncoder()


}