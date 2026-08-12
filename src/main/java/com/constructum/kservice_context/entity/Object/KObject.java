package com.constructum.kservice_context.entity.Object;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

import com.constructum.kservice_context.entity.common.KAnchor;

/**
 * Якорь для всех объектов системы.
 * Содержит уникальный идентификатор, тип объекта и временной интервал действия.
 * @DiscriminatorColumn указывает, какая колонка содержит значение типа подкласса.
 * При загрузке JPA автоматически создаёт объект нужного типа в зависимости от type.
 */

@Entity
@Table(name = "kobject")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@Getter @Setter //@NoArgsConstructor @AllArgsConstructor @Builder
public abstract class KObject extends KAnchor{ // <-- делаем класс абстрактным для защиты от прямого создания

    // Идентификатор компании владельца объекта, для разграничения прав (RLS)
    @Column(name = "company_id", nullable = false, updatable = false)
    private UUID companyId;

    // Идентификатор пользователя (кто создал запись)
    @Column(name = "created_by", nullable = false, updatable = false)
    private UUID createdBy;

    //Hibernate сам управляет колонкой, указанной в @DiscriminatorColumn.
    //чтобы не возникли конфликты должно быть insertable = false, updatable = false
    @Column(nullable = false, length = 64, insertable = false, updatable = false)
    private String type; 

    // защищаем конструктор
    protected KObject() {}

    /**
     * Возвращает владельца контекста по умолчанию.
     * Для большинства объектов:
     * owner = created_by
     * Может быть переопределен наследниками.
     */
    public UUID getDefaultOwner() { return createdBy; }
}
