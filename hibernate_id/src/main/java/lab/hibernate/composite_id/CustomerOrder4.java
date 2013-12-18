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
 * 第三种方式
 * 设置@IdClass并在主键上加@Id
 *
 */
@Entity
@IdClass(CustomerOrder4.CustomerOrderPK.class)
public class CustomerOrder4 {
    private int customerId;
    private int orderNumber;
    private Date orderDate;

    @Id
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    @Id
    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
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
