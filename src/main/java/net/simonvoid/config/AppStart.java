package net.simonvoid.config;

import net.simonvoid.view.GuiWrapper;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by stephan on 18.11.2015.
 */
public class AppStart {
    public static void main(String[] args) {
        //init Spring Dependency Injection and instanciate the gui service
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        GuiWrapper guiWrapper = ctx.getBean(GuiWrapper.class);

        guiWrapper.startupGUI();
    }
}
