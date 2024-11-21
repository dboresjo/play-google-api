package com.boresjo.play.api.google.cloudprofiler

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtCreateProfileRequest: Format[Schema.CreateProfileRequest] = Json.format[Schema.CreateProfileRequest]
	given fmtDeployment: Format[Schema.Deployment] = Json.format[Schema.Deployment]
	given fmtCreateProfileRequestProfileTypeEnum: Format[Schema.CreateProfileRequest.ProfileTypeEnum] = JsonEnumFormat[Schema.CreateProfileRequest.ProfileTypeEnum]
	given fmtProfile: Format[Schema.Profile] = Json.format[Schema.Profile]
	given fmtProfileProfileTypeEnum: Format[Schema.Profile.ProfileTypeEnum] = JsonEnumFormat[Schema.Profile.ProfileTypeEnum]
	given fmtListProfilesResponse: Format[Schema.ListProfilesResponse] = Json.format[Schema.ListProfilesResponse]
}
