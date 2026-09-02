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
 * Атрибут: Логин пользователя в системе.
 */
@Entity
@Table(name = "attr_user_login")
@Getter @Setter @NoArgsConstructor @SuperBuilder
public class AKUserLogin extends KObjectAttributes {

    @Column(name = "user_login", nullable = false)
    private String userLogin;


    public static AKUserLogin create(KUser user, String login, UUID companyId, UUID createdBy, Instant now) {

        if (user == null || login == null || login.isBlank()) {
            throw new IllegalArgumentException("user and login are required");
        }

        if (companyId == null || createdBy == null) {
            throw new IllegalArgumentException("companyId and createdBy are required");
        }

        return AKUserLogin.builder()
                .key(new KAttributeKey(user.getId(), now))
                .kObject(user)
                .userLogin(login)
                .systemAttributes(new KSystemAttributes(null, companyId, createdBy))
                .build();
    }


}
