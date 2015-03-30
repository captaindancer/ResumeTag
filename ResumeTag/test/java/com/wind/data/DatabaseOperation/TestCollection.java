package com.wind.data.DatabaseOperation;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import com.wind.information.model.Resume;

/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Jul 29, 2014  11:16:56 AM
 *@Description
 */
public class TestCollection {

	@Test
	public void test() {
		List<String> testList=new ArrayList<String>();
//		Resume resume=null;
		System.out.println(testList);
		System.out.println(testList.toString());
		String testStr=testList.toString();
		String briefStr="[一、信誉优势 <br>多年珠宝首饰经营品牌，国家工商局监管，信心保证； <br>公平实时的汇率，结算清晰； <br>与农业银行系统合作，签署第三方资金存管协议，资金安全有保障。 <br>二、投资优势 <br>低手续费，预付款模式，获利更容易； <br>低门槛，投资更灵活，双向买卖，涨跌均可获利； <br>投资便捷，24小时交易，T+0模式优势。 <br>三、平台优势 <br>最新电子订购平台，下单方便快捷； <br>分析图表，功能强大，交易稳定； <br>操作简便，出入金方便快捷。 <br>四、服务优势 <br>专业管理团队、技术团队和服务团队，全程无忧服务 <br>信息资讯及时更新，每天早晚行情分析指导； <br>讲师讲解，更快学习，专家咨询，解答疑问。]";
		briefStr=briefStr.replaceAll("\\[", "");
		System.out.println(testStr);
		String regExp=".+/\\d+--.+";
		System.out.println(regExp);
		String dataStr="2006 /11--至今";
		System.out.println(dataStr);
		dataStr=dataStr.replaceAll(regExp, "");
		System.out.println(":"+dataStr);
		Pattern pattern=Pattern.compile(".+/\\d+--[\\s\\S]+");
		Matcher matcher=pattern.matcher("2006      /11--至今：贝塔斯曼集团");
		if(matcher.matches()){
			System.out.println("I catch you");
		}
		/*String startStr=null;
		String join="join";
		if(startStr.equals("")){
			System.out.println(join);
		}*/
//		System.out.println(startStr+join);
		
	}

}
