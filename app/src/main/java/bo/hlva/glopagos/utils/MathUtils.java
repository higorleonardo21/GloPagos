package bo.hlva.glopagos.utils;

import bo.hlva.glopagos.data.model.Customer;

public class MathUtils {

    // calculate percentage
    public static int calculatePercentage(Customer customer) {
        return (customer.getAmount() * customer.getPercentage()) / 100;
    }

    public static int getBalanceDue(Customer customer) {

        int amount = customer.getAmount() + MathUtils.calculatePercentage(customer);
        int day = amount / 24;
        int value = day * customer.getDays().size();

        return amount - value;
    }
}
