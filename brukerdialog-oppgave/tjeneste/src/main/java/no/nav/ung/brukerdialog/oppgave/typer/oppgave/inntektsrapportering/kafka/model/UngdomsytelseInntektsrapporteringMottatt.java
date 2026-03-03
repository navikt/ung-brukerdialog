package no.nav.ung.brukerdialog.oppgave.typer.oppgave.inntektsrapportering.kafka.model;

import no.nav.k9.søknad.ytelse.ung.v1.inntekt.OppgittInntektForPeriode;

public record UngdomsytelseInntektsrapporteringMottatt(
    String oppgaveReferanse,
    OppgittInntektForPeriode oppgittInntektForPeriode
) {
}
