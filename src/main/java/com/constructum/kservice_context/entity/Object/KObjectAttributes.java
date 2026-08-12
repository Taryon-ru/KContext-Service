package com.constructum.kservice_context.entity.Object;

import com.constructum.kservice_context.entity.common.KAttribute;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder; // <-- ВАЖНО: импортировать SuperBuilder

/**
 * Абстрактный класс от которого наследуются все атрибуты (атрибутные таблицы)
 * для объектов KObject
 * Инкапсулирует (содержит) Служебные атрибуты для каждой таблицы (например, validTo).
 * Используются для управления жизненным циклом версии.
 * На текущий момент содержит дату окончания действия версии (validTo).
 */

@MappedSuperclass
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@SuperBuilder 
public abstract class KObjectAttributes extends KAttribute{

    /** Ссылка на «якорный» объект, поле "id" в KAttributeKey */
    @MapsId("id")                     // связывает поле id в PK с колонкой id в таблице
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id", nullable = false)
    protected KObject kObject;  // <-- вот здесь задается связ с якорем KObject

    // /** Служебные поля определены в классе KSystemAttributes наследуются через KAttribute**/
}
