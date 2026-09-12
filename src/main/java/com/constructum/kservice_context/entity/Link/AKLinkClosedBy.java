package com.constructum.kservice_context.entity.Link;

import java.util.UUID;

import com.constructum.kservice_context.entity.common.KAttributeKey;
import com.constructum.kservice_context.entity.common.KSystemAttributes;

import java.time.Instant;

import lombok.*;
import jakarta.persistence.*;
import lombok.experimental.SuperBuilder; 

/**
* Атрибут для всех якорей
* Хранит пользователя, который закрыл (удалид) якорь
*/

@Entity
@Table(name = "attr_klink_closed_by")
@Getter @Setter @NoArgsConstructor @SuperBuilder
public class AKLinkClosedBy extends KLinkAttributes {

    @Column(name = "closed_by", nullable = false)
    private UUID closedBy;

    public static AKLinkClosedBy create(KLink anchor, Instant now, UUID companyId, UUID closedBy) {
        return AKLinkClosedBy.builder()
            .key(new KAttributeKey(anchor.getId(), now))
            .kLink(anchor)
            .closedBy(closedBy)
            .systemAttributes(new KSystemAttributes(now, companyId, closedBy)) // в поле createdBy записываем того кто закрыл.
            .build();
    }
}
