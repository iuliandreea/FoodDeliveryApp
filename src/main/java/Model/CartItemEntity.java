package Model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "cart_item", schema = "deliveryapp", catalog = "")
public class CartItemEntity {
    private int idcartItem;
    private int idcart;
    private int idproduct;
    private int quantity;
    private double interPrice;
    private boolean deleted;

    @Id
    @Column(name = "idcart_item")
    public int getIdcartItem() {
        return idcartItem;
    }

    public void setIdcartItem(int idcartItem) {
        this.idcartItem = idcartItem;
    }

    @Basic
    @Column(name = "idcart")
    public int getIdcart() {
        return idcart;
    }

    public void setIdcart(int idcart) {
        this.idcart = idcart;
    }

    @Basic
    @Column(name = "idproduct")
    public int getIdproduct() {
        return idproduct;
    }

    public void setIdproduct(int idproduct) {
        this.idproduct = idproduct;
    }

    @Basic
    @NotNull(message="Quantity cannot be 0")
    @Min(value=1, message="Please enter a valid quantity")
    @Column(name = "quantity")
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Basic
    @NotNull(message="Price cannot be 0")
    @Min(value=1, message="Please enter a valid price")
    @Column(name = "inter_price")
    public double getInterPrice() {
        return interPrice;
    }

    public void setInterPrice(double interPrice) {
        this.interPrice = interPrice;
    }

    @Basic
    @Column(name = "deleted")
    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CartItemEntity that = (CartItemEntity) o;

        if (idcartItem != that.idcartItem) return false;
        if (idcart != that.idcart) return false;
        if (idproduct != that.idproduct) return false;
        if (quantity != that.quantity) return false;
        if (Double.compare(that.interPrice, interPrice) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = idcartItem;
        result = 31 * result + idcart;
        result = 31 * result + idproduct;
        result = 31 * result + quantity;
        temp = Double.doubleToLongBits(interPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
