package bo.hlva.glopagos.utils;

import bo.hlva.glopagos.data.model.Customer;

public class MathUtils {

    // calculate percentage to 20%
    public static int calculatePercentage(int amount) {
        return (amount * 20) / 100;
    }

    public static int getBalanceDue(Customer customer) {

        int amount = customer.getAmount() + MathUtils.calculatePercentage(customer.getAmount());
        int day = amount / 24;
        int value = day * customer.getDays().size();

        return amount - value;
    }
}
