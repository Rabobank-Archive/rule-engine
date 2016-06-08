package org.scalarules.engine

import scala.collection.concurrent.TrieMap

class LRUCache[K, V](val maxSize: Int = LRUCache.GRAPH_CACHE_MAX_SIZE, val cleanupInterval: Long = LRUCache.GRAPH_CACHE_CLEANUP_INTERVAL_MS) {

  private var graphCache: TrieMap[K, (V, Long)] = TrieMap()
  private var lastCacheCleanup = System.currentTimeMillis()

  def add(key: K, value: V): V = {
    updateCacheWithNewEntry(key, value)
    value
  }

  def get(key: K): Option[V] = graphCache.get(key).map{ case (cacheEntry, timestamp) => cacheEntry }

  def size: Int = graphCache.size

  private def updateCacheWithNewEntry(key: K, value: V): Unit = {
    if ((System.currentTimeMillis() - lastCacheCleanup) > maxSize) {
      // Cleaning up the cache before adding anything new to it
      val cacheEntriesSortedByAge: List[(K, (V, Long))] = graphCache.toList.sortBy[Long]( entry => entry._2._2 )
      val removalCandidates =
        if (cacheEntriesSortedByAge.size > maxSize)
          cacheEntriesSortedByAge.takeRight(cacheEntriesSortedByAge.size - maxSize)
        else
          List()

      removalCandidates.map{ case (key, value) => graphCache -= key }

      lastCacheCleanup = System.currentTimeMillis()
    }

    graphCache += key -> ((value, System.currentTimeMillis()))
  }


}

object LRUCache {
  val GRAPH_CACHE_MAX_SIZE: Int = 15
  val GRAPH_CACHE_CLEANUP_INTERVAL_MS: Long = 5000
}
