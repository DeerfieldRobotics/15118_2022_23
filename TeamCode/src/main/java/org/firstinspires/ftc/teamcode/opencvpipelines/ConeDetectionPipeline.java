package org.firstinspires.ftc.teamcode.opencvpipelines;

//import java.lang.FdLibm.Cbrt;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Range;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;


//import javafx.util.Pair;

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

        Imgproc.cvtColor(workingMatrix, workingMatrix, Imgproc.COLOR_BGR2HSV); //converts to hsv

        //create rectangles, for loop
        double[][][] initial = new double[54][96][2] ;
        double hueTotal = 0;

        double hueStDev = 0;


        for(int i = 0; i<initial.length;i++) { //collects all cr cb values values
            for(int j = 0; j<initial[0].length;j++) {
                initial[i][j][0]=Core.sumElems(workingMatrix.submat(new Range(j*1920/initial[0].length,(j+1)*1920/initial[0].length),new Range(i*1080/initial.length,(i+1)*1080/initial.length))).val[0]; //finds sum of submat and gets cb value
                hueTotal+=initial[i][j][0];

            }
        }

        //standard deviation:
        


        double cr_sum = 0;
        double cb_sum = 0;
        for(int i = 0; i<initial.length;i++) {
            for(int j = 0;j<initial[0].length;j++) {
                cr_sum +=Math.abs(initial[i][j][1]-initial[i+1][j][1]);
                cr_sum+=Math.abs(initial[i][j][1]-initial[i][j+1][1]);
                cb_sum +=Math.abs(initial[i][j][0]-initial[i+1][j][0]);
                cb_sum+=Math.abs(initial[i][j][0]-initial[i][j+1][0]);
            }
        }
        double cbStandardDev = 0;
        double crStandardDev = 0;
        final double total_value = 0;
        final double cr_mean = cr_sum/total_value;
        final double cb_mean = cb_sum/total_value;
        for(int i = 0; i<initial.length;i++) {
            for(int j = 0;j<initial[0].length;j++) {
                cbStandardDev+=Math.pow((initial[i][j][0]-cb_mean),2);
                crStandardDev+=Math.pow((initial[i][j][1]-cr_mean),2);
            }
        }

        cbStandardDev=Math.sqrt(cbStandardDev/(initial.length*initial[0].length));
        crStandardDev=Math.sqrt(crStandardDev/(initial.length*initial[0].length));
        // 3-d array (num of x outliers, num of y outliers, four(each direction))
        int cr_left_outliers = 0;
        int cr_right_outliers = 0;
        int cr_up_outliers = 0;
        int cr_down_outliers = 0;
        int cb_left_outliers = 0;
        int cb_right_outliers = 0;
        int cb_up_outliers = 0;
        int cb_down_outliers = 0;
        //counts outliers from each side
        //left outliers are from low saturation -> high saturation
        //down otliers are low saturation
        //                  high saturation
        for(int i = 0; i<initial.length;i++) {
            for(int j = 0;j<initial[0].length;j++) {
                if(initial[i+1][j][1]-initial[i][j][1]>crStandardDev){
                    cr_left_outliers++;
                }
                if(initial[initial.length-i][initial.length-j][1]-initial[initial.length-i-1][j][1]>crStandardDev){
                    cr_right_outliers++;
                }
                if(initial[i+1][j][1]-initial[i][j][1]>crStandardDev){
                    cb_left_outliers++;
                }
                if(initial[initial.length-i][initial.length-j][2]-initial[initial.length-i-1][initial.length-j][2]>cbStandardDev){
                    cb_right_outliers++;
                }
                if(initial[i][j+1][1]-initial[i][j][1]>crStandardDev){
                    cr_down_outliers++;
                }
                if(initial[initial.length-i][initial.length-j-1][1]>crStandardDev){
                    cr_up_outliers++;
                }
                if(initial[i][j+1][1]-initial[i][j][1]>cbStandardDev){
                    cb_down_outliers++;
                }
                if(initial[initial.length-i][initial.length-j-1][1]>cbStandardDev){
                    cb_up_outliers++;
                }
            }
        }
        //stores the left and right outliers, the amount of left and right outliers, and the place of them.
        int[][][] cb_hori_array = new int[2][Math.max(cb_left_outliers, cb_right_outliers)][2];
        int[][][] cb_vert_array = new int[2][Math.max(cb_up_outliers, cb_down_outliers)][2];
        int[][][] cr_hori_array = new int[2][Math.max(cr_left_outliers, cr_right_outliers)][2];
        int[][][] cr_vert_array = new int[2][Math.max(cr_up_outliers, cr_down_outliers)][2];
        cb_left_outliers = 0;
        cb_right_outliers = 0;
        cb_up_outliers = 0;
        cb_down_outliers = 0;
        cr_left_outliers = 0;
        cr_right_outliers = 0;
        cr_up_outliers = 0;
        cr_down_outliers = 0;
        for(int i = 0; i<initial.length;i++) {
            for(int j = 0;j<initial[0].length;j++) {
                if(initial[i+1][j][1]-initial[i][j][1]>crStandardDev){
                    cr_hori_array[0][cr_left_outliers][0] = i;
                    cr_hori_array[0][cr_left_outliers][1] = j;
                    cr_left_outliers++;
                }
                if(initial[initial.length-i][initial.length-j][1]-initial[initial.length-i-1][j][1]>crStandardDev){
                    cr_hori_array[1][cr_right_outliers][0] = i;
                    cr_hori_array[1][cr_right_outliers][1] = j;
                    cr_right_outliers++;
                }
                if(initial[i+1][j][1]-initial[i][j][1]>crStandardDev){
                    cb_hori_array[0][cb_left_outliers][0] = i;
                    cb_hori_array[0][cb_left_outliers][1] = j;
                    cb_left_outliers++;
                }
                if(initial[initial.length-i][initial.length-j][2]-initial[initial.length-i-1][initial.length-j][2]>cbStandardDev){
                    cb_hori_array[1][cb_right_outliers][0] = i;
                    cb_hori_array[1][cb_right_outliers][1] = j;
                    cb_right_outliers++;
                }
                if(initial[i][j+1][1]-initial[i][j][1]>crStandardDev){
                    cr_vert_array[0][cr_down_outliers][0] = i;
                    cr_vert_array[0][cr_down_outliers][1] = j;
                    cr_down_outliers++;
                }
                if(initial[initial.length-i][initial.length-j-1][1]>crStandardDev){
                    cr_vert_array[1][cr_up_outliers][0] = i;
                    cr_vert_array[1][cr_up_outliers][1] = j;
                    cr_up_outliers++;
                }
                if(initial[i][j+1][1]-initial[i][j][1]>cbStandardDev){
                    cb_vert_array[0][cb_down_outliers][0] = i;
                    cb_vert_array[0][cb_down_outliers][1] = j;
                    cb_down_outliers++;
                }
                if(initial[initial.length-i][initial.length-j-1][1]>cbStandardDev){
                    cb_vert_array[1][cb_up_outliers][0] = i;
                    cb_vert_array[1][cb_up_outliers][1] = j;
                    cb_up_outliers++;
                }
            }
        }
        //identify outliers with mean and threshold for standard deviation thing
        //store outlier location somehow, maybe boolean array, or just array of points
        //iterate using squares found with above


        return workingMatrix;
    }
}
