package com.marginiq.sales;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.marginiq.product.Product;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="sale_items")
public class SaleItem {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="sale_id", nullable=false)
    private Sale sale;
    @ManyToOne(fetch=FetchType.EAGER) @JoinColumn(name="product_id", nullable=false)
    private Product product;
    @Column(nullable=false) private Integer quantity;
    @Column(nullable=false, precision=12, scale=2) private BigDecimal unitPrice;
    @Column(nullable=false, precision=12, scale=2) private BigDecimal unitCost;

    protected SaleItem() {}
    public SaleItem(Product product, Integer quantity, BigDecimal unitPrice, BigDecimal unitCost) {
        this.product=product; this.quantity=quantity; this.unitPrice=unitPrice; this.unitCost=unitCost;
    }
    void attachTo(Sale sale){this.sale=sale;}
    public Long getId(){return id;} public Product getProduct(){return product;}
    public Integer getQuantity(){return quantity;} public BigDecimal getUnitPrice(){return unitPrice;}
    public BigDecimal getUnitCost(){return unitCost;}
}
