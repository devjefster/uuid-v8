package com.devjefster.javaspring.utils;

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.UUID;

public class UUIDv8Generator {

    private UUIDv8Generator() {
    }

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();


    public static UUID generate() {
        long timestamp = Instant.now().toEpochMilli(); // Higher precision timestamp
        long randomBits1 = SECURE_RANDOM.nextLong();
        long randomBits2 = SECURE_RANDOM.nextLong();

        ByteBuffer buffer = ByteBuffer.allocate(16);
        buffer.putLong(timestamp);
        buffer.putLong(randomBits1 ^ randomBits2); // More entropy in the least significant bits

        byte[] bytes = buffer.array();

        // Set version to 8 (UUIDv8)
        bytes[6] = (byte) ((bytes[6] & 0x0F) | 0x80);
        // Set variant to RFC 4122 standard
        bytes[8] = (byte) ((bytes[8] & 0x3F) | 0x80);

        ByteBuffer uuidBuffer = ByteBuffer.wrap(bytes);
        return new UUID(uuidBuffer.getLong(), uuidBuffer.getLong());
    }

}
