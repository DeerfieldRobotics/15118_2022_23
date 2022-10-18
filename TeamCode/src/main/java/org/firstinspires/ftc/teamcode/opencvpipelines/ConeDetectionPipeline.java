package org.firstinspires.ftc.teamcode.opencvpipelines;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class ConeDetectionPipeline extends OpenCvPipeline {


    Mat workingMatrix = new Mat();
    public ConeDetectionPipeline() {
    }

    @Override
    public Mat processFrame(Mat input) {
        input.copyTo(workingMatrix);

        if(workingMatrix.empty()) {
            return input;
        }

        Imgproc.cvtColor(workingMatrix, workingMatrix, Imgproc.COLOR_RGB2YCrCb); //converts to ycrcb colorspace

        //create rectangles, for loop
        double[][][] initial = new double[54][96][2] ;
        double cbTotal = 0;
        double crTotal = 0;
        double cbStandardDev = 0;
        double crStandardDev = 0;
        for(int i = 0; i<initial.length;i++) { //collects all cr cb values values
            for(int j = 0; j<initial[0].length;j++) {
                initial[i][j][0]=Core.sumElems(workingMatrix.submat(j*1920/initial[0].length,(j+1)*1920/initial[0].length),i*1080/initial.length,(i+1)*1080/initial.length).val[2]; //finds sum of submat and gets cb value
                cbTotal+=initial[i][j][0];
                initial[i][j][1]=Core.sumElems(workingMatrix.submat(j*1920/initial[0].length,(j+1)*1920/initial[0].length),i*1080/initial.length,(i+1)*1080/initial.length).val[3]; //finds sum of submat and gets cr value
                crTotal+=initial[i][j][1];
            }
        }
        final double cbMean = cbTotal/(initial.length*initial[0].length);
        final double crMean = crTotal/(initial.length*initial[0].length);

        //standard deviation:
        
        for(int i = 0; i<initial.length;i++) {
            for(int j = 0;j<initial[0].length;j++) {
                cbStandardDev+=Math.pow((initial[i][j][0]-cbMean),2);
                crStandardDev+=Math.pow((initial[i][j][1]-crMean),2);
            }
        }

        cbStandardDev=Math.sqrt(cbStandardDev/(initial.length*initial[0].length));
        crStandardDev=Math.sqrt(crStandardDev/(initial.length*initial[0].length));

        for(int i = 0; i<initial.length;i++) {
            for(int j = 0;j<initial[0].length;j++) {
                
            }
        }
        //identify outliers with mean and threshold for standard deviation thing
        //store outlier location somehow, maybe boolean array, or just array of points
        //iterate using squares found with above


        for(int i = 0; i<10;i++) {

        }

        Mat matLeft = workingMatrix.submat(120, 150, 10, 50);
        Mat matCenter = workingMatrix.submat(120, 150, 80, 120);
        Mat matRight = workingMatrix.submat(120, 150, 150, 190);

        Imgproc.rectangle(workingMatrix, new Rect(10,120,40,30), new Scalar(255,255,255));

        double leftTotal = Core.sumElems(matLeft).val[2];
        double centerTotal = Core.sumElems(matCenter).val[2];
        double rightTotal = Core.sumElems(matRight).val[2];

        if(leftTotal > centerTotal) {
            if(leftTotal > rightTotal) {
                //left is skystone
            }
            else {
                //right is skystone
            }
        }
        else if(centerTotal>rightTotal) {
            //center is skystone
        }
        else {
            //right is skystone
        }


        return workingMatrix;
    }
}
