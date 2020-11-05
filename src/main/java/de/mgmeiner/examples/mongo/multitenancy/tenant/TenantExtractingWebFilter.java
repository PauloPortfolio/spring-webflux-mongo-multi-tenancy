package de.mgmeiner.examples.mongo.multitenancy.tenant;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

@AllArgsConstructor
class TenantExtractingWebFilter implements WebFilter {

    private List<String> validTenantIds;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange,WebFilterChain chain) {

        var tenantIdHeader =
                exchange
                        .getRequest()
                        .getHeaders()
                        .getOrEmpty("X-Tenant");

        var response =
                exchange
                        .getResponse();

        // INVALID TENANT
        if (tenantIdHeader.isEmpty() || !validTenantIds.contains(tenantIdHeader.get(0))) {
            response.setStatusCode(HttpStatus.BAD_REQUEST);
            return response.setComplete();
        }

        var tenantId = tenantIdHeader.get(0);

        return chain
                .filter(exchange)
                .subscriberContext(context -> context.put("tenant",tenantId));
    }
}
