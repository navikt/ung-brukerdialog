package no.nav.ung.brukerdialog.web.app.exceptions;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import no.nav.k9.felles.feil.Feil;
import no.nav.ung.brukerdialog.kontrakt.FeilDto;
import no.nav.ung.brukerdialog.kontrakt.FeltFeilDto;
import no.nav.ung.brukerdialog.kontrakt.oppgaver.OppgavetypeDataDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    private static final Logger log = LoggerFactory.getLogger(ConstraintViolationExceptionMapper.class);

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        try {
            Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();

            Collection<FeltFeilDto> feilene = new ArrayList<>();
            for (ConstraintViolation<?> constraintViolation : constraintViolations) {
                String kode = getOppgaveType(constraintViolation.getLeafBean());
                String feltNavn = getFeltNavn(constraintViolation.getPropertyPath());
                feilene.add(new FeltFeilDto(feltNavn, constraintViolation.getMessage(), kode));
            }
            List<String> feltNavn = feilene.stream()
                .map(FeltFeilDto::getNavn)
                .toList();

            Feil feil = FeltValideringFeil.FACTORY.feltverdiKanIkkeValideres(feltNavn);
            feil.log(log);

            return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(new FeilDto(feil.getFeilmelding(), feilene))
                .type(MediaType.APPLICATION_JSON)
                .build();
        } finally {
            MDC.remove("prosess");
        }
    }

    private String getOppgaveType(Object leafBean) {
        return leafBean instanceof OppgavetypeDataDto ? ((OppgavetypeDataDto) leafBean).oppgavetype().name() : null;
    }

    private String getFeltNavn(Path propertyPath) {
        String pathString = propertyPath.toString();
        if (pathString.contains(".")) {
            return pathString.substring(pathString.lastIndexOf('.') + 1);
        }
        return pathString;
    }

}
