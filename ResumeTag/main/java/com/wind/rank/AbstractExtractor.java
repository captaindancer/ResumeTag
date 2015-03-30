package com.wind.rank;


import java.util.Map;


/**
 * 抽象抽取类
 * @author liufeng
 *
 */
public abstract class AbstractExtractor {

	//中文分词器(能否做成容易切换成其他分词器实现的抽象)
	protected AbstractTagger tag;
	//自定义的停用词
	protected StopWords stopwords;

	protected double precision = 0.001;//权重收敛的默认阈值

	protected double dN = 0.85;  //阻尼参数,经验值

	//TODO:默认修改为3
	protected int windowN = 3; //窗体大小,根据应用进行修改

	
	public void setN(int windowN,double dN){
		this.windowN = windowN;
		this.dN = dN;
	}

	/**
	 * 将权重收敛的阈值设小
	 * 算出来的关键词更精确
	 */
	public void setPrecisionHigh() {
		this.precision = 0.000000001;
	}

	/**
	 * 将权重收敛的阈值设大
	 * 算出来的关键词粗糙，但速度更快
	 */
	public void setPrecisionLow() {
		this.precision = 0.1;
	}

	/**
	 * 将权重收敛的阈值设为默认
	 */
	public void setPrecisionDefault() {
		this.precision = 0.001;
	}

	/**
	 * 
	 * @param precision
	 *       权重收敛的阈值
	 */
	public void setPrecision(double precision){
		this.precision = precision;
	}

	//关键词抽取的方法
	abstract public String extract(String str, int num, boolean isWeighted);
	abstract  public Map<String,Double> extract(String readFile, int i);
}
