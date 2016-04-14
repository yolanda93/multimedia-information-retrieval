import org.bytedeco.javacpp.*;
import org.bytedeco.javacpp.lept.PIX;
import org.bytedeco.javacpp.tesseract.TessBaseAPI;

import static org.bytedeco.javacpp.lept.*;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileFilter;
import java.io.PrintWriter;

import org.apache.commons.io.filefilter.WildcardFileFilter;


public class ReadImage {

	public static File[] findFilesInFolder(String path,String format){

		File dir = new File(path);
		FileFilter fileFilter = new WildcardFileFilter("*."+format);
		File[] files = dir.listFiles(fileFilter);
		return files;

	}

	public static void givenTessBaseApi_whenImageOcrd(File file, String format) throws Exception{
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
										
		createJSONFile(generatedText, fileName);
		
		api.End();
		outText.deallocate();
		pixDestroy(image);
		// Destroy used object and release memory

	}

	public static void createJSONFile(String content, String fileName) throws Exception {
		//Create text file
		PrintWriter writer = new PrintWriter(fileName+".txt", "UTF-8");
		writer.println(content);
		writer.close();

	}

	public static void main(String[] args) throws Exception {

		//options
		String path =".";
		String format ="tif";

		File[] files= findFilesInFolder(path,format);

		for (File file: files){
			givenTessBaseApi_whenImageOcrd(file,format);
		}
	}

}
