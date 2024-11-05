package com.xocialive.accubook.annotation;

import com.xocialive.accubook.model.entity.Client;
import com.xocialive.accubook.model.entity.UserPrincipal;
import com.xocialive.accubook.model.repository.ClientRepo;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Aspect
@Component
@RequiredArgsConstructor
public class ClientAuthorizationAspect {

    private final ClientRepo clientRepo;

    @Before("@annotation(com.xocialive.accubook.annotation.CheckClientAuthorization) && args(clientId,..)")
    public void checkClientUserAuthorization(Long clientId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof UserPrincipal userPrincipal)) {
            throw new AccessDeniedException("User not authenticated");
        }

        Client client = clientRepo.findById(clientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client with ID " + clientId + " not found"));

        if (!client.getUser().getId().equals(userPrincipal.getId())) {
            throw new AccessDeniedException("You do not have permission to update this client.");
        }
    }
}
