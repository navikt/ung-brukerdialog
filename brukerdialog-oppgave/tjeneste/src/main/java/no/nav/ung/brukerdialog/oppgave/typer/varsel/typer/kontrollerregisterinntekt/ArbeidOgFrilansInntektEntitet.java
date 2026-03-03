package no.nav.ung.brukerdialog.oppgave.typer.varsel.typer.kontrollerregisterinntekt;

import jakarta.persistence.*;
import no.nav.ung.brukerdialog.BaseEntitet;

/**
 * En enkeltpost for arbeid- eller frilansinntekt knyttet til en
 * {@link KontrollerRegisterinntektOppgaveDataEntitet}.
 */
@Entity(name = "ArbeidOgFrilansInntekt")
@Table(name = "BD_OPPGAVE_DATA_ARBEID_FRILANS_INNTEKT")
@Access(AccessType.FIELD)
public class ArbeidOgFrilansInntektEntitet extends BaseEntitet {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BD_OPPGAVE_DATA_ARBEID_FRILANS_INNTEKT")
    private Long id;

    @Column(name = "arbeidsgiverIdentifikator", nullable = false, updatable = false)
    private String arbeidsgiverIdentifikator;

    @Column(name = "inntekt", nullable = false, updatable = false)
    private int inntekt;

    protected ArbeidOgFrilansInntektEntitet() {
        // For JPA
    }

    ArbeidOgFrilansInntektEntitet(String arbeidsgiverIdentifikator, int inntekt) {
        this.arbeidsgiverIdentifikator = arbeidsgiverIdentifikator;
        this.inntekt = inntekt;
    }

    public Long getId() {
        return id;
    }

    public String getArbeidsgiverIdentifikator() {
        return arbeidsgiverIdentifikator;
    }

    public int getInntekt() {
        return inntekt;
    }
}
