package com.boresjo.play.api.google.firebasedatabase

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtReenableDatabaseInstanceRequest: Format[Schema.ReenableDatabaseInstanceRequest] = Json.format[Schema.ReenableDatabaseInstanceRequest]
	given fmtDisableDatabaseInstanceRequest: Format[Schema.DisableDatabaseInstanceRequest] = Json.format[Schema.DisableDatabaseInstanceRequest]
	given fmtUndeleteDatabaseInstanceRequest: Format[Schema.UndeleteDatabaseInstanceRequest] = Json.format[Schema.UndeleteDatabaseInstanceRequest]
	given fmtDatabaseInstance: Format[Schema.DatabaseInstance] = Json.format[Schema.DatabaseInstance]
	given fmtDatabaseInstanceStateEnum: Format[Schema.DatabaseInstance.StateEnum] = JsonEnumFormat[Schema.DatabaseInstance.StateEnum]
	given fmtDatabaseInstanceTypeEnum: Format[Schema.DatabaseInstance.TypeEnum] = JsonEnumFormat[Schema.DatabaseInstance.TypeEnum]
	given fmtListDatabaseInstancesResponse: Format[Schema.ListDatabaseInstancesResponse] = Json.format[Schema.ListDatabaseInstancesResponse]
}
