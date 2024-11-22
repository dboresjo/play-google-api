package com.boresjo.play.api.google.licensing

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
	given fmtLicenseAssignment: Format[Schema.LicenseAssignment] = Json.format[Schema.LicenseAssignment]
	given fmtLicenseAssignmentInsert: Format[Schema.LicenseAssignmentInsert] = Json.format[Schema.LicenseAssignmentInsert]
	given fmtLicenseAssignmentList: Format[Schema.LicenseAssignmentList] = Json.format[Schema.LicenseAssignmentList]
}
