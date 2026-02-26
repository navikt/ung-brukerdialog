package no.nav.ung.brukerdialog.web.server.typedresponse;

import jakarta.ws.rs.core.EntityTag;

import java.util.Optional;

public interface ETaggableResponse {
    public Optional<EntityTag> etag();
}
