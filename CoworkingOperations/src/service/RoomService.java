package service;

import dao.RoomDao;
import domain.Room;
import errors.DataAccessException;
import errors.ServiceException;

import javax.swing.*;
import java.util.List;

public class RoomService {
    private final RoomDao roomDao;

    public RoomService(RoomDao roomDao){
        this.roomDao = roomDao;
    }

    public void createRoom(Room room) throws ServiceException {
        try {
            if (roomDao.findByName(room.getName()) != null){
                JOptionPane.showMessageDialog(null,
                        "There is already a room with that name.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            roomDao.create(room);
            JOptionPane.showMessageDialog(null, "Room created successfully");
        } catch (DataAccessException e){
            throw new ServiceException("Technical error when creating the room", e);
        }
    }

    public List<Room> listRooms() throws ServiceException {
        try {
            return roomDao.findAll();
        } catch (DataAccessException e) {
            throw new ServiceException("Technical error when listing rooms", e);
        }
    }

    public void updateRoom(Room room) throws ServiceException {
        try {
            roomDao.update(room);
            JOptionPane.showMessageDialog(null, "Successfully update room");
        } catch (DataAccessException e) {
            throw new ServiceException("Technical error while updating the room", e);
        }
    }

    public void deleteRoom(int id) throws ServiceException {
        try {
            roomDao.delete(id);
            JOptionPane.showMessageDialog(null, "Room successfully deleted");
        } catch (DataAccessException e){
            throw new ServiceException("Technical error when deleting the room", e);
        }
    }
}
