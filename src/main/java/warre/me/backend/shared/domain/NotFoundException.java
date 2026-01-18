package warre.me.backend.shared.domain;

import java.util.UUID;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }

    public static NotFoundException notFoundForCompoundKey(NotFoundThrower key1, NotFoundThrower key2) {
        var key1Message= key1.notFoundMessage();
        var key2Message= key2.notFoundMessage();

        return new NotFoundException(key1Message+" "+key2Message);
    }

    public static String createNotFoundMessageFromNameAndId(String name, UUID id) {
        return String.format("%s with id [%s] not found", name, id);
    }
}
