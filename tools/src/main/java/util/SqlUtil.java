package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import base.DbPool;

public class SqlUtil extends DbPool {

	public Logger log = Logger.getLogger(this.toString());

	public Map<String, String> getQueryMap(String sql){

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		conn = getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				map.put(rs.getString(1), rs.getString(2));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			realeaseRs(rs);
			realeaseResource(ps, conn);
		}
		return map;
	}

	public List<List<String>> getQueryList(String sql){
		
		Connection conn = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<List<String>> list = new ArrayList<List<String>>();
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			int cloumn = rs.getMetaData().getColumnCount();
			while (rs.next()) {
				List<String> li = new ArrayList<String>(); 
				for (int i = 1; i <= cloumn; i++) {
					li.add(rs.getString(i));
				}	
				list.add(li);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			realeaseRs(rs);
			realeasePs(ps);
			realeaseConn(conn);
		}
		return list;
	}
	
	public int update(String sql) {

		Connection conn = null;
		PreparedStatement ps = null;
		int rs = 0;
		conn = getConnection();
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			realeaseResource(ps, conn);
		}
		return rs;
	}

	public int insertSql(String sql, List<String> args) {

		log.info(sql);
		if(args!=null) {
			log.info(args.toString());
		}
		Connection conn = getConnection();
		int i = 0;
		PreparedStatement pstmt = null;
		try {
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			if(args!=null) {
				Iterator<String> it = args.iterator();
				while (it.hasNext()) {
					pstmt.setString(++i, it.next());
				}
			}
			pstmt.execute();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			realeaseResource(pstmt, conn);
		}
		return i;
	}
	
	public Boolean recordExist(String sql){

		log.info(sql);
		
		Connection conn = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean bl = false;
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			rs.next();
			bl = rs.getString(1).equals("0")?false:true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			realeasePs(ps);
			realeaseRs(rs);
			realeaseConn(conn);
		}
		return bl;
	}
	public Boolean excuteDel(String sql){

		Connection conn = getConnection();
		PreparedStatement ps = null;
		boolean rs = false;
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			realeasePs(ps);
			realeaseConn(conn);
		}
		return rs;
	}
}
