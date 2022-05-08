package com.pahomovichk.pokedex.core.utils.net.result

interface HttpResponse {

    val statusCode: Int

    val statusMessage: String?

    val url: String?
}