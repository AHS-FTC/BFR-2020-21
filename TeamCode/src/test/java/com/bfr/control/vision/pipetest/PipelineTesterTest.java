package com.bfr.control.vision.pipetest;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class PipelineTesterTest {

    @Test
    void testMain() {
        String rsdir = new File("").getAbsolutePath();
        rsdir += "/src/test/rs/";
        String inputPath = rsdir + "input.png";
        String outputPath = rsdir + "output.png";

        PipelineTester.main(inputPath,outputPath,"TestPipeline");
    }
}