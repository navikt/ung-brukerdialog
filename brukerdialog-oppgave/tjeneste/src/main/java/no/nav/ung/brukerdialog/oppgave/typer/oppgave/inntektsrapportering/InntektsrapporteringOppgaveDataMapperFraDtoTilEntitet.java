package no.nav.ung.brukerdialog.oppgave.typer.oppgave.inntektsrapportering;

import jakarta.enterprise.context.ApplicationScoped;
import no.nav.ung.brukerdialog.kontrakt.oppgaver.OppgaveType;
import no.nav.ung.brukerdialog.kontrakt.oppgaver.OppgavetypeDataDto;
import no.nav.ung.brukerdialog.kontrakt.oppgaver.typer.inntektsrapportering.InntektsrapporteringOppgavetypeDataDto;
import no.nav.ung.brukerdialog.oppgave.OppgaveDataMapperFraDtoTilEntitet;
import no.nav.ung.brukerdialog.oppgave.OppgaveTypeRef;
import no.nav.ung.brukerdialog.oppgave.typer.OppgaveDataEntitet;

@ApplicationScoped
@OppgaveTypeRef(OppgaveType.RAPPORTER_INNTEKT)
public class InntektsrapporteringOppgaveDataMapperFraDtoTilEntitet implements OppgaveDataMapperFraDtoTilEntitet {

    protected InntektsrapporteringOppgaveDataMapperFraDtoTilEntitet() {
        // CDI proxy
    }

    @Override
    public OppgaveDataEntitet map(OppgavetypeDataDto data) {
        var dto = (InntektsrapporteringOppgavetypeDataDto) data;
        return new InntektsrapporteringOppgaveDataEntitet(dto.fraOgMed(), dto.tilOgMed(), dto.gjelderDelerAvMåned());
    }
}
