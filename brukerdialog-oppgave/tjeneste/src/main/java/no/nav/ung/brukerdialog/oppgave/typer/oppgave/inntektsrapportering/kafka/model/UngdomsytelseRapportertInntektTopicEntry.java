package no.nav.ung.brukerdialog.oppgave.typer.oppgave.inntektsrapportering.kafka.model;

public record UngdomsytelseRapportertInntektTopicEntry(
    MetaInfo metadata,
    UngdomsytelseInntektsrapporteringMottatt data
) {
}

