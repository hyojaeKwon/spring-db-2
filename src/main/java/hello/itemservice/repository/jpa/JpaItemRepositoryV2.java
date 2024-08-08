package hello.itemservice.repository.jpa;

import hello.itemservice.domain.Item;
import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.ItemUpdateDto;
import hello.itemservice.repository.entity.ItemEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;


@Repository
@Transactional
@RequiredArgsConstructor
public class JpaItemRepositoryV2 implements ItemRepository {

    private final SpringDataJpaItemRepository repository;

    @Override
    public Item save(Item item) {
        return repository.save(item.toModel()).toItem();
    }

    @Override
    public void update(Long itemId, ItemUpdateDto updateParam) {
        ItemEntity itemEntity = repository.findById(itemId).orElseThrow();
        itemEntity.setItemName(updateParam.getItemName());
        itemEntity.setQuantity(updateParam.getQuantity());
        itemEntity.setPrice(updateParam.getPrice());
    }

    @Override
    public Optional<Item> findById(Long id) {
        Optional<ItemEntity> result = repository.findById(id);
        return result.map(ItemEntity::toItem);
    }

    @Override
    public List<Item> findAll(ItemSearchCond cond) {
        String itemName = cond.getItemName();
        Integer price = cond.getMaxPrice();

        if(StringUtils.hasText(itemName) && price != null) {
            return repository.findItemEntities("%" + itemName + "%", price).stream().map(ItemEntity::toItem).toList();
        }else if(StringUtils.hasText(itemName)) {
            return repository.findByItemNameLike("%" + itemName + "%").stream().map(ItemEntity::toItem).toList();
        }else if(price != null) {
            return repository.findByPriceLessThanEqual(price).stream().map(ItemEntity::toItem).toList();
        }else {
            return repository.findAll().stream().map(ItemEntity::toItem).toList();
        }
    }
}
