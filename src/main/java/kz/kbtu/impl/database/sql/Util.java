package kz.kbtu.impl.database.sql;

import java.nio.ByteBuffer;
import java.util.UUID;

public final class Util {
    private Util() {
    }

    public static byte[] serialize(UUID id) {
        ByteBuffer buffer = ByteBuffer.wrap(new byte[16]);
        buffer.putLong(id.getMostSignificantBits());
        buffer.putLong(id.getLeastSignificantBits());
        return buffer.array();
    }

    public static UUID deserialize(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        return new UUID(buffer.getLong(), buffer.getLong());
    }
}
