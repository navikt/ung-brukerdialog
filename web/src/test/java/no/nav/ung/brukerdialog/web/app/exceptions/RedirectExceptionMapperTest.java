package no.nav.ung.brukerdialog.web.app.exceptions;

import jakarta.ws.rs.core.Response;
import no.nav.k9.felles.exception.ManglerTilgangException;
import no.nav.k9.felles.sikkerhet.ContextPathHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

@SuppressWarnings("deprecation")
public class RedirectExceptionMapperTest {

    private RedirectExceptionMapper exceptionMapper;

    @BeforeEach
    public void setUp() throws Exception {
        initMocks(this);

        exceptionMapper = new RedirectExceptionMapper();
        System.setProperty("loadBalancerUrl", "https://erstatter.nav.no");
        ContextPathHolder.instance("/ung/brukerdialog");
    }

    @Test
    @SuppressWarnings("resource")
    public void skalMappeValideringsfeil() {
        // Arrange
        String feilmelding = "feilmelding";

        // Act
        Response response = exceptionMapper.toResponse(new ManglerTilgangException("123abc", feilmelding));

        // Assert
        assertThat(response.getStatus()).isEqualTo(Response.Status.TEMPORARY_REDIRECT.getStatusCode());
        assertThat(response.getMediaType()).isEqualTo(null);
        assertThat(response.getMetadata().get("Content-Encoding").get(0))
            .isEqualTo("UTF-8");
        assertThat(response.getMetadata().get("Location").get(0).toString())
            .isEqualTo("https://erstatter.nav.no/ung/brukerdialog/#?errorcode=" + feilmelding);
    }

}
