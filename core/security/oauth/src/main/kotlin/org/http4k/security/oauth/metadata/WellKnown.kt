package org.http4k.security.oauth.metadata

import org.http4k.routing.routes

fun WellKnown(
    server: ServerMetadata,
    resource: ResourceMetadata? = null,
    jsonWebKeySet: JsonWebKeySet? = null,
    oidc: OpenIdConfiguration? = null
) = routes(
    listOfNotNull(
        OAuthAuthorizationServerMetadata(server),
        resource?.let(::OAuthProtectedResourceMetadata),
        jsonWebKeySet?.let(::OAuthJwks),
        oidc?.let(::OAuthOpenIdConfiguration)
    )
)
