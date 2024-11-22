package com.boresjo.play.api.google.appengine

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://appengine.googleapis.com/"

	object apps {
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withApplication(body: Schema.Application) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object create {
			def apply()(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/apps").addQueryStringParameters())
		}
		class repair(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withRepairApplicationRequest(body: Schema.RepairApplicationRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object repair {
			def apply(appsId: String)(using auth: AuthToken, ec: ExecutionContext): repair = new repair(ws.url(BASE_URL + s"v1/apps/${appsId}:repair").addQueryStringParameters())
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Application]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Application])
		}
		object get {
			def apply(appsId: String, includeExtraData: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/apps/${appsId}").addQueryStringParameters("includeExtraData" -> includeExtraData.toString))
			given Conversion[get, Future[Schema.Application]] = (fun: get) => fun.apply()
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withApplication(body: Schema.Application) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
		}
		object patch {
			def apply(appsId: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/apps/${appsId}").addQueryStringParameters("updateMask" -> updateMask.toString))
		}
		class listRuntimes(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListRuntimesResponse]) {
			/** Optional. The environment of the Application.<br>Possible values:<br>ENVIRONMENT_UNSPECIFIED: Default value.<br>STANDARD: App Engine Standard.<br>FLEXIBLE: App Engine Flexible */
			def withEnvironment(environment: String) = new listRuntimes(req.addQueryStringParameters("environment" -> environment.toString))
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListRuntimesResponse])
		}
		object listRuntimes {
			def apply(appsId: String)(using auth: AuthToken, ec: ExecutionContext): listRuntimes = new listRuntimes(ws.url(BASE_URL + s"v1/apps/${appsId}:listRuntimes").addQueryStringParameters())
			given Conversion[listRuntimes, Future[Schema.ListRuntimesResponse]] = (fun: listRuntimes) => fun.apply()
		}
		object services {
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
			}
			object delete {
				def apply(appsId: String, servicesId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/apps/${appsId}/services/${servicesId}").addQueryStringParameters())
				given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Service]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Service])
			}
			object get {
				def apply(appsId: String, servicesId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/apps/${appsId}/services/${servicesId}").addQueryStringParameters())
				given Conversion[get, Future[Schema.Service]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withService(body: Schema.Service) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
			}
			object patch {
				def apply(appsId: String, servicesId: String, updateMask: String, migrateTraffic: Boolean)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/apps/${appsId}/services/${servicesId}").addQueryStringParameters("updateMask" -> updateMask.toString, "migrateTraffic" -> migrateTraffic.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListServicesResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListServicesResponse])
			}
			object list {
				def apply(appsId: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/apps/${appsId}/services").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListServicesResponse]] = (fun: list) => fun.apply()
			}
			object versions {
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(appsId: String, servicesId: String, versionsId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/apps/${appsId}/services/${servicesId}/versions/${versionsId}").addQueryStringParameters())
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Version]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Version])
				}
				object get {
					def apply(appsId: String, servicesId: String, versionsId: String, view: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/apps/${appsId}/services/${servicesId}/versions/${versionsId}").addQueryStringParameters("view" -> view.toString))
					given Conversion[get, Future[Schema.Version]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withVersion(body: Schema.Version) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(appsId: String, servicesId: String, versionsId: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/apps/${appsId}/services/${servicesId}/versions/${versionsId}").addQueryStringParameters("updateMask" -> updateMask.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListVersionsResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListVersionsResponse])
				}
				object list {
					def apply(appsId: String, servicesId: String, view: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/apps/${appsId}/services/${servicesId}/versions").addQueryStringParameters("view" -> view.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListVersionsResponse]] = (fun: list) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withVersion(body: Schema.Version) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(appsId: String, servicesId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/apps/${appsId}/services/${servicesId}/versions").addQueryStringParameters())
				}
				object instances {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListInstancesResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListInstancesResponse])
					}
					object list {
						def apply(appsId: String, servicesId: String, versionsId: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/apps/${appsId}/services/${servicesId}/versions/${versionsId}/instances").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListInstancesResponse]] = (fun: list) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Instance]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Instance])
					}
					object get {
						def apply(appsId: String, servicesId: String, versionsId: String, instancesId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/apps/${appsId}/services/${servicesId}/versions/${versionsId}/instances/${instancesId}").addQueryStringParameters())
						given Conversion[get, Future[Schema.Instance]] = (fun: get) => fun.apply()
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
					}
					object delete {
						def apply(appsId: String, servicesId: String, versionsId: String, instancesId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/apps/${appsId}/services/${servicesId}/versions/${versionsId}/instances/${instancesId}").addQueryStringParameters())
						given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
					}
					class debug(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withDebugInstanceRequest(body: Schema.DebugInstanceRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object debug {
						def apply(appsId: String, servicesId: String, versionsId: String, instancesId: String)(using auth: AuthToken, ec: ExecutionContext): debug = new debug(ws.url(BASE_URL + s"v1/apps/${appsId}/services/${servicesId}/versions/${versionsId}/instances/${instancesId}:debug").addQueryStringParameters())
					}
				}
			}
		}
		object authorizedDomains {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListAuthorizedDomainsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListAuthorizedDomainsResponse])
			}
			object list {
				def apply(appsId: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/apps/${appsId}/authorizedDomains").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListAuthorizedDomainsResponse]] = (fun: list) => fun.apply()
			}
		}
		object authorizedCertificates {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withAuthorizedCertificate(body: Schema.AuthorizedCertificate) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AuthorizedCertificate])
			}
			object create {
				def apply(appsId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/apps/${appsId}/authorizedCertificates").addQueryStringParameters())
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(appsId: String, authorizedCertificatesId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/apps/${appsId}/authorizedCertificates/${authorizedCertificatesId}").addQueryStringParameters())
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AuthorizedCertificate]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.AuthorizedCertificate])
			}
			object get {
				def apply(appsId: String, authorizedCertificatesId: String, view: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/apps/${appsId}/authorizedCertificates/${authorizedCertificatesId}").addQueryStringParameters("view" -> view.toString))
				given Conversion[get, Future[Schema.AuthorizedCertificate]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withAuthorizedCertificate(body: Schema.AuthorizedCertificate) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.AuthorizedCertificate])
			}
			object patch {
				def apply(appsId: String, authorizedCertificatesId: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/apps/${appsId}/authorizedCertificates/${authorizedCertificatesId}").addQueryStringParameters("updateMask" -> updateMask.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListAuthorizedCertificatesResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListAuthorizedCertificatesResponse])
			}
			object list {
				def apply(appsId: String, view: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/apps/${appsId}/authorizedCertificates").addQueryStringParameters("view" -> view.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListAuthorizedCertificatesResponse]] = (fun: list) => fun.apply()
			}
		}
		object locations {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListLocationsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListLocationsResponse])
			}
			object list {
				def apply(appsId: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/apps/${appsId}/locations").addQueryStringParameters("filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListLocationsResponse]] = (fun: list) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Location]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Location])
			}
			object get {
				def apply(appsId: String, locationsId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/apps/${appsId}/locations/${locationsId}").addQueryStringParameters())
				given Conversion[get, Future[Schema.Location]] = (fun: get) => fun.apply()
			}
		}
		object domainMappings {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withDomainMapping(body: Schema.DomainMapping) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object create {
				def apply(appsId: String, overrideStrategy: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/apps/${appsId}/domainMappings").addQueryStringParameters("overrideStrategy" -> overrideStrategy.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
			}
			object delete {
				def apply(appsId: String, domainMappingsId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/apps/${appsId}/domainMappings/${domainMappingsId}").addQueryStringParameters())
				given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.DomainMapping]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.DomainMapping])
			}
			object get {
				def apply(appsId: String, domainMappingsId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/apps/${appsId}/domainMappings/${domainMappingsId}").addQueryStringParameters())
				given Conversion[get, Future[Schema.DomainMapping]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withDomainMapping(body: Schema.DomainMapping) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
			}
			object patch {
				def apply(appsId: String, domainMappingsId: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/apps/${appsId}/domainMappings/${domainMappingsId}").addQueryStringParameters("updateMask" -> updateMask.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListDomainMappingsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListDomainMappingsResponse])
			}
			object list {
				def apply(appsId: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/apps/${appsId}/domainMappings").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListDomainMappingsResponse]] = (fun: list) => fun.apply()
			}
		}
		object operations {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListOperationsResponse])
			}
			object list {
				def apply(appsId: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/apps/${appsId}/operations").addQueryStringParameters("filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Operation])
			}
			object get {
				def apply(appsId: String, operationsId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/apps/${appsId}/operations/${operationsId}").addQueryStringParameters())
				given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
			}
		}
		object firewall {
			object ingressRules {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withFirewallRule(body: Schema.FirewallRule) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.FirewallRule])
				}
				object create {
					def apply(appsId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/apps/${appsId}/firewall/ingressRules").addQueryStringParameters())
				}
				class batchUpdate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withBatchUpdateIngressRulesRequest(body: Schema.BatchUpdateIngressRulesRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BatchUpdateIngressRulesResponse])
				}
				object batchUpdate {
					def apply(appsId: String)(using auth: AuthToken, ec: ExecutionContext): batchUpdate = new batchUpdate(ws.url(BASE_URL + s"v1/apps/${appsId}/firewall/ingressRules:batchUpdate").addQueryStringParameters())
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(appsId: String, ingressRulesId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/apps/${appsId}/firewall/ingressRules/${ingressRulesId}").addQueryStringParameters())
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.FirewallRule]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.FirewallRule])
				}
				object get {
					def apply(appsId: String, ingressRulesId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/apps/${appsId}/firewall/ingressRules/${ingressRulesId}").addQueryStringParameters())
					given Conversion[get, Future[Schema.FirewallRule]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withFirewallRule(body: Schema.FirewallRule) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.FirewallRule])
				}
				object patch {
					def apply(appsId: String, ingressRulesId: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/apps/${appsId}/firewall/ingressRules/${ingressRulesId}").addQueryStringParameters("updateMask" -> updateMask.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListIngressRulesResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListIngressRulesResponse])
				}
				object list {
					def apply(appsId: String, pageSize: Int, pageToken: String, matchingAddress: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/apps/${appsId}/firewall/ingressRules").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "matchingAddress" -> matchingAddress.toString))
					given Conversion[list, Future[Schema.ListIngressRulesResponse]] = (fun: list) => fun.apply()
				}
			}
		}
	}
	object projects {
		object locations {
			object applications {
				object services {
					object versions {
						class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
							def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
						}
						object delete {
							def apply(projectsId: String, locationsId: String, applicationsId: String, servicesId: String, versionsId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/applications/${applicationsId}/services/${servicesId}/versions/${versionsId}").addQueryStringParameters())
							given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
						}
					}
				}
				object authorizedDomains {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListAuthorizedDomainsResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListAuthorizedDomainsResponse])
					}
					object list {
						def apply(projectsId: String, locationsId: String, applicationsId: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/applications/${applicationsId}/authorizedDomains").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListAuthorizedDomainsResponse]] = (fun: list) => fun.apply()
					}
				}
			}
		}
	}
}
