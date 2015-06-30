package org.codehaus.groovy.grails.plugins.remoting.hessian;

import com.caucho.hessian.client.*;
import org.codehaus.groovy.grails.plugins.remoting.security.SecurityProvider;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by andrearizzini on 30/06/15.
 */
public class SecureHessianProxyFactoryBean extends HessianProxyFactoryBean {

    private SecureHessianProxyFactory proxyFactory = new SecureHessianProxyFactory();

    public SecurityProvider getSecurityProvider() {
        return proxyFactory.getSecurityProvider();
    }

    public void setSecurityProvider(SecurityProvider securityProvider) {
        proxyFactory.setSecurityProvider(securityProvider);
    }


}
