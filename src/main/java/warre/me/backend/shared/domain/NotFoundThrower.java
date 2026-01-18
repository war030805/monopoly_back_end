package warre.me.backend.shared.domain;

public interface NotFoundThrower {
    default NotFoundException notFound() {
        return new NotFoundException(notFoundMessage());
    }

    String notFoundMessage();

}
