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
import ec.edu.espe.notification.dto.investigacion.TareaDocenteInvestigacion;
import ec.edu.espe.notification.service.EmailService;

public class SendInvestigationActivityEmail implements Tasklet, StepExecutionListener{

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private BaseURLValues baseURLs;

    private List<TareaDocenteInvestigacion> tareasDocenteInvestigacion;

    @Autowired
    private EmailService emservice;
    
    @Override
    public void beforeStep(StepExecution stepExecution) {
        ResponseEntity<TareaDocenteInvestigacion[]> response = this.restTemplate.getForEntity(baseURLs.getGprMicroserviceInvestigationURL()+"/", TareaDocenteInvestigacion[].class);//Tratar de traer todos dependiendo de la fecha
        TareaDocenteInvestigacion[] objectArray = response.getBody();
        this.tareasDocenteInvestigacion = Arrays.asList(objectArray);
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        
        for(TareaDocenteInvestigacion tareaDocenteInvestigacion: this.tareasDocenteInvestigacion){
            if(tareaDocenteInvestigacion.getCodigoTarea().getFechaEntregaTarea().before(new Date())){
                emservice.enviarCorreo(tareaDocenteInvestigacion.getCodigoDocente().getCorreoDocente(),
                    "GPR - Actividad: " +tareaDocenteInvestigacion.getCodigoTarea().getNombreTarea(),
                    "Su Actividad del Módulo de Investigación debe ser realizada hasta:"+tareaDocenteInvestigacion.getCodigoTarea().getFechaEntregaTarea());
            }else{
                emservice.enviarCorreo(tareaDocenteInvestigacion.getCodigoDocente().getCorreoDocente(),
                    "GPR - Actividad: " +tareaDocenteInvestigacion.getCodigoTarea().getNombreTarea(),
                    "Su Actividad del M\u00F3dulo de Investigaci\u00F3n se encuentra atrasada. Esta Actividad tenía que ser realizada hasta:"+tareaDocenteInvestigacion.getCodigoTarea().getFechaEntregaTarea());
            }          
        }
        return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }
}
