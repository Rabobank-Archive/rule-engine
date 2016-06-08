package org.scalarules.engine

import org.scalatest.{FlatSpec, Matchers}

class LRUCacheTest extends FlatSpec with Matchers {

  it should "cleanup older items back to initial size" in {
    val cache: LRUCache[Int, Int] = new LRUCache(4, 10)

    (0 until 10).foreach( (n: Int) => cache.add(n, n) )

    Thread.sleep(20)

    cache.add(15, 15)

    cache.size should be (5)
    cache.get(15) should be(Some(15))
  }

}
