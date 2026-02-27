package no.nav.ung.brukerdialog.web.app.jackson;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import no.nav.ung.brukerdialog.web.app.tjenester.RestImplementationClasses;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ObjectMapperFactory {

    private static ObjectMapper baseObjectMapper;

    public static ObjectMapper createBaseObjectMapperCopy() {
        if(baseObjectMapper == null) {
            baseObjectMapper = ObjectMapperFactory.createBaseObjectMapper();
        }
        return baseObjectMapper.copy();
    }

    /**
     * Scan subtyper dynamisk fra WAR slik at superklasse slipper å deklarere @JsonSubtypes.
     */
    private static List<Class<?>> getJsonTypeNameClasses(URI classLocation) {
        IndexClasses indexClasses;
        indexClasses = IndexClasses.getIndexFor(classLocation);
        return indexClasses.getClassesWithAnnotation(JsonTypeName.class);
    }

    public static Collection<Class<?>> allJsonTypeNameClasses() {
        // registrer jackson JsonTypeName subtypes basert på rest implementasjoner
        final Collection<Class<?>> restClasses = new RestImplementationClasses().getImplementationClasses();

        final Set<Class<?>> scanClasses = new LinkedHashSet<>(restClasses);

        // avled code location fra klassene
        return scanClasses
            .stream()
            .map(c -> {
                try {
                    return c.getProtectionDomain().getCodeSource().getLocation().toURI();
                } catch (URISyntaxException e) {
                    throw new IllegalArgumentException("Ikke en URI for klasse: " + c, e);
                }
            })
            .distinct()
            .flatMap(uri -> getJsonTypeNameClasses(uri).stream())
            .collect(Collectors.toUnmodifiableSet());
    }


    private static ObjectMapper createBaseObjectMapper() {
        final var om = new ObjectMapper();
        om.registerModule(new Jdk8Module());
        om.registerModule(new JavaTimeModule());
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        om.disable(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS);
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // Registrer alle klasser med JsonTypeName annotasjon som subtyper i object mapper.
        // Slik at ein ikkje må deklarere disse manuelt som subtyper på alle superklasser.
        om.registerSubtypes(allJsonTypeNameClasses());

        return om;
    }

}
