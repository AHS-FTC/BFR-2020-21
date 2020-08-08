package com.bfr.control.obm;

import com.bfr.control.Position;
import com.bfr.hardware.sensors.Localizer;

public interface OBMCommand {

    /**
     * Interface for synchronous tasks to be ran by the robot as it drives.
     * Use this instead of a Thread, especially when you're 'writing' to hardware. Reads can get away with threads if necessary.
     * @author Alex Appleby
     */


    /**
     * Method to synchronously run OBM tasks. Should not block.
     *
     * @return boolean where true breaks higher running loop, ending motion.
     */
    boolean check(Position robotPosition);

    void reset();

    boolean isFinished();

}
