package com.constructum.kservice_context.entity.Context;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.UUID;

import com.constructum.kservice_context.entity.common.KAttributeKey;
import com.constructum.kservice_context.entity.common.KSystemAttributes;

/**
 * Атрибут: группа-владелец объекта.
 * * ownerId
 *   = персонально ответственный владелец
 *   = влияет на права
 *
 * owningGroupId
 *   = группа/папка размещения
 *   = сам по себе права не определяет
 */
@Entity
@Table(
    name = "attr_context_owning_group",
    indexes = {
        @Index(name = "idx_state_owning_group_state", columnList = "state_id"),
        @Index(name = "idx_state_owning_group_group", columnList = "owning_group_id")
    }
)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @SuperBuilder
public class AKContextOwningGroup extends KContextAttributes{

    @Column(name = "owning_group_id", nullable = false)
    private UUID owningGroupId;

    // TODO: оптимизировать
    public static AKContextOwningGroup create(KContext state, UUID owningGroupId, Instant now, UUID companyId, UUID createdBy) {
        return AKContextOwningGroup.builder()
            .key(new KAttributeKey(state.getId(), now))
            .kContext(state)
            .systemAttributes(new KSystemAttributes(null, companyId, createdBy))
            .owningGroupId(owningGroupId)
            .build();
    }

}
