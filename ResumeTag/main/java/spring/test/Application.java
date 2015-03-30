package spring.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Jul 24, 2014  8:45:22 PM
 *@Description
 */

@Component
public class Application {
	
	@Bean
    MessageService mockMessageService() {
        return new MessageService() {
            public String getMessage() {
              return "Hello World!";
            }
        };
    }

  public static void main(String[] args) {
      ApplicationContext context =new AnnotationConfigApplicationContext(MessagePrinter.class);
      MessagePrinter printer = context.getBean(MessagePrinter.class);
      printer.printMessage();
  }
}
