package hello.itemservice.service;

import hello.itemservice.domain.Item;
import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.ItemUpdateDto;
import hello.itemservice.repository.entity.ItemEntity;
import hello.itemservice.repository.v2.ItemQueryRepositoryV2;
import hello.itemservice.repository.v2.ItemRepositoryV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemServiceV2 implements ItemService{

    private final ItemRepositoryV2 itemRepository;
    private final ItemQueryRepositoryV2 itemQueryRepositoryV2;

    @Override
    public Item save(Item item) {
        return itemRepository.save(item.toModel()).toItem();
    }

    @Override
    public void update(Long itemId, ItemUpdateDto updateParam) {
        ItemEntity itemEntity = itemRepository.findById(itemId).orElseThrow();
        itemEntity.setItemName(updateParam.getItemName());
        itemEntity.setQuantity(updateParam.getQuantity());
        itemEntity.setPrice(updateParam.getPrice());

    }

    @Override
    public Optional<Item> findById(Long id) {
        Optional<ItemEntity> entity = itemRepository.findById(id);
        return entity.map(ItemEntity::toItem);
    }

    @Override
    public List<Item> findItems(ItemSearchCond itemSearch) {
        return itemQueryRepositoryV2.findAll(itemSearch);
    }
}
