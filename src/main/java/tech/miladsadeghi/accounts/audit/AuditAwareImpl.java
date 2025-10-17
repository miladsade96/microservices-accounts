package tech.miladsadeghi.accounts.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * This class is used to provide the current auditor (user) for auditing purposes.
 * In this example, it returns a fixed value "Accounts_MS".
 * In a real application, you would typically fetch the current user from the security context.
 */
@Component("auditAwareImpl")
public class AuditAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("Accounts_MS");
    }
}
