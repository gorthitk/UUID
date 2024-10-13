package org.jet.uuid;

import org.jet.uuid.util.MacAddressFetcher;

import java.time.Instant;

/**
 * Snowflake UUID generated based on
 * <a href="https://blog.x.com/engineering/en_us/a/2010/announcing-snowflake">...</a>
 * <a href="https://github.com/twitter-archive/snowflake">...</a>
 *
 */
public class SnowflakeGenerator implements UUIDGenerator {
    // January 1st, 2024
    private static final long STARTING_EPOCH = 1704153600000L;
    private static final int UNUSED_BITS = 1; // Sign bit, Unused (always set to 0)
    private static final int EPOCH_BITS = 41;
    private static final int MAC_BITS = 10;
    private static final int SEQUENCE_BITS = 12;

    private static final int MAX_MAC_ID = (1 << MAC_BITS) - 1;
    private static final int MAX_SEQUENCE_ID = (1 << SEQUENCE_BITS) - 1;

    private long currentSequence = 0;
    private long lastTimeStamp = 0;

    SnowflakeGenerator() {
        this.lastTimeStamp = getEpoch();
    }

    /**
     * Synchronized ensures only one-thread can execute this method.
     * @return long UUID
     */
    @Override
    public synchronized long generate() throws Exception {
        long currentTs = getEpoch();

        if (currentTs == lastTimeStamp ) {
            currentSequence = (currentSequence + 1) & MAX_SEQUENCE_ID;
        } else {
            currentSequence = 0;
        }

        lastTimeStamp = currentTs;

        return currentTs << MAC_BITS + SEQUENCE_BITS
                | getMacAddress() << SEQUENCE_BITS
                | currentSequence;
    }

    private long getEpoch() {
        return Instant.now().toEpochMilli() - STARTING_EPOCH;
    }

    private long getMacAddress() throws Exception {
        String address = MacAddressFetcher.get();
        return address.hashCode() & MAX_MAC_ID;
    }
}
