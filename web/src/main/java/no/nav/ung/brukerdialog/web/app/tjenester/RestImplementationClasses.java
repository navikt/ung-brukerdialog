package no.nav.ung.brukerdialog.web.app.tjenester;

import no.nav.k9.prosesstask.rest.ProsessTaskRestTjeneste;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class RestImplementationClasses {

    public Collection<Class<?>> getImplementationClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(ProsessTaskRestTjeneste.class);
        return Set.copyOf(classes);
    }


}
