package lab.hibernate.composite_id;

import java.io.Serializable;
import java.util.Date;

/**
 * Author: Frank
 * Date: 13-12-12
 * 联合主键
 * XML配置方式
 */
public class CustomerOrder {

    private Id id;

    private Date orderDate;

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    /**
     * Compsite-id类，必须实现Serializable，并重写equals和hashCode()方法，可以用IDE生成
     *
     */
    public static class Id implements Serializable{
        private int customerId;
        private int orderNumber;

        public int getCustomerId() {
            return customerId;
        }

        public void setCustomerId(int customerId) {
            this.customerId = customerId;
        }

        public int getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(int orderNumber) {
            this.orderNumber = orderNumber;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Id id = (Id) o;

            if (customerId != id.customerId) return false;
            if (orderNumber != id.orderNumber) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = customerId;
            result = 31 * result + orderNumber;
            return result;
        }
    }
}
