package hello.itemservice.repository.jpa;

import hello.itemservice.repository.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpringDataJpaItemRepository extends JpaRepository<ItemEntity, Long> {

    List<ItemEntity> findByItemNameLike(String itemName);

    List<ItemEntity> findByPriceLessThanEqual(Integer price);

    // 쿼리 메서드
    List<ItemEntity> findByItemNameLikeAndPriceLessThanEqual(String itemName, Integer price);

    // 쿼리 직접 실행
    @Query("select i from ItemEntity i where i.itemName like :itemName and i.price <= :price")
    List<ItemEntity> findItemEntities(@Param("itemName") String itemName, @Param("price") Integer price);

}
