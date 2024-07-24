package com.projects.investmentaggregator.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "tb_accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "description")
    private String description;

    @OneToOne(mappedBy = "account")
    @JsonManagedReference
    @PrimaryKeyJoinColumn
    private BillingAddress billingAddress;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account")
    @JsonManagedReference
    private List<AccountStock> accountStocks;

    public Account(String description, BillingAddress billingAddress, User user, List<AccountStock> accountStocks) {
        this.description = description;
        this.billingAddress = billingAddress;
        this.user = user;
        this.accountStocks = accountStocks;
    }
}
