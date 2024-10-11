package com.taj.TajHotel.service.interfac;

import com.taj.TajHotel.dto.Response;
import com.taj.TajHotel.entity.Booking;

public interface IBookingService {

    Response saveBooking(Long roomId, Long userId, Booking bookingRequest);

    Response findBookingByConfirmationCode(String confirmationCode);

    Response getAllBookings();

    Response cancelBooking(Long bookingId);

}