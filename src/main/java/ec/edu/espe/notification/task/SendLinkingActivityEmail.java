package ec.edu.espe.notification.task;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import ec.edu.espe.notification.config.BaseURLValues;
import ec.edu.espe.notification.dto.vinculacion.TareaDocenteVinculacion;
import ec.edu.espe.notification.service.EmailService;


public class SendLinkingActivityEmail implements Tasklet, StepExecutionListener {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private BaseURLValues baseURLs;

    private List<TareaDocenteVinculacion> tareasDocenteVinculacion;

    @Autowired
    private EmailService emservice;

    // private ArrayList<tareasVinculacion> collectionOrderDTOs;
    
    @Override
    public void beforeStep(StepExecution stepExecution) {
        ResponseEntity<TareaDocenteVinculacion[]> response = this.restTemplate.getForEntity(baseURLs.getGprMicroserviceVinculacion()+"/", TareaDocenteVinculacion[].class);//Tratar de traer todos dependiendo de la fecha
        TareaDocenteVinculacion[] objectArray = response.getBody();
        this.tareasDocenteVinculacion = Arrays.asList(objectArray);
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        
        for(TareaDocenteVinculacion tareaDocenteVinculacion: this.tareasDocenteVinculacion){
            if(tareaDocenteVinculacion.getTarea().getFechaEntregaTarea().before(new Date())){
                emservice.enviarCorreo(tareaDocenteVinculacion.getDocente().getCorreoDocente(),
                    "GPR - Actividad: " +tareaDocenteVinculacion.getTarea().getNombreTarea(),
                    "Su Actividad del Módulo de Vinculación debe ser realizada hasta:"+tareaDocenteVinculacion.getTarea().getFechaEntregaTarea());
            }else{
                emservice.enviarCorreo(tareaDocenteVinculacion.getDocente().getCorreoDocente(),
                    "GPR - Actividad: " +tareaDocenteVinculacion.getTarea().getNombreTarea(),
                    "Su Actividad del Módulo de Vinculación se encuentra atrasada. Esta Actividad tenía que ser realizada hasta:"+tareaDocenteVinculacion.getTarea().getFechaEntregaTarea());
            }          
        }
        return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }
}