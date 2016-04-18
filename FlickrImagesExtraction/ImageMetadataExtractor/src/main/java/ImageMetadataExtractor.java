
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;

import com.drew.imaging.png.*;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

/**
 * @author Yolanda de la Hoz, Renzo Verastegui
 */
public class ImageMetadataExtractor
{
    /**
     * @param args command line parameters
     */
    public static void main(String[] args)
    {
        if( args.length <= 1 )
        {
            System.out.println( "Usage: ImageMetadataExtractor <image-file> <output-path>" );
            System.exit( 0 );
        }
        
        String filename = args[ 0 ];
        String outPath = args[ 1 ];
        System.out.println( "Filename: " + filename );
        
        File file = new File( filename );
        System.out.println(file.exists());
        
        try {
            Metadata metadata = PngMetadataReader.readMetadata(file);

            String [] metadataText=print(metadata);
            writeJSON(filename,metadataText,outPath);
            
        } catch (PngProcessingException e) {
        	e.printStackTrace();
        } catch (IOException e) {
        	e.printStackTrace();
        }
    }

    private static String [] print(Metadata metadata)
    {
        System.out.println("-----Reading metadata directory objects----");
        String [] metadataText = null;
        for (Directory directory : metadata.getDirectories()) {
         metadataText = new String[directory.getTagCount()];
         if(directory != null){

            // Each Directory stores values in Tag objects
        	 int i=0;
            for (Tag tag : directory.getTags()) {
                System.out.println(tag);
                metadataText[i] = tag.toString();
                i++;
            }

            // Print error messages
            if (directory.hasErrors()) {
                for (String error : directory.getErrors()) {
                    System.err.println("ERROR: " + error);
                }
            }
        }
      }
       return metadataText;
    }
    
    public static void writeJSON(String imageName,String [] metadata, String outPath) {
		MetadataFeature features = new MetadataFeature(imageName,metadata);

		ObjectMapper mapper = new ObjectMapper();
		String jsonInString;
		try {
			jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(features);
			System.out.println(jsonInString);
			FileWriter jsonfile = new FileWriter(outPath);
			jsonfile.write(jsonInString);
			jsonfile.flush();
		} catch (IOException e) {
			e.printStackTrace();	
		}


	}
}