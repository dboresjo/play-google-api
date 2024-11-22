package com.boresjo.play.api.google.apikeys

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtV2Restrictions: Format[Schema.V2Restrictions] = Json.format[Schema.V2Restrictions]
	given fmtV2ServerKeyRestrictions: Format[Schema.V2ServerKeyRestrictions] = Json.format[Schema.V2ServerKeyRestrictions]
	given fmtV2BrowserKeyRestrictions: Format[Schema.V2BrowserKeyRestrictions] = Json.format[Schema.V2BrowserKeyRestrictions]
	given fmtV2IosKeyRestrictions: Format[Schema.V2IosKeyRestrictions] = Json.format[Schema.V2IosKeyRestrictions]
	given fmtV2ApiTarget: Format[Schema.V2ApiTarget] = Json.format[Schema.V2ApiTarget]
	given fmtV2AndroidKeyRestrictions: Format[Schema.V2AndroidKeyRestrictions] = Json.format[Schema.V2AndroidKeyRestrictions]
	given fmtV2UndeleteKeyRequest: Format[Schema.V2UndeleteKeyRequest] = Json.format[Schema.V2UndeleteKeyRequest]
	given fmtV2AndroidApplication: Format[Schema.V2AndroidApplication] = Json.format[Schema.V2AndroidApplication]
	given fmtStatus: Format[Schema.Status] = Json.format[Schema.Status]
	given fmtV2GetKeyStringResponse: Format[Schema.V2GetKeyStringResponse] = Json.format[Schema.V2GetKeyStringResponse]
	given fmtV2LookupKeyResponse: Format[Schema.V2LookupKeyResponse] = Json.format[Schema.V2LookupKeyResponse]
	given fmtOperation: Format[Schema.Operation] = Json.format[Schema.Operation]
	given fmtV2Key: Format[Schema.V2Key] = Json.format[Schema.V2Key]
	given fmtV2ListKeysResponse: Format[Schema.V2ListKeysResponse] = Json.format[Schema.V2ListKeysResponse]
}
