package com.constructum.kservice_context.entity.Object.Project;

import java.time.Instant;
import java.util.UUID;

import com.constructum.kservice_context.entity.Object.KObjectAttributes;
import com.constructum.kservice_context.entity.common.KAttributeKey;
import com.constructum.kservice_context.entity.common.KSystemAttributes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "attr_project_name")
@Getter @Setter @NoArgsConstructor @SuperBuilder
public class AKProjectName extends KObjectAttributes {

    @Column(name = "project_name", nullable = false)
    private String projectName;



    ///////////////////////////////////////////////
    // Вспомогательные методы
    ///////////////////////////////////////////////

    // Вложенный класс-record нужен для объединения всех полей 
    // класса атрибута, позволяет пользоваться updateAttribute
    // благодаря ему над полями можно выполнять сравнение, как на единым целым
    // public record DefinitionAttr(String designation, UUID productId) {}

    // возвращает все атрибуты как один оюъект
    public String getValue() {
        return projectName;
    }

    /**
     * Фабричный метод для создания экземпляра AKProjectName.
     */
    public static AKProjectName create(KProject kProject, String projectName, UUID companyId,
            UUID createdBy, Instant now) {

        if (kProject == null || projectName == null || projectName.isBlank()) {
            throw new IllegalArgumentException("kProject and projectName are required");
        }

        if (companyId == null || createdBy == null) {
            throw new IllegalArgumentException("companyId and createdBy are required");
        }

        return AKProjectName.builder()
                .key(new KAttributeKey(kProject.getId(), now))
                .kObject(kProject)
                .projectName(projectName)
                .systemAttributes(new KSystemAttributes(null, companyId, createdBy))
                .build();
    }


}
