package com.hotels.example.security;


import com.hotels.example.model.User;
import com.hotels.example.repositories.UserRepo;
import io.jsonwebtoken.*;
import io.micrometer.core.instrument.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class AuthorizationJwtFilter extends OncePerRequestFilter {
    private UserRepo userRepository;

    private static final Logger log = LoggerFactory.getLogger(AuthorizationJwtFilter.class);


    public AuthorizationJwtFilter(UserRepo userRepository) {

        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        Optional<Authentication> authe = Optional.ofNullable(getAuthenticationReq(request));

        if ( !authe.isPresent())     {

            SecurityContextHolder.clearContext();

            filterChain.doFilter(request, response);

            return;
        }


        SecurityContextHolder.getContext().setAuthentication(authe.get());

        filterChain.doFilter(request, response);

    }



    //get bearer_token from request and validate it
    private UsernamePasswordAuthenticationToken getAuthenticationReq(HttpServletRequest request) {



        //get header
        String token = request.getHeader(JwtTokenSource.header);


        if (StringUtils.isNotEmpty(token) && token.startsWith(JwtTokenSource._prefix)) {
            try {

                //get username that corresponds to the token
                String bearerToken = token.substring(7);

                Claims claims = Jwts.parser()
                        .setSigningKey(JwtTokenSource.key.getBytes())
                        .parseClaimsJws(bearerToken)
                        .getBody();


                String username = claims.getSubject();


                //validate bearer_token
                if (StringUtils.isNotEmpty(username)) {
                    Optional<User> u = userRepository.findByUsername(username);

                    if (u.isPresent()) {
                        User user = u.get();
                        return new UsernamePasswordAuthenticationToken(username, user.getPassword(), user.getAuthorities());

                    }

                }
            }


            catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
                log.info("Invalid JWT signature.");
                log.trace("Invalid JWT signature trace: {}", e);
            } catch (ExpiredJwtException e) {
                log.info("Expired JWT token.");
                log.trace("Expired JWT token trace: {}", e);
            } catch (UnsupportedJwtException e) {
                log.info("Unsupported JWT token.");
                log.trace("Unsupported JWT token trace: {}", e);
            } catch (IllegalArgumentException e) {
                log.info("JWT token compact of handler are invalid.");
                log.trace("JWT token compact of handler are invalid trace: {}", e);
            }

        }

        return null;
    }









}
