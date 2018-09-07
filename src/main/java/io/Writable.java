package io;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by xxh on 18-8-14.
 */
public interface Writable {
    void write(DataOutput out) throws IOException;
    void readFields(DataInput in) throws IOException;
}
