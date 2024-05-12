package moj.project.infrastructure.database.repository.jpa;

import lombok.AllArgsConstructor;
import moj.project.infrastructure.security.UserEntity;
import moj.project.util.EntityFixtures;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


@AllArgsConstructor(onConstructor = @__(@Autowired))

class UserJpaRepositoryTest  extends AbstractJpa{
    private UserJpaRepository userJpaRepository;
    @Test
    @Transactional()
    void thatUserCanBeFindCorrectly(){
        //given
        UserEntity userEntity = EntityFixtures.someUser1();
        String email = "roman_niespiency@pacjent.pl";
        userJpaRepository.saveAndFlush(userEntity);
        //when
        UserEntity userFound = userJpaRepository.findByEmail(email);
        //then
        Assertions.assertThat(userFound.getUserName()).isEqualTo(userEntity.getUserName());
    }

}