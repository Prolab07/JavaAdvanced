package com.advanced.spring.repository;

import com.advanced.spring.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/*
JpaRepository: Цей інтерфейс наслідується від CrudRepository
та включає в себе додаткові JPA-специфічні операції, такі як збереження в пакетному режимі
(flush(), saveAndFlush()) і пагінація (findAll(Pageable pageable)).

JpaRepository надає розширений набір операцій, які корисні для JPA, такі як flush(), deleteInBatch(), getOne(), findAll(Sort sort) та ін.

JpaRepository надає методи, такі як saveAll(), flush(), deleteInBatch()
 */
@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
