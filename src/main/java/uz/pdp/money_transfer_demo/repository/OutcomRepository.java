package uz.pdp.money_transfer_demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.money_transfer_demo.entity.Outcome;

import java.util.List;

public interface OutcomRepository extends JpaRepository<Outcome, Integer> {
    boolean existsByFromCardId(Integer fromCard_id);

    List<Outcome> findAllByFromCardId(Integer fromCard_id);
}
