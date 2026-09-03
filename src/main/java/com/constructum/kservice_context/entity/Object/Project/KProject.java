package com.constructum.kservice_context.entity.Object.Project;

import java.time.Instant;
import java.util.UUID;

import com.constructum.kservice_context.entity.Object.KObject;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * Объект типа "Проект". 
 * Служит контекстом для работы с другими объектами системы.
 */
@Entity
@DiscriminatorValue("kproject")
public class KProject extends KObject {
    // Поля не добавляем, так как используем атрибутивную модель

    // Приватный конструктор, чтобы запретить создание через new KProject()
    // и принудить использовать фабричный метод create.
    // JPA/Hibernate требуют, чтобы у каждой @Entity был конструктор без аргументов
    private KProject() {}

    // --- Фабричный метод ---
    public static KProject create(UUID companyId, UUID createdBy, Instant now) {
       
        KProject user = new KProject();
        user.setId(UUID.randomUUID()); // без генерации
        user.setCompanyId(companyId);
        user.setValidFrom(now);
        user.setValidTo(null);
        user.setCreatedBy(createdBy);
        // type устанавливается автоматически Hibernate через @DiscriminatorValue
        return user;
    }
}
