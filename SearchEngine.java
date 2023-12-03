package hw7;
/**
 * @author Gretta Halollari
 * 
 * Write a fully-documented class named SearchEngine which will initialize a WebGraph from the appropriate text files and allow the user to search for keywords in the graph. 
 * To keep things interesting, the class should provide functionality to add/remove pages to/from the graph, as well as alter the hyperlinks between pages in the graph.
 */

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

import hw7.Comparator.IndexComparator;
import hw7.Comparator.RankComparator;
import hw7.Comparator.URLComparator;

public class SearchEngine {
	public static final String PAGES_FILE = "/Users/grettahalollari/eclipse-workspace/cse214hw7/src/hw7/pages.txt";
	public static final String LINKS_FILE = "/Users/grettahalollari/eclipse-workspace/cse214hw7/src/hw7/links.txt";
//	public static final String PAGES_FILE = "pages.txt";
//	public static final String LINKS_FILE = "links.txt";
	private static WebGraph web;
	
	public static void main(String[] args) {
		try {
			Scanner input = new Scanner(System.in);
			boolean running = true;
			System.out.println("Loading WebGraph data...");
			web = WebGraph.buildFromFiles(PAGES_FILE, LINKS_FILE);
			System.out.println("Success!");
			while (running) {
				try {
				System.out.println("\nMenu:\n"
					+ "    (AP) - Add a new page to the graph.\n"
					+ "    (RP) - Remove a page from the graph.\n"
					+ "    (AL) - Add a link between pages in the graph.\n"
					+ "    (RL) - Remove a link between pages in the graph.\n"
					+ "    (P)  - Print the graph.\n"
					+ "    (S)  - Search for pages with a keyword.\n"
					+ "    (Q)  - Quit.");
				System.out.println("\nPlease select an option:");
				String answer = input.next().toUpperCase();
				switch (answer) {
				case "AP":
					System.out.println("Enter a URL: ");
					String url = input.next();
					input.nextLine();
					System.out.println("Enter keywords (space-separated): ");
					String keywords = input.nextLine();
					String[] collection = keywords.split(" ");
					LinkedList<String> linkedList = new LinkedList<String>();
					for (String s : collection) {
						linkedList.add(s);
					}
					try {
						web.addPage(url, linkedList);
						System.out.println("\n" + url + " successuflly added to the WebGraph!");
					} catch (IllegalArgumentException e) {
						System.out.println("Error: " + url + " already exists in the WebGraph. Could not add new WebPage.");
					}
					break;
				case "RP":
					System.out.println("Enter a URL:");
					String url2 = input.next();
					web.removePage(url2);
					System.out.println(url2 + " has been removed from the graph!");
					break;
				case "AL":
					System.out.println("Enter a source URL: ");
					String url3 = input.next();
					System.out.println("Enter a destination URL:" );
					String url4 = input.next();
					try {
					web.addLink(url3, url4);
					System.out.println("\nLink successfully added from " + url3 + " to " + url4 + "!");
					} catch (IllegalArgumentException e) {
						String error = e.toString().replace("java.lang.IllegalArgumentException: ", "");
						System.out.println("Error: " + error + " could not be found in the WebGraph.");
					} catch (RepeatException e) {
					System.out.println("Error: link was already established.");
				}
					break;
				case "RL":
					System.out.println("Enter a source URL: ");
					String url5 = input.next();
					System.out.println("Enter a destination URL:" );
					String url6 = input.next();
					web.removeLink(url5, url6);
					System.out.println("\nLink successfully removed from " + url5 + " to " + url6 + "!");
					break;
				case "P":
					System.out.println("\n"
						+ "\n"
						+ "    (I) Sort based on index (ASC)\n"
						+ "    (U) Sort based on URL (ASC)\n"
						+ "    (R) Sort based on rank (DSC)");
					String answer2 = input.next().toUpperCase();
					HashMap<String, WebPage> pages = web.getPages();
					WebPage[] pages2 =  new WebPage[pages.size()];
					int i = 0;
					for (String key : pages.keySet()) {
						pages2[i] = pages.get(key);
						i++;	
					}
					web.setCollection(pages2);
					if (answer2.equals("I")) {
						IndexComparator.compare(pages2, pages2.length);
					}
					else if (answer2.equals("U")) {
						URLComparator.compare(pages2, pages2.length);
					}
					else if (answer2.equals("R")) {
						RankComparator.compare(pages2, pages2.length);
					}
					web.printTable();
					break;
				case "S":
					System.out.println("Search keyword: ");
					String answer3 = input.next();
					HashMap<String, WebPage> page4 = web.getPages();
					HashMap<String, WebPage> search = new HashMap<String, WebPage>();
					for (String key : page4.keySet()) {
						LinkedList<String> words = page4.get(key).getKeywords();
						for (String word : words) {
							//System.out.println(word);
							if (word.equals(answer3)) {
								search.put(key, page4.get(key));
							}
						}	
					}
					WebPage[] sort =  new WebPage[search.size()];
					int j = 0;
					for (String key : search.keySet()) {
						//System.out.println(j);
						sort[j] = search.get(key);
						j++;	
					}
					RankComparator.compare(sort, sort.length);
					String str = "";
					int count = 1;
					for (int index = 0; index < sort.length; index++) {
						//System.out.println("hi");
						str += String.format("%-3s%-2s%-7s%-3s%-7s" , String.valueOf(count++), "|", String.valueOf(sort[index].getRank()), "|", sort[index].getUrl() + "\n");
						
					}
					if (str.equals("")) {
						System.out.println("No search results found for the keyword " + answer3);
					}
					else  {
						System.out.println("Rank   PageRank    URL");
						System.out.println("---------------------------------------------");
						System.out.println(str);
					}
					break;
				case "Q":
					System.out.println("Goodbye.");
					input.close();
					running = false;
				}
			} catch (Exception e) {
				System.out.println("Error: Illegal Entry. Try again.");
			}
			}
	} catch (Exception e) {
		System.out.println("Error: File did not open. Try again.");
	}
	}
}
