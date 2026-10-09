// ===== entity/Context/CrossContext/AKGrantClosedBy.java =====
package com.constructum.kservice_context.entity.Publication;

import java.util.UUID;

import org.checkerframework.checker.units.qual.C;

import java.time.Instant;

import com.constructum.kservice_context.entity.common.KAttributeKey;
import com.constructum.kservice_context.entity.common.KSystemAttributes;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "attr_publication_closed_by", schema = "entities")
@Getter @Setter @NoArgsConstructor @SuperBuilder
public class AKPublicationClosedBy extends KPublicationAttributes {
    
    @Column(name = "closed_by", nullable = false)
    private UUID closedBy;
    
    public static AKPublicationClosedBy create(KPublication anchor, Instant now, UUID companyId, UUID closedBy) {

        if (anchor == null) {throw new IllegalArgumentException("anchor is required");}

        if (now == null) {throw new IllegalArgumentException("now is required");}

        if (companyId == null) {throw new IllegalArgumentException("companyId is required");}

        if (closedBy == null) {throw new IllegalArgumentException("closedBy is required");}

        return AKPublicationClosedBy.builder()
            .key(new KAttributeKey(anchor.getId(), now))
            .kObjectPublication(anchor)
            .closedBy(closedBy)
            .systemAttributes(new KSystemAttributes(now, companyId, closedBy))
            .build();
    }
}
