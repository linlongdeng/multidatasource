package weChat.repository.primary;

import org.springframework.data.jpa.repository.JpaRepository;

import weChat.domain.primary.Cardnum;

public interface CardnumRepository extends JpaRepository<Cardnum, Integer> {

}
