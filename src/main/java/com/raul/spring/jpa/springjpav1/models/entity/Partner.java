package com.raul.spring.jpa.springjpav1.models.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="partners")
public class Partner implements Serializable {

    private static final long serialVersionUid = 1l;

    /**
     * GenerationType.IDENTITY -> autoincrement id to h2 and mysql
     * GenerationType.SEQUENCE -> autoincrement id to Msql Server, postgres
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @NotNull
    @Size(min=4, max=20)
    private String name;
    @NotEmpty
    @NotNull
    private String surname;

    @NotEmpty
    @Email
    @NotNull
    @Email
    private String email;

    @Column(name="create_at")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createAt;

    private String photo;

    @OneToMany(mappedBy = "partner",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<SaleOrder> saleOrders;

    public Partner() {
        saleOrders = new ArrayList<>();
    }

    public Partner(Long id, String name, String surname, String email, Date createAt, String photo) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.createAt = createAt;
        this.photo = photo;
    }

    @PrePersist
    public void prePersist() {
        this.createAt = new Date();
    }

    public List<SaleOrder> getSaleOrders() {
        return saleOrders;
    }

    public void setSaleOrders(List<SaleOrder> saleOrders) {
        this.saleOrders = saleOrders;
    }

    public void addSaleOrder(SaleOrder so){
        this.saleOrders.add(so);
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public static long getSerialVersionUid() {
        return serialVersionUid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    @Override
    public String toString() {
        return "Partner{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", createAt=" + createAt +
                ", photo='" + photo + '\'' +
                ", saleOrders=" + saleOrders +
                '}';
    }
}
