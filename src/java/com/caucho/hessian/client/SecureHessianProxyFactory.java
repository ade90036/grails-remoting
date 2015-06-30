package com.caucho.hessian.client;

import org.codehaus.groovy.grails.plugins.remoting.security.SecurityProvider;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by andrearizzini on 30/06/15.
 */
public class SecureHessianProxyFactory extends HessianProxyFactory {


    private SecureHessianURLConnectionFactory _connFactory;
    private SecurityProvider securityProvider;

    public SecureHessianProxyFactory()
    {
        super();
    }

    public SecurityProvider getSecurityProvider() {
        return securityProvider;
    }

    public void setSecurityProvider(SecurityProvider securityProvider) {
        this.securityProvider = securityProvider;
        if (this._connFactory != null)
        {
            this._connFactory.setSecurityProvider(securityProvider);
        }
    }


    public HessianConnectionFactory getConnectionFactory() {
        if(this._connFactory == null) {
            this._connFactory = new SecureHessianURLConnectionFactory();
            this._connFactory.setSecurityProvider(securityProvider);
            this._connFactory.setHessianProxyFactory(this);
        }
        return this._connFactory;
    }



    public class SecureHessianURLConnectionFactory extends HessianURLConnectionFactory
    {
        private final Logger log = Logger.getLogger(HessianURLConnectionFactory.class.getName());
        private HessianProxyFactory _proxyFactory;

        private String HTTP_HEADER_AUTHORIZATION = "Authorization";

        private SecurityProvider securityProvider;

        public SecurityProvider getSecurityProvider() {
            return securityProvider;
        }

        public void setSecurityProvider(SecurityProvider securityProvider) {
            this.securityProvider = securityProvider;
        }

        public HessianConnection open(URL url) throws IOException {
            if(log.isLoggable(Level.FINER)) {
                log.finer(this + " open(" + url + ")");
            }

            URLConnection conn = url.openConnection();
            long connectTimeout = this._proxyFactory.getConnectTimeout();
            if(connectTimeout >= 0L) {
                conn.setConnectTimeout((int)connectTimeout);
            }

            if (securityProvider != null)
            {
                log.finer("authenticationHeader: "+securityProvider.getAuthorisation());
                conn.setRequestProperty(HTTP_HEADER_AUTHORIZATION, securityProvider.getAuthorisation());
            }

            conn.setDoOutput(true);
            long readTimeout = this._proxyFactory.getReadTimeout();
            if(readTimeout > 0L) {
                try {
                    conn.setReadTimeout((int)readTimeout);
                } catch (Throwable e) {
                    ;
                }
            }

            return new HessianURLConnection(url, conn);
        }
    }
}
