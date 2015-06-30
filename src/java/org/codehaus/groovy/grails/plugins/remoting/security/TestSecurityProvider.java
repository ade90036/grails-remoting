package org.codehaus.groovy.grails.plugins.remoting.security;

import java.util.Random;

/**
 * Created by andrearizzini on 30/06/15.
 */
public class TestSecurityProvider implements SecurityProvider {

    Random ran = new Random();

    @Override
    public String getAuthorisation() {
        return String.valueOf( ran.nextLong() );
    }
}
