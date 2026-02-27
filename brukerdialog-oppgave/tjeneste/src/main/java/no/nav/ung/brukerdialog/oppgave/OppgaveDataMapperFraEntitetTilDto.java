package no.nav.ung.brukerdialog.oppgave;

import jakarta.enterprise.inject.Instance;
import no.nav.ung.brukerdialog.kontrakt.oppgaver.OppgaveType;
import no.nav.ung.brukerdialog.kontrakt.oppgaver.OppgavetypeDataDto;
import no.nav.ung.brukerdialog.oppgave.typer.OppgaveDataEntitet;

/**
 * Mapper en {@link OppgaveDataEntitet}-subklasse til tilsvarende {@link OppgavetypeDataDto}.
 * Implementasjoner annoteres med {@link OppgaveTypeRef} for CDI-oppslag.
 */
public interface OppgaveDataMapperFraEntitetTilDto {

    static OppgaveDataMapperFraEntitetTilDto finnTjeneste(Instance<OppgaveDataMapperFraEntitetTilDto> instanser, OppgaveType oppgaveType) {
        return OppgaveTypeRef.Lookup.find(instanser, oppgaveType)
            .orElseThrow(() -> new IllegalArgumentException("Finner ingen OppgaveDataEntitetTilDtoMapper for oppgavetype: " + oppgaveType));
    }

    OppgavetypeDataDto tilDto(OppgaveDataEntitet entitet);
}
