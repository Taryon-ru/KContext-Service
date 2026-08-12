package com.constructum.kservice_context.entity.common;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Базовый абстрактный класс для всех темпоральных атрибутов.
 *
 * Общая структура атрибутов одинакова:
 *  - key (id якоря + validFrom)
 *  - systemAttributes (validTo, companyId, createdBy)
 *
 * ВАЖНО:
 *  - связь с якорем (ManyToOne) НЕ входит в базовый класс,
 *    потому что у KObjectAttributes и KContextAttributes разные типы якорей.
 *  - на основе этого класса сделаны KObjectAttributes и KContextAttributes
 *    
 */
@MappedSuperclass
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @SuperBuilder
public abstract class KAttribute {

    /**
     * Составной ключ атрибута:
     *  - id якоря (KObject.id или KContext.id)
     *  - validFrom — момент начала действия версии атрибута
     */
    @EmbeddedId
    protected KAttributeKey key;

    /**
     * Системные поля:
     *  - validTo — момент окончания действия версии
     *  - companyId — компания, в рамках которой действует атрибут
     *  - createdBy — пользователь, создавший версию
     */
    @Embedded
    protected KSystemAttributes systemAttributes;
}
