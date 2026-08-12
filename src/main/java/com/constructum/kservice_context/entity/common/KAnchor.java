package com.constructum.kservice_context.entity.common;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

/**
 * Базовый абстрактный класс для всех темпоральных якорей.
 *
 * Этот класс описывает только то, что является общим для всех якорей:
 *  - уникальный идентификатор
 *  - временной интервал действия (validFrom / validTo)
 *
 * ВАЖНО:
 *  - companyId, createdBy, targetObjectId, type — это доменные поля,
 *    поэтому они НЕ входят в базовый класс.
 *  - KAnchor НЕ является @Entity, а является @MappedSuperclass,
 *    чтобы его поля наследовались таблицами потомков.
 */
@MappedSuperclass
@Getter
@Setter
public abstract class KAnchor {

    /**
     * Уникальный идентификатор якоря.
     * Для KObject — это ID объекта.
     */
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    protected UUID id;

    /**
     * Момент времени, с которого данная версия якоря считается активной.
     */
    @Column(name = "valid_from", nullable = false)
    protected Instant validFrom;

    /**
     * Момент времени, когда версия перестала быть активной.
     * NULL означает, что версия активна.
     */
    @Column(name = "valid_to")
    protected Instant validTo;

    /**
     * Удобный метод: активна ли версия.
     */
    public boolean isActive() {
        return validTo == null;
    }
}

