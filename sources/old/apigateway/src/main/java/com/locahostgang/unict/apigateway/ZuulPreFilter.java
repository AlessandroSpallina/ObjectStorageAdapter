package com.locahostgang.unict.apigateway;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

//@Component
public class ZuulPreFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "route";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        System.out.println("INIZIO PREFILTRAGGIO");
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String requestUrl = request.getRequestURL().toString() + request.getQueryString();

        System.out.println("questa è l'url iniziale: "+requestUrl);

        //0->39
        String newRequestUrl;
        if(requestUrl.contains("http://localhost:9090/minio/fms-default/")) {
            newRequestUrl = "http://storage:9000/fms-default/".concat(requestUrl.substring(40, requestUrl.length()));
            System.out.println("questa è l'url modificata: "+newRequestUrl);
            context.set(newRequestUrl);
        }

        System.out.println(context.getRequest());
        System.out.println("FINE PREFILTRAGGIO");
        return null;
    }
}
