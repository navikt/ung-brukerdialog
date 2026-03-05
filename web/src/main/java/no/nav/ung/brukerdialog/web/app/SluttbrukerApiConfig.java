package no.nav.ung.brukerdialog.web.app;


import io.swagger.v3.jaxrs2.integration.JaxrsOpenApiContextBuilder;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import io.swagger.v3.oas.integration.OpenApiConfigurationException;
import io.swagger.v3.oas.integration.SwaggerConfiguration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import jakarta.ws.rs.ApplicationPath;
import no.nav.k9.felles.konfigurasjon.env.Environment;
import no.nav.k9.prosesstask.rest.ProsessTaskRestTjeneste;
import no.nav.openapi.spec.utils.http.DynamicObjectMapperResolverVaryFilter;
import no.nav.openapi.spec.utils.jackson.DynamicJacksonJsonProvider;
import no.nav.openapi.spec.utils.openapi.OpenApiSetupHelper;
import no.nav.openapi.spec.utils.openapi.PrefixStrippingFQNTypeNameResolver;
import no.nav.ung.brukerdialog.web.app.exceptions.KnownExceptionMappers;
import no.nav.ung.brukerdialog.web.app.jackson.ObjectMapperFactory;
import no.nav.ung.brukerdialog.web.app.jackson.ObjectMapperResolver;
import no.nav.ung.brukerdialog.web.app.tjenester.RestImplementationClasses;
import no.nav.ung.brukerdialog.web.app.tjenester.bruker.BrukerOppgaveRestTjeneste;
import no.nav.ung.brukerdialog.web.app.tjenester.oppgavebehandling.MigrerBrukerdialogOppgaverRestTjeneste;
import no.nav.ung.brukerdialog.web.app.tjenester.oppgavebehandling.OppgavebehandlingRestTjeneste;
import no.nav.ung.brukerdialog.web.server.caching.CacheControlFeature;
import no.nav.ung.brukerdialog.web.server.typedresponse.TypedResponseFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationPath(SluttbrukerApiConfig.API_URI)
public class SluttbrukerApiConfig extends ResourceConfig {


    public static final String API_URI = "/sluttbruker/api";

    public SluttbrukerApiConfig() {
        final var info = new Info()
            .title("Ung brukerdialog - Brukerdialog-API for sluttbruker")
            .version("1.0")
            .description("REST grensesnitt for data tilgjengelig for sluttbruker knyttet til vedtaksløsning for ungdomsprogramytelsen og aktivitetspenger.");

        OpenAPI oas = new OpenAPI();
        oas.info(info)
            .addServersItem(new Server().url("/ung/brukerdialog"));
        SwaggerConfiguration oasConfig = new SwaggerConfiguration()
            .openAPI(oas)
            .prettyPrint(true)
            .scannerClass("io.swagger.v3.jaxrs2.integration.JaxrsAnnotationScanner")
            .resourcePackages(Stream.of("no.nav.ung.")
                .collect(Collectors.toSet()));

        try {
            new JaxrsOpenApiContextBuilder<>()
                .openApiConfiguration(oasConfig)
                .buildContext(true)
                .read();
        } catch (OpenApiConfigurationException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
        register(OpenApiResource.class);
        registerClasses(new LinkedHashSet<>(getImplementationClasses()));
        register(ObjectMapperFactory.createBaseObjectMapperCopy());
        registerInstances(new LinkedHashSet<>(new KnownExceptionMappers().getExceptionMappers()));
        property(ServerProperties.PROCESSING_RESPONSE_ERRORS_ENABLED, true);

    }


    public Collection<Class<?>> getImplementationClasses() {
        Set<Class<?>> classes = new HashSet<>(List.of(BrukerOppgaveRestTjeneste.class));
        return Set.copyOf(classes);
    }


}
