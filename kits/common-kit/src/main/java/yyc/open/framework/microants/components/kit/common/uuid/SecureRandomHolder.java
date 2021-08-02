package yyc.open.framework.microants.components.kit.common.uuid;

import java.security.SecureRandom;

public class SecureRandomHolder {
    // class loading is atomic - this is a lazy & safe singleton to be used by this package
    public static final SecureRandom INSTANCE = new SecureRandom();
}
