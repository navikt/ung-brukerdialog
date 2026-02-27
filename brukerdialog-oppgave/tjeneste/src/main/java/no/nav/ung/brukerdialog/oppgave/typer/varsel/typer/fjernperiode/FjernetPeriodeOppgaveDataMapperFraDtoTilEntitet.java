package no.nav.ung.brukerdialog.oppgave.typer.varsel.typer.fjernperiode;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import no.nav.ung.brukerdialog.kontrakt.oppgaver.OppgavetypeDataDto;
import no.nav.ung.brukerdialog.kontrakt.oppgaver.OppgaveType;
import no.nav.ung.brukerdialog.kontrakt.oppgaver.typer.fjernperiode.FjernetPeriodeDataDto;
import no.nav.ung.brukerdialog.oppgave.OppgaveDataMapperFraDtoTilEntitet;
import no.nav.ung.brukerdialog.oppgave.OppgaveTypeRef;
import no.nav.ung.brukerdialog.oppgave.typer.OppgaveDataEntitet;

@ApplicationScoped
@OppgaveTypeRef(OppgaveType.BEKREFT_FJERNET_PERIODE)
public class FjernetPeriodeOppgaveDataMapperFraDtoTilEntitet implements OppgaveDataMapperFraDtoTilEntitet {

    private EntityManager entityManager;

    protected FjernetPeriodeOppgaveDataMapperFraDtoTilEntitet() {
        // CDI proxy
    }

    @Inject
    public FjernetPeriodeOppgaveDataMapperFraDtoTilEntitet(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public OppgaveDataEntitet map(OppgavetypeDataDto data) {
        var dto = (FjernetPeriodeDataDto) data;
        var entitet = new FjernetPeriodeOppgaveDataEntitet(dto.forrigeStartdato(), dto.forrigeSluttdato());
        entityManager.persist(entitet);
        return entitet;
    }
}
