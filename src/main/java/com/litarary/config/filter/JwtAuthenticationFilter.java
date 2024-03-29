package com.litarary.config.filter;

import com.litarary.account.domain.UseYn;
import com.litarary.account.domain.entity.Member;
import com.litarary.account.repository.AccountRepository;
import com.litarary.account.service.AccountService;
import com.litarary.common.ErrorCode;
import com.litarary.common.exception.LitararyErrorException;
import com.litarary.utils.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;
    private final AccountRepository accountRepository;
    private static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    private static final String TOKEN_START_WITH = "Bearer";
    private static final int TOKEN_START_INDEX = 7;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String token = findTokenByHeader(httpRequest);

        //토큰 검증
        if (token != null && jwtTokenProvider.validateToken(token)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            Member member = accountRepository.findByEmailAndUseYn(authentication.getName(), UseYn.Y)
                    .orElseThrow(() -> new LitararyErrorException(ErrorCode.ACCOUNT_NOT_FOUND_EMAIL));
            httpRequest.setAttribute("memberId", member.getId());
        }
        chain.doFilter(request, response);
    }

    private String findTokenByHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER_NAME);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_START_WITH)) {
            return bearerToken.substring(TOKEN_START_INDEX);
        }
        return null;
    }
}
