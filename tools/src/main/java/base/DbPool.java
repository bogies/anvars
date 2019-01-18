package base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;

import com.zaxxer.hikari.HikariDataSource;

@Component
public class DbPool{

	public Logger log = Logger.getLogger(this.toString());

	private static HikariDataSource ds;

	public DbPool() {
		log.info("初始化 Mysql 线程池");
		try {
			ds = new HikariDataSource();
			ds.setDriverClassName("com.mysql.jdbc.Driver");
			ds.setJdbcUrl(Term.DbPool_Url.val());
			ds.setUsername(Term.DbPool_UserName.val());
			ds.setPassword(Term.DbPool_Password.val());
			ds.setAutoCommit(true);
			ds.setReadOnly(false); // 连接只读数据库时配置为true， 保证安全
			ds.setConnectionTimeout(30000); // 等待连接池分配连接的最大时长（毫秒），超过这个时长还没可用的连接则发生SQLException， 缺省:30秒
			ds.setIdleTimeout(600000); // 一个连接idle状态的最大时长（毫秒），超时则被释放（retired），缺省:10分钟
			ds.setMaxLifetime(3600000); // 一个连接的生命时长（毫秒），超时而且没被使用则被释放（retired），缺省:30分钟，建议设置比数据库超时时长少30秒
			ds.setMaximumPoolSize(100); // 连接池中允许的最大连接数。缺省值：10；推荐的公式：((core_count * 2) + effective_spindle_count)

			log.info("初始化连接数据库-->>  " + ds.getJdbcUrl());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 从连接池返回一个连接
	public synchronized Connection getConnection() {
		// log.info("获取数据库连接");
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	// 释放通道资源
	public void realeaseResource(PreparedStatement ps, Connection conn) {

		// log.info("数据库通道资源释放");
		if (null != ps) {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 释放结果集资源
	public void realeaseRs(ResultSet rs) {

		if (null != rs) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void realeasePs(PreparedStatement ps) {

		if (null != ps) {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public void realeaseConn(Connection conn) {

		if (null != conn) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}