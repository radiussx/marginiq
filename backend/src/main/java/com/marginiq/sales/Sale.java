package com.marginiq.sales;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="sales")
public class Sale {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false) private LocalDateTime soldAt;
    @Column(nullable=false, precision=12, scale=2) private BigDecimal discountAmount = BigDecimal.ZERO;
    @OneToMany(mappedBy="sale", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<SaleItem> items = new ArrayList<>();

    protected Sale() {}
    public Sale(LocalDateTime soldAt, BigDecimal discountAmount) {
        this.soldAt=soldAt; this.discountAmount=discountAmount;
    }
    public void addItem(SaleItem item){ items.add(item); item.attachTo(this); }
    public Long getId(){return id;} public LocalDateTime getSoldAt(){return soldAt;}
    public BigDecimal getDiscountAmount(){return discountAmount;} public List<SaleItem> getItems(){return items;}
}
