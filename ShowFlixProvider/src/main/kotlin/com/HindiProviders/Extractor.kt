package com.Phisher98

//import android.util.Log
import com.lagradost.cloudstream3.SubtitleFile
import com.lagradost.cloudstream3.app
import com.lagradost.cloudstream3.utils.ExtractorApi
import com.lagradost.cloudstream3.utils.ExtractorLink
import com.lagradost.cloudstream3.utils.INFER_TYPE
import com.lagradost.cloudstream3.utils.Qualities
import com.lagradost.cloudstream3.utils.getQualityFromName
import java.net.URI


open class Streamwish : ExtractorApi() {
    override var name = "Streamwish"
    override var mainUrl = "https://streamwish.to"
    override val requiresReferer = false

    override suspend fun getUrl(url: String, referer: String?): List<ExtractorLink>? {
        val responsecode=app.get(url)
            val serverRes = responsecode.document
            val script = serverRes.selectFirst("script:containsData(sources)")?.data().toString()
            val headers = mapOf(
                "Accept" to "*/*",
                "Connection" to "keep-alive",
                "Sec-Fetch-Dest" to "empty",
                "Sec-Fetch-Mode" to "cors",
                "Sec-Fetch-Site" to "cross-site",
                "Origin" to url,
            )
            Regex("file:\"(.*)\"").find(script)?.groupValues?.get(1)?.let { link ->
                return listOf(
                    ExtractorLink(
                        this.name,
                        this.name,
                        link,
                        referer ?: "",
                        Qualities.P1080.value,
                        type = INFER_TYPE,
                        headers
                    )
                )
            }
        return null
    }
}


open class Filelion : ExtractorApi() {
    override val name = "Filelion"
    override val mainUrl = "https://filelions.to"
    override val requiresReferer = false

    override suspend fun getUrl(
        url: String,
        referer: String?,
    ): List<ExtractorLink>? {
        val responsecode=app.get(url)
            val response = responsecode.document
            val script = response.selectFirst("script:containsData(sources)")?.data().toString()
            Regex("file:\"(.*)\"").find(script)?.groupValues?.get(1)?.let { link ->
                return listOf(
                    ExtractorLink(
                        this.name,
                        this.name,
                        link,
                        referer ?: "",
                        getQualityFromName(""),
                        URI(link).path.endsWith(".m3u8")
                    )
                )
            }
        return null
    }
}


open class StreamRuby : ExtractorApi() {
    override val name = "StreamRuby"
    override val mainUrl = "https://streamruby.com"
    override val requiresReferer = false

    override suspend fun getUrl(
        url: String,
        referer: String?,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit
    ) {
        val response=app.get(url,referer=url, headers = mapOf("X-Requested-With" to "XMLHttpRequest")).document
            val script = response.selectFirst("script:containsData(vplayer)")?.data().toString()
            val headers = mapOf(
                "Accept" to "*/*",
                "Connection" to "keep-alive",
                "Sec-Fetch-Dest" to "empty",
                "Sec-Fetch-Mode" to "cors",
                "Sec-Fetch-Site" to "cross-site",
                "Origin" to url,
            )

            Regex("file:\"(.*)\"").find(script)?.groupValues?.get(1)?.let { link ->
                callback.invoke(
                    ExtractorLink(
                        this.name,
                        this.name,
                        link,
                        "https://rubystm.com",
                        Qualities.P1080.value,
                        type = INFER_TYPE,
                        headers
                    )
                )
            }
    }
}