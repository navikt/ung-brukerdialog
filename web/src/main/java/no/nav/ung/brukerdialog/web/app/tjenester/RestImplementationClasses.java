package no.nav.ung.brukerdialog.web.app.tjenester;

import no.nav.k9.prosesstask.rest.ProsessTaskRestTjeneste;
import no.nav.ung.brukerdialog.web.app.tjenester.brukerdialog.BrukerdialogOppgaveRestTjeneste;
import no.nav.ung.brukerdialog.web.app.tjenester.brukerdialog.MigrerBrukerdialogOppgaverRestTjeneste;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RestImplementationClasses {

    public Collection<Class<?>> getImplementationClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.addAll(List.of(ProsessTaskRestTjeneste.class,
            BrukerdialogOppgaveRestTjeneste.class,
            MigrerBrukerdialogOppgaverRestTjeneste.class));
        return Set.copyOf(classes);
    }


}
