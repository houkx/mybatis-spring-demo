package com.mq.demo.springmybatis.dbtest;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

public class SqlTest {

	@Test
	public void testSql() {
		String sql = "select count(1) from users where id in? AND role=? AND names in (?)";
		System.out.println(Arrays.toString(cache(sql)));
	}

	private static SqlSegEntry[] cache(String sql) {
		String[] segs = sql.split("[?]");
		int countIn = 0;
		ArrayList<SqlSegEntry> list = new ArrayList<>(segs.length);
		StringBuilder sb = new StringBuilder(sql.length());
		for (int i = 0; i < segs.length; i++) {
			String s = segs[i], ts = s.trim().toLowerCase(), tm = ts;
			if (ts.endsWith("(")) {
				tm = ts.substring(0, ts.length() - 1).trim();
			}
			if (tm.endsWith(" in")) {
				int st = 0;
				if (ts.charAt(0) == ')') {
					st = s.indexOf(')') + 1;
				}
				if (tm.length() != ts.length()) {
					s = s.substring(st, s.lastIndexOf('('));
				} else if (st > 0) {
					s = s.substring(st);
				}
				sb.append(s);
				SqlSegEntry e = new SqlSegEntry();
				e.index = i;
				e.segment = sb.toString();
				list.add(e);
				sb.delete(0, sb.length());
				countIn++;
			} else {
				if (ts.charAt(0) == ')') {
					s = s.substring(s.indexOf(')') + 1);
				}
				sb.append(s);
				if (i < segs.length - 1) {
					sb.append('?');
				}
			}
		}
		if (countIn == 0) {
			return null;
		}
		if (sb.length() > 0) {
			SqlSegEntry e = new SqlSegEntry();
			e.segment = sb.toString();
			list.add(e);
		}
		sb = null;
		return list.toArray(new SqlSegEntry[list.size()]);
	}

	// --------
	private static class SqlSegEntry {
		String segment;
		int index = -1;

		@Override
		public String toString() {
			if (index < 0) {
				return "{\"segment\" : \"" + segment + "\" }";
			}
			return "{\"segment\" : \"" + segment + "\", \"index\" : " + index + "}";
		}
	}
}
