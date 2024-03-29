package com.mgarcia.dns.resource_records;

import java.nio.ByteBuffer;

/**
 *
 */
public interface ResourceRecordData {
    void toBits(ByteBuffer byteBuffer);

    static ResourceRecordData parse(ByteBuffer byteBuffer, ResourceRecordType type) {

        switch(type) {
            case A:
                return new A(byteBuffer.get(), byteBuffer.get(), byteBuffer.get(), byteBuffer.get());
            default:
                return null;
        }


    }
}
