package com.constructum.kservice_context.entity.Object.Group;

import java.time.Instant;
import java.util.UUID;

import com.constructum.kservice_context.entity.Object.KObject;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * Объект типа "Группа" (Организационная единица).
 */
@Entity
@DiscriminatorValue("kgroup")
public class KGroup extends KObject {
    // Поля не добавляем, так как используем атрибутивную модель

    // Приватный конструктор, чтобы запретить создание через new KGroup()
    // и принудить использовать фабричный метод create.
    // JPA/Hibernate требуют, чтобы у каждой @Entity был конструктор без аргументов
    private KGroup() {
    }

    // --- Фабричный метод ---
    public static KGroup create(UUID companyId, UUID createdBy, Instant now) {

        KGroup user = new KGroup();
        user.setId(UUID.randomUUID()); // FIX: сосздаем UUID здесь
        user.setCompanyId(companyId);
        user.setValidFrom(now);
        user.setValidTo(null);
        user.setCreatedBy(createdBy);
        // type устанавливается автоматически Hibernate через @DiscriminatorValue
        return user;

    }
}
