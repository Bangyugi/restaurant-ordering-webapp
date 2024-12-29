package com.group2.restaurantorderingwebapp.enumeration;

public enum ReservationStatus {

    WAIT_FOR_RESERVATION(1, "Chờ xác nhận"),
    RESERVATION_SUCCESSFUL(2,"Đặt bàn thành công"),
    PAID(3,"Đã thanh toán");

    public final Integer value;

    public final String text;

    ReservationStatus(Integer value, String text) {
        this.value = value;
        this.text = text;
    }

    public static String getText(Integer value) {
        if (value.equals(ReservationStatus.WAIT_FOR_RESERVATION.value))
            return ReservationStatus.WAIT_FOR_RESERVATION.text;
        else if (value.equals(ReservationStatus.RESERVATION_SUCCESSFUL.value))
            return ReservationStatus.RESERVATION_SUCCESSFUL.text;
        else return ReservationStatus.PAID.text;
    }
}
