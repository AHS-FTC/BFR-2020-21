package com.bfr.control.vision.pipetest;

import org.junit.jupiter.api.Test;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.openftc.easyopencv.OpenCvPipeline;

import java.io.File;

public class PipelineTester {

    private static final String PIPELINE_PACKAGE = "com.bfr.control.vision.";
    private static final String LIB_DIR = System.getProperty("user.dir") + "/src/test/lib";

    /**
     * arg 0: Input location
     * arg 1: Output location
     * arg 2: classname
     */
    public static void main(String... args){
        System.load(LIB_DIR + "/libopencv_java344.so");

        Mat input = Imgcodecs.imread(args[0]);
        Mat output;

        try {
            Class<?> pipeClass = Class.forName(PIPELINE_PACKAGE + args[2]);
            OpenCvPipeline pipeline = (OpenCvPipeline)pipeClass.newInstance();

            output = pipeline.processFrame(input);
            Imgcodecs.imwrite(args[1], output);

        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }

    }


}
