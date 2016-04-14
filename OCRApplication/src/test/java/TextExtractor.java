import org.bytedeco.javacpp.*;
import org.bytedeco.javacpp.lept.PIX;
import org.bytedeco.javacpp.tesseract.TessBaseAPI;

import static org.bytedeco.javacpp.lept.*;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import org.json.simple.JSONObject;

import org.apache.commons.io.filefilter.WildcardFileFilter;


public class TextExtractor {

	static String imagesFolder;
	static String format;
	static String exitFolder;

	public static File[] findFilesInFolder(String path,String format){

		File dir = new File(path);
		FileFilter fileFilter = new WildcardFileFilter("*."+format);
		File[] files = dir.listFiles(fileFilter);
		return files;

	}

	public static void givenTessBaseApi_whenImageOcrd(File file, String format,String exitFolder) throws Exception{
		BytePointer outText;
		TessBaseAPI api = new TessBaseAPI();

		// Initialize tesseract-ocr with English, without specifying tessdata path
		if (api.Init(".", "ENG") != 0) {
			System.err.println("Could not initialize tesseract.");
			System.exit(1);
		}		

		PIX image = pixRead(file.getAbsolutePath());
		api.SetImage(image);
		// Get OCR result

		outText = api.GetUTF8Text();
		String generatedText = outText.getString();

		assertTrue(!generatedText.isEmpty());
		//System.out.println(generatedText);
		String fileName= file.getName().split("."+format)[0];

		createTxtFile(generatedText, fileName,exitFolder);

		api.End();
		outText.deallocate();
		pixDestroy(image);
		// Destroy used object and release memory

	}

	public static void createTxtFile(String content, String fileName, String exitFolder) throws Exception {
		//Create text file
		//PrintWriter writer = new PrintWriter(exitFolder+ "/"+ fileName+".json", "UTF-8");

		JSONObject obj = new JSONObject();
		obj.put("Content", content);
		
		FileWriter file = new FileWriter(exitFolder+ "/"+ fileName+".json");

		file.write(obj.toJSONString());
		file.close();
		
		System.out.println("Successfully Copied JSON Object to File...");
		System.out.println("\nJSON Object: " + obj);
	}

	public static void main(String[] args) throws Exception {


		//options
		if (args.length==0)
		{

			imagesFolder =".";
			format ="tif";
			exitFolder=".";

		}
		else
		{

			imagesFolder= args[0];
			format= args[1];
			exitFolder= args[2];

		}

		File[] files= findFilesInFolder(imagesFolder,format);

		for (File file: files){

			givenTessBaseApi_whenImageOcrd(file,format,exitFolder);

		}

	}

}
