package hello.itemservice.repository.v2;

import hello.itemservice.repository.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepositoryV2 extends JpaRepository<ItemEntity, Long> {

}
