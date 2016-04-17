package upm.master;

import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

public class MultimediaRetrievalApp {

	//1 /Users/renzo.verastegui/information-retrieval-multimedia/LuceneApplication/Data /Users/renzo.verastegui/information-retrieval-multimedia/LuceneApplication/Index
	//2 /Users/renzo.verastegui/information-retrieval-multimedia/LuceneApplication/Index phase

	static String indexDir; 
	static String dataDir;
	static Indexer indexer;
	static Searcher searcher;

	public static void main(String[] args) {

		if (args.length==0){

			System.out.println(" - Modes: \n1 (Indexer) \n - dataDir: The directory path with the data to be indexed. \n - indexDir: The directory path to store the indexed data.\n2 (Searcher)\n - indexDir: The directory path where is stored the indexed data. \n - word: The word to search.");
		}
		else{

			MultimediaRetrievalApp tester;
			try {
				tester = new MultimediaRetrievalApp();
				System.out.println(args[0]+" "+args[1]+ " "+ args[2]);
				if (args[0].equals("1")) {

					dataDir= args[1];
					indexDir=args[2];
					
					tester.createIndex();

				}

				else if (args[0].equals("2")) {
					
					indexDir=args[1];
					tester.search(args[2]);

				}

			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}

	private void createIndex() throws IOException{
		
		indexer = new Indexer(indexDir);
		int numIndexed;
		long startTime = System.currentTimeMillis();	
		numIndexed = indexer.createIndex(dataDir, new TextFileFilter());
		long endTime = System.currentTimeMillis();
		indexer.close();
		
		System.out.println(numIndexed+" File indexed, time taken: " +(endTime-startTime)+" ms");		
	
	}

	private void search(String searchQuery) throws IOException, ParseException{
		
		
		searcher = new Searcher(indexDir);
		long startTime = System.currentTimeMillis();
		TopDocs hits = searcher.search(searchQuery);
		long endTime = System.currentTimeMillis();

		System.out.println("Searched term: "+ searchQuery);
		
		System.out.println(hits.totalHits + " documents found. Time :" + (endTime - startTime));
		for(ScoreDoc scoreDoc : hits.scoreDocs) {
			Document doc = searcher.getDocument(scoreDoc);
			System.out.println("File: " + doc.get(LuceneConstants.FILE_NAME));
		}
		searcher.close();
	}
}