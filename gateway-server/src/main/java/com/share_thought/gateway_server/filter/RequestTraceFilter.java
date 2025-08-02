package com.share_thought.gateway_server.filter;

import com.share_thought.gateway_server.util.FilterUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Order(1)
@Component
public class RequestTraceFilter implements GlobalFilter {

    private final FilterUtility utility = new FilterUtility();
    private static final Logger log = LoggerFactory.getLogger(RequestTraceFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders headers = exchange.getRequest().getHeaders();
        if(utility.isTraceIdPresent(headers))
        {
            String traceId = utility.getTraceId(headers);
            log.info("Request received with traceId :: "+traceId);
        }
        else{
            String traceId = utility.generateTraceId();
            exchange = utility.setTraceId(exchange,traceId);
            log.info("Request received with generated traceId :: "+traceId);
        }
        return chain.filter(exchange);
        }



}
