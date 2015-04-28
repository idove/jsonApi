/**
 * 
 */
package com.idove.utils;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * @author gery
 * @date 2012-11-2
 */
public class JsonUtil {

	private static ObjectMapper mapper = new ObjectMapper();

	/**
	 * 把 java 对象转化为 json 对象
	 */
	public static <T> String java2json(T t) {
		String jsonString = null;
		try {
			jsonString = mapper.writeValueAsString(t); // 把JAVA对象转化为json字符串
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return jsonString;
	}

	/**
	 * 把 json 对象转化为 JAVA 对象
	 * 
	 * -----------------------------------------------------------------------
	 * 说明1: 1. json 对象中的属性 在 java 对象中 必须存在, 否则会报错; 2. java 对象中 可以 包含 json 对象中
	 * 没有的属性.
	 * 
	 * 以上两点, 可以总结如下: json 对象中的属性列表 必须是 java 对象中的属性列表 的一个子集 (子集的特例是: 自己是自己的子集)
	 * 
	 * 用途: 把简单的 json 对象 转成 简单的 JavaBean 对象, 例如: Apple apple =
	 * JsonUtil.json2java(json, Apple.class);
	 * -----------------------------------------------------------------------
	 * 
	 * 说明2: 如果 json 对象比较复杂, 可以先转成 Map 对象, 再操作 Map 对象. 这种方式不太 OO, 但可以说是 "万能的"
	 * 转换方法
	 * 
	 * 用途: 把复杂的 json 对象 转成 Map 对象, 例如: Map<String, Object> map =
	 * JsonUtil.json2java(json, Map.class);
	 * -----------------------------------------------------------------------
	 * 
	 * 说明3: 如果 json 对象比较复杂, 也可以 转成 复杂的 JavaBean 对象. 这种方式比较 OO, 但要求写一个复杂的
	 * JavaBean 对象, 来与 json 对象 匹配
	 * 
	 * 用途: 把复杂的 json 对象 转成 复杂的 JavaBean 对象, 例如: JsonExampleVo vo =
	 * JsonUtil.json2java(json, JsonExampleVo.class);
	 * 
	 */
	public static <T> T json2java(String json, Class<T> valueType) {
		T obj = null;
		try {
			obj = mapper.readValue(json, valueType);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return obj;
	}
	
	public static void main(String[] args) {
//		String fieldName = "text"; 
//		//检索内容 
//		String text = "IK Analyzer是一个结合词典分词和文法分词的中文分词开源工具包。它使用了全新的正向迭代最细粒度切分算法。"; 
//		//实例化IKAnalyzer分词器 
//		IKAnalyzer analyzer = new IKAnalyzer(); 
//		Directory directory = null; 
//		IndexWriter iwriter = null; 
//		IndexReader ireader = null; 
//		IndexSearcher isearcher = null;
//		
//		try {
//			//建立内存索引对象 
//			directory = new RAMDirectory(); 
//			//配置IndexWriterConfig 
//			IndexWriterConfig iwConfig = new IndexWriterConfig(Version.LUCENE_34 , analyzer); 
//			iwConfig.setOpenMode(OpenMode.CREATE_OR_APPEND); 
//			iwriter = new IndexWriter(directory , iwConfig); 
//			//写入索引 
//			Document doc = new Document(); 
//			doc.add(new Field("ID", "10000", Field.Store.YES, Field.Index.NOT_ANALYZED)); 
//			doc.add(new Field(fieldName, text, Field.Store.YES, Field.Index.ANALYZED)); 
//			iwriter.addDocument(doc); iwriter.close(); 
//			//搜索过程********************************** 
//			//实例化搜索器 ireader = IndexReader.open(directory);
//			isearcher = new IndexSearcher(ireader); String keyword = "中文分词工具包"; 
//			//使用QueryParser查询分析器构造Query对象
//			QueryParser qp = new QueryParser(Version.LUCENE_34, fieldName, analyzer); 
//			qp.setDefaultOperator(QueryParser.AND_OPERATOR); 
//			Query query = qp.parse(keyword); 
//			//搜索相似度最高的5条记录
//			TopDocs topDocs = isearcher.search(query , 5); 
//			System.out.println("命中：" + topDocs.totalHits); 
//			//输出结果 
//			ScoreDoc[] scoreDocs = topDocs.scoreDocs; 
//			for (int i = 0; i < topDocs.totalHits; i++){ 
//				Document targetDoc = isearcher.doc(scoreDocs[i].doc); 
//				System.out.println("内容：" + targetDoc.toString()); 
//			}
//		} catch (Exception e) {
//		}
	}
}
