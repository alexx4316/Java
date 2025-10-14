package util;

import errors.BadRequestException;
import errors.ConflictException;
import errors.NotFoundException;
import errors.ServiceException;

public class ErrorMapper {

    public static int getStatusCode(Exception e) {
        if (e instanceof BadRequestException) return 400;
        if (e instanceof NotFoundException) return 404;
        if (e instanceof ConflictException) return 409;
        if (e instanceof ServiceException) return 500;
        return 500; // Para el resto de errores no mapeados
    }

    public static void printErrorResponse(Exception e) {
        int code = getStatusCode(e);
        System.out.println("Error (" + code + "): " + e.getMessage());
    }
}
