package com.share_thoughts.thoughts_sync.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UniqueIdGenerator {

    public Long generate10DigitId() {
        UUID uuid = UUID.randomUUID();
        long hash = Math.abs(uuid.getMostSignificantBits() ^ uuid.getLeastSignificantBits());
        return 1_000_000_000L + (hash % 9_000_000_000L);
    }

}
