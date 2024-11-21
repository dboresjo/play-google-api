package com.boresjo.play.api.google.servicedirectory

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtListLocationsResponse: Format[Schema.ListLocationsResponse] = Json.format[Schema.ListLocationsResponse]
	given fmtLocation: Format[Schema.Location] = Json.format[Schema.Location]
	given fmtResolveServiceRequest: Format[Schema.ResolveServiceRequest] = Json.format[Schema.ResolveServiceRequest]
	given fmtResolveServiceResponse: Format[Schema.ResolveServiceResponse] = Json.format[Schema.ResolveServiceResponse]
	given fmtService: Format[Schema.Service] = Json.format[Schema.Service]
	given fmtEndpoint: Format[Schema.Endpoint] = Json.format[Schema.Endpoint]
	given fmtNamespace: Format[Schema.Namespace] = Json.format[Schema.Namespace]
	given fmtListNamespacesResponse: Format[Schema.ListNamespacesResponse] = Json.format[Schema.ListNamespacesResponse]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
	given fmtListServicesResponse: Format[Schema.ListServicesResponse] = Json.format[Schema.ListServicesResponse]
	given fmtListEndpointsResponse: Format[Schema.ListEndpointsResponse] = Json.format[Schema.ListEndpointsResponse]
	given fmtGetIamPolicyRequest: Format[Schema.GetIamPolicyRequest] = Json.format[Schema.GetIamPolicyRequest]
	given fmtGetPolicyOptions: Format[Schema.GetPolicyOptions] = Json.format[Schema.GetPolicyOptions]
	given fmtPolicy: Format[Schema.Policy] = Json.format[Schema.Policy]
	given fmtBinding: Format[Schema.Binding] = Json.format[Schema.Binding]
	given fmtExpr: Format[Schema.Expr] = Json.format[Schema.Expr]
	given fmtSetIamPolicyRequest: Format[Schema.SetIamPolicyRequest] = Json.format[Schema.SetIamPolicyRequest]
	given fmtTestIamPermissionsRequest: Format[Schema.TestIamPermissionsRequest] = Json.format[Schema.TestIamPermissionsRequest]
	given fmtTestIamPermissionsResponse: Format[Schema.TestIamPermissionsResponse] = Json.format[Schema.TestIamPermissionsResponse]
}
