package com.constructum.kservice_context.entity.Link.LinkUserGroup;


import com.constructum.kservice_context.entity.Link.KLinkAttributes;
import com.constructum.kservice_context.entity.common.KAttributeKey;
import com.constructum.kservice_context.entity.common.KSystemAttributes;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.UUID;


/**
 * Атрибут связи пользователь ↔ группа.
 */
@Entity
@Table(name = "attr_klink_user_group")
@Getter @Setter @NoArgsConstructor @SuperBuilder
public class AKLnkUserGroupData extends KLinkAttributes {

    @Column(name = "user_id", nullable = false, updatable = false)
    private UUID userId;

    @Column(name = "group_id", nullable = false, updatable = false)
    private UUID groupId;

    // ВАЖНО: все атрибуты должны содержать метод getValue() для работы updateAttribute

    // Вложенный класс-record для нужен для объединения всех полей 
    // класса атрибута, позволяет пользоваться updateAttribute
    // благодаря ему над полями можно выполнять сравнение, как на ндиным целом
    public record lnkUserGroup(UUID userId, UUID groupId) {}

    // возвращает все атрибуты как один оюъект
    public lnkUserGroup getValue() {
        return new lnkUserGroup(userId, groupId);
    }

    public static AKLnkUserGroupData create(KLinkUserGroup kLink, Instant now, UUID userId, UUID groupId, UUID companyId, UUID createdBy ){

         if (kLink == null || userId == null || groupId == null) {
            throw new IllegalArgumentException("kObject, userId, and groupId are required");
        }
        if (companyId == null || createdBy == null) {
            throw new IllegalArgumentException("companyId and createdBy are required");
        }

        return AKLnkUserGroupData.builder()
                .key(new KAttributeKey(kLink.getId(), now))
                .kLink(kLink)
                .userId(userId)
                .groupId(groupId)
                .systemAttributes(new KSystemAttributes(null, companyId, createdBy))
                .build();
    }
}
