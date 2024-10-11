package com.taj.TajHotel.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomType;
    private BigDecimal roomPrice;
//    private String roomPhotoUrl;
@Lob
private byte[] roomPhoto;
    private String roomDescription;
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Booking> bookings;

//    @Override
//    public String toString() {
//        return "Room{" +
//                "id=" + id +
//                ", roomType='" + roomType + '\'' +
//                ", roomPrice=" + roomPrice +
//                ", roomPhotoUrl='" + roomPhotoUrl + '\'' +
//                ", roomDescription='" + roomDescription + '\'' +
//                ", bookings=" + bookings +
//                '}';
//    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", roomType='" + roomType + '\'' +
                ", roomPrice=" + roomPrice +
                ", roomPhoto=" + Arrays.toString(roomPhoto) +
                ", roomDescription='" + roomDescription + '\'' +
                ", bookings=" + bookings +
                '}';
    }
}
