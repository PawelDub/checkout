package com.pragmaticcoders.checkout.test.unittest;

import com.pragmaticcoders.checkout.model.PriceDiscount;
import com.pragmaticcoders.checkout.repository.PriceDiscountRepository;
import com.pragmaticcoders.checkout.service.PriceDiscountService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Discount tests")
@SpringBootTest
public class DiscountTest {

    @Autowired
    PriceDiscountService priceDiscountService;

    @Autowired
    PriceDiscountRepository priceDiscountRepository;

    @Test
    @DisplayName("discount should be add correctly")
    public  void addItem() {

        PriceDiscount discount = new PriceDiscount("koszule", 3, new BigDecimal(70.00));
        PriceDiscount discountSaved = priceDiscountService.save(discount);

        assertThat(discountSaved.getType()).isEqualTo(discount.getType());
        assertThat(discountSaved.getQuantity()).isEqualTo(discount.getQuantity());
        assertThat(discountSaved.getPriceDiscount()).isEqualTo(discount.getPriceDiscount());
        priceDiscountService.deleteById(discount.getId());
    }

    @Test
    @DisplayName("should update discount correctly")
    public  void updateItem() {

        PriceDiscount discount = new PriceDiscount("koszule", 3, new BigDecimal(70.00));
        PriceDiscount discountSaved = priceDiscountService.save(discount);
        assertThat(discountSaved.getType()).isEqualTo(discount.getType());

        discount.setType("spodnie");
        discount.setQuantity(4);
        discount.setPriceDiscount(new BigDecimal(80.00));

        discountSaved = priceDiscountService.save(discount);
        assertThat(discountSaved.getType()).isEqualTo(discount.getType());
        assertThat(discountSaved.getQuantity()).isEqualTo(discount.getQuantity());
        assertThat(discountSaved.getPriceDiscount()).isEqualTo(discount.getPriceDiscount());
        priceDiscountService.deleteById(discount.getId());
    }

    @Test
    @DisplayName("should deleteById discount correctly")
    public  void deleteItem() {

        PriceDiscount discount = new PriceDiscount("koszule", 3, new BigDecimal(70.00));
        PriceDiscount discountSaved = priceDiscountService.save(discount);
        assertThat(discountSaved.getType()).isEqualTo(discount.getType());

        priceDiscountService.deleteById(discountSaved.getId());

        assertThat(priceDiscountRepository.findById(discountSaved.getId())).isEmpty();
    }

    @Test
    @DisplayName("should get all discounts")
    public  void findItemByType() {

        PriceDiscount discount_1 = new PriceDiscount("koszule", 3, new BigDecimal(70.00));
        PriceDiscount discount_2 = new PriceDiscount("spodnie", 2, new BigDecimal(10.00));
        PriceDiscount discount_3 = new PriceDiscount("kurtka", 4, new BigDecimal(45.00));
        priceDiscountService.save(discount_1);
        priceDiscountService.save(discount_2);
        priceDiscountService.save(discount_3);

        List<PriceDiscount> priceDiscounts = (List<PriceDiscount>) priceDiscountService.findAll();
        assertThat(priceDiscounts).hasSize(3);
        priceDiscountRepository.deleteAll();

    }

}
