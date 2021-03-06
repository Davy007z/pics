package net.azib.photos

import java.text.SimpleDateFormat
import java.util.*

class GalleryLoader : XMLListener<Gallery> {
  override var result = Gallery()
  private var album: Album? = null
  val timestampFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")

  init {
    timestampFormat.timeZone = TimeZone.getTimeZone("UTC")
  }

  private fun parseTimestamp(value: String) = timestampFormat.parse(value).time

  override fun value(path: String, value: String) {
    when (path) {
      "user" -> result.authorId = value
      "nickname" -> result.author = value
      "updated" -> result.timestamp = parseTimestamp(value)

      "entry/id" -> album?.id = value
      "entry/name" -> album?.name = value
      "entry/title" -> album?.title = value
      "entry/summary" -> album?.description = value
      "entry/nickname" -> album?.author = value
      "entry/access" -> album?.isPublic = "public" == value
      "entry/updated" -> album?.timestamp = parseTimestamp(value)
      "entry/group/thumbnail@url" -> album?.thumbUrl = value + ".jpg"
      "entry/where/Point/pos" -> album?.geo = GeoLocation(value)
      "entry/numphotos" -> album?.size = value.toInt()
    }
  }

  override fun start(path: String) {
    if ("entry" == path) album = Album()
  }

  override fun end(path: String) {
    if ("entry" == path) result += album!!
  }
}
