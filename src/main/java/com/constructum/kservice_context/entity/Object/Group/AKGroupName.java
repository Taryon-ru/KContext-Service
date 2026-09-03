package com.constructum.kservice_context.entity.Object.Group;

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

@Entity
@Table(name = "attr_group_name")
@Getter @Setter @NoArgsConstructor @SuperBuilder
public class AKGroupName extends KObjectAttributes {

    @Column(name = "group_name", nullable = false)
    private String groupName;


    /**
     * Фабричный метод для создания экземпляра AKProjectName.
     */
    public static AKGroupName create(KGroup kGroup, String groupName, UUID companyId, UUID createdBy, Instant now) {

        if (kGroup == null || groupName == null || groupName.isBlank()) {
            throw new IllegalArgumentException("kGroup and groupName are required");
        }

        if (companyId == null || createdBy == null) {
            throw new IllegalArgumentException("companyId and createdBy are required");
        }
        return AKGroupName.builder()
                .key(new KAttributeKey(kGroup.getId(), now))
                .kObject(kGroup)
                .groupName(groupName)
                .systemAttributes(new KSystemAttributes(null, companyId, createdBy))
                .build();
    }
}
