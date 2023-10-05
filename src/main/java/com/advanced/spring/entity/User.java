package com.advanced.spring.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder(toBuilder=true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users", schema = "advanced_robot_dreams")
@Component
@Scope("prototype")
public class User implements Serializable {
    /*
    TABLE:
    Цей метод використовує окрему таблицю бази даних для збереження ідентифікаторів. Для кожного сутності, який використовує цей спосіб,
    в базі даних буде створена окрема таблиця (або вони можуть використовувати одну спільну таблицю), яка буде містити наступний доступний ідентифікатор.
    Коли потрібно згенерувати новий ідентифікатор, система звертається до цієї таблиці, отримує наступний доступний ідентифікатор і збільшує його значення.

    SEQUENCE:
    Цей метод використовує базу даних, яка підтримує послідовності (наприклад, Oracle). Послідовність - це об'єкт в базі даних,
    який автоматично генерує унікальні числові значення.
    Коли нова сутність зберігається в базі даних, вона звертається до послідовності для отримання наступного значення ідентифікатора.

    IDENTITY:
    Цей тип використовує автоінкрементні поля в базах даних, які підтримують цей механізм (наприклад, MySQL).
    Коли новий рядок вставляється в таблицю, база даних автоматично генерує та надає наступне значення для ідентифікатора.

    UUID:
    Цей метод генерує унікальний ідентифікатор у форматі UUID. UUID це 128-бітне число, яке зазвичай представлено в шестнадцятковому форматі.
    Він може бути використаний для гарантування унікальності ідентифікаторів навіть при великих масштабах розподіленої системи.

    AUTO:
    Цей тип дозволяє постачальнику постійності (наприклад, Hibernate або EclipseLink) вирішувати, який з методів використовувати.
    Залежно від конкретної реалізації та доступності різних типів в базі даних, постачальник може вибирати найкращий метод для генерації ідентифікаторів.
    Ці методи генерації ідентифікаторів використовуються у поєднанні з анотацією @GeneratedValue
    у JPA для автоматичного створення значень для полів ідентифікаторів.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //@Column - колонки, якщо треба додаткова валидация
    private String email;
    private String name;
    private String password;
    @ManyToMany
    @JoinTable(
            name = "user_roles", schema = "advanced_robot_dreams",
            joinColumns = {
                    @JoinColumn(
                            name = "user_id",
                            nullable = false
                    )
            },
            inverseJoinColumns = {
                    @JoinColumn(
                            name = "role_id",
                            nullable = false
                    )
            }

    )
    @JsonIgnore
    private List<Role> roleList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;
}
