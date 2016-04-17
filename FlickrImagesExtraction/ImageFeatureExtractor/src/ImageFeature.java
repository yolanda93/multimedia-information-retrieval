
public class ImageFeature {
      
	  private String image;
	  private double[] hist;
	  private double mode;
	  private String average;
	  
	  public ImageFeature(String image,double[] hist, double mode,String average){
		  this.setImage(image);
		  this.setAverage(average);
		  this.setMode(mode);
		  this.setHist(hist);  
	  }

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public double[] getHist() {
		return hist;
	}

	public void setHist(double[] hist) {
		this.hist = hist;
	}

	public double getMode() {
		return mode;
	}

	public void setMode(double mode) {
		this.mode = mode;
	}

	public String getAverage() {
		return average;
	}

	public void setAverage(String average) {
		this.average = average;
	}

}
