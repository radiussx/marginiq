package com.marginiq.supplier;

import jakarta.persistence.*;

@Entity
@Table(name = "suppliers")
public class Supplier {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false) private String name;
    private String contactEmail;
    private Integer leadTimeDays;

    protected Supplier() {}
    public Supplier(String name, String contactEmail, Integer leadTimeDays) {
        this.name = name; this.contactEmail = contactEmail; this.leadTimeDays = leadTimeDays;
    }
    public Long getId(){ return id; }
    public String getName(){ return name; }
    public String getContactEmail(){ return contactEmail; }
    public Integer getLeadTimeDays(){ return leadTimeDays; }
}
