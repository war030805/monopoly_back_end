package warre.me.backend.chared.domain;

public interface NotFoundThrower {
    default NotFoundException notFound() {
        return new NotFoundException(notFoundMessage());
    }

    String notFoundMessage();

}
