package ec.edu.espe.notification.dto.vinculacion;

import java.util.Date;

import ec.edu.espe.notification.dto.microservicegpr.Docente;
import lombok.Data;

@Data
public class TareaDocenteVinculacion {
    
    private TareaVinculacion tarea;
    
    private String descripcionTareadocente;

    private Date fechaEntregadaTareaDocente;

    private String cedulaDocenteRevisor;

    private Docente docente;
    
}
