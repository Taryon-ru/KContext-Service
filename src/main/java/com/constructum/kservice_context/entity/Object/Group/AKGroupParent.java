package com.constructum.kservice_context.entity.Object.Group;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.UUID;

import com.constructum.kservice_context.entity.Object.KObjectAttributes;
import com.constructum.kservice_context.entity.common.KAttributeKey;
import com.constructum.kservice_context.entity.common.KSystemAttributes;

/**
 * Атрибут для реализации иерархии групп.
 * Указывает на ID вышестоящей группы (якоря).
 */
@Entity
@Table(name = "attr_group_parent")
@Getter @Setter @NoArgsConstructor @SuperBuilder
public class AKGroupParent extends KObjectAttributes {

    @Column(name = "parent_group_id", nullable = false)
    private UUID parentGroupId;

    /**
     * Фабричный метод для создания экземпляра AKProjectName.
     */
    public static AKGroupParent create(
            KGroup kGroup,
            UUID parentGroupId,
            UUID companyId,
            UUID createdBy,
            Instant now) {

        if (kGroup == null || companyId == null || createdBy == null) {
            throw new IllegalArgumentException(
                    "kGroup, companyId, createdBy are required");
        }

        return AKGroupParent.builder()
                .key(new KAttributeKey(kGroup.getId(), now))
                .kObject(kGroup)
                .parentGroupId(parentGroupId)
                .systemAttributes(
                        new KSystemAttributes(null, companyId, createdBy))
                .build();
    }
}
