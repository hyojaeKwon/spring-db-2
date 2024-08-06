package hello.itemservice.repository.entity;

import hello.itemservice.domain.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_name", length=10)
    private String itemName;
    private Integer price;
    private Integer quantity;

    public ItemEntity(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }

    public Item toItem() {
        return new Item(id, itemName, price, quantity);
    }
}