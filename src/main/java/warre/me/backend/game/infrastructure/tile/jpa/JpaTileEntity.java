package warre.me.backend.game.infrastructure.tile.jpa;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "tiles")
public class JpaTileEntity {

    @Id
    private UUID id;

    protected JpaTileEntity() {
    } // for JPA
}
