package com.wind.data.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.SequenceFile.Writer.Option;
import org.apache.hadoop.io.Text;


/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Aug 14, 2014  10:07:10 AM
 *@Description
 */
public class SequenceFileCreation {
    private static Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private String dataTable="fullresume";  //默认的数据表
    private static Set<String> stopwordSet=new HashSet<String>(1000);
    private static final int dataLimit=100;//默认每次的查询记录数
    
    static{
		InputStream inputStream=SequenceFileCreation.class.getClassLoader().getResourceAsStream("stopword.dic");
		BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
		String line;
		try {
			connection=JDBCUtils.getLocalConnection();
			while((line=reader.readLine())!=null){
				stopwordSet.add(line);
			}
		} catch (IOException e) {
			throw new ExceptionInInitializerError("can't load the stopword");
		} catch (SQLException e1) {
			throw new ExceptionInInitializerError("construct connection fails!");
		}
	}
    
    public String getDataTable() {
		return dataTable;
	}

	public void setDataTable(String dataTable) {
		this.dataTable = dataTable;
	}

	public int getMaxID() throws SQLException{
		/*int maxID;
		String sqlStatement="select max(id) from "+dataTable;
		preparedStatement=connection.prepareStatement(sqlStatement);
		resultSet=preparedStatement.executeQuery();
		maxID=resultSet.getInt(1);
		JDBCUtils.free(resultSet, preparedStatement, null);*/
		return getMaxID(this.dataTable);
	}
	
	public int getMaxID(String dataTable) throws SQLException{
		int maxID=0;
		String sqlStatement="select max(id) from "+dataTable;
		preparedStatement=connection.prepareStatement(sqlStatement);
		resultSet=preparedStatement.executeQuery();
		if(resultSet.next()){
		    maxID=resultSet.getInt(1);
		}
		JDBCUtils.free(resultSet, preparedStatement, null);
		return maxID;
	}
	
	/**
	 * 根据sql语句和指定的数据库中的条数在uri中生成sequenceFile文件
	 * @param uri 生成文件的uri
	 * @param sqlStatement 传递的sql语句
	 * @param size  指定的数据记录的条数
	 * @throws IOException
	 * @throws SQLException
	 */
	public void createSequenceFile(String uri,String sqlStatement,int size) throws IOException, SQLException{
		Configuration configuration=new Configuration();
    	Path path=new Path(uri);
    	Text keyText=new Text();
    	Text valueText=new Text();
    	String resumeId;
    	String keywords;
    	String assessment;
    	String experience;
    	String project;
    	String education;
    	String skill;
    	Option optPath=SequenceFile.Writer.file(path);
    	Option optKey=SequenceFile.Writer.keyClass(keyText.getClass());
    	Option optValue=SequenceFile.Writer.valueClass(valueText.getClass());
    	SequenceFile.Writer writer=SequenceFile.createWriter(configuration, optPath,optKey,optValue);
    	for(int index=0;index<=size;index+=dataLimit){
    		preparedStatement=connection.prepareStatement(sqlStatement);
    		preparedStatement.setInt(1, index);
    		preparedStatement.setInt(2, index+dataLimit);
    		resultSet=preparedStatement.executeQuery();
    		while(resultSet.next()){
    			resumeId=resultSet.getString("resumeId");
    			keywords=resultSet.getString("keyword");
    			assessment=resultSet.getString("self_assessment");
    			experience=resultSet.getString("brief_work_experience");
    			project=resultSet.getString("brief_project");
    			education=resultSet.getString("brief_edu_experience");
    			skill=resultSet.getString("it_skill");
    			String join="";
    			if(assessment!=null){
    				join=join+assessment;
    			}
    			if(experience!=null){
    				join=join+" "+experience;
    			}
    			if(project!=null){
    				join=join+" "+project;
    			}
    			if(education!=null){
    				join=join+" "+education;
    			}
    			if(!skill.equals("[]")){
    				skill=skill.replaceAll("\\[|\\]", "");
    				join=join+" "+skill;
    			}
    			//直接采用将所有已有关键词与正文关联,不考虑包含关系
    			for(String keyword:keywords.split(" |、|,|，|。|!|;")){
    				if(!keyword.matches(".|=\\w|\\\\|")&&!stopwordSet.contains(keyword)){
    					keyText.set(resumeId+"@"+keyword);
    					valueText.set(join);
    					writer.append(keyText, valueText);
    				}
    			}
    		}
    		JDBCUtils.free(resultSet, preparedStatement, null);
    	}
    	IOUtils.closeStream(writer);
	}
	
   /* public void createSequenceFile(String uri,String sqlStatement) throws IOException, SQLException{
    	Configuration configuration=new Configuration();
//    	FileSystem fileSystem=FileSystem.get(URI.create(uri), configuration);
    	int maxID=getMaxID();
    	Path path=new Path(uri);
    	Text keyText=new Text();
    	Text valueText=new Text();
    	String resumeId;
    	String keywords;
    	String assessment;
    	String experience;
    	String project;
    	String education;
    	String skill;
    	Option optPath=SequenceFile.Writer.file(path);
    	Option optKey=SequenceFile.Writer.keyClass(keyText.getClass());
    	Option optValue=SequenceFile.Writer.valueClass(valueText.getClass());
    	SequenceFile.Writer writer=SequenceFile.createWriter(fileSystem, configuration, path,
				keyText.getClass(), valueText.getClass());
    	SequenceFile.Writer writer=SequenceFile.createWriter(configuration, optPath,optKey,optValue);
    	for(int index=0;index<=maxID;index+=dataLimit){
    		preparedStatement=connection.prepareStatement(sqlStatement);
    		preparedStatement.setInt(1, index);
    		preparedStatement.setInt(2, index+dataLimit);
    		resultSet=preparedStatement.executeQuery();
    		while(resultSet.next()){
    			resumeId=resultSet.getString("resumeId");
    			keywords=resultSet.getString("keyword");
    			assessment=resultSet.getString("self_assessment");
    			experience=resultSet.getString("brief_work_experience");
    			project=resultSet.getString("brief_project");
    			education=resultSet.getString("brief_edu_experience");
    			skill=resultSet.getString("it_skill");
    			String join="";
    			if(assessment!=null){
    				join=join+assessment;
    			}
    			if(experience!=null){
    				join=join+" "+experience;
    			}
    			if(project!=null){
    				join=join+" "+project;
    			}
    			if(education!=null){
    				join=join+" "+education;
    			}
    			if(!skill.equals("[]")){
    				skill=skill.replaceAll("\\[|\\]", "");
    				join=join+" "+skill;
    			}
    			//直接采用将所有已有关键词与正文关联,不考虑包含关系
    			for(String keyword:keywords.split(" |、|,|，|。|!|;")){
    				if(!keyword.matches(".|=\\w|\\\\|")&&!stopwordSet.contains(keyword)){
    					keyText.set(resumeId+"@"+keyword);
    					valueText.set(join);
    					writer.append(keyText, valueText);
    				}
    			}
    		}
    		JDBCUtils.free(resultSet, preparedStatement, null);
    	}
    	IOUtils.closeStream(writer);
    }*/
    
	public void createSequenceFile(String uri,String sqlStatement) throws IOException, SQLException{
		int maxID=getMaxID();
		createSequenceFile(uri, sqlStatement, maxID);
	}
	
	public static void main(String[] args) throws IOException, SQLException {
		String sqlStatement="select resumeId,keyword,self_assessment,brief_work_experience,brief_project,brief_edu_experience,it_skill from fullresume where keyword is not null and self_assessment is not null and id>= ? and id < ?";
		SequenceFileCreation sequenceFileCreation=new SequenceFileCreation();
		sequenceFileCreation.createSequenceFile("/home/liufeng/dataset/sequencefile.dat", sqlStatement);
	}

}
