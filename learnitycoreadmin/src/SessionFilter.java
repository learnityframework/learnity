import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.grlea.log.SimpleLogger;

public class SessionFilter implements Filter
{
    private static final SimpleLogger log;
    private FilterConfig filterConfig;
    private Map<String, String> headersMap;
    
    public void init(final FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        System.out.println("=================initiated============");
        final String headerParam = filterConfig.getInitParameter("header");
        System.out.println("=================headerParam============" + headerParam);
        if (headerParam == null) {
            SessionFilter.log.warn("No headers were found in the web.xml (init-param) for the CacheFilter !");
            return;
        }
        this.headersMap = new LinkedHashMap<String, String>();
        if (headerParam.contains("|")) {
            final String[] arr$;
            final String[] headers = arr$ = headerParam.split("|");
            for (final String header : arr$) {
                this.parseHeader(header);
            }
        }
        else {
            this.parseHeader(headerParam);
        }
        SessionFilter.log.info("The following headers were registered in the CacheFilter :");
        final Set<Map.Entry<String, String>> headers2 = this.headersMap.entrySet();
        for (final Map.Entry<String, String> item : headers2) {
            SessionFilter.log.info(item.getKey() + ':' + item.getValue());
        }
    }
    
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        if (this.headersMap != null) {
            System.out.println("=================doFilter============");
            final HttpServletRequest req = (HttpServletRequest)request;
            final String context_path = req.getContextPath();
            System.out.println("==============context_path=========" + context_path);
            final String context_host = req.getServerName();
            final int context_port = req.getServerPort();
            final String context_port_string = Integer.toString(context_port);
            System.out.println("==============context_host=========" + context_host + "=======" + context_port);
            final HttpServletResponse res = (HttpServletResponse)response;
            final HttpSession session = req.getSession();
            String user_id = "";
            user_id = (String)session.getAttribute("user_id");
            System.out.println("user_id===================" + user_id);
            if (user_id == null || user_id.equals("")) {
                System.out.println("================null======");
                res.sendRedirect("http://" + context_host + ":" + context_port_string + "/" + context_path);
            }
            final Set<Map.Entry<String, String>> headers = this.headersMap.entrySet();
            for (final Map.Entry<String, String> header : headers) {
                ((HttpServletResponse)response).setHeader((String)header.getKey(), (String)header.getValue());
            }
        }
        chain.doFilter(request, response);
    }
    
    public void destroy() {
        this.filterConfig = null;
        this.headersMap = null;
    }
    
    private void parseHeader(final String header) {
        final String headerName = header.substring(0, header.indexOf(":"));
        System.out.println("=================headerName============" + headerName);
        if (!this.headersMap.containsKey(headerName)) {
            this.headersMap.put(headerName, header.substring(header.indexOf(":") + 1));
        }
    }
    
    static {
        log = new SimpleLogger((Class)SessionFilter.class, true);
    }
}
