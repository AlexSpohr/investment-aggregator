package com.projects.investmentaggregator.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_billingaddress")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BillingAddress {

    @Id
    @Column(name = "account_id")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "street")
    private String street;

    @Column(name = "number")
    private Integer number;
}
