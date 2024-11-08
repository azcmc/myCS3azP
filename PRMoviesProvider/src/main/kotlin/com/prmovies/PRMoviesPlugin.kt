package com.prmovies

import com.lagradost.cloudstream3.plugins.CloudstreamPlugin
import com.lagradost.cloudstream3.plugins.Plugin
import android.content.Context

@CloudstreamPlugin
class PRMoviesPlugin: Plugin() {
    override fun load(context: Context) {
        registerMainAPI(PRMoviesProvider())
        registerExtractorAPI(Waaw())
        registerExtractorAPI(Minoplres())
    }
}
