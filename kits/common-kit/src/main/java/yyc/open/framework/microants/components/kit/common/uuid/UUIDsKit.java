package yyc.open.framework.microants.components.kit.common.uuid;

import yyc.open.framework.microants.components.kit.common.SecureString;

import java.util.Random;

/**
 * {@link UUIDsKit}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/8/2
 */
public class UUIDsKit {
    private static final RandomBasedUUIDGenerator RANDOM_UUID_GENERATOR = new RandomBasedUUIDGenerator();
    private static final UUIDGenerator LEGACY_TIME_UUID_GENERATOR = new LegacyTimeBasedUUIDGenerator();
    private static final UUIDGenerator TIME_UUID_GENERATOR = new TimeBasedUUIDGenerator();

    /**
     * Generates a time-based UUID(similar to Flake IDs), which is preferred when generating an ID. The id is opaque
     * and the implementation is free to change at any time.
     *
     * @return
     */
    public static String base64UUID() {
        return TIME_UUID_GENERATOR.getBase64UUID();
    }

    /**
     * Legacy implementation of {@link #base64UUID()}.
     */
    public static String legacyBase64UUID() {
        return LEGACY_TIME_UUID_GENERATOR.getBase64UUID();
    }

    /**
     * Returns a Base64 encoded version of a Version 4.0 compatible UUID as defined here: http://www.ietf.org/rfc/rfc4122.txt, using the
     * provided {@code Random} instance
     */
    public static String randomBase64UUID(Random random) {
        return RANDOM_UUID_GENERATOR.getBase64UUID(random);
    }

    /**
     * Returns a Base64 encoded version of a Version 4.0 compatible UUID as defined here: http://www.ietf.org/rfc/rfc4122.txt, using a
     * private {@code SecureRandom} instance
     */
    public static String randomBase64UUID() {
        return RANDOM_UUID_GENERATOR.getBase64UUID();
    }

    /**
     * Returns a Base64 encoded {@link SecureString} of a Version 4.0 compatible UUID as defined here: http://www.ietf.org/rfc/rfc4122.txt,
     * using a private {@code SecureRandom} instance
     */
    public static SecureString randomBase64UUIDSecureString() {
        return RANDOM_UUID_GENERATOR.getBase64UUIDSecureString();
    }
}
