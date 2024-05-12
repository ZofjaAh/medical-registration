package moj.project.infrastructure.database.repository.jpa;

import moj.project.infrastructure.security.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity findByEmail(String email);
}

