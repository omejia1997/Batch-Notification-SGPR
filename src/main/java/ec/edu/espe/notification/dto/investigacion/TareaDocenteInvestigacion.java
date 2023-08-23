package ec.edu.espe.notification.dto.investigacion;


import ec.edu.espe.notification.dto.microservicegpr.Docente;
import lombok.Data;

@Data
public class TareaDocenteInvestigacion {

    private String cedulaDocenteRevisor;

    private Docente codigoDocente;

    private Tarea codigoTarea;
}
