package com.bfr.control.vision;

import org.opencv.core.Mat;
import org.openftc.easyopencv.OpenCvPipeline;

/**
 * returns the input as the output
 */
public class TestPipeline extends OpenCvPipeline {
    @Override
    public Mat processFrame(Mat input) {
        return input;
    }
}
