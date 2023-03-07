package com.sphirye.spring.boot.template.security.util

import com.sphirye.spring.boot.template.entity.dto.CustomUserTokenDetails
import com.sphirye.spring.boot.template.service.tool.RetrofitTool.gson
import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*
import javax.servlet.http.HttpServletRequest

@Component
class JwtTokenUtil {

    @Value("\${jwt.secret}") private lateinit var jwtSecret: String
    @Value("\${jwt.token-validity-in-seconds}") private var jwtExpirationInSeconds: Long = 0
    @Value("\${spring.application.id}") lateinit var jwtIssuer: String

    private val logger: Logger? = LogManager.getLogger()
    private val signingKey: Key by lazy { Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret)) }

    companion object {
        private const val AUTHORITIES_KEY = "auth"
    }

    fun generateToken(authentication: Authentication): String {
        val validity = Date(Date().time + (jwtExpirationInSeconds * 1000))

        return Jwts.builder()
            .setSubject(authentication.principal.toString())
            .setIssuer(jwtIssuer)
            .claim("details", authentication.details)
            .setIssuedAt(Date())
            .setExpiration(validity) // 1 week
            .signWith(signingKey, SignatureAlgorithm.HS512)
            .compact()
    }

    fun getAuthentication(token: String): Authentication {
        val userTokenDetails = getUserDetailsFromToken(token)
        val username = getUsernameFromToken(token)

        val authentication = UsernamePasswordAuthenticationToken(username, null, userTokenDetails!!.authorities)
        authentication.details = userTokenDetails
        return authentication
    }

    fun getUsernameFromToken(token: String): String {
        return Jwts.parserBuilder()
            .setSigningKey(signingKey)
            .build()
            .parseClaimsJws(token)
            .body
            .subject
    }

    fun getUserDetailsFromToken(token: String): CustomUserTokenDetails? {
        val claim = Jwts.parserBuilder()
            .setSigningKey(signingKey)
            .build()
            .parseClaimsJws(token)
            .body["details"]

        return if (claim != null) { gson.fromJson(claim.toString(), CustomUserTokenDetails::class.java) }
        else { null }
    }

    fun validateToken(token: String): Boolean {
        try {
            Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)

            return true
        }  catch (e: SecurityException) { logger!!.error("Invalid JWT signature.")
        } catch (e: MalformedJwtException) { logger!!.error("Invalid JWT signature.")
        } catch (e: ExpiredJwtException) { logger!!.error("Expired JWT token.")
        } catch (e: UnsupportedJwtException) { logger!!.error("Unsupported JWT token.")
        } catch (e: IllegalArgumentException) { logger!!.error("Invalid JWT token.")
        }
        return false
    }

    fun resolveToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7)
        }
        return null
    }
}