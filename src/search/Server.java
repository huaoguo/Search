package search;

import java.io.IOException;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import search.db.DBUtils;
import search.model.Dict;
import search.utils.Conventions;
import search.utils.Kongjian;
import search.utils.Xiangliang;
import search.utils.Kongjian.KongjianBuilder;
import search.utils.Xiangliang.XiangliangBuilder;

public class Server {
	private static final Logger logger = LogManager.getLogger(Server.class);

	public static void main(String[] args) throws Exception {
		List<Result> results = search("幽鬼出门装备");
		for (Result result : results) {
			System.out.println("doc: " + result.docId + ", cosineValue: " + result.cosineValue);
		}
	}

	public static List<Result> search(String query) throws IOException, Exception, ClassNotFoundException, SQLException {
		IKSegmenter seg = new IKSegmenter(new StringReader(query), false);
		Lexeme lex = null;
		List<Dict> dicts = new ArrayList<>();
		KongjianBuilder kjBuilder = Kongjian.build();
		while ((lex = seg.next()) != null) {
			Dict dict = DBUtils.queryObject(Dict.class, "value='" + lex.getLexemeText() + "'");
			logger.debug("word:" + dict.getValue() + ",idf:" + dict.getIdf());
			dicts.add(dict);
			kjBuilder.addWeidu(dict.getId());
		}
		Kongjian kj = kjBuilder.toKongjian();
		XiangliangBuilder xlBuilder = kj.buildXiangliang();
		for (Dict dict : dicts) {
			xlBuilder.setValue(dict.getId(), dict.getIdf());
		}
		Xiangliang xl = xlBuilder.toXiangliang();
		StringBuilder sql = new StringBuilder();
		sql.append("select doc.id doc_id,dict.id dict_id,dict.idf * dd.tf3 weight from dict_doc dd "
				+ "inner join dict on dd.dict_id = dict.id " + "inner join doc on dd.doc_id = doc.id "
				+ "where dict.id in (");
		for (Dict d : dicts) {
			sql.append(d.getId()).append(",");
		}
		sql.deleteCharAt(sql.length() - 1);
		sql.append(") order by doc.id,dict.id,weight desc");
		logger.debug(sql);
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:" + Conventions.getDBPath());
		PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		ResultSet rs = pstmt.executeQuery();
		List<Record> records = new ArrayList<>();
		while (rs.next()) {
			Record r = new Record();
			r.docId = rs.getInt("doc_id");
			r.dictId = rs.getInt("dict_id");
			r.weight = rs.getDouble("weight");
			records.add(r);
		}
		logger.debug("命中：" + records.size());
		rs.close();
		pstmt.close();
		conn.close();

		Record previous = null;
		XiangliangBuilder xlBuilder2 = kj.buildXiangliang();
		List<Result> results = new ArrayList<>();
		for (Record record : records) {
			if (previous != null && previous.docId != record.docId) {
				Result result = new Result();
				result.docId = previous.docId;
				result.cosineValue = Xiangliang.Cosine(xl, xlBuilder2.toXiangliang());
				results.add(result);
				xlBuilder2 = kj.buildXiangliang();
			}
			xlBuilder2.setValue(record.dictId, record.weight);
			previous = record;
		}
		Collections.sort(results, new Comparator<Result>() {

			@Override
			public int compare(Result o1, Result o2) {
				if (o1.cosineValue > o2.cosineValue) {
					return -1;
				} else if (o1.cosineValue == o2.cosineValue) {
					return 0;
				} else {
					return 1;
				}
			}

		});
		return results;
	}
	
	public static class Result {
		public int docId;
		double cosineValue;
	}

}

class Record {
	int docId;
	int dictId;
	double weight;
}