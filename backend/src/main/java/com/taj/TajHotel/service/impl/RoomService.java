package com.taj.TajHotel.service.impl;

import com.taj.TajHotel.dto.Response;
import com.taj.TajHotel.dto.RoomDTO;
import com.taj.TajHotel.entity.Room;
import com.taj.TajHotel.exception.OurException;
import com.taj.TajHotel.repo.BookingRepository;
import com.taj.TajHotel.repo.RoomRepository;
import com.taj.TajHotel.service.interfac.IRoomService;
import com.taj.TajHotel.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class RoomService implements IRoomService {


    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private BookingRepository bookingRepository;


//    @Override
//    public Response addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice, String description) {
//        Response response = new Response();
//
//        try {
//            String imageUrl = "C:\\Users\\adminuser\\Desktop\\Project\\TajHotel\\src\\img";
//            Room room = new Room();
//            room.setRoomPhotoUrl(imageUrl);
//            room.setRoomType(roomType);
//            room.setRoomPrice(roomPrice);
//            room.setRoomDescription(description);
//            Room savedRoom = roomRepository.save(room);
//            RoomDTO roomDTO = Utils.mapRoomEntityToRoomDTO(savedRoom);
//            response.setStatusCode(200);
//            response.setMessage("successful");
//            response.setRoom(roomDTO);
//
//        } catch (Exception e) {
//            response.setStatusCode(500);
//            response.setMessage("Error saving a room " + e.getMessage());
//        }
//        return response;
//    }
    @Override
public Response addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice, String description) {
    Response response = new Response();

    try {
        // Convert the MultipartFile to byte array
        byte[] imageBytes = photo.getBytes();

        // Create a new Room entity
        Room room = new Room();
        room.setRoomPhoto(imageBytes);
        room.setRoomType(roomType);
        room.setRoomPrice(roomPrice);
        room.setRoomDescription(description);

        // Save the Room entity to the database
        Room savedRoom = roomRepository.save(room);

        // Map the saved Room entity to RoomDTO
        RoomDTO roomDTO = Utils.mapRoomEntityToRoomDTO(savedRoom);

        // Set the response details
        response.setStatusCode(200);
        response.setMessage("successful");
        response.setRoom(roomDTO);

    } catch (IOException e) {
        response.setStatusCode(500);
        response.setMessage("Error processing room photo: " + e.getMessage());
    } catch (Exception e) {
        response.setStatusCode(500);
        response.setMessage("Error saving a room: " + e.getMessage());
    }
    return response;
}

    @Override
    public List<String> getAllRoomTypes() {
        return roomRepository.findDistinctRoomTypes();
    }

    @Override
    public Response getAllRooms() {
        Response response = new Response();

        try {
            List<Room> roomList = roomRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
            List<RoomDTO> roomDTOList = Utils.mapRoomListEntityToRoomListDTO(roomList);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setRoomList(roomDTOList);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error saving a room " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response deleteRoom(Long roomId) {
        Response response = new Response();

        try {
            roomRepository.findById(roomId).orElseThrow(() -> new OurException("Room Not Found"));
            roomRepository.deleteById(roomId);
            response.setStatusCode(200);
            response.setMessage("successful");

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error saving a room " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response updateRoom(Long roomId, String description, String roomType, BigDecimal roomPrice, MultipartFile photo) {
        Response response = new Response();

        try {
            byte[] imageBytes = null;
            if (photo != null && !photo.isEmpty()) {
                imageBytes = photo.getBytes();
            }
            Room room = roomRepository.findById(roomId).orElseThrow(() -> new OurException("Room Not Found"));
            if (roomType != null) room.setRoomType(roomType);
            if (roomPrice != null) room.setRoomPrice(roomPrice);
            if (description != null) room.setRoomDescription(description);
            if (imageBytes != null) room.setRoomPhoto(imageBytes);

            Room updatedRoom = roomRepository.save(room);
            RoomDTO roomDTO = Utils.mapRoomEntityToRoomDTO(updatedRoom);

            response.setStatusCode(200);
            response.setMessage("successful");
            response.setRoom(roomDTO);

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error saving a room " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getRoomById(Long roomId) {
        Response response = new Response();

        try {
            Room room = roomRepository.findById(roomId).orElseThrow(() -> new OurException("Room Not Found"));
            RoomDTO roomDTO = Utils.mapRoomEntityToRoomDTOPlusBookings(room);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setRoom(roomDTO);

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error saving a room " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAvailableRoomsByDataAndType(LocalDate checkInDate, LocalDate checkOutDate, String roomType) {
        Response response = new Response();

        try {
            List<Room> availableRooms = roomRepository.findAvailableRoomsByDatesAndTypes(checkInDate, checkOutDate, roomType);
            List<RoomDTO> roomDTOList = Utils.mapRoomListEntityToRoomListDTO(availableRooms);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setRoomList(roomDTOList);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error saving a room " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAllAvailableRooms() {
        Response response = new Response();

        try {
            List<Room> roomList = roomRepository.getAllAvailableRooms();
            List<RoomDTO> roomDTOList = Utils.mapRoomListEntityToRoomListDTO(roomList);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setRoomList(roomDTOList);

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error saving a room " + e.getMessage());
        }
        return response;
    }
}
