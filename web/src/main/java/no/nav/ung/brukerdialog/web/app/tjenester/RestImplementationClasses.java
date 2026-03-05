package no.nav.ung.brukerdialog.web.app.tjenester;

import no.nav.k9.prosesstask.rest.ProsessTaskRestTjeneste;
import no.nav.ung.brukerdialog.web.app.tjenester.bruker.BrukerOppgaveRestTjeneste;
import no.nav.ung.brukerdialog.web.app.tjenester.oppgavebehandling.MigrerBrukerdialogOppgaverRestTjeneste;
import no.nav.ung.brukerdialog.web.app.tjenester.oppgavebehandling.OppgavebehandlingRestTjeneste;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RestImplementationClasses {

    public Collection<Class<?>> getImplementationClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.addAll(List.of(ProsessTaskRestTjeneste.class,
            BrukerOppgaveRestTjeneste.class,
            MigrerBrukerdialogOppgaverRestTjeneste.class,
            OppgavebehandlingRestTjeneste.class));
        return Set.copyOf(classes);
    }


}
