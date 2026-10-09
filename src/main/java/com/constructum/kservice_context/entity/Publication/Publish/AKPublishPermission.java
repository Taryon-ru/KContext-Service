package com.constructum.kservice_context.entity.Publication.Publish;

import com.constructum.kservice_context.entity.Publication.KPublication;
import com.constructum.kservice_context.entity.Publication.KPublicationAttributes;
import com.constructum.kservice_context.entity.common.KAttributeKey;
import com.constructum.kservice_context.entity.common.KSystemAttributes;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.time.Instant;
import java.util.UUID;

import org.checkerframework.checker.units.qual.C;

// ===== AKPublishPermission.java =====
/**
 * Атрибут: потолок прав локальной публикации.
 *
 * АРХИТЕКТУРНЫЙ ИНВАРИАНТ:
 * - permission_mask = -1 означает "без ограничений" (все биты = 1)
 * - permission_mask >= 0 означает конкретную маску прав
 * - Хранится отдельно от scope для независимого изменения
 */
@Entity
@Table(name = "attr_publish_permission", schema = "entities")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @SuperBuilder
public class AKPublishPermission extends KPublicationAttributes {

    public static final int UNLIMITED = -1;

    @Column(name = "permission_mask", nullable = false)
    private Integer permissionMask;

    // возвращает все атрибуты как один оюъект
    public Integer getValue() { return permissionMask; }

    public static AKPublishPermission create(KPublication pub, Integer permissionMask, Instant now, UUID companyId, UUID createdBy) 
    {

        if (pub == null ) { throw new IllegalArgumentException("pub is required"); }
        if (permissionMask == null) permissionMask = UNLIMITED;
        if (permissionMask < -1) { throw new IllegalArgumentException("permissionMask must be >= -1"); }

        return AKPublishPermission.builder()
            .key(new KAttributeKey(pub.getId(), now))
            .kObjectPublication(pub)
            .permissionMask(permissionMask)
            .systemAttributes(new KSystemAttributes(null, companyId, createdBy))
            .build();
    }
}
