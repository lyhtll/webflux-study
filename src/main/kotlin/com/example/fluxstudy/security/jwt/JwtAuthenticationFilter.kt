package com.example.fluxstudy.security.jwt

import io.jsonwebtoken.JwtException
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Component
class JwtAuthenticationFilter (
    private val jwtParser: JwtParser,
) : WebFilter {
    override fun filter(
        exchange: ServerWebExchange,
        chain: WebFilterChain
    ): Mono<Void> {
       val token = resolveToken(exchange) ?: return chain.filter(exchange)

       return try {
           val claims = jwtParser.extractClaims(token)
           val auth = UsernamePasswordAuthenticationToken(
               claims.userId, null,
               listOf(SimpleGrantedAuthority("ROLE_USER"))
           )
           chain.filter(exchange)
               .contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth))
       }catch (e: JwtException) {
           exchange.response.statusCode = HttpStatus.UNAUTHORIZED
           exchange.response.setComplete()
       } catch (e: IllegalStateException) {
           // ACCESS 토큰이 아닌 경우 (REFRESH 토큰으로 요청)
           exchange.response.statusCode = HttpStatus.UNAUTHORIZED
           exchange.response.setComplete()
       }
    }

    private fun resolveToken(exchange: ServerWebExchange): String? =
        exchange.request.headers
            .getFirst("Authorization")
            ?.takeIf { it.startsWith("Bearer ") }
            ?.substring(7)

}