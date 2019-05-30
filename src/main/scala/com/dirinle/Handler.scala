package com.dirinle

import com.dirinle.Rest.Entity
import java.lang.management.{ManagementFactory, OperatingSystemMXBean}
case class Info(memoryUsage: Double)

trait Handler {
  def alphabetOrder (entity: Entity): Entity
  def info: Info
}
class HandlerImpl  extends Handler{
  def alphabetOrder(entity: Entity): Entity = entity.toSeq.sortBy(_._1).toMap
  def info: Info = {
    val system = ManagementFactory.getOperatingSystemMXBean()
    Info(system.getSystemLoadAverage)

  }
}