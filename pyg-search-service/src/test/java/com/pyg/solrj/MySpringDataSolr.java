package com.pyg.solrj;

import java.util.List;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.HighlightOptions;
import org.springframework.data.solr.core.query.HighlightQuery;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleHighlightQuery;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.HighlightEntry.Highlight;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pyg.pojo.TbItem;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/applicationContext-solr.xml")
public class MySpringDataSolr {

	// 注入solr模板对象
	@Autowired
	private SolrTemplate solrTemplate;

	/**
	 * 需求：添加索引库 solrJ添加索引库步骤： 1，创建solrInputDocument文档对象 2，向文档对象中添加与域字段对应值 3，添加索引库即可
	 * 4，提交 springDataSolr如何实现？
	 * 
	 */
	@Test
	public void addIndex() {

		// 创建javabean对象
		TbItem item = new TbItem();
		item.setId(10000L);
		item.setTitle("齐天大圣孙悟空的净额帮");
		item.setSellPoint("非常大");
		item.setBrand("西游");

		// 保存
		solrTemplate.saveBean(item);
		// 条件
		solrTemplate.commit();

	}

	/**
	 * 需求：根据id查询索引库
	 */
	@Test
	public void getById() {
		TbItem item = solrTemplate.getById(10000L, TbItem.class);
		System.out.println(item);
	}

	/**
	 * 需求：根据id删除
	 */
	@Test
	public void deleById() {
		solrTemplate.deleteById("10000");
		solrTemplate.commit();
	}

	/**
	 * 需求：分页查询
	 */
	@Test
	public void findSolrByPage() {
		
		//创建query对象，封装查询参数 
		Query query = new SimpleQuery("*:*");
		
		//封装查询条件
		//分页查询
		//查询其实页
		query.setOffset(0);
		//每页显示10条数据
		query.setRows(10);
		
		//分页查询
		ScoredPage<TbItem> queryForPage = solrTemplate.queryForPage(query, TbItem.class);
		
		//获取查询结果
		//获取总记录数
		long totalElements = queryForPage.getTotalElements();
		System.out.println("总记录数："+totalElements);
		
		//查询总记录
		List<TbItem> list = queryForPage.getContent();
		System.out.println(list);

	}
	
	
	/**
	 * 需求：条件查询
	 */
	@Test
	public void findSolrByCondition() {
		
		//创建query对象，封装查询参数 
		Query query = new SimpleQuery();
		
		//创建criteria对象，设置条件
		Criteria criteria = new Criteria("item_title").is("孙悟空");
		criteria.contains("齐天大圣");
		//使用solr语法查询
		criteria.expression("*:*");
		
		query.addCriteria(criteria);
		
		//封装查询条件
		//分页查询
		//查询其实页
		query.setOffset(0);
		//每页显示10条数据
		query.setRows(10);
		
		//分页查询
		ScoredPage<TbItem> queryForPage = solrTemplate.queryForPage(query, TbItem.class);
		
		//获取查询结果
		//获取总记录数
		long totalElements = queryForPage.getTotalElements();
		System.out.println("总记录数："+totalElements);
		
		//查询总记录
		List<TbItem> list = queryForPage.getContent();
		System.out.println(list);

	}
	
	/**
	 * 需求：高亮查询
	 */
	@Test
	public void findSolrByHighlight() {
		
		//创建query对象，封装查询参数 
		HighlightQuery query = new SimpleHighlightQuery();
		
		//创建criteria对象，设置条件
		Criteria criteria = new Criteria("item_title").is("孙悟空");
		criteria.contains("齐天大圣");
		//使用solr语法查询
		//criteria.expression("*:*");
		
		query.addCriteria(criteria);
		
		
		//添加高亮
		HighlightOptions highlightOptions = new HighlightOptions();
		highlightOptions.addField("item_title");
		highlightOptions.setSimplePrefix("<font color='red'>");
		highlightOptions.setSimplePostfix("</font>");
		//添加高亮查询
		query.setHighlightOptions(highlightOptions);
		
		//封装查询条件
		//分页查询
		//查询其实页
		query.setOffset(0);
		//每页显示10条数据
		query.setRows(10);
		
		//高亮查询
		HighlightPage<TbItem> highlightPage = solrTemplate.queryForHighlightPage(query, TbItem.class);
		
		//获取查询结果
		//获取总记录数
		long totalElements = highlightPage.getTotalElements();
		System.out.println("总记录数："+totalElements);
		
		//查询总记录
		List<TbItem> list = highlightPage.getContent();
		
		//循环集合，获取每一个对象高亮
		for (TbItem tbItem : list) {
			
			//获取高亮集合
			List<Highlight> highlights = highlightPage.getHighlights(tbItem);
			
			List<String> snipplets = highlights.get(0).getSnipplets();
			
			String hname = snipplets.get(0);
			System.out.println(hname);
			
			
			
		}
		
		System.out.println(list);

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
