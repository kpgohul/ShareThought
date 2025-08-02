package com.share_thought.gateway_server.filter;
import com.share_thought.gateway_server.util.FilterUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;


@Configuration
//@Slf4j
public class ResponseTraceFilter {

    private final FilterUtility utility = new FilterUtility();
    private static final Logger logger = LoggerFactory.getLogger(ResponseTraceFilter.class);


    @Bean
    public GlobalFilter postFilter()
    {
        return (exchange, chain) -> {
            return chain.filter(exchange).then(Mono.fromRunnable(() ->
                    {
                        HttpHeaders headers = exchange.getRequest().getHeaders();
                        String traceId = utility.getTraceId(headers);
                        logger.info("Response sent with traceId :: "+traceId);
                        exchange.getResponse().getHeaders().add(FilterUtility.TRACE_ID, traceId);
                    }));
        };
    }



}
