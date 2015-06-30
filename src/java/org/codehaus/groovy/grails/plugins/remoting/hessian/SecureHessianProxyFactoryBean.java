package org.codehaus.groovy.grails.plugins.remoting.hessian;

import com.caucho.hessian.client.*;
import org.codehaus.groovy.grails.plugins.remoting.security.SecurityProvider;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;


/**
 * Created by andrearizzini on 30/06/15.
 * This is the extended class which implements the SecureHessianProxyFactory
 * The hole purpose of this class is to pass to the SecuyreHessianProxyFactory a SecurityProvicer whic is used to extract user information from the session.
 * This implements the hessian protocol.
 * @author Andrea Rizzini
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
