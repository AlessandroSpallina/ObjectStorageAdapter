package com.locahostgang.unict.apigateway;

import com.google.common.io.CharStreams;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.coyote.Response;
import org.apache.http.protocol.ResponseContent;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.UUID;

@Component
public class ZuulPostFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "post";
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
        System.out.println("Inside Response Filter");

        RequestContext context = RequestContext.getCurrentContext();
        String responseData;
        try {
            responseData = CharStreams.toString(new InputStreamReader(context.getResponseDataStream(), "UTF-8"));
            context.setResponseBody(responseData);
            System.out.println("prima della modifica");
            System.out.println(context.getResponseBody());
            if(context.getResponseBody().contains("http://storage:9000/fms-default/")) {
                context.setResponseBody("http://localhost:9090/minio/fms-default/".concat(responseData.substring(32, responseData.length())));

                // da console si vede l'url modificato
                System.out.println("dopo la modifica");
                System.out.println(context.getResponseBody());
                return context;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
