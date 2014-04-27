package edu.apsu.csci.rssfdr;

public class Articles {

	private String article_title;
	private String article_url;
	private String article_summary;
	private String link_to_article;

	public Articles(){
	}
	
	public String getArticle_title() {
		return article_title;
	}


	public String getArticle_url() {
		return article_url;
	}


	public String getArticle_summary() {
		return article_summary;
	}


	public String getLink_to_article() {
		return link_to_article;
	}


	public void setArticle_title(String article_title) {
		this.article_title = article_title;
	}


	public void setArticle_url(String article_url) {
		this.article_url = article_url;
	}


	public void setArticle_summary(String article_summary) {
		this.article_summary = article_summary;
	}


	public void setLink_to_article(String link_to_article) {
		this.link_to_article = link_to_article;
	}


	@Override
	public String toString(){
		return article_title;
	}

}
