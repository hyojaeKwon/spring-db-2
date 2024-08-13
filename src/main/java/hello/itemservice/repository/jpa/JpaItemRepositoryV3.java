package hello.itemservice.repository.jpa;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.itemservice.domain.Item;
import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.ItemUpdateDto;
import hello.itemservice.repository.entity.ItemEntity;
import hello.itemservice.repository.entity.QItemEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static hello.itemservice.repository.entity.QItemEntity.*;

@Transactional
public class JpaItemRepositoryV3 implements ItemRepository {

    private final EntityManager em;
    private final JPAQueryFactory query;

    public JpaItemRepositoryV3(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public Item save(Item item) {
        ItemEntity model = item.toModel();
        em.persist(model);
        return model.toItem();
    }

    @Override
    public void update(Long itemId, ItemUpdateDto updateParam) {
        ItemEntity entity = em.find(ItemEntity.class, itemId);
        entity.setItemName(updateParam.getItemName());
        entity.setPrice(updateParam.getPrice());
        entity.setQuantity(updateParam.getQuantity());
    }

    @Override
    public Optional<Item> findById(Long id) {
        ItemEntity itemEntity = em.find(ItemEntity.class, id);
        return Optional.of(itemEntity.toItem());
    }


    public List<Item> findAllOld(ItemSearchCond cond) {
        String itemName = cond.getItemName();
        Integer maxPrice = cond.getMaxPrice();

        QItemEntity item = itemEntity;
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (StringUtils.hasText(itemName)) {
            booleanBuilder.and(item.itemName.like("%" + itemName + "%"));
        }
        if(maxPrice != null) {
            booleanBuilder.and(item.price.loe(maxPrice));
        }

        List<ItemEntity> result = query.select(item)
                .from(item)
                .where(booleanBuilder)
                .fetch();
        return result.stream().map(ItemEntity::toItem).toList();
    }

    @Override
    public List<Item> findAll(ItemSearchCond cond) {
        String itemName = cond.getItemName();
        Integer maxPrice = cond.getMaxPrice();

        List<ItemEntity> result = query.select(itemEntity)
                .from(itemEntity)
                .where(likeItemName(itemName), maxPrice(maxPrice))
                .fetch();
        return result.stream().map(ItemEntity::toItem).toList();
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
