package com.rere.auth.ui;

import com.rere.auth.application.AuthService;
import com.rere.auth.domain.AuthenticationPrincipal;
import com.rere.auth.infrastructure.AuthorizationExtractor;
import com.rere.user.domain.LoginUser;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {
    private AuthService authService;

    public AuthenticationPrincipalArgumentResolver(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    // parameter에 @AuthenticationPrincipal이 붙어있는 경우 동작
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String accessToken = AuthorizationExtractor.extract((HttpServletRequest) webRequest.getNativeRequest());
        if (accessToken == null)
            return null;
        return LoginUser.of(authService.checkInvalidToken(accessToken));
    }
}
