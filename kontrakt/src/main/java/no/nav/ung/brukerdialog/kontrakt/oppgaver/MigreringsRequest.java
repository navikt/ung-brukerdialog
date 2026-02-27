package no.nav.ung.brukerdialog.kontrakt.oppgaver;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import no.nav.k9.felles.sikkerhet.abac.StandardAbacAttributtType;
import no.nav.ung.brukerdialog.abac.StandardAbacAttributt;

import java.util.List;

/**
 * Request for migrering av brukerdialogoppgaver fra annen applikasjon.
 */
public record MigreringsRequest(
    @JsonProperty(value = "oppgaver", required = true)
    @NotNull
    @Size(min = 1, max = 1000)
    List<@NotNull @Valid MigrerOppgaveDto> oppgaver
) {
}

