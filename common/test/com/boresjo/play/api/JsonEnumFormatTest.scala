package com.boresjo.play.api

import org.scalatest.funsuite.AnyFunSuite
import play.api.libs.json.{JsNumber, JsString, JsSuccess}

enum TestEnum extends Enum[TestEnum] {
  case Value1, Value2, Value3
}

class JsonEnumFormatTest extends AnyFunSuite {

  given JsonEnumFormat[TestEnum] = new JsonEnumFormat[TestEnum]()

  test("reads returns JsSuccess for valid enum value") {
    val json = JsString("Value1")
    assert(JsonEnumFormat[TestEnum].reads(json) == JsSuccess(TestEnum.Value1))
  }

  test("reads returns JsError for invalid enum value") {
    val json = JsString("InvalidValue")
    assert(JsonEnumFormat[TestEnum].reads(json).isError)
  }

  test("reads returns JsError for non-string value") {
    val json = JsNumber(123)
    assert(JsonEnumFormat[TestEnum].reads(json).isError)
  }

  test("writes returns JsString for enum value") {
    val value = TestEnum.Value2
    assert(JsonEnumFormat[TestEnum].writes(value) == JsString("Value2"))
  }
}