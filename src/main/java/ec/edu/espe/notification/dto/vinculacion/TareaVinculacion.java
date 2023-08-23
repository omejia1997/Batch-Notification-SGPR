package ec.edu.espe.notification.dto.vinculacion;

import java.util.Date;

import lombok.Data;

@Data
public class TareaVinculacion {
    
    private String nombreTarea;

    private Date fechaEntregaTarea;

    private String idDocenteRevisor;

    private String nombreDocenteRevisor;
}
