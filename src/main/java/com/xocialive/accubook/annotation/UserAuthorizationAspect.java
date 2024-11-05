package com.xocialive.accubook.annotation;

import com.xocialive.accubook.model.entity.UserPrincipal;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserAuthorizationAspect {

    @Before("@annotation(com.xocialive.accubook.annotation.CheckUserAuthorization) && args(userId,..)")
    public void checkUserAuthorization(Long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof UserPrincipal userPrincipal)) {
            throw new AccessDeniedException("User not authenticated");
        }

        if (!userId.equals(userPrincipal.getId())) {
            throw new AccessDeniedException("You do not have permission to access this resource.");
        }
    }
}
