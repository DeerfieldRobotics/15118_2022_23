package org.firstinspires.ftc.teamcode.opencvpipelines;

public class PoleDetectionPipeline {
}
package org.firstinspires.ftc.teamcode.opencvpipelines;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Range;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.List;

public class ConeDetectionPipeline extends OpenCvPipeline {

    private int yellowSens = 1;



    List<int[]> yXArray = new ArrayList<int[]>();
    List<int[]> yYArray = new ArrayList<int[]>();

    private final double low_yThreshold = 61;
    private final double high_yThreshold = 120;
    private Telemetry t;


    Mat workingMatrix = new Mat();
    public ConeDetectionPipeline(Telemetry telemetry) {
        t = telemetry;
    }

    public int height() {
        return workingMatrix.height();
    }

    public int width() {
        return workingMatrix.width();
    }

    public String tester() {return "1";};

    public double yThreshold() {return yThreshold;}

    public int outliersyX() {return yXArray.size();}
    
    public int outliersyY() {return yYArray.size();}

    @Override
    public Mat processFrame(Mat input) {
        yXArray.clear();
        yYArray.clear();


        input.copyTo(workingMatrix);

        if(workingMatrix.empty()) {
            return input;
        }

        Imgproc.cvtColor(workingMatrix, workingMatrix, Imgproc.COLOR_RGB2HSV); // move to HSV color spacec

        //create rectangles, for loop
        double[][][] initial = new double[input.width()/5][input.height()/5][1] ;

        int widthMult = input.width()/initial.length;
        int heightMult = input.height()/initial[0].length;

        for(int i = 0; i<initial.length-1;i++) { //collects all cr cb values values
            for(int j = 0; j<initial[0].length-1;j++) {
                //Imgproc.rectangle(input, new Point(i*widthMult, j*heightMult), new Point((i+1)*widthMult-1, (j+1)*heightMult-1), new Scalar(255,255,255), 1); //draws grid for reference
                initial[i][j][0]=Core.sumElems(workingMatrix.submat(new Range(j*widthMult,(j+1)*widthMult-1), new Range(i*heightMult,(i+1)*heightMult-1))).val[0]; //finds sum of submat and gets cb value

                
            }
        }

        int x1 = 0;
        int y1 = 0;
        for(x1 = 0; x1<initial.length;x1++) {
            for(y1 = 0;y1<initial[0].length;y1++) {
                if((x1%2==1&&y1%2==0)||(x1%2==0&&y1%2==1)) {
                    int yL = 0;
                    int yR = 0;
                    int yU = 0;
                    int yD = 0;

                    double temp = 0;


                    if (x1 > 0) {
                        temp = initial[x1][y1][0] - initial[x1 - 1][y1][0];
                        if (Math.abs(temp) > low_yThreshold * yellowSens && Math.abs(temp) > high_yThreshold * yellowSens) //Cr Left, should set the right value for the square next to it as -CrL
                            yL = (int) (temp);
                    }


                    if (x1 < initial.length - 1) {
                        temp = initial[x1][y1][0] - initial[x1 + 1][y1][0];
                        if (Math.abs(temp) > low_yThreshold * yellowSens && Math.abs(temp) > high_yThreshold * yellowSens) //Cr right, should set right value for this square to CrR
                            yR = (int) (temp);
                    }

                    if (y1 > 0) {
                        temp = initial[x1][y1][0] - initial[x1][y1 - 1][0];
                        if (Math.abs(temp) > low_yThreshold * yellowSens && Math.abs(temp) > high_yThreshold * yellowSens)  //Cr up
                            yU = (int) (temp);
                    }


                    if (y1 < initial[0].length - 1) {
                        temp = initial[x1][y1][0] - initial[x1][y1 + 1][0];
                        if (Math.abs(temp) > low_yThreshold * yellowSens && Math.abs(temp) > high_yThreshold * yellowSens)  //Cr down
                            yD = (int) (temp);
                    }


                    if (yL != 0) {
                        yXArray.add(new int[]{x1 - 1, y1, -1 * yL});
                    }
                    if (yR != 0) {
                        yXArray.add(new int[]{x1, y1, yR});
                    }
                    if (yU != 0) {
                        yYArray.add(new int[]{x1, y1 - 1, -1 * yU});
                    }
                    if (yD != 0) {
                        yYArray.add(new int[]{x1, y1, yD});
                    }

                }
            }
        }


/*
        Visualization
*/
        for(int[] p: yXArray) {
            if(p[2]<0) {
                int y = p[1]*heightMult;
                int x = p[0]*widthMult;

                if(y==input.height())
                    y-=1;
                if(x==input.width())
                    x-=1;

                Imgproc.rectangle(input, new Point(x, y), new Point(x+1,y+heightMult), new Scalar(0, 255, 0), -1);
            }
            else {
                int y = p[1] * heightMult;
                int x = p[0] * widthMult;

                if (y == input.height())
                    y -= 1;
                if (x == input.width())
                    x -= 1;

                Imgproc.rectangle(input, new Point(x+widthMult-1, y), new Point(x+widthMult,y+heightMult), new Scalar(255, 0, 0), -1);
            }
        }
        
        for(int[] p: yYArray) {
            if(p[2]<0) {
                int y = p[1]*heightMult;
                int x = p[0]*widthMult;

                if(y==input.height())
                    y-=1;
                if(x==input.width())
                    x-=1;

                Imgproc.rectangle(input, new Point(x, y), new Point(x+widthMult,y+1), new Scalar(0, 0, 255), -1);
            }
            else {
                int y = p[1] * heightMult;
                int x = p[0] * widthMult;

                if (y == input.height())
                    y -= 1;
                if (x == input.width())
                    x -= 1;

                Imgproc.rectangle(input, new Point(x, y+heightMult-1), new Point(x+widthMult,y+heightMult), new Scalar(255, 255, 0), -1);
            }
        }
        return input;
    }
}
