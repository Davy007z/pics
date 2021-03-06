package net.azib.photos

import java.text.SimpleDateFormat
import java.util.*

open class Entity(var id: String? = null, open var title: String? = null, description: String? = null) {
  var thumbUrl: String? = null
  var timestamp: Long? = null
  var geo: GeoLocation? = null

  open var description = description
    get() = field ?: ""

  val timestampISO: String?
    get() = timestamp?.formatISO()

  companion object {
    private val timestampFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    init {
      timestampFormat.timeZone = TimeZone.getTimeZone("UTC")
    }

    private fun Long.formatISO() = synchronized(timestampFormat) {
      timestampFormat.format(this)
    }
  }
}
