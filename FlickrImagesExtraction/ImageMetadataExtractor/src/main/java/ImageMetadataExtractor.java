
import java.io.File;
import java.io.IOException;
import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
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
        if( args.length == 0 )
        {
            System.out.println( "Usage: ImageMetadataExtractor <image-file>" );
            System.exit( 0 );
        }
        
        String filename = args[ 0 ];
        System.out.println( "Filename: " + filename );
        
        File file = new File( filename );
        
        try {
            Metadata metadata = JpegMetadataReader.readMetadata(file);

            print(metadata);
        } catch (JpegProcessingException e) {
            // handle exception
        } catch (IOException e) {
            // handle exception
        }
    }

    private static void print(Metadata metadata)
    {
        System.out.println("-----Reading metadata directory objects----");
  
        for (Directory directory : metadata.getDirectories()) {
         if(directory != null){

            // Each Directory stores values in Tag objects
            for (Tag tag : directory.getTags()) {
                System.out.println(tag);
            }

            // Print error messages
            if (directory.hasErrors()) {
                for (String error : directory.getErrors()) {
                    System.err.println("ERROR: " + error);
                }
            }
        }
      }
    }
}