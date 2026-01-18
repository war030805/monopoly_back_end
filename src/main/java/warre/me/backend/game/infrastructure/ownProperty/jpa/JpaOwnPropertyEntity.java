package warre.me.backend.game.infrastructure.ownProperty.jpa;

import jakarta.persistence.*;
import warre.me.backend.shared.domain.board.property.Property;
import warre.me.backend.game.domain.property.OwnProperty;
import warre.me.backend.game.domain.property.OwnPropertyId;

import java.util.UUID;

@Entity
@Table(name = "own_properties")
public class JpaOwnPropertyEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Property property;

    @Column(nullable = false)
    private int houses;

    protected JpaOwnPropertyEntity() {
    } // for JPA

    public JpaOwnPropertyEntity(UUID id, Property property,int houses) {
        this.id = id;
        this.property=property;
        this.houses = houses;
    }

    public static JpaOwnPropertyEntity fromDomain(OwnProperty ownProperty) {
        return new JpaOwnPropertyEntity(
                ownProperty.getOwnPropertyId().id(),
                ownProperty.getProperty(),
                ownProperty.getHouses()
        );
    }

    public OwnProperty toDomain() {
        return new OwnProperty(
                new OwnPropertyId(id),
                property,
                houses
        );
    }
}
