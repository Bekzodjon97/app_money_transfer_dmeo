package uz.pdp.money_transfer_demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.pdp.money_transfer_demo.service.MyAuthService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    MyAuthService myAuthService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        //request dan tokenni olish
        String token = httpServletRequest.getHeader("Authorization");

        //tokn borligini tekshirish
        if (token!=null&&token.startsWith("Bearer")){
            token=token.substring(7);

            //tokenni validatsiyadan o'tkazdik(token buzilgan buzilmaganligini, muddati o'tmaganligini va hk)
            boolean validateToken = jwtProvider.validateToken(token);
            if (validateToken){

                //tokenni ichidan username ni oldik
                String usernameFromToken = jwtProvider.getUsernameFromToken(token);

                //username orqali userdetails ni oldik
                UserDetails userDetails = myAuthService.loadUserByUsername(usernameFromToken);

                //userdetails orqali autentication yasab oldik
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new
                        UsernamePasswordAuthenticationToken(userDetails,
                        null, userDetails.getAuthorities());

                //sistemaga kim kirganligini o'rnatdik
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }

        }

        filterChain.doFilter(httpServletRequest,httpServletResponse);

    }
}
