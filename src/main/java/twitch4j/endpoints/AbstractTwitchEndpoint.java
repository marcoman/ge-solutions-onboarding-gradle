package twitch4j.endpoints;

import java.util.Collection;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;
import org.springframework.web.client.RestTemplate;
import twitch4j.TwitchClient;
import twitch4j.enums.Scope;
import twitch4j.exceptions.ScopeMissingException;

@RequiredArgsConstructor
abstract class AbstractTwitchEndpoint {

	/**
	 * Cache - Objects
	 */
	static protected ExpiringMap<String, Object> restObjectCache = ExpiringMap.builder()
			.expiration(30, TimeUnit.SECONDS)
			.expirationPolicy(ExpirationPolicy.ACCESSED)
			.variableExpiration()
			.build();

	protected final TwitchClient client;

	/**
	 * Holds the {@link RestTemplate} instance
	 */
	protected final RestTemplate restTemplate;

	/**
	 * Check that the api has the required scopes before making a request
	 *
	 * @param scopes        Scopes, we have access to.
	 * @param requiredScope Scope, we want to access.
	 */
	protected void checkScopePermission(Collection<Scope> scopes, Scope requiredScope) throws ScopeMissingException {
		if (!scopes.contains(requiredScope)) {
			throw new ScopeMissingException(requiredScope);
		}
	}

}
