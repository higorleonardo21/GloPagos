package bo.hlva.glopagos.utils;

import bo.hlva.glopagos.data.model.Customer;

public class MessageUtils {

    public static String detailsCustomer(Customer customer) {
        return "Nombre:\n"
                + customer.getName()
                + "\n\n"
                + "Apellidos:\n"
                + customer.getLastName()
                + "\n\n"
                + "Numero C.I:\n"
                + customer.getIdNumber()
                + "\n\n"
                + "Telefono:\n"
                + customer.getPhone()
                + "\n\n"
                + "Monto Retirado:\n"
                + customer.getAmount()
                + " bs"
                + "\n\n"
                + "Porcentaje de Retiro:\n"
                + customer.getPercentage()
                + "%"
                + "\n\n"
                + "Fecha de Retiro:\n"
                + customer.getDate();
    }

    public static String infoCustomer(Customer customer) {
        return "Monto Total A Cancelar:\n"
                + (customer.getAmount() + MathUtils.calculatePercentage(customer))
                + " bs"
                + "\n\n"
                + "Monto Retirado:\n"
                + customer.getAmount()
                + " bs"
                + "\n\n"
                + "Monto Pendiente:\n"
                + MathUtils.getBalanceDue(customer)
                + " bs";
    }
}
