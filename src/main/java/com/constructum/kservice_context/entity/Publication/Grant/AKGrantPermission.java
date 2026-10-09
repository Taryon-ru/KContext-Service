package com.constructum.kservice_context.entity.Publication.Grant;

import com.constructum.kservice_context.entity.Publication.KPublicationAttributes;
import com.constructum.kservice_context.entity.common.KAttributeKey;
import com.constructum.kservice_context.entity.common.KSystemAttributes;
import com.constructum.kservice_context.security.PermissionMapper;
import com.constructum.kservice_context.security.PermissionMasks;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.UUID;


/**
 * Атрибут: уровень доступа кросс-организационного гранта.
 * Темпоральный (может иметь множество версий).
 *
 * Уровни доступа:
 * - READ / VIEW: только чтение
 * - WRITE / EDIT / CO_DEVELOPMENT: чтение + запись
 * - FULL / ADMIN / OWNER: полный доступ
 *
 * !Наследует KAttribute, но НЕ KObjectAttributes (грант не привязан к KObject напрямую)
 */


@Entity
@Table(
    name = "attr_grant_permission",
    indexes = {
        @Index(name = "idx_permission_active", columnList = "id, valid_to")
    }
)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @SuperBuilder
public class AKGrantPermission extends KPublicationAttributes{
        
    @Column(name = "permission_level", nullable = false, length = 50)
    private String permissionLevel;

    // маска доступа, вычисляется на этапе создания версии атрибута
    @Column(name = "permission_mask", nullable = false)
    private Integer permissionMask;
    
    /**
     * Фабричный метод создания атрибута.
     */
    public static AKGrantPermission create(
        KGrant grant,
        Instant now,
        String permissionLevel,
        UUID companyId,
        UUID createdBy
    ) {
        if (grant == null || permissionLevel == null || permissionLevel.isBlank()) {
            throw new IllegalArgumentException("Grant and permissionLevel are required");
        }
        if (companyId == null || createdBy == null) {
            throw new IllegalArgumentException("companyId and createdBy are required");
        }
        
        // Нормализация
        String normalized = permissionLevel == null ? null : permissionLevel.toUpperCase();

        // Вычисление маски (fail-fast)
        int mask = PermissionMapper.grantToMask(normalized);
        PermissionMasks.validate(mask);

        return AKGrantPermission.builder()
            .key(new KAttributeKey(grant.getId(), now))
            .kObjectPublication(grant)
            .permissionLevel(permissionLevel.toUpperCase())
            .permissionMask(mask)
            .systemAttributes(new KSystemAttributes(null, companyId, createdBy))
            .build();
    }

    /**
     * Проверка: разрешает ли уровень доступа операцию записи.
     */
    public boolean allowsWrite() {
        if (permissionLevel == null) return false;
        String level = permissionLevel.toUpperCase();
        return level.equals("WRITE") || level.equals("EDIT") || 
               level.equals("CO_DEVELOPMENT") || level.equals("FULL") || 
               level.equals("ADMIN") || level.equals("OWNER");
    }
    
    /**
     * Проверка: разрешает ли уровень доступа операцию чтения.
     */
    public boolean allowsRead() {
        if (permissionLevel == null) return false;
        return true; // Любой грант даёт минимум READ
    }

}
