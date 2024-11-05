package com.xocialive.accubook.annotation;

import com.xocialive.accubook.model.entity.Transaction;
import com.xocialive.accubook.model.entity.UserPrincipal;
import com.xocialive.accubook.model.repository.TransactionRepo;
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
public class TransactionAuthorizationAspect {

    private final TransactionRepo transactionRepo;

    @Before("@annotation(com.xocialive.accubook.annotation.CheckTransactionOwnership) && args(transactionId,..)")
    public void checkTransactionOwnership(Long transactionId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof UserPrincipal userPrincipal)) {
            throw new AccessDeniedException("User not authenticated");
        }

        Transaction transaction = transactionRepo.findById(transactionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction with ID " + transactionId + " not found"));

        if (!transaction.getClient().getUser().getId().equals(userPrincipal.getId())) {
            throw new AccessDeniedException("You do not have permission to modify this transaction.");
        }
    }
}
