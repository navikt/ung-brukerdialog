# ung-brukerdialog
Tilbyr funksjonalitet for innsyn og brukervarsling for ungdomsprogramytelsen og aktivitetspenger


# Utvikling

## Enhetstester
Start postgres først for å kjøre alle enhetstester. Bruker schema ung_sak_unit i
[Verdikjede](https://github.com/navikt/k9-verdikjede/tree/master/saksbehandling)
`git clone git@github.com:navikt/k9-verdikjede.git; cd k9-verdikjede/saksbehandling; ./update-versions.sh; docker-compose up postgres`

Kjør `no.nav.ung.sak.db.util.Databaseskjemainitialisering` for å få med skjemaendringer

## Lokal utvikling
1. Start postgres først. Bruker schema ung_sak lokalt
   `cd dev; docker-compose up postgres`

2. Start webserver fra f.eks. IDE
   Start `JettyDevServer --vtp`

Swagger: http://localhost:8901/ung/sak/swagger

### Generering av openapi spesifikasjon og typescript klient.

I build pipeline blir det automatisk generert en ung-brukerdialog.openapi.json fil som beskriver rest apiet på samme måte som
swagger serveren. Denne fila blir deretter brukt til å automatisk generere et typescript klientbibliotek som kan brukes
til å kommunisere med serveren fra nettleser.

Generert typescript klient blir publisert som **GitHub npm pakke [@navikt/ung-brukerdialog-typescript-client](https://github.com/navikt/ung-brukerdialog/pkgs/npm/ung-brukerdialog-typescript-client)**

Ved behov kan openapi spesifikasjon og typescript klient genereres i lokalt utviklingsmiljø.

Man kan kjøre dette script:

```bash
dev/generate-openapi-ts-client.sh
```

Eller en av intellij run konfigurasjonene som heter `web/generate typescript client`

Dette vil generere fil _web/src/main/resources/openapi-ts-client/ung-brukerdialog.openapi.json_. Deretter vil den
kjøre docker image for å generere typescript klient ut fra generert openapi.json fil.

Generert typescript/javascript blir plassert i _web/target/ts-client_.

Se også [openapi-ts-client/README](web/src/main/resources/openapi-ts-client/README.md) for mer teknisk info.

## Tilkobling til database
For å koble til databasen i dev-gcp kan man kjøre denne hjelpe-scriptet:

```shell script
./scripts/nais-postgres.sh --context dev-gcp --namespace k9saksbehandling --app ung-brukerdialog
```

## Kode generert av GitHub Copilot

Dette repoet bruker GitHub Copilot til å generere kode.
