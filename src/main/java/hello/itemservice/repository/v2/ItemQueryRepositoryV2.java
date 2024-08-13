package hello.itemservice.repository.v2;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.itemservice.domain.Item;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.entity.ItemEntity;
import hello.itemservice.repository.entity.QItemEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;

import java.util.List;

import static hello.itemservice.repository.entity.QItemEntity.itemEntity;

@Transactional
@Repository
public class ItemQueryRepositoryV2 {

    private final JPAQueryFactory query;

    public ItemQueryRepositoryV2(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    public List<Item> findAll(ItemSearchCond cond) {
        return query.select(itemEntity)
                .from(itemEntity)
                .where(likeItemName(cond.getItemName()),
                        maxPrice(cond.getMaxPrice()))
                .fetch()
                .stream()
                .map(ItemEntity::toItem)
                .toList();
    }
    private BooleanExpression likeItemName(String itemName) {
        if (StringUtils.hasText(itemName)) {
            return itemEntity.itemName.like("%" + itemName + "%");
        }
        return null;
    }

    private BooleanExpression maxPrice(Integer maxPrice) {
        if(maxPrice != null) {
            return itemEntity.price.loe(maxPrice);
        }
        return null;
    }
}
