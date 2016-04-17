import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class FeatureExtractor
{
	public static final int BINS = 32;
	public static final float MIN_VALUE = 0.0f;
	public static final float MAX_VALUE = 255.0f;
	private static long redBucket;
	private static long greenBucket;
	private static long blueBucket;

	public static void main( String[] args )
	{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		if( args.length < 1 )
		{
			System.out.println( "Usage: ImageFeatureExtractor <image-path-file> <output-path-file>" );
			System.exit( 0 );
		}

		String imagePath = args[0];
		String outPath = args[1];
		System.out.println( "Opening image: " + imagePath );

		double[]hist= extract(imagePath);
		
		Color averageColor;
		try {
			averageColor = averageColor(imagePath);
			System.out.println(" Red: " + averageColor.getRed() +"  Green: " + averageColor.getGreen() + " Blue: " + averageColor.getBlue());					
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	/* generate a 32-bar histogram for each color channel */
	public static double[] extract(String path) {
		System.out.println("Path: " + path);	
		Mat image = Imgcodecs.imread(path);
		showResult(image); 
		return calculateHist(image);
	}


	public static Color averageColor(String imagePath) throws IOException {
			File input = new File(imagePath);
			BufferedImage image = ImageIO.read(input);
			int width = image.getWidth();
			int height = image.getHeight();

			int count = 0;

			for(int i=0; i<height; i++){
				for(int j=0; j<width; j++){
					count++;
					Color c = new Color(image.getRGB(j, i));
				    redBucket =+ c.getRed();
			        greenBucket =+ c.getGreen();
			        blueBucket =+ c.getBlue();	        			       		       			     					
				}
			}
			
			 Color averageColor = new Color(redBucket / count,
                     greenBucket / count,
                     blueBucket / count);	
			return averageColor;
	}


	public static double[] calculateHist(Mat image) {
		Mat hsvImage = new Mat(image.width(), image.height(), image.type());
		Mat histHue = new Mat();
		Mat histSaturation = new Mat();

		Imgproc.cvtColor(image, hsvImage, Imgproc.COLOR_BGR2HSV);
		List<Mat> channels = new ArrayList<Mat>();
		Core.split(hsvImage, channels);

		//Histogram for hue
		Imgproc.calcHist(Arrays.asList( new Mat[]{channels.get(0)} ), new MatOfInt(0), 
				new Mat(), histHue, new MatOfInt(BINS), new MatOfFloat(MIN_VALUE, MAX_VALUE));

		//Histogram for saturation
		Imgproc.calcHist(Arrays.asList( new Mat[]{channels.get(1)} ), new MatOfInt(0), 
				new Mat(), histSaturation, new MatOfInt(BINS), new MatOfFloat(MIN_VALUE, MAX_VALUE));


		double sum = Core.sumElems(histHue).val[0];
		double[] values = new double[histHue.height()+histSaturation.height()];
		int k = 0;
		for (int i = 0; i < histHue.height(); ++i ) {
			values[k++] = histHue.get(i, 0)[0]/sum;
		}
		sum = Core.sumElems(histSaturation).val[0];
		for ( int i = 0; i < histSaturation.height(); ++i) {
			values[k++] = histSaturation.get(i, 0)[0]/sum;
		}
		return values;
	}

	public static void showResult(Mat img) {
		Imgproc.resize(img, img, new Size(640, 480));
		MatOfByte matOfByte = new MatOfByte();
		Imgcodecs.imencode(".jpg", img, matOfByte);
		byte[] byteArray = matOfByte.toArray();
		BufferedImage bufImage = null;
		try {
			InputStream in = new ByteArrayInputStream(byteArray);
			bufImage = ImageIO.read(in);
			JFrame frame = new JFrame();
			frame.getContentPane().add(new JLabel(new ImageIcon(bufImage)));
			frame.pack();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}