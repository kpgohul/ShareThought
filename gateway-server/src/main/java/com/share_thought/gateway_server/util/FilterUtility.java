package com.share_thought.gateway_server.util;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;
import java.util.UUID;


@Component
public class FilterUtility {

    public static final String TRACE_ID = "traceID";

    public String getTraceId(HttpHeaders reqHeaders)
    {
        if(reqHeaders.get(TRACE_ID) != null)
        {
            List<String> TraceIdList = reqHeaders.get(TRACE_ID);
            assert TraceIdList != null;
            return TraceIdList.getFirst();
        }
        else return null;
    }

    public ServerWebExchange setRequestHeader(ServerWebExchange exchange,String key, String value)
    {
        return exchange.mutate().request(exchange.getRequest().mutate()
                        .header(key,value)
                        .build())
                .build();
    }

    public ServerWebExchange setTraceId(ServerWebExchange exchange,String traceId)
    {
        return this.setRequestHeader(exchange,TRACE_ID,traceId);
    }

    public boolean isTraceIdPresent(HttpHeaders request) {
        return this.getTraceId(request) != null;
    }

    public String generateTraceId() {
        return UUID.randomUUID().toString();
    }

}
