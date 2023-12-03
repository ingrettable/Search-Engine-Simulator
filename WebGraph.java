package hw7;
/**
 * @author Gretta Halollari
 * 
 * This class creates an WebGraph, which organizes the WebPage objects as a directed graph. 
 * The class should contain a Collection of WebPages member variable called pages and a 2-dimensional array of integers (adjacency matrix) member variable called links. 
 * The matrix should be of size MAX_PAGES x MAX_PAGES, where MAX_PAGES is a static integer constant defined as 40. 
 */
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class WebGraph {
	
	public static final int MAX_PAGES = 40;
	private HashMap<String, WebPage> pages;
	private WebPage[] collection;
	private int[][] edges = new int[MAX_PAGES][MAX_PAGES]; 
	private int index;
	
	/**
	 * Constructor, which creates a WebGraph
	 */
	public WebGraph() {
		
	}
	
	/**
	 * The WebGraph is created with the following parameter:
	 * @param pages
	 */
	public WebGraph(HashMap<String, WebPage> pages) {
		this.pages = pages;
	}
	
	/**
	 * The WebGraph is created with the following parameter:
	 * @param pages
	 */
	public WebGraph(HashMap<String, WebPage> pages, int[][] edges, int index) {
		this.pages = pages;
		this.edges = edges;
		this.index = index;
	}
	
	/**
	 * Constructs a WebGraph object using the indicated files as the source for pages and edges.
	 * @param pagesFile, String of the relative path to the file containing the page information.
	 * @param linksFile, String of the relative path to the file containing the link information.
	 * @return graph, constructed and initialized based on the text files.
	 * @throws IllegalArgumentException, Thrown if either of the files does not reference a valid text file, or if the files are not formatted correctly.
	 */
	public static WebGraph buildFromFiles(String pagesFile, String linksFile) throws IllegalArgumentException {
		try {
		Scanner reader = new Scanner(new File(pagesFile));
		int index = 0;
		HashMap<String, WebPage> pages = new LinkedHashMap<>();
		int[][] edges = new int[MAX_PAGES][MAX_PAGES]; 
		while (reader.hasNext()) {
			String data = reader.nextLine();
			String[] collection = data.trim().split(" ");
			//System.out.println(data);
			LinkedList<String> keywords = new LinkedList<String>();
			for (int i = 1; i < collection.length; i++) {
				keywords.add(collection[i]);
			}
			WebPage page = new WebPage(collection[0], index, 0, keywords);
			pages.put(collection[0], page);
			//System.out.println(pages.toString());
			index++;
		}
		index--;
		Scanner reader2 = new Scanner(new File(linksFile));
		while (reader2.hasNext()) {
			String word = reader2.nextLine();
			//System.out.println(word);
			String[] collection = word.trim().split(" ");
			WebPage edit = pages.get(collection[0]);
			int one = edit.getIndex();
			WebPage edit2 = pages.get(collection[1]);
			LinkedList<Integer> list = edit.getLinks();
			list.add(edit2.getIndex());
			edit.setLinks(list);
			int two = edit2.getIndex();
			edges[one][two] = 1;
			//edit2.setRank(edit2.getRank() + 1);
		}
		WebGraph graph = new WebGraph(pages, edges, index);
		graph.updatePageRanks();
		return graph;
		} catch(Exception e) {
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * Adds a page to the WebGraph.
	 * @param url, webpage (must not already exist in the WebGraph).
	 * @param keywords, associated with the WebPage.
	 * @throws IllegalArgumentException, If url is not unique and already exists in the graph, or if either argument is null.
	 */
	public void addPage(String url, LinkedList<String> keywords) throws IllegalArgumentException {
		if (url.equals(null)||keywords.equals(null) || pages.containsKey(url)) {
			throw new IllegalArgumentException();
		}
		else {
			this.index++;
			WebPage page = new WebPage(url, this.index, 0, keywords);
			pages.put(url, page);
			//this.updatePageRanks();
		}
	}
	
	/**
	 * Adds a link from the WebPage with the URL indicated by source to the WebPage with the URL indicated by destination
	 * @param source, the URL of the page which contains the hyperlink to destination.
	 * @param destination, the URL of the page which the hyperlink points to.
	 * @throws IllegalArgumentException, If either of the URLs are null or could not be found in pages.
	 * @throws RepeatException, If link is repeated.
	 */
	public void addLink(String source, String destination) throws IllegalArgumentException, RepeatException {
		if (source.equals(null)||destination.equals(null) || !pages.containsKey(source) || !pages.containsKey(destination)) {
			if (!pages.containsKey(source)) {
				throw new IllegalArgumentException(source);
			}
			if (!pages.containsKey(destination)) {
				throw new IllegalArgumentException(destination);
			}
			else throw new IllegalArgumentException("Null");
		}
		else {
			WebPage edit = this.pages.get(source);
			int one = edit.getIndex();
			WebPage edit2 = this.pages.get(destination);
			LinkedList<Integer> list = edit.getLinks();
			if (list.contains(edit2.getIndex())) {
				throw new RepeatException();
			}
			else {
			list.add(edit2.getIndex());
			Collections.sort(list);			
			edit.setLinks(list);
			int two = edit2.getIndex();
			this.edges[one][two] = 1;
			//edit2.setRank(edit2.getRank() + 1);
			this.updatePageRanks();
			}
		}
	}
	
	/**
	 * Removes the WebPage from the graph with the given URL.
	 * @param url, The URL of the page to remove from the graph.
	 */
	public void removePage(String url) {
		if (!url.equals(null) && this.pages.containsKey(url)) {
			WebPage page = this.pages.get(url);
			int one = page.getIndex();
			for (int i = 0; i < this.edges[one].length; i++) {
				edges[one][i] = 0;
				
			}
			this.updatePageRanks();
			for (String s : this.pages.keySet()) {
				LinkedList<Integer> list = this.pages.get(s).getLinks();
				LinkedList<Integer> list2 = new LinkedList<Integer>();
				for (Integer i : list) {
					if (i != page.getIndex()) {
						if (i < page.getIndex()) {
							list2.add(i);
						}
						else {
							list2.add(i-1);
						}
					}
				}
			
				this.pages.get(s).setLinks(list2);
				if (this.pages.get(s).getIndex() > page.getIndex()) {
					this.pages.get(s).setIndex(this.pages.get(s).getIndex() - 1);
					
				}
	
			}
			this.pages.remove(url);
		}
	}
	
	/**
	 * Removes the link from WebPage with the URL indicated by source to the WebPage with the URL indicated by destination.
	 * @param source, The URL of the WebPage to remove the link.
	 * @param destination, The URL of the link to be removed.
	 */
	public void removeLink(String source, String destination) {
		if (!source.equals(null) && !destination.equals(null) && pages.containsKey(source) && pages.containsKey(destination)) {
			WebPage edit = this.pages.get(source);
			int one = edit.getIndex();
			WebPage edit2 = this.pages.get(destination);
			LinkedList<Integer> list = edit.getLinks();
			list.removeFirstOccurrence(edit2.getIndex());
			Collections.sort(list);			
			edit.setLinks(list);
			int two = edit2.getIndex();
			this.edges[one][two] = 0;
			//edit2.setRank(edit2.getRank() - 1);
			this.updatePageRanks();
		}
	}
	
	/**
	 * Calculates and assigns the PageRank for every page in the WebGraph.
	 */
	public void updatePageRanks() {
		for (int i = 0; i < this.edges.length; i++) {
			int count = 0;
			for (int j = 0; j < this.edges[i].length; j++) {
				if (edges[j][i] == 1) {
					count++;
					//System.out.println(count);
				}
			}
			for (String key : this.pages.keySet()) {
				if (this.pages.get(key).getIndex() == i) {
					//System.out.println(i + " " + count);
					this.pages.get(key).setRank(count);
				}
			}
			if (i == this.index) {
				break;
			}
		}
	}
	
	/**
	 * Prints the WebGraph in tabular form.
	 */
	public void printTable() {
		String str = "";
		str += "\nIndex     URL               PageRank  Links               Keywords";
		str += "\n------------------------------------------------------------------------------------------------------";
		for (WebPage page: this.collection) {
			str += "\n";
			str += page.toString();
		}
		System.out.println(str);
	}
	
	/**
	 * Function returns the hashmap of the WebPages
	 * @return pages
	 */
	public HashMap<String, WebPage> getPages() {
		return pages;
	}
	
	/**
	 * Function returns the edges of the WebPages
	 * @return edges
	 */
	public int[][] getEdges() {
		return edges;
	}
	
	/**
	 * Function returns the array of the WebPages
	 * @return collection
	 */
	public WebPage[] getCollection() {
		return collection;
	}
	
	/**
	 * Sets the array of WebPages.
	 * @param collection
	 */
	public void setCollection(WebPage[] collection) {
		this.collection = collection;
	}

}
