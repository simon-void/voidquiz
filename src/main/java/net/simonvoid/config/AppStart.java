package net.simonvoid.config;

import net.simonvoid.provider.NameProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by stephan on 18.11.2015.
 */
public class AppStart {
    public static void main(String[] args) {
        //init Spring Dependency Injection and instanciate the gui provider
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        NameProvider nameProvider = ctx.getBean(NameProvider.class);

        System.out.println("-------------------------------");
        System.out.println("name: "+nameProvider.getName());
        System.out.println("-------------------------------");
    }
}
