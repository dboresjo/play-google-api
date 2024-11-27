package com.boresjo.play.api

import play.api.libs.json.*

import scala.reflect.ClassTag

/**
 * Companion object for JsonEnumFormat providing a factory method to create a JsonEnumFormat instance.
 */
object JsonEnumFormat {
  /**
   * Creates a JsonEnumFormat instance for the given enum type.
   *
   * @param ct The ClassTag for the enum type.
   * @tparam T The enum type.
   * @return A Format instance for the enum type.
   */
  def apply[T <: Enum[T]](using ClassTag[T]): Format[T] = new JsonEnumFormat[T]
}

/**
 * A custom JSON format for serializing and deserializing enum values.
 *
 * @param ct The ClassTag for the enum type.
 * @tparam T The enum type.
 */
class JsonEnumFormat[T <: Enum[T]](using ClassTag[T]) extends Format[T] {
  /**
   * Deserializes a JSON value into an enum value.
   *
   * @param json The JSON value to deserialize.
   * @return A JsResult containing the enum value or an error.
   */
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

  /**
   * Serializes an enum value into a JSON value.
   *
   * @param value The enum value to serialize.
   * @return A JsValue representing the enum value.
   */
  def writes(value: T): JsValue = JsString(value.toString)
}