package com.boresjo.play.api

trait PlayApi {
  type PathParam = String | Numeric[?]
}
