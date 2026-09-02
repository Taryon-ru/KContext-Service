package com.constructum.kservice_context.entity.Object.User;

import java.time.Instant;
import java.util.UUID;

import com.constructum.kservice_context.entity.Object.KObjectAttributes;
import com.constructum.kservice_context.entity.common.KAttributeKey;
import com.constructum.kservice_context.entity.common.KSystemAttributes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Атрибут: Имя пользователя.
 */
@Entity
@Table(name = "attr_user_first_name")
@Getter @Setter @NoArgsConstructor @SuperBuilder
public class AKUserFirstName extends KObjectAttributes {

    @Column(name = "user_name", nullable = false)
    private String userFirstName;


    public static AKUserFirstName create(KUser user, String firstName, UUID companyId, UUID createdBy, Instant now) {

        if (user == null || firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("user and firstName are required");
        }

        if (companyId == null || createdBy == null) {
            throw new IllegalArgumentException("companyId and createdBy are required");
        }

        return AKUserFirstName.builder()
                .key(new KAttributeKey(user.getId(), now))
                .kObject(user)
                .userFirstName(firstName)
                .systemAttributes(new KSystemAttributes(null, companyId, createdBy))
                .build();
    }
}
