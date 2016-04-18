
public class MetadataFeature {

	  private String image;
	  private String [] metadata;

	  
	  public MetadataFeature(String image,String [] metadata){
          this.image=image;
          this.metadata=metadata;
	  }


	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}


	public String [] getMetadata() {
		return metadata;
	}


	public void setMetadata(String [] metadata) {
		this.metadata = metadata;
	}
}
