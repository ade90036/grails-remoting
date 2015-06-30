package org.codehaus.groovy.grails.plugins.remoting.security;

import java.util.Random;

/**
 * Created by andrearizzini on 30/06/15.
 * This is a test SecurityProvider which sets the Authorisation token in the header.
 * @author Andrea Rizzini
 */
public class TestSecurityProvider implements SecurityProvider {

    Random ran = new Random();

    @Override
    public String getAuthorisationToken() {
        return String.valueOf( ran.nextLong() );
    }
}
