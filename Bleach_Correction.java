package HomeMade.Tools;
import ij.*;
import ij.process.*;
import ij.gui.*;
import java.awt.*;
import ij.plugin.*;

public class Bleach_Correction implements PlugIn {

	public void run(String arg) {
		ImagePlus imp = IJ.getImage();
		int[] Dim =new int[5];
		Dim=imp.getDimensions();
		Roi cRoi= imp.getRoi();
		double StartMean=0;
		if (cRoi!=null && cRoi.isArea()){
			StartMean=imp.getStatistics(2).mean;
		} else {
			StartMean= imp.getStatistics(2).mean;
		}

		for (int i=1;i<Dim[3]+1;i++){
			for (int j=1;j<Dim[4]+1;j++){
				imp.setPosition(imp.getChannel(),i,j);
				if (cRoi!=null && cRoi.isArea()){
					imp.setRoi(cRoi);
					double m=imp.getStatistics(2).mean;
					IJ.run(imp, "Select None", "");
					IJ.run(imp, "Multiply...", "value="+StartMean/m+" slice");
				} else {
					double m= imp.getStatistics(2).mean;
					IJ.run(imp, "Multiply...", "value="+StartMean/m+" slice");
				}
			}
		}
	}
}
