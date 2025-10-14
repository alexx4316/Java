package service;

import dao.RoomDao;
import dao.RoomDaoJDBC;
import domain.Room;
import errors.*;

import javax.swing.*;
import java.util.List;

public class RoomService {
    private final RoomDao roomDao = new RoomDaoJDBC();

    public void createRoom(Room room){
        if (room == null || room.getName() == null || room.getName().isEmpty()){
            throw new BadRequestException("The name of the room is mandatory.");
        }
        try {
            Room existing = roomDao.findByName(room.getName());
            if (existing != null){
                throw new ConflictException("There is already a room with the same name.");
            }
            roomDao.insert(room);
        } catch (DataAccessException e) {
            throw new ServiceException("Technical error when creating the room", e);
        } finally {
            JOptionPane.showMessageDialog(null,"Room creation operation completed");
        }
    }

    public Room findById(int id){
        try{
            Room room = roomDao.findById(id);
            if (room == null){
                throw new NotFoundException("Room with ID not found " + id);
            }
            return room;
        } catch (DataAccessException e){
            throw new ServiceException("Technical error when consulting the room", e);
        }
    }

    public List<Room> listAll(){
        try{
            return roomDao.findAll();
        } catch (DataAccessException e){
            throw new ServiceException("Technical error when listing rooms", e);
        }
    }

    public void deleteRoom(int id){
        try{
            Room room = roomDao.findById(id);
            if (room == null){
                throw new NotFoundException("Cannot delete: room does not exist");
            }
            roomDao.delete(id);
        } catch (DataAccessException e){
            throw new ServiceException("Technical error when deleting the room", e);
        } finally {
            JOptionPane.showMessageDialog(null, "Room removal operation completed");
        }
    }
}
