package com.example.mymusicappcompose.extensions

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import com.example.mymusicappcompose.log.logDebug

fun Activity.openSpotify(url: String?) {
    logDebug("spotify 1")
    if (url == null) return
    logDebug("spotify 2")
    if (!isInstalledSpotify()) return

    logDebug("spotify 3")
    val branchLink =
        ("https://spotify.link/content_linking?~campaign=$packageName").toString() + "&\$deeplink_path=" + url + "&\$fallback_url=" + url
    val intent = Intent(Intent.ACTION_VIEW)
    intent.setData(Uri.parse(branchLink))
    startActivity(intent)
    logDebug("spotify $url")
}

fun Activity.isInstalledSpotify(): Boolean {
    val packageManager: PackageManager = packageManager
    return try {
        packageManager.getPackageInfo("com.spotify.music", 0)
        true
    } catch (e: PackageManager.NameNotFoundException) {
        false
    }
}