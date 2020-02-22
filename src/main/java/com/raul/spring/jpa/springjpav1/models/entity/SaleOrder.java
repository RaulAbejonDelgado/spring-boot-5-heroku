package com.raul.spring.jpa.springjpav1.models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="sales_order")
public class SaleOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String description;
    private String obserbation;

    @Column(name="create_at")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference //to skip redundancy
    private Partner partner;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "sale_order_id")
    private List<SaleOrderLine> lines;

    public SaleOrder(){this.lines = new ArrayList<>();}


    @PrePersist
    public void prePersist() {
        this.createAt = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getObserbation() {
        return obserbation;
    }

    public void setObserbation(String obserbation) {
        this.obserbation = obserbation;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    //When the object is serializable this method will be skip
    @XmlTransient
    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public List<SaleOrderLine> getLines() {
        return lines;
    }

    public void setLines(List<SaleOrderLine> lines) {
        this.lines = lines;
    }

    public void addLine(SaleOrderLine sol){
        this.lines.add(sol);
    }

    public Double getTotal(){
        Double total = 0.0;
        for(SaleOrderLine sol : lines){
            total += sol.computeTotal();
        }
        return total;
    }
}
