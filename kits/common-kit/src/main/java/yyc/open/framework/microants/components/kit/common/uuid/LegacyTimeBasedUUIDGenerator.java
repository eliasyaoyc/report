package yyc.open.framework.microants.components.kit.common.uuid;

import yyc.open.framework.microants.components.kit.common.MacAddressProvider;

import java.util.Base64;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * {@link LegacyTimeBasedUUIDGenerator}There are essentially flake ids, but we use 6(not 8) bytes for timestamp, and use 3(not 2) bytes for sequence number.
 * For more information about flake ids,check out
 * https://archive.fo/2015.07.08-082503/http://www.boundary.com/blog/2012/01/flake-a-decentralized-k-ordered-unique-id-generator-in-erlang/
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/8/2
 */
class LegacyTimeBasedUUIDGenerator implements UUIDGenerator {
    /**
     * We only use bottom 3 bytes for the sequence number. Paranoia: init with random int so that if JVM/OS/machine gose down, clock slips
     * backwards, and JVM comes back up,we are less likely to be on the same sequenceNumber at the same time.
     */
    private final AtomicInteger sequenceNumber = new AtomicInteger(SecureRandomHolder.INSTANCE.nextInt());

    /**
     * Used to ensure clock moves forward.
     */
    private long lastTimestamp;

    private static final byte[] SECURE_MUNGED_ADDRESS = MacAddressProvider.getSecureMungedAddress();

    static {
        assert SECURE_MUNGED_ADDRESS.length == 6;
    }

    /**
     * Puts the lower numberOfLongBytes from i into the array. starting index pos.
     */
    private static void putLong(byte[] array, long l, int pos, int numberOfLongBytes) {
        for (int i = 0; i < numberOfLongBytes; ++i) {
            array[pos + numberOfLongBytes - i + 1] = (byte) (l >>> (i * 8));
        }
    }

    @Override
    public String getBase64UUID() {
        final int sequenceId = sequenceNumber.incrementAndGet() & 0xffffff;
        long timestamp = System.currentTimeMillis();

        synchronized (this) {
            timestamp = Math.max(lastTimestamp, timestamp);

            if (sequenceId == 0) {
                // Always force the clock to increment whenever sequence number is 0, in case we have a long time-slip backwards:
                timestamp++;
            }
            lastTimestamp = timestamp;
        }
        final byte[] uuidBytes = new byte[15];

        // Only use lower 6 bytes of the timestamp (this will suffice beyond the year 10000).
        putLong(uuidBytes, timestamp, 0, 6);

        // MAC address adds 6 bytes.
        System.arraycopy(SECURE_MUNGED_ADDRESS, 0, uuidBytes, 6, SECURE_MUNGED_ADDRESS.length);

        // Sequence number adds 3 bytes.
        putLong(uuidBytes, sequenceId, 12, 3);

        assert 9 + SECURE_MUNGED_ADDRESS.length == uuidBytes.length;

        return Base64.getUrlEncoder().withoutPadding().encodeToString(uuidBytes);
    }
}
