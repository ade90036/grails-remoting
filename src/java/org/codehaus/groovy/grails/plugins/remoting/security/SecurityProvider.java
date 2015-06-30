package org.codehaus.groovy.grails.plugins.remoting.security;

/**
 * Created by andrearizzini on 30/06/15.
 * This is an interface which expose a method to return the user Authorization token from the session.
 * @author Andrea Rizzini
 *
 */
public interface SecurityProvider {

    public String getAuthorisationToken();
}
