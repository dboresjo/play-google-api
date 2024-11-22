package com.boresjo.play.api.google.sqladmin

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://sqladmin.googleapis.com/"

	object instances {
		class listServerCas(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.InstancesListServerCasResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.InstancesListServerCasResponse])
		}
		object listServerCas {
			def apply(project: String, instance: String)(using auth: AuthToken, ec: ExecutionContext): listServerCas = new listServerCas(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/listServerCas").addQueryStringParameters())
			given Conversion[listServerCas, Future[Schema.InstancesListServerCasResponse]] = (fun: listServerCas) => fun.apply()
		}
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withDatabaseInstance(body: Schema.DatabaseInstance) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object insert {
			def apply(project: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"v1/projects/${project}/instances").addQueryStringParameters())
		}
		class releaseSsrsLease(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SqlInstancesReleaseSsrsLeaseResponse]) {
			def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.SqlInstancesReleaseSsrsLeaseResponse])
		}
		object releaseSsrsLease {
			def apply(project: String, instance: String)(using auth: AuthToken, ec: ExecutionContext): releaseSsrsLease = new releaseSsrsLease(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/releaseSsrsLease").addQueryStringParameters())
			given Conversion[releaseSsrsLease, Future[Schema.SqlInstancesReleaseSsrsLeaseResponse]] = (fun: releaseSsrsLease) => fun.apply()
		}
		class addServerCertificate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object addServerCertificate {
			def apply(project: String, instance: String)(using auth: AuthToken, ec: ExecutionContext): addServerCertificate = new addServerCertificate(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/addServerCertificate").addQueryStringParameters())
			given Conversion[addServerCertificate, Future[Schema.Operation]] = (fun: addServerCertificate) => fun.apply()
		}
		class rotateServerCa(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withInstancesRotateServerCaRequest(body: Schema.InstancesRotateServerCaRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object rotateServerCa {
			def apply(project: String, instance: String)(using auth: AuthToken, ec: ExecutionContext): rotateServerCa = new rotateServerCa(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/rotateServerCa").addQueryStringParameters())
		}
		class promoteReplica(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object promoteReplica {
			def apply(project: String, instance: String, failover: Boolean)(using auth: AuthToken, ec: ExecutionContext): promoteReplica = new promoteReplica(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/promoteReplica").addQueryStringParameters("failover" -> failover.toString))
			given Conversion[promoteReplica, Future[Schema.Operation]] = (fun: promoteReplica) => fun.apply()
		}
		class truncateLog(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withInstancesTruncateLogRequest(body: Schema.InstancesTruncateLogRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object truncateLog {
			def apply(project: String, instance: String)(using auth: AuthToken, ec: ExecutionContext): truncateLog = new truncateLog(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/truncateLog").addQueryStringParameters())
		}
		class RotateServerCertificate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withInstancesRotateServerCertificateRequest(body: Schema.InstancesRotateServerCertificateRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object RotateServerCertificate {
			def apply(project: String, instance: String)(using auth: AuthToken, ec: ExecutionContext): RotateServerCertificate = new RotateServerCertificate(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/rotateServerCertificate").addQueryStringParameters())
		}
		class `export`(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withInstancesExportRequest(body: Schema.InstancesExportRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object `export` {
			def apply(project: String, instance: String)(using auth: AuthToken, ec: ExecutionContext): `export` = new `export`(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/export").addQueryStringParameters())
		}
		class _clone(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withInstancesCloneRequest(body: Schema.InstancesCloneRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object _clone {
			def apply(project: String, instance: String)(using auth: AuthToken, ec: ExecutionContext): _clone = new _clone(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/clone").addQueryStringParameters())
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
		}
		object delete {
			def apply(project: String, instance: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}").addQueryStringParameters())
			given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.DatabaseInstance]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.DatabaseInstance])
		}
		object get {
			def apply(project: String, instance: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}").addQueryStringParameters())
			given Conversion[get, Future[Schema.DatabaseInstance]] = (fun: get) => fun.apply()
		}
		class restoreBackup(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withInstancesRestoreBackupRequest(body: Schema.InstancesRestoreBackupRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object restoreBackup {
			def apply(project: String, instance: String)(using auth: AuthToken, ec: ExecutionContext): restoreBackup = new restoreBackup(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/restoreBackup").addQueryStringParameters())
		}
		class acquireSsrsLease(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withInstancesAcquireSsrsLeaseRequest(body: Schema.InstancesAcquireSsrsLeaseRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SqlInstancesAcquireSsrsLeaseResponse])
		}
		object acquireSsrsLease {
			def apply(project: String, instance: String)(using auth: AuthToken, ec: ExecutionContext): acquireSsrsLease = new acquireSsrsLease(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/acquireSsrsLease").addQueryStringParameters())
		}
		class startReplica(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object startReplica {
			def apply(project: String, instance: String)(using auth: AuthToken, ec: ExecutionContext): startReplica = new startReplica(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/startReplica").addQueryStringParameters())
			given Conversion[startReplica, Future[Schema.Operation]] = (fun: startReplica) => fun.apply()
		}
		class reencrypt(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withInstancesReencryptRequest(body: Schema.InstancesReencryptRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object reencrypt {
			def apply(project: String, instance: String)(using auth: AuthToken, ec: ExecutionContext): reencrypt = new reencrypt(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/reencrypt").addQueryStringParameters())
		}
		class `import`(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withInstancesImportRequest(body: Schema.InstancesImportRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object `import` {
			def apply(project: String, instance: String)(using auth: AuthToken, ec: ExecutionContext): `import` = new `import`(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/import").addQueryStringParameters())
		}
		class demote(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withInstancesDemoteRequest(body: Schema.InstancesDemoteRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object demote {
			def apply(project: String, instance: String)(using auth: AuthToken, ec: ExecutionContext): demote = new demote(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/demote").addQueryStringParameters())
		}
		class addServerCa(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object addServerCa {
			def apply(project: String, instance: String)(using auth: AuthToken, ec: ExecutionContext): addServerCa = new addServerCa(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/addServerCa").addQueryStringParameters())
			given Conversion[addServerCa, Future[Schema.Operation]] = (fun: addServerCa) => fun.apply()
		}
		class demoteMaster(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withInstancesDemoteMasterRequest(body: Schema.InstancesDemoteMasterRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object demoteMaster {
			def apply(project: String, instance: String)(using auth: AuthToken, ec: ExecutionContext): demoteMaster = new demoteMaster(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/demoteMaster").addQueryStringParameters())
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withDatabaseInstance(body: Schema.DatabaseInstance) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Operation])
		}
		object update {
			def apply(project: String, instance: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}").addQueryStringParameters())
		}
		class restart(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object restart {
			def apply(project: String, instance: String)(using auth: AuthToken, ec: ExecutionContext): restart = new restart(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/restart").addQueryStringParameters())
			given Conversion[restart, Future[Schema.Operation]] = (fun: restart) => fun.apply()
		}
		class resetSslConfig(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object resetSslConfig {
			def apply(project: String, instance: String)(using auth: AuthToken, ec: ExecutionContext): resetSslConfig = new resetSslConfig(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/resetSslConfig").addQueryStringParameters())
			given Conversion[resetSslConfig, Future[Schema.Operation]] = (fun: resetSslConfig) => fun.apply()
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withDatabaseInstance(body: Schema.DatabaseInstance) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
		}
		object patch {
			def apply(project: String, instance: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}").addQueryStringParameters())
		}
		class switchover(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			/** Optional. (MySQL only) Cloud SQL instance operations timeout, which is a sum of all database operations. Default value is 10 minutes and can be modified to a maximum value of 24 hours.<br>Format: google-duration */
			def withDbTimeout(dbTimeout: String) = new switchover(req.addQueryStringParameters("dbTimeout" -> dbTimeout.toString))
			def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object switchover {
			def apply(project: String, instance: String)(using auth: AuthToken, ec: ExecutionContext): switchover = new switchover(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/switchover").addQueryStringParameters())
			given Conversion[switchover, Future[Schema.Operation]] = (fun: switchover) => fun.apply()
		}
		class ListServerCertificates(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.InstancesListServerCertificatesResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.InstancesListServerCertificatesResponse])
		}
		object ListServerCertificates {
			def apply(project: String, instance: String)(using auth: AuthToken, ec: ExecutionContext): ListServerCertificates = new ListServerCertificates(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/listServerCertificates").addQueryStringParameters())
			given Conversion[ListServerCertificates, Future[Schema.InstancesListServerCertificatesResponse]] = (fun: ListServerCertificates) => fun.apply()
		}
		class failover(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withInstancesFailoverRequest(body: Schema.InstancesFailoverRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object failover {
			def apply(project: String, instance: String)(using auth: AuthToken, ec: ExecutionContext): failover = new failover(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/failover").addQueryStringParameters())
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.InstancesListResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.InstancesListResponse])
		}
		object list {
			def apply(project: String, filter: String, maxResults: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${project}/instances").addQueryStringParameters("filter" -> filter.toString, "maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.InstancesListResponse]] = (fun: list) => fun.apply()
		}
		class stopReplica(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object stopReplica {
			def apply(project: String, instance: String)(using auth: AuthToken, ec: ExecutionContext): stopReplica = new stopReplica(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/stopReplica").addQueryStringParameters())
			given Conversion[stopReplica, Future[Schema.Operation]] = (fun: stopReplica) => fun.apply()
		}
	}
	object sslCerts {
		class createEphemeral(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withSslCertsCreateEphemeralRequest(body: Schema.SslCertsCreateEphemeralRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SslCert])
		}
		object createEphemeral {
			def apply(project: String, instance: String)(using auth: AuthToken, ec: ExecutionContext): createEphemeral = new createEphemeral(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/createEphemeral").addQueryStringParameters())
		}
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withSslCertsInsertRequest(body: Schema.SslCertsInsertRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SslCertsInsertResponse])
		}
		object insert {
			def apply(project: String, instance: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/sslCerts").addQueryStringParameters())
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
		}
		object delete {
			def apply(project: String, instance: String, sha1Fingerprint: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/sslCerts/${sha1Fingerprint}").addQueryStringParameters())
			given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SslCert]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.SslCert])
		}
		object get {
			def apply(project: String, instance: String, sha1Fingerprint: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/sslCerts/${sha1Fingerprint}").addQueryStringParameters())
			given Conversion[get, Future[Schema.SslCert]] = (fun: get) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SslCertsListResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.SslCertsListResponse])
		}
		object list {
			def apply(project: String, instance: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/sslCerts").addQueryStringParameters())
			given Conversion[list, Future[Schema.SslCertsListResponse]] = (fun: list) => fun.apply()
		}
	}
	object projects {
		object instances {
			class getDiskShrinkConfig(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SqlInstancesGetDiskShrinkConfigResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.SqlInstancesGetDiskShrinkConfigResponse])
			}
			object getDiskShrinkConfig {
				def apply(project: String, instance: String)(using auth: AuthToken, ec: ExecutionContext): getDiskShrinkConfig = new getDiskShrinkConfig(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/getDiskShrinkConfig").addQueryStringParameters())
				given Conversion[getDiskShrinkConfig, Future[Schema.SqlInstancesGetDiskShrinkConfigResponse]] = (fun: getDiskShrinkConfig) => fun.apply()
			}
			class verifyExternalSyncSettings(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withSqlInstancesVerifyExternalSyncSettingsRequest(body: Schema.SqlInstancesVerifyExternalSyncSettingsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SqlInstancesVerifyExternalSyncSettingsResponse])
			}
			object verifyExternalSyncSettings {
				def apply(project: String, instance: String)(using auth: AuthToken, ec: ExecutionContext): verifyExternalSyncSettings = new verifyExternalSyncSettings(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/verifyExternalSyncSettings").addQueryStringParameters())
			}
			class getLatestRecoveryTime(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SqlInstancesGetLatestRecoveryTimeResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.SqlInstancesGetLatestRecoveryTimeResponse])
			}
			object getLatestRecoveryTime {
				def apply(project: String, instance: String)(using auth: AuthToken, ec: ExecutionContext): getLatestRecoveryTime = new getLatestRecoveryTime(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/getLatestRecoveryTime").addQueryStringParameters())
				given Conversion[getLatestRecoveryTime, Future[Schema.SqlInstancesGetLatestRecoveryTimeResponse]] = (fun: getLatestRecoveryTime) => fun.apply()
			}
			class startExternalSync(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withSqlInstancesStartExternalSyncRequest(body: Schema.SqlInstancesStartExternalSyncRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object startExternalSync {
				def apply(project: String, instance: String)(using auth: AuthToken, ec: ExecutionContext): startExternalSync = new startExternalSync(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/startExternalSync").addQueryStringParameters())
			}
			class performDiskShrink(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withPerformDiskShrinkContext(body: Schema.PerformDiskShrinkContext) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object performDiskShrink {
				def apply(project: String, instance: String)(using auth: AuthToken, ec: ExecutionContext): performDiskShrink = new performDiskShrink(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/performDiskShrink").addQueryStringParameters())
			}
			class resetReplicaSize(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withSqlInstancesResetReplicaSizeRequest(body: Schema.SqlInstancesResetReplicaSizeRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object resetReplicaSize {
				def apply(project: String, instance: String)(using auth: AuthToken, ec: ExecutionContext): resetReplicaSize = new resetReplicaSize(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/resetReplicaSize").addQueryStringParameters())
			}
			class rescheduleMaintenance(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withSqlInstancesRescheduleMaintenanceRequestBody(body: Schema.SqlInstancesRescheduleMaintenanceRequestBody) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object rescheduleMaintenance {
				def apply(project: String, instance: String)(using auth: AuthToken, ec: ExecutionContext): rescheduleMaintenance = new rescheduleMaintenance(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/rescheduleMaintenance").addQueryStringParameters())
			}
		}
	}
	object backupRuns {
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
		}
		object delete {
			def apply(project: String, instance: String, id: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/backupRuns/${id}").addQueryStringParameters())
			given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.BackupRun]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.BackupRun])
		}
		object get {
			def apply(project: String, instance: String, id: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/backupRuns/${id}").addQueryStringParameters())
			given Conversion[get, Future[Schema.BackupRun]] = (fun: get) => fun.apply()
		}
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withBackupRun(body: Schema.BackupRun) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object insert {
			def apply(project: String, instance: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/backupRuns").addQueryStringParameters())
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.BackupRunsListResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.BackupRunsListResponse])
		}
		object list {
			def apply(project: String, instance: String, maxResults: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/backupRuns").addQueryStringParameters("maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.BackupRunsListResponse]] = (fun: list) => fun.apply()
		}
	}
	object connect {
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ConnectSettings]) {
			/** Optional. Optional snapshot read timestamp to trade freshness for performance.<br>Format: google-datetime */
			def withReadTime(readTime: String) = new get(req.addQueryStringParameters("readTime" -> readTime.toString))
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ConnectSettings])
		}
		object get {
			def apply(project: String, instance: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/connectSettings").addQueryStringParameters())
			given Conversion[get, Future[Schema.ConnectSettings]] = (fun: get) => fun.apply()
		}
		class generateEphemeralCert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGenerateEphemeralCertRequest(body: Schema.GenerateEphemeralCertRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GenerateEphemeralCertResponse])
		}
		object generateEphemeralCert {
			def apply(project: String, instance: String)(using auth: AuthToken, ec: ExecutionContext): generateEphemeralCert = new generateEphemeralCert(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}:generateEphemeralCert").addQueryStringParameters())
		}
	}
	object databases {
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withDatabase(body: Schema.Database) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object insert {
			def apply(project: String, instance: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/databases").addQueryStringParameters())
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
		}
		object delete {
			def apply(project: String, instance: String, database: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/databases/${database}").addQueryStringParameters())
			given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Database]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Database])
		}
		object get {
			def apply(project: String, instance: String, database: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/databases/${database}").addQueryStringParameters())
			given Conversion[get, Future[Schema.Database]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withDatabase(body: Schema.Database) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Operation])
		}
		object update {
			def apply(project: String, instance: String, database: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/databases/${database}").addQueryStringParameters())
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withDatabase(body: Schema.Database) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
		}
		object patch {
			def apply(project: String, instance: String, database: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/databases/${database}").addQueryStringParameters())
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.DatabasesListResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.DatabasesListResponse])
		}
		object list {
			def apply(project: String, instance: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/databases").addQueryStringParameters())
			given Conversion[list, Future[Schema.DatabasesListResponse]] = (fun: list) => fun.apply()
		}
	}
	object flags {
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.FlagsListResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.FlagsListResponse])
		}
		object list {
			def apply(databaseVersion: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/flags").addQueryStringParameters("databaseVersion" -> databaseVersion.toString))
			given Conversion[list, Future[Schema.FlagsListResponse]] = (fun: list) => fun.apply()
		}
	}
	object operations {
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Operation])
		}
		object get {
			def apply(project: String, operation: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${project}/operations/${operation}").addQueryStringParameters())
			given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.OperationsListResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.OperationsListResponse])
		}
		object list {
			def apply(project: String, instance: String, maxResults: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${project}/operations").addQueryStringParameters("instance" -> instance.toString, "maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.OperationsListResponse]] = (fun: list) => fun.apply()
		}
		class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.Empty])
		}
		object cancel {
			def apply(project: String, operation: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/projects/${project}/operations/${operation}/cancel").addQueryStringParameters())
			given Conversion[cancel, Future[Schema.Empty]] = (fun: cancel) => fun.apply()
		}
	}
	object tiers {
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.TiersListResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.TiersListResponse])
		}
		object list {
			def apply(project: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${project}/tiers").addQueryStringParameters())
			given Conversion[list, Future[Schema.TiersListResponse]] = (fun: list) => fun.apply()
		}
	}
	object users {
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withUser(body: Schema.User) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object insert {
			def apply(project: String, instance: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/users").addQueryStringParameters())
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
		}
		object delete {
			def apply(project: String, instance: String, host: String, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/users").addQueryStringParameters("host" -> host.toString, "name" -> name.toString))
			given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.User]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.User])
		}
		object get {
			def apply(project: String, instance: String, name: String, host: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/users/${name}").addQueryStringParameters("host" -> host.toString))
			given Conversion[get, Future[Schema.User]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			/** Optional. Host of the user in the instance. */
			def withHost(host: String) = new update(req.addQueryStringParameters("host" -> host.toString))
			def withUser(body: Schema.User) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Operation])
		}
		object update {
			def apply(project: String, instance: String, name: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/users").addQueryStringParameters("name" -> name.toString))
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.UsersListResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.UsersListResponse])
		}
		object list {
			def apply(project: String, instance: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/users").addQueryStringParameters())
			given Conversion[list, Future[Schema.UsersListResponse]] = (fun: list) => fun.apply()
		}
	}
}
