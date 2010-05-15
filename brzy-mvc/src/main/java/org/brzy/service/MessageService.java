package org.brzy.service;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 *
 * @author Michael Fortin
 * @version $Id: $
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface MessageService {

    //	Spawns 5 threads to listen for messages	defaults to 1
    int listenerCount() default 1;

    //	Sets an alternate destination name	defaults to Service's property name
    String destination();

    //   Sets an alternate listener method (or closure) name defaults to "onMessage"
    String listenerMethod() default "onMessage";

    //	Tells the Spring JMS classes to use Topic instead of Queue	defaults to false
    boolean pubSub() default true;

    // 	Associates JMS Message Selector to the listener	defaults to null
    String messageSelector();

    // 	Creates a durable subscription (applies to Topics only)	defaults to false
    boolean durable() default false;

    // 	Provides a client identifier for durable subscriptions.	defaults to
    // "grailsAppListener", id property can be used instead of clientId for
    // backwards compatibility
    String clientId() default "brzyListener";
}