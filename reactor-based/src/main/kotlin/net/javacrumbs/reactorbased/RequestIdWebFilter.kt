package net.javacrumbs.reactorbased

import org.slf4j.MDC
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Component
class RequestIdWebFilter : WebFilter {
    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        MDC.put("requestId", exchange.request.headers["Request-Id"]?.firstOrNull())
        return chain.filter(exchange).doFinally {
            MDC.clear()
        }
    }
}
// See https://stackoverflow.com/questions/51738140/how-to-correctly-use-slf4j-mdc-in-spring-webflux-webfilter for how to do it correctly
