package hello.itemservice.domain;

import hello.itemservice.repository.entity.ItemEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Item {

    private Long id;

    private String itemName;
    private Integer price;
    private Integer quantity;

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }

    public ItemEntity toModel() {
        return new ItemEntity(this.itemName, this.price, this.quantity);
    }

}
