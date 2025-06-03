package com.bankmanagement.banksystem.entities;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "bank_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long userId;

    @Column(name = "userName")
    private String userName;

    @Column(name = "email")
    private String email;

    @Column(name = "userLogin")
    private String userLogin;

    @Column(name = "userPassword")
    private String userPassword;

    @Column(name = "yearsCountry")
    private int yearsCountry;

    @Column(name = "employed")
    private int employed;

    @Column(name = "totalLoans")
    private Long totalLoans;

    @Column(name = "userBalance")
    private Long userBalance;

    @Column(name = "isAdmin")
    private int isAdmin;

    @Column(name = "contactNumber")
    private String contactNumber;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles",
               joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "userId"),
               inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<Role> roles;

    @Override
    public String toString() {
        String isEmployedString;
        String isAdminString;
        
        if (this.employed == 1) {isEmployedString = "employed";}
        else {isEmployedString = "unemployed";}

        if (this.isAdmin == 1) {isAdminString = "admin";}
        else {isAdminString = "not admin";}

        return String.format("%s ( user name = %s, email = %s, user id = %d, user login = %s, lived %d years in this country, %s, total loans = %d, %s, contact number = %s )",
            getClass().getSimpleName(), userName, email, userId, userLogin, yearsCountry, isEmployedString, totalLoans, isAdminString, contactNumber);

    }
}
