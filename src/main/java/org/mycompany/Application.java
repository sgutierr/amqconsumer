/**
 *  Copyright 2005-2018 Red Hat, Inc.
 *
 *  Red Hat licenses this file to you under the Apache License, version
 *  2.0 (the "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *  implied.  See the License for the specific language governing
 *  permissions and limitations under the License.
 */
package org.mycompany;

import java.util.Hashtable;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.component.jms.JmsConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.Environment;
import org.springframework.jms.connection.CachingConnectionFactory;

/**
 * A spring-boot application that includes a Camel route builder to setup the Camel routes
 */
@SpringBootApplication
@ImportResource({"classpath:spring/camel-context.xml"})
public class Application {

    // must have a main method spring-boot can run
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @Autowired
    private Environment env;
    private final static String CNX_FACTORY_NAME = "default";

    @Bean
    CachingConnectionFactory cachingConnectionFactory() throws JMSException, NamingException {
        Hashtable<String,String>  jndiConfig = new Hashtable<String,String>();
        
        jndiConfig.put(InitialContext.INITIAL_CONTEXT_FACTORY, env.getProperty("jms."+InitialContext.INITIAL_CONTEXT_FACTORY));
        jndiConfig.put("connectionFactory."+CNX_FACTORY_NAME, env.getProperty("jms.connectionFactory."+CNX_FACTORY_NAME));

        Context context = new InitialContext(jndiConfig);
        ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup(CNX_FACTORY_NAME);

        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(connectionFactory);
        return cachingConnectionFactory;
    }
    
    
    @Bean
    public JmsComponent jms(CachingConnectionFactory cachingConnectionFactory) {

        JmsConfiguration jmsConfiguration =new JmsConfiguration(cachingConnectionFactory);
        jmsConfiguration.setCacheLevelName(env.getProperty("jms.cache.level"));

        JmsComponent jmsComponent = new JmsComponent(jmsConfiguration);

        return jmsComponent;
    }
    
}
