package lab.hibernate.composite_id;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Author: Frank
 * Date: 13-12-12
 *
 * Annotation方式的联合主键
 *
 * 第二种方式
 * 在主键加@EmbeddableId注解，
 *
 */
@Entity
public class CustomerOrder3 {

    private CustomerOrderPK id;

    private Date orderDate;

    @EmbeddedId
    public CustomerOrderPK getId() {
        return id;
    }

    public void setId(CustomerOrderPK id) {
        this.id = id;
    }

    @Temporal(TemporalType.DATE)
    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    /**
     * Author: Frank
     * Date: 13-12-12
     *
     */
    public static class CustomerOrderPK implements Serializable {
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

            CustomerOrderPK pk = (CustomerOrderPK) o;

            if (customerId != pk.customerId) return false;
            if (orderNumber != pk.orderNumber) return false;

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
