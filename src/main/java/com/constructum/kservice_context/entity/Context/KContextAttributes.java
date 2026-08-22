package com.constructum.kservice_context.entity.Context;

import com.constructum.kservice_context.entity.common.KAttribute;
// import com.constructum.kservice_context.entity.common.KAttributeKey;
// import com.constructum.kservice_context.entity.common.KSystemAttributes;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;



/**
 * Абстрактный класс от которого наследуются все атрибуты (атрибутные таблицы)
 * для объектов KContext
 * Инкапсулирует (содержит) Служебные атрибуты для каждой таблицы (например, validTo).
 * Используются для управления жизненным циклом версии.
 */


@MappedSuperclass
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@SuperBuilder
public abstract class KContextAttributes extends KAttribute{

    // @EmbeddedId
    // protected KAttributeKey key; // KContext не наследует KObject поэтому key.id = KContext.id

    @MapsId("id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_ctx_attr_state"))
    protected KContext kContext; // <-- вот здесь задается связ с якорем KContext

    // @Embedded
    // protected KSystemAttributes systemAttributes;
}
