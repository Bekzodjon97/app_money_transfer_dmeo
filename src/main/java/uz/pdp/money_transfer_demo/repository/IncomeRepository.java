package uz.pdp.money_transfer_demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.money_transfer_demo.entity.Income;

import java.util.List;

public interface IncomeRepository extends JpaRepository<Income, Integer> {
    boolean existsByToCardId(Integer toCard_id);

    List<Income> findAllByToCardId(Integer toCard_id);
}
