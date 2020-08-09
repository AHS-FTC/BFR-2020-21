package com.bfr.control.sync;

import com.bfr.control.path.Position;

/**
 * Interface for synchronous tasks to be ran by the robot as it drives.
 * Use this instead of a Thread, especially when you're 'writing' to hardware. Reads can get away with threads if necessary.
 * @author Alex Appleby
 */
public interface SyncCommand {

    /**
     * Method to synchronously run tasks. Implementation should not block.
     *
     * @param robotPosition The position of the robot, often as informed by a Localizer. In cases where the implementing command doesn't care about Position, null will do/
     * @return boolean where true breaks higher running loop, ending motion.
     */
    boolean check(Position robotPosition);

    void reset();

    boolean isFinished();

}
