package com.eCommerce.eCommerceApp.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "order_product")
public class OrderProduct {
      @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    @ManyToOne
    // @MapsId("idOrder")
    @JoinColumn(name = "idOrder")
    @JsonIgnore
    private Orders order;

    @ManyToOne
    // @MapsId("productId")
    @JoinColumn(name = "productId")
    private Product product;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    private Long productCostId;

    private Float productCostAmount;
   
    public Float getProductCostAmount() {
        return productCostAmount;
    }

    public void setProductCostAmount(Float productCostAmount) {
        this.productCostAmount = productCostAmount;
    }

    public Long getProductCostId() {
        return productCostId;
    }

    public void setProductCostId(Long productCostId) {
        this.productCostId = productCostId;
    }

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}
