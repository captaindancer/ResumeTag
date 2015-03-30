package spring.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Jul 23, 2014  8:04:01 PM
 *@Description
 */

@Component
@ComponentScan
public class MessagePrinter {

	final private MessageService service;
	
	@Autowired
	public MessagePrinter(MessageService service){
		this.service=service;
	}
	
	public void printMessage(){
		System.out.println(this.service.getMessage());
	}
	
}
