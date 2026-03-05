package no.nav.ung.brukerdialog.web.app;


import io.swagger.v3.oas.integration.OpenApiConfigurationException;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import jakarta.ws.rs.ApplicationPath;
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

@ApplicationPath(InternApiConfig.API_URI)
public class InternApiConfig extends ResourceConfig {

    public static final String API_URI = "intern/api";

    public OpenAPI resolveOpenAPI() {
        final var info = new Info()
            .title("Ung brukerdialog - Brukerdialog-API for ungdomsprogramytelsen og aktivitetspenger")
            .version("2.0")
            .description("REST grensesnitt for operasjoner fra veileder, saksbehandler og interne systemer knyttet til vedtaksløsning for ungdomsprogramytelsen og aktivitetspenger.");

        final var server =new Server().url("/ung/brukerdialog");
        final var openapiSetupHelper = new OpenApiSetupHelper(this, info, server);
        openapiSetupHelper.addResourcePackage("no.nav.ung.brukerdialog");
        openapiSetupHelper.addResourcePackage("no.nav.k9");
        // The same classes registered as subtypes in object mapper are registered as subtypes in openapi setup helper:
        openapiSetupHelper.registerSubTypes(ObjectMapperFactory.allJsonTypeNameClasses());
        openapiSetupHelper.setTypeNameResolver(new PrefixStrippingFQNTypeNameResolver("no.nav."));
        try {
            return openapiSetupHelper.resolveOpenAPI();
        } catch (OpenApiConfigurationException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public InternApiConfig() {
        register(DynamicJacksonJsonProvider.class); // Denne må registrerast før anna OpenAPI oppsett for å fungere.
        final var resolvedOpenAPI = resolveOpenAPI();
        register(new no.nav.openapi.spec.utils.openapi.OpenApiResource(resolvedOpenAPI));

        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);

        registerClasses(new LinkedHashSet<>(getImplementationClasses()));

        register(ObjectMapperResolver.class);
        register(DynamicObjectMapperResolverVaryFilter.class);

        registerInstances(new LinkedHashSet<>(new KnownExceptionMappers().getExceptionMappers()));
        register(CacheControlFeature.class);
        register(TypedResponseFeature.class);

        property(org.glassfish.jersey.server.ServerProperties.PROCESSING_RESPONSE_ERRORS_ENABLED, true);

    }

    public Collection<Class<?>> getImplementationClasses() {
        Set<Class<?>> classes = new HashSet<>(List.of(ProsessTaskRestTjeneste.class,
            MigrerBrukerdialogOppgaverRestTjeneste.class,
            OppgavebehandlingRestTjeneste.class));
        return Set.copyOf(classes);
    }

}
