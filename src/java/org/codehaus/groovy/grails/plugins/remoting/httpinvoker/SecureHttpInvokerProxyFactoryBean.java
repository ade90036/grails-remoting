package org.codehaus.groovy.grails.plugins.remoting.httpinvoker;

import org.codehaus.groovy.grails.plugins.remoting.security.SecurityProvider;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;
import org.springframework.remoting.httpinvoker.HttpInvokerRequestExecutor;
import org.springframework.remoting.httpinvoker.SimpleHttpInvokerRequestExecutor;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Locale;

/**
 * Created by andrearizzini on 29/06/15.
 */
public class SecureHttpInvokerProxyFactoryBean extends HttpInvokerProxyFactoryBean {

    private SecureHttpInvokerRequestExecutor httpInvokerRequestExecutor;
    private SecurityProvider securityProvider;

    public SecureHttpInvokerProxyFactoryBean()
    {
        super();
    }

    public HttpInvokerRequestExecutor getHttpInvokerRequestExecutor() {
        if (this.httpInvokerRequestExecutor == null) {
            SecureHttpInvokerRequestExecutor executor = new SecureHttpInvokerRequestExecutor();
            executor.setSecurityProvider(securityProvider);
            executor.setBeanClassLoader(getBeanClassLoader());
            this.httpInvokerRequestExecutor = executor;
        }
        return this.httpInvokerRequestExecutor;
    }

    public SecurityProvider getSecurityProvider() {
        return securityProvider;
    }

    public void setSecurityProvider(SecurityProvider securityProvider) {
        this.securityProvider = securityProvider;
        if (this.httpInvokerRequestExecutor != null) {
            this.httpInvokerRequestExecutor.setSecurityProvider(securityProvider);
        }
    }


    public class SecureHttpInvokerRequestExecutor extends SimpleHttpInvokerRequestExecutor {

        private int connectTimeout = -1;

        private int readTimeout = -1;

        private SecurityProvider securityProvider;

        private String HTTP_HEADER_AUTHORIZATION = "Authorization";
        /**
         * Prepare the given HTTP connection.
         * <p>The default implementation specifies POST as method,
         * "application/x-java-serialized-object" as "Content-Type" header,
         * and the given content length as "Content-Length" header.
         * @param connection the HTTP connection to prepare
         * @param contentLength the length of the content to send
         * @throws IOException if thrown by HttpURLConnection methods
         * @see java.net.HttpURLConnection#setRequestMethod
         * @see java.net.HttpURLConnection#setRequestProperty
         */
        protected void prepareConnection(HttpURLConnection connection, int contentLength) throws IOException {
            if (this.connectTimeout >= 0) {
                connection.setConnectTimeout(this.connectTimeout);
            }
            if (this.readTimeout >= 0) {
                connection.setReadTimeout(this.readTimeout);
            }
            connection.setDoOutput(true);
            connection.setRequestMethod(HTTP_METHOD_POST);

            //arizzini: this is the overloading method which we enable the JWT - json web token to validate the user.
            if (securityProvider != null)
            {
                logger.debug("authenticationHeader: "+securityProvider.getAuthorisation());
                connection.setRequestProperty(HTTP_HEADER_AUTHORIZATION, securityProvider.getAuthorisation());
            }

            connection.setRequestProperty(HTTP_HEADER_CONTENT_TYPE, getContentType());
            connection.setRequestProperty(HTTP_HEADER_CONTENT_LENGTH, Integer.toString(contentLength));

            LocaleContext localeContext = LocaleContextHolder.getLocaleContext();
            if (localeContext != null) {
                Locale locale = localeContext.getLocale();
                if (locale != null) {
                    connection.setRequestProperty(HTTP_HEADER_ACCEPT_LANGUAGE, StringUtils.toLanguageTag(locale));
                }
            }
            if (isAcceptGzipEncoding()) {
                connection.setRequestProperty(HTTP_HEADER_ACCEPT_ENCODING, ENCODING_GZIP);
            }

            logger.debug("connection: "+connection);


        }

        public void setSecurityProvider(SecurityProvider provider)
        {
            this.securityProvider = provider;
        }

    }
}



