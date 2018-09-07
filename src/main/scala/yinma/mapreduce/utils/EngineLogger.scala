package yinma.mapreduce.utils

import org.slf4j.LoggerFactory

/**
  * Created by xxh on 18-9-5.
  */
trait EngineLogger {
    protected val logger = LoggerFactory.getLogger(getClass)
}
