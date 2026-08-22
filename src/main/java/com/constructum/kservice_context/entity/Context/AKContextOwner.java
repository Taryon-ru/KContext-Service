package com.constructum.kservice_context.entity.Context;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.UUID;

import com.constructum.kservice_context.entity.common.KAttributeKey;
import com.constructum.kservice_context.entity.common.KSystemAttributes;


/**
 * Атрибут: персональная ответственность за объект.
 * 
 * ownerId
 *   = персонально ответственный владелец
 *   = влияет на права
 *
 * owningGroupId
 *   = группа/папка размещения
 *   = сам по себе права не определяет
 */
@Entity
@Table(
    name = "attr_context_owner",
    indexes = {
        @Index(name = "idx_state_owner_state", columnList = "state_id"),
        @Index(name = "idx_state_owner_user", columnList = "owner_id")
    }
)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @SuperBuilder
public class AKContextOwner extends KContextAttributes {

    @Column(name = "owner_id", nullable = false)
    private UUID ownerId;

    // TODO: оптимизировать
    public static AKContextOwner create(KContext state, UUID ownerId, Instant now, UUID companyId, UUID createdBy) {
        return AKContextOwner.builder()
            .kContext(state)
            .key(new KAttributeKey(state.getId(), now))
            .systemAttributes(new KSystemAttributes(null, companyId, createdBy))
            .ownerId(ownerId)
            .build();
    }

}
