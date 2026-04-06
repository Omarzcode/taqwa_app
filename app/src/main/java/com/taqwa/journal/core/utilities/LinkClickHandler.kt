package com.taqwa.journal.core.utilities

import android.content.Context
import android.content.Intent
import android.net.Uri

/**
 * Handles opening external links, emails, phone calls, and other intent launches.
 */
object LinkClickHandler {
    
    fun openUrl(context: Context, url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun sendEmail(
        context: Context,
        email: String,
        subject: String? = null,
        body: String? = null
    ) {
        try {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:$email")
                subject?.let { putExtra(Intent.EXTRA_SUBJECT, it) }
                body?.let { putExtra(Intent.EXTRA_TEXT, it) }
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun makePhoneCall(context: Context, phoneNumber: String) {
        try {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
            context.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun sendSms(context: Context, phoneNumber: String, message: String? = null) {
        try {
            val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:$phoneNumber")).apply {
                message?.let { putExtra("sms_body", it) }
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun openMap(context: Context, location: String) {
        try {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("geo:0,0?q=" + Uri.encode(location))
            )
            context.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun shareText(context: Context, title: String, text: String) {
        try {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, text)
            }
            val chooser = Intent.createChooser(intent, title)
            context.startActivity(chooser)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun shareFile(context: Context, title: String, fileUri: Uri, mimeType: String = "*/*") {
        try {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = mimeType
                putExtra(Intent.EXTRA_STREAM, fileUri)
            }
            val chooser = Intent.createChooser(intent, title)
            context.startActivity(chooser)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun openFileChooser(context: Context, mimeType: String, requestCode: Int) {
        try {
            val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                type = mimeType
                addCategory(Intent.CATEGORY_OPENABLE)
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun isValidUrl(url: String): Boolean {
        return try {
            Uri.parse(url)
            url.startsWith("http://") || url.startsWith("https://") || url.startsWith("www.")
        } catch (e: Exception) {
            false
        }
    }

    fun extractDomain(url: String): String {
        return try {
            val uri = Uri.parse(url)
            uri.host ?: url
        } catch (e: Exception) {
            url
        }
    }
}
