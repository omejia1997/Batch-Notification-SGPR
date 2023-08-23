package ec.edu.espe.notification.dto.investigacion;

import java.util.Date;

import lombok.Data;

@Data
public class Tarea {

    private String nombreTarea;
    
    private Date fechaEntregaTarea;

    private String idDocenteRevisor;

    private String nombreDocenteRevisor;
}
