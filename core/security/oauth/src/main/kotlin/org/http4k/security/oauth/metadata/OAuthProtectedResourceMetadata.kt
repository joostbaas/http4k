package org.http4k.security.oauth.metadata

import org.http4k.core.Method.GET
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.bind
import org.http4k.security.oauth.format.OAuthMoshi.json

fun OAuthProtectedResourceMetadata(resource: ResourceMetadata) =
    ".well-known/oauth-protected-resource" bind GET to { Response(OK).json(resource) }

