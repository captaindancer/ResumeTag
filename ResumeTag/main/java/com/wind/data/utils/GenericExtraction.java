package com.wind.data.utils;
/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Jul 29, 2014  9:42:00 AM
 *@Description
 */
public interface GenericExtraction <T>{
	T getExtraction(String content,int primaryKey);
}
