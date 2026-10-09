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

// ===== AKPublicationScope.java =====
/**
 * Атрибут: scope публикации (куда опубликовано).
 *
 * АРХИТЕКТУРНЫЙ ИНВАРИАНТ:
 * Этот атрибут НЕ содержит permission_mask. Permission хранится в отдельном
 * атрибуте {@link AKPublishPermission}, что позволяет независимо менять
 * scope и ceiling публикации.
 * 
 * Модель:
 * COMPANY → scopeId = null
 * PROJECT → scopeId = projectId
 * GROUP → scopeId = groupId
 * 
 * примечание:
 * SELF возникает автоматически через personal synthetic group (effective_user_group, где group_id = user_id).
 * OWNER/SELF вычисляется через has_object_permission() и проекции, а не через KPublish.
 */
@Entity
@Table(name = "attr_object_publication_scope", schema = "entities")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @SuperBuilder
public class AKPublishScope extends KPublicationAttributes {

    public enum ScopeType { PROJECT, GROUP, COMPANY }

    @Enumerated(EnumType.STRING)
    @Column(name = "scope_type", nullable = true)
    private ScopeType scopeType;

    @Column(name = "scope_id")  //<-- COMPANY → scopeId = null
    private UUID scopeId;

    public static AKPublishScope create(KPublication pub, ScopeType scopeType, UUID scopeId, Instant now, UUID companyId, UUID createdBy) 
    {
        if (pub == null || scopeType == null) {
            throw new IllegalArgumentException("pub and scopeType are required");
        }

        if (scopeType != ScopeType.COMPANY && scopeId == null) {
            throw new IllegalArgumentException("scopeId is required for " + scopeType);
        }

        if (scopeType == ScopeType.COMPANY && scopeId != null) {
            throw new IllegalArgumentException("scopeId must be null for COMPANY scope");
        }


        return AKPublishScope.builder()
            .key(new KAttributeKey(pub.getId(), now))
            .kObjectPublication(pub)
            .scopeType(scopeType)
            .scopeId(scopeId)
            .systemAttributes(new KSystemAttributes(null, companyId, createdBy))
            .build();
    }



    // ========================================================================
    // Value Object (record) для атомарного сравнения и мержа
    // ========================================================================
    /**
     * Вложенный record для инкапсуляции полного состояния атрибута.
     *
     * Зачем это нужно?
     * - Позволяет сравнивать составные атрибуты как единое целое (record.equals)
     * - Упрощает логику "слияния" в updateAttribute(..., Supplier<V>, ...)
     */
    public record ScopeData(ScopeType scopeType, UUID scopeId) {}

    /**
     * Возвращает все поля атрибута как один Value Object.
     * Используется в updateAttribute для атомарного сравнения.
     */
    public ScopeData getValue() {
        return new ScopeData(scopeType, scopeId);
    }

    ///////////////////////////////////////////////
    // Вспомогательные методы
    ///////////////////////////////////////////////
    public ScopeType getScopeType() {return scopeType;}
    public UUID getScopeId() {return scopeId;}

}
