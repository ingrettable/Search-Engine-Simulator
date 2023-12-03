package hw7;
/**
 * @author Gretta Halollari
 * 
 * This class creates an WebPage, which represents a hyperlinked document. 
 */

import java.util.LinkedList;

public class WebPage {
	
	private String url;
	private int index;
	private int rank;
	private LinkedList<String> keywords;
	private LinkedList<Integer> links;
	
	/**
	 * The constructor.
	 */
	public WebPage() {
		
	}
	
	/**
	 * @param url creates WebPage with given URL.
	 */
	public WebPage(String url) {
		this.url = url;
	}
	
	/**
	 * Creates WebPage with the following parameters.
	 * @param url
	 * @param index
	 * @param rank
	 * @param keywords
	 */
	public WebPage(String url, int index, int rank, LinkedList<String> keywords) {
		this.url = url;
		this.index = index;
		this.rank = rank;
		this.keywords = keywords;
		this.links = new LinkedList<Integer>();
	}
	
	/**
	 * Prints the URL of the document as well as all of the associated keywords in a neat tabular form.
	 */
	public String toString() {
		String str = "";
		str += String.format("%-3s%-2s%-23s%-2s%-7s%-2s%-20s%-2s%-10s", String.valueOf(this.index), "|", this.url, "|" , String.valueOf(this.rank), "|" , this.links.toString().replace("]", "").replace("[", ""), "|", this.keywords.toString().replace("]", "").replace("[", ""));
		return str;
	}
	
	/**
	 * @return URL of the document. 
	 */
	public String getUrl() {
		return url;
	}
	/*
	 * @param URL sets the Object's URL
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	
	/**
	 * @return the index of the WebPage
	 */
	public int getIndex() {
		return index;
	}
	
	/**
	 * @param index sets the index of the WebPage
	 */
	public void setIndex(int index) {
		this.index = index;
	}
	
	/**
	 * @return Linked list of the keywords. 
	 */
	public LinkedList<String> getKeywords() {
		return keywords;
	}
	
	/**
	 * @param keywords sets the keywords of the WebPage.
	 */
	public void setKeywords(LinkedList<String> keywords) {
		this.keywords = keywords;
	}
	
	/**
	 * @return the rank of the WebPage
	 */
	public int getRank() {
		return rank;
	}
	
	/**
	 * @param rank, set the rank of the WebPage
	 */
	public void setRank(int rank) {
		this.rank = rank;
	}
	
	/**
	 * @return the links of the WebPage
	 */
	public LinkedList<Integer> getLinks() {
		return links;
	}
	
	/**
	 * @param links, sets links of the WebPage
	 */
	public void setLinks(LinkedList<Integer> links) {
		this.links = links;
	}
	

}
