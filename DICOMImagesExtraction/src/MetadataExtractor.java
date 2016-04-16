import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import org.dcm4che2.data.DicomElement;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.Tag;
import org.dcm4che2.io.DicomInputStream;
import org.dcm4che2.util.TagUtils;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.impl.DefaultPrettyPrinter;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;


/**
 * @author Yolanda de la Hoz, Renzo Verastegui
 */
public class MetadataExtractor {

	private static DicomInputStream dis;
	private boolean isSQ;
	
    /**
     * @param args command line parameters
     */
    public static void main(String[] args) throws IOException
    {

        if( args.length < 1)
        {
            System.out.println( "Usage: MetadataExtractor <dicom-file-path>, <json-file-name> ");
            System.exit( 0 );
        }   
        
        String filename = args[ 0 ];  
        String jsonname = args[ 1 ];
        
        System.out.println( "Reading from file: " + filename );

    	File file = new File(filename);
    	dis = new DicomInputStream(file);
    	DicomObject DicomObject = dis.readDicomObject(); // Transform into a DicomObject
    	FileWriter jsonfile = new FileWriter(jsonname);
        ObjectMapper mapper = new ObjectMapper();
	    ObjectWriter writer = mapper.writer();
    	extractHeaderInfo(DicomObject,jsonfile,writer);
    }
    
    
    public static void extractHeaderInfo(DicomObject object, FileWriter jsonfile,ObjectWriter writer) {
    	   object.remove(Tag.PixelData); // Remove pixel data
    	   Iterator iter = object.datasetIterator();
    	   while(iter.hasNext()) {
    	      DicomElement element = (DicomElement) iter.next();
    	      int tag = element.tag();
    	      try {  
    	         String tagName = object.nameOf(tag);
    	         String tagAddr = TagUtils.toString(tag);
    	         String tagVR = object.vrOf(tag).toString();
    	         
    	         if (tagVR.equals("SQ")) {
    	            if (element.hasItems()) {
    	            	
    	               System.out.println(tagAddr +" ["+  tagVR +"] "+ tagName);
    	               HeaderElement jsonElement = new HeaderElement(tagAddr,tagVR,tagName,"");
    	    	       writeJSON(jsonElement,jsonfile,writer);
    	               extractHeaderInfo(element.getDicomObject(),jsonfile,writer);
    	               continue;
    	            }
    	         }    
    	         String tagValue = object.getString(tag);  
    	         HeaderElement jsonElement = new HeaderElement(tagAddr,tagVR,tagName,tagValue);
    	         writeJSON(jsonElement,jsonfile,writer);
    	         System.out.println(tagAddr +" ["+ tagVR +"] "+ tagName +" ["+ tagValue+"]");
    	      } catch (Exception e) {
    	         e.printStackTrace();
    	      }finally {
    	    	  org.dcm4che2.util.CloseUtils.safeClose(dis);
    	    	  
    	      }
    	   }  
    	}

    public static void writeJSON(HeaderElement jsonElement,FileWriter jsonfile,ObjectWriter writer) throws JsonGenerationException, JsonMappingException, IOException{
    	 String json = writer.writeValueAsString(jsonElement);   			
		 jsonfile.write(json);
		 jsonfile.write("\n");
		 jsonfile.flush();
    }
}