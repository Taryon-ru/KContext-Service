package com.constructum.kservice_context.entity.Context;

import java.util.UUID;

import com.constructum.kservice_context.entity.common.KAttributeKey;
import com.constructum.kservice_context.entity.common.KSystemAttributes;

import java.time.Instant;

import lombok.*;
import jakarta.persistence.*;
import lombok.experimental.SuperBuilder; 

@Entity
@Table(name = "attr_kcontext_closed_by")
@Getter @Setter @NoArgsConstructor @SuperBuilder
public class AKContextClosedBy extends KContextAttributes {

    @Column(name = "closed_by", nullable = false)
    private UUID closedBy;

    public static AKContextClosedBy create(KContext anchor, Instant now, UUID companyId, UUID closedBy) {
        return AKContextClosedBy.builder()
            .key(new KAttributeKey(anchor.getId(), now))
            .kContext(anchor)
            .closedBy(closedBy)
            .systemAttributes(new KSystemAttributes(null, companyId, closedBy)) // в поле createdBy записываем того кто закрыл.
            .build();
    }
}
