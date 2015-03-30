package spring.test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

/**
 * @author liufeng E-mail:fliu.Dancer@wind.com.cn
 * @version Time:Jul 24, 2014 8:10:29 PM
 * @Description
 */
public class JDBCTemplateApplication {

	public static void main(String[] args) {
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		dataSource.setDriverClass(com.mysql.jdbc.Driver.class);
		dataSource.setUsername("root");
		dataSource.setUrl("jdbc:mysql://localhost/resume");
		dataSource.setPassword("liufeng");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		System.out.println("Creating tables");
		//mysql语法
		jdbcTemplate.execute("drop table if exists customers");
		jdbcTemplate.execute("create table customers(" + "id int, first_name varchar(255), last_name varchar(255))");

		String[] names = "John Woo;Jeff Dean;Josh Bloch;Josh Long".split(";");
		for (String fullname : names) {
			String[] name = fullname.split(" ");
			System.out.printf("Inserting customer record for %s %s\n", name[0], name[1]);
			System.out.println(jdbcTemplate.update("INSERT INTO customers(first_name,last_name) values(?,?)", name[0], name[1]));		
			}

		System.out.println("Querying for customer records where first_name = 'Josh':");
		List<Customer> results = jdbcTemplate.query("select * from customers where first_name = ?", new Object[] { "Josh" },
				new RowMapper<Customer>() {
					@Override
					public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
						return new Customer(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"));
					}
				});

		for (Customer customer : results) {
			System.out.println(customer);
		}
	}
}
