package com.boresjo.play.api

import play.api.libs.json.*

import scala.reflect.ClassTag

object JsonEnumFormat {
  def apply[T <: Enum[T]](using ClassTag[T]): Format[T] = new JsonEnumFormat[T]
}
class JsonEnumFormat[T <: Enum[T]](using ClassTag[T]) extends Format[T] {
  def reads(json: JsValue): JsResult[T] = json match {
    case JsString(value) =>
      val denum: Class[T] = implicitly[ClassTag[T]].runtimeClass.asInstanceOf[Class[T]]
      val values = denum.getEnumConstants
      values.find(_.toString == value) match {
        case Some(v) => JsSuccess(v)
        case None => JsError(s"Invalid value '$value' for ${denum.getSimpleName}")
      }
    case _ => JsError("String value expected")
  }

  def writes(value: T): JsValue = JsString(value.toString)
}
