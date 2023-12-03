package hw7;
/**
 * @author Gretta Halollari
 * 
 * Helper classes that will help you with the sorting.
 */
public interface Comparator {
	
	public class IndexComparator implements Comparator {
		// sort numerically ASCENDING based on index of the WebPage
		public static void compare (WebPage[] pages, int n) {
			int i, j;
			for (i=0; i <= n-2; i++) {
					for (j=n-1; j > i; j--) {
						if (pages[j].getIndex() < pages[j-1].getIndex()) {
							WebPage page = pages[j];
							WebPage page2 = pages[j-1];
							pages[j] = page2;
							pages[j-1] = page;
						}
					}
			}
		
		}
	}
	
	public class URLComparator implements Comparator {
		// sort alphabetically ASCENDING based the URL of the WebPage
		public static void compare (WebPage[] pages, int n) {
			int i, j;
			for (i=0; i <= n-2; i++) {
					for (j=n-1; j > i; j--) {
						char[] ch = pages[j].getUrl().toCharArray();
						char[] ch2 = pages[j-1].getUrl().toCharArray();
						if (ch[0] < ch2[0]) {
							WebPage page = pages[j];
							WebPage page2 = pages[j-1];
							pages[j] = page2;
							pages[j-1] = page;
						}
						else if (ch[0] == ch2[0]) {
							int index = 0;
							for (Character c : ch) {
								if (index < ch2.length) {
									if (c < ch2[index]) {
										WebPage page = pages[j];
										WebPage page2 = pages[j-1];
										pages[j] = page2;
										pages[j-1] = page;
									} else {
										index++;
									}
								}
							}
						}
					}
			}
		
		}
	}
	
	public class RankComparator implements Comparator {
		// sort numerically DESCENDING based on the PageRank of the WebPage
		public static void compare (WebPage[] pages, int n) {
			int i, j;
			for (i=0; i <= n-2; i++) {
					for (j=n-1; j > i; j--) {
						if (pages[j].getRank() > pages[j-1].getRank()) {
							WebPage page = pages[j];
							WebPage page2 = pages[j-1];
							pages[j] = page2;
							pages[j-1] = page;
						}
					}
			}
		
		}
	}

	
}
