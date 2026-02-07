package dev.am.spring_h2_db.domain;

import org.springframework.data.jpa.repository.JpaRepository;

interface PersonRepository extends JpaRepository<PersonEntity, Integer> {
}
