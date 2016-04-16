import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.dcm4che2.data.DicomElement;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.Tag;
import org.dcm4che2.io.DicomInputStream;
import org.dcm4che2.util.TagUtils;

public class MetadataExtractor {

	private static DicomInputStream dis;
	
    public static void main(String[] args) throws IOException
    {
        if( args.length == 0 )
        {
            System.out.println( "Usage: MetadataExtractor <dicom-image-file>" );
            System.exit( 0 );
        }   
        
        String filename = args[ 0 ];
        System.out.println( "Filename: " + filename );

    	File file = new File(filename);
    	dis = new DicomInputStream(file);
    	DicomObject object = dis.readDicomObject(); // Transform into a DicomObject
    	listHeader(object);
    }
    
    
    public static void listHeader(DicomObject object) {
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
    	               listHeader(element.getDicomObject());
    	               continue;
    	            }
    	         }    
    	         String tagValue = object.getString(tag);    
    	         System.out.println(tagAddr +" ["+ tagVR +"] "+ tagName +" ["+ tagValue+"]");
    	      } catch (Exception e) {
    	         e.printStackTrace();
    	      }finally {
    	    	  org.dcm4che2.util.CloseUtils.safeClose(dis);
    	      }
    	   }  
    	}

}
