package dancer.liufeng.process.bar;

import org.springframework.stereotype.Repository;

import spring.test.MessageService;

/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Jul 23, 2014  8:32:00 PM
 *@Description
 */

@Repository("wind")
public class WindMessageService implements MessageService {

	@Override
	public String getMessage() {
		return "wind";
	}

}
