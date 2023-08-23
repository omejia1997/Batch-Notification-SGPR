package ec.edu.espe.notification.config;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import lombok.Getter;

@Getter
@Component
public class BaseURLValues {
    private final String gprMicroserviceVinculacion;

    private final String gprMicroserviceInvestigationURL;

    public BaseURLValues(@Value("${gpr.microservice.vinculacion.base-url}") String gprMicroserviceVinculacion,
                         @Value("${gpr.microservice.investigation.base-url}") String gprMicroserviceInvestigationURL) {
        this.gprMicroserviceVinculacion = gprMicroserviceVinculacion;
        this.gprMicroserviceInvestigationURL = gprMicroserviceInvestigationURL;
    }
}
