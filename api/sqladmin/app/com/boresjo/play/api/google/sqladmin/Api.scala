package com.boresjo.play.api.google.sqladmin

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	val scopes = Seq(
		"""https://www.googleapis.com/auth/cloud-platform""" /* See, edit, configure, and delete your Google Cloud data and see the email address for your Google Account. */,
		"""https://www.googleapis.com/auth/sqlservice.admin""" /* Manage your Google SQL Service instances */
	)

	private val BASE_URL = "https://sqladmin.googleapis.com/"

	object instances {
		/** Lists all of the trusted Certificate Authorities (CAs) for the specified instance. There can be up to three CAs listed: the CA that was used to sign the certificate that is currently in use, a CA that has been added but not yet used to sign a certificate, and a CA used to sign a certificate that has previously rotated out. */
		class listServerCas(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.InstancesListServerCasResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sqlservice.admin""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.InstancesListServerCasResponse])
		}
		object listServerCas {
			def apply(project: String, instance: String)(using signer: RequestSigner, ec: ExecutionContext): listServerCas = new listServerCas(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/listServerCas").addQueryStringParameters())
			given Conversion[listServerCas, Future[Schema.InstancesListServerCasResponse]] = (fun: listServerCas) => fun.apply()
		}
		/** Creates a new Cloud SQL instance. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sqlservice.admin""")
			/** Perform the request */
			def withDatabaseInstance(body: Schema.DatabaseInstance) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object insert {
			def apply(project: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"v1/projects/${project}/instances").addQueryStringParameters())
		}
		/** Release a lease for the setup of SQL Server Reporting Services (SSRS). */
		class releaseSsrsLease(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SqlInstancesReleaseSsrsLeaseResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sqlservice.admin""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.SqlInstancesReleaseSsrsLeaseResponse])
		}
		object releaseSsrsLease {
			def apply(project: String, instance: String)(using signer: RequestSigner, ec: ExecutionContext): releaseSsrsLease = new releaseSsrsLease(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/releaseSsrsLease").addQueryStringParameters())
			given Conversion[releaseSsrsLease, Future[Schema.SqlInstancesReleaseSsrsLeaseResponse]] = (fun: releaseSsrsLease) => fun.apply()
		}
		/** Add a new trusted server certificate version for the specified instance using Certificate Authority Service (CAS) server CA. Required to prepare for a certificate rotation. If a server certificate version was previously added but never used in a certificate rotation, this operation replaces that version. There cannot be more than one certificate version waiting to be rotated in. For instances not using CAS server CA, use AddServerCa instead. */
		class addServerCertificate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sqlservice.admin""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object addServerCertificate {
			def apply(project: String, instance: String)(using signer: RequestSigner, ec: ExecutionContext): addServerCertificate = new addServerCertificate(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/addServerCertificate").addQueryStringParameters())
			given Conversion[addServerCertificate, Future[Schema.Operation]] = (fun: addServerCertificate) => fun.apply()
		}
		/** Rotates the server certificate to one signed by the Certificate Authority (CA) version previously added with the addServerCA method. For instances that have enabled Certificate Authority Service (CAS) based server CA, use RotateServerCertificate to rotate the server certificate. */
		class rotateServerCa(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sqlservice.admin""")
			/** Perform the request */
			def withInstancesRotateServerCaRequest(body: Schema.InstancesRotateServerCaRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object rotateServerCa {
			def apply(project: String, instance: String)(using signer: RequestSigner, ec: ExecutionContext): rotateServerCa = new rotateServerCa(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/rotateServerCa").addQueryStringParameters())
		}
		/** Promotes the read replica instance to be an independent Cloud SQL primary instance. Using this operation might cause your instance to restart. */
		class promoteReplica(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sqlservice.admin""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object promoteReplica {
			def apply(project: String, instance: String, failover: Boolean)(using signer: RequestSigner, ec: ExecutionContext): promoteReplica = new promoteReplica(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/promoteReplica").addQueryStringParameters("failover" -> failover.toString))
			given Conversion[promoteReplica, Future[Schema.Operation]] = (fun: promoteReplica) => fun.apply()
		}
		/** Truncate MySQL general and slow query log tables MySQL only. */
		class truncateLog(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sqlservice.admin""")
			/** Perform the request */
			def withInstancesTruncateLogRequest(body: Schema.InstancesTruncateLogRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object truncateLog {
			def apply(project: String, instance: String)(using signer: RequestSigner, ec: ExecutionContext): truncateLog = new truncateLog(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/truncateLog").addQueryStringParameters())
		}
		/** Rotates the server certificate version to one previously added with the addServerCertificate method. For instances not using Certificate Authority Service (CAS) server CA, use RotateServerCa instead. */
		class RotateServerCertificate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sqlservice.admin""")
			/** Perform the request */
			def withInstancesRotateServerCertificateRequest(body: Schema.InstancesRotateServerCertificateRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object RotateServerCertificate {
			def apply(project: String, instance: String)(using signer: RequestSigner, ec: ExecutionContext): RotateServerCertificate = new RotateServerCertificate(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/rotateServerCertificate").addQueryStringParameters())
		}
		/** Exports data from a Cloud SQL instance to a Cloud Storage bucket as a SQL dump or CSV file. */
		class `export`(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withInstancesExportRequest(body: Schema.InstancesExportRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object `export` {
			def apply(project: String, instance: String)(using signer: RequestSigner, ec: ExecutionContext): `export` = new `export`(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/export").addQueryStringParameters())
		}
		/** Creates a Cloud SQL instance as a clone of the source instance. Using this operation might cause your instance to restart. */
		class _clone(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sqlservice.admin""")
			/** Perform the request */
			def withInstancesCloneRequest(body: Schema.InstancesCloneRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object _clone {
			def apply(project: String, instance: String)(using signer: RequestSigner, ec: ExecutionContext): _clone = new _clone(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/clone").addQueryStringParameters())
		}
		/** Deletes a Cloud SQL instance. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sqlservice.admin""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
		}
		object delete {
			def apply(project: String, instance: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}").addQueryStringParameters())
			given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
		}
		/** Retrieves a resource containing information about a Cloud SQL instance. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.DatabaseInstance]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sqlservice.admin""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.DatabaseInstance])
		}
		object get {
			def apply(project: String, instance: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}").addQueryStringParameters())
			given Conversion[get, Future[Schema.DatabaseInstance]] = (fun: get) => fun.apply()
		}
		/** Restores a backup of a Cloud SQL instance. Using this operation might cause your instance to restart. */
		class restoreBackup(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sqlservice.admin""")
			/** Perform the request */
			def withInstancesRestoreBackupRequest(body: Schema.InstancesRestoreBackupRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object restoreBackup {
			def apply(project: String, instance: String)(using signer: RequestSigner, ec: ExecutionContext): restoreBackup = new restoreBackup(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/restoreBackup").addQueryStringParameters())
		}
		/** Acquire a lease for the setup of SQL Server Reporting Services (SSRS). */
		class acquireSsrsLease(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sqlservice.admin""")
			/** Perform the request */
			def withInstancesAcquireSsrsLeaseRequest(body: Schema.InstancesAcquireSsrsLeaseRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SqlInstancesAcquireSsrsLeaseResponse])
		}
		object acquireSsrsLease {
			def apply(project: String, instance: String)(using signer: RequestSigner, ec: ExecutionContext): acquireSsrsLease = new acquireSsrsLease(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/acquireSsrsLease").addQueryStringParameters())
		}
		/** Starts the replication in the read replica instance. */
		class startReplica(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sqlservice.admin""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object startReplica {
			def apply(project: String, instance: String)(using signer: RequestSigner, ec: ExecutionContext): startReplica = new startReplica(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/startReplica").addQueryStringParameters())
			given Conversion[startReplica, Future[Schema.Operation]] = (fun: startReplica) => fun.apply()
		}
		/** Reencrypt CMEK instance with latest key version. */
		class reencrypt(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sqlservice.admin""")
			/** Perform the request */
			def withInstancesReencryptRequest(body: Schema.InstancesReencryptRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object reencrypt {
			def apply(project: String, instance: String)(using signer: RequestSigner, ec: ExecutionContext): reencrypt = new reencrypt(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/reencrypt").addQueryStringParameters())
		}
		/** Imports data into a Cloud SQL instance from a SQL dump or CSV file in Cloud Storage. */
		class `import`(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withInstancesImportRequest(body: Schema.InstancesImportRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object `import` {
			def apply(project: String, instance: String)(using signer: RequestSigner, ec: ExecutionContext): `import` = new `import`(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/import").addQueryStringParameters())
		}
		/** Demotes an existing standalone instance to be a Cloud SQL read replica for an external database server. */
		class demote(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sqlservice.admin""")
			/** Perform the request */
			def withInstancesDemoteRequest(body: Schema.InstancesDemoteRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object demote {
			def apply(project: String, instance: String)(using signer: RequestSigner, ec: ExecutionContext): demote = new demote(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/demote").addQueryStringParameters())
		}
		/** Adds a new trusted Certificate Authority (CA) version for the specified instance. Required to prepare for a certificate rotation. If a CA version was previously added but never used in a certificate rotation, this operation replaces that version. There cannot be more than one CA version waiting to be rotated in. For instances that have enabled Certificate Authority Service (CAS) based server CA, use AddServerCertificate to add a new server certificate. */
		class addServerCa(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sqlservice.admin""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object addServerCa {
			def apply(project: String, instance: String)(using signer: RequestSigner, ec: ExecutionContext): addServerCa = new addServerCa(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/addServerCa").addQueryStringParameters())
			given Conversion[addServerCa, Future[Schema.Operation]] = (fun: addServerCa) => fun.apply()
		}
		/** Demotes the stand-alone instance to be a Cloud SQL read replica for an external database server. */
		class demoteMaster(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sqlservice.admin""")
			/** Perform the request */
			def withInstancesDemoteMasterRequest(body: Schema.InstancesDemoteMasterRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object demoteMaster {
			def apply(project: String, instance: String)(using signer: RequestSigner, ec: ExecutionContext): demoteMaster = new demoteMaster(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/demoteMaster").addQueryStringParameters())
		}
		/** Updates settings of a Cloud SQL instance. Using this operation might cause your instance to restart. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sqlservice.admin""")
			/** Perform the request */
			def withDatabaseInstance(body: Schema.DatabaseInstance) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Operation])
		}
		object update {
			def apply(project: String, instance: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}").addQueryStringParameters())
		}
		/** Restarts a Cloud SQL instance. */
		class restart(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sqlservice.admin""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object restart {
			def apply(project: String, instance: String)(using signer: RequestSigner, ec: ExecutionContext): restart = new restart(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/restart").addQueryStringParameters())
			given Conversion[restart, Future[Schema.Operation]] = (fun: restart) => fun.apply()
		}
		/** Deletes all client certificates and generates a new server SSL certificate for the instance. */
		class resetSslConfig(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sqlservice.admin""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object resetSslConfig {
			def apply(project: String, instance: String)(using signer: RequestSigner, ec: ExecutionContext): resetSslConfig = new resetSslConfig(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/resetSslConfig").addQueryStringParameters())
			given Conversion[resetSslConfig, Future[Schema.Operation]] = (fun: resetSslConfig) => fun.apply()
		}
		/** Partially updates settings of a Cloud SQL instance by merging the request with the current configuration. This method supports patch semantics. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sqlservice.admin""")
			/** Perform the request */
			def withDatabaseInstance(body: Schema.DatabaseInstance) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
		}
		object patch {
			def apply(project: String, instance: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}").addQueryStringParameters())
		}
		/** Switches over from the primary instance to the designated DR replica instance. */
		class switchover(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sqlservice.admin""")
			/** Optional. (MySQL only) Cloud SQL instance operations timeout, which is a sum of all database operations. Default value is 10 minutes and can be modified to a maximum value of 24 hours.<br>Format: google-duration */
			def withDbTimeout(dbTimeout: String) = new switchover(req.addQueryStringParameters("dbTimeout" -> dbTimeout.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object switchover {
			def apply(project: String, instance: String)(using signer: RequestSigner, ec: ExecutionContext): switchover = new switchover(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/switchover").addQueryStringParameters())
			given Conversion[switchover, Future[Schema.Operation]] = (fun: switchover) => fun.apply()
		}
		/** Lists all versions of server certificates and certificate authorities (CAs) for the specified instance. There can be up to three sets of certs listed: the certificate that is currently in use, a future that has been added but not yet used to sign a certificate, and a certificate that has been rotated out. For instances not using Certificate Authority Service (CAS) server CA, use ListServerCas instead. */
		class ListServerCertificates(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.InstancesListServerCertificatesResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sqlservice.admin""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.InstancesListServerCertificatesResponse])
		}
		object ListServerCertificates {
			def apply(project: String, instance: String)(using signer: RequestSigner, ec: ExecutionContext): ListServerCertificates = new ListServerCertificates(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/listServerCertificates").addQueryStringParameters())
			given Conversion[ListServerCertificates, Future[Schema.InstancesListServerCertificatesResponse]] = (fun: ListServerCertificates) => fun.apply()
		}
		/** Initiates a manual failover of a high availability (HA) primary instance to a standby instance, which becomes the primary instance. Users are then rerouted to the new primary. For more information, see the [Overview of high availability](https://cloud.google.com/sql/docs/mysql/high-availability) page in the Cloud SQL documentation. If using Legacy HA (MySQL only), this causes the instance to failover to its failover replica instance. */
		class failover(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sqlservice.admin""")
			/** Perform the request */
			def withInstancesFailoverRequest(body: Schema.InstancesFailoverRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object failover {
			def apply(project: String, instance: String)(using signer: RequestSigner, ec: ExecutionContext): failover = new failover(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/failover").addQueryStringParameters())
		}
		/** Lists instances under a given project. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.InstancesListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sqlservice.admin""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.InstancesListResponse])
		}
		object list {
			def apply(project: String, filter: String, maxResults: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${project}/instances").addQueryStringParameters("filter" -> filter.toString, "maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.InstancesListResponse]] = (fun: list) => fun.apply()
		}
		/** Stops the replication in the read replica instance. */
		class stopReplica(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sqlservice.admin""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object stopReplica {
			def apply(project: String, instance: String)(using signer: RequestSigner, ec: ExecutionContext): stopReplica = new stopReplica(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/stopReplica").addQueryStringParameters())
			given Conversion[stopReplica, Future[Schema.Operation]] = (fun: stopReplica) => fun.apply()
		}
	}
	object sslCerts {
		/** Generates a short-lived X509 certificate containing the provided public key and signed by a private key specific to the target instance. Users may use the certificate to authenticate as themselves when connecting to the database. */
		class createEphemeral(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sqlservice.admin""")
			/** Perform the request */
			def withSslCertsCreateEphemeralRequest(body: Schema.SslCertsCreateEphemeralRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SslCert])
		}
		object createEphemeral {
			def apply(project: String, instance: String)(using signer: RequestSigner, ec: ExecutionContext): createEphemeral = new createEphemeral(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/createEphemeral").addQueryStringParameters())
		}
		/** Creates an SSL certificate and returns it along with the private key and server certificate authority. The new certificate will not be usable until the instance is restarted. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sqlservice.admin""")
			/** Perform the request */
			def withSslCertsInsertRequest(body: Schema.SslCertsInsertRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SslCertsInsertResponse])
		}
		object insert {
			def apply(project: String, instance: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/sslCerts").addQueryStringParameters())
		}
		/** Deletes the SSL certificate. For First Generation instances, the certificate remains valid until the instance is restarted. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sqlservice.admin""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
		}
		object delete {
			def apply(project: String, instance: String, sha1Fingerprint: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/sslCerts/${sha1Fingerprint}").addQueryStringParameters())
			given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
		}
		/** Retrieves a particular SSL certificate. Does not include the private key (required for usage). The private key must be saved from the response to initial creation. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SslCert]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sqlservice.admin""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SslCert])
		}
		object get {
			def apply(project: String, instance: String, sha1Fingerprint: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/sslCerts/${sha1Fingerprint}").addQueryStringParameters())
			given Conversion[get, Future[Schema.SslCert]] = (fun: get) => fun.apply()
		}
		/** Lists all of the current SSL certificates for the instance. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SslCertsListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sqlservice.admin""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SslCertsListResponse])
		}
		object list {
			def apply(project: String, instance: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/sslCerts").addQueryStringParameters())
			given Conversion[list, Future[Schema.SslCertsListResponse]] = (fun: list) => fun.apply()
		}
	}
	object projects {
		object instances {
			/** Get Disk Shrink Config for a given instance. */
			class getDiskShrinkConfig(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SqlInstancesGetDiskShrinkConfigResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sqlservice.admin""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SqlInstancesGetDiskShrinkConfigResponse])
			}
			object getDiskShrinkConfig {
				def apply(project: String, instance: String)(using signer: RequestSigner, ec: ExecutionContext): getDiskShrinkConfig = new getDiskShrinkConfig(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/getDiskShrinkConfig").addQueryStringParameters())
				given Conversion[getDiskShrinkConfig, Future[Schema.SqlInstancesGetDiskShrinkConfigResponse]] = (fun: getDiskShrinkConfig) => fun.apply()
			}
			/** Verify External primary instance external sync settings. */
			class verifyExternalSyncSettings(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sqlservice.admin""")
				/** Perform the request */
				def withSqlInstancesVerifyExternalSyncSettingsRequest(body: Schema.SqlInstancesVerifyExternalSyncSettingsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SqlInstancesVerifyExternalSyncSettingsResponse])
			}
			object verifyExternalSyncSettings {
				def apply(project: String, instance: String)(using signer: RequestSigner, ec: ExecutionContext): verifyExternalSyncSettings = new verifyExternalSyncSettings(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/verifyExternalSyncSettings").addQueryStringParameters())
			}
			/** Get Latest Recovery Time for a given instance. */
			class getLatestRecoveryTime(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SqlInstancesGetLatestRecoveryTimeResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sqlservice.admin""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SqlInstancesGetLatestRecoveryTimeResponse])
			}
			object getLatestRecoveryTime {
				def apply(project: String, instance: String)(using signer: RequestSigner, ec: ExecutionContext): getLatestRecoveryTime = new getLatestRecoveryTime(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/getLatestRecoveryTime").addQueryStringParameters())
				given Conversion[getLatestRecoveryTime, Future[Schema.SqlInstancesGetLatestRecoveryTimeResponse]] = (fun: getLatestRecoveryTime) => fun.apply()
			}
			/** Start External primary instance migration. */
			class startExternalSync(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sqlservice.admin""")
				/** Perform the request */
				def withSqlInstancesStartExternalSyncRequest(body: Schema.SqlInstancesStartExternalSyncRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object startExternalSync {
				def apply(project: String, instance: String)(using signer: RequestSigner, ec: ExecutionContext): startExternalSync = new startExternalSync(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/startExternalSync").addQueryStringParameters())
			}
			/** Perform Disk Shrink on primary instance. */
			class performDiskShrink(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sqlservice.admin""")
				/** Perform the request */
				def withPerformDiskShrinkContext(body: Schema.PerformDiskShrinkContext) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object performDiskShrink {
				def apply(project: String, instance: String)(using signer: RequestSigner, ec: ExecutionContext): performDiskShrink = new performDiskShrink(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/performDiskShrink").addQueryStringParameters())
			}
			/** Reset Replica Size to primary instance disk size. */
			class resetReplicaSize(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sqlservice.admin""")
				/** Perform the request */
				def withSqlInstancesResetReplicaSizeRequest(body: Schema.SqlInstancesResetReplicaSizeRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object resetReplicaSize {
				def apply(project: String, instance: String)(using signer: RequestSigner, ec: ExecutionContext): resetReplicaSize = new resetReplicaSize(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/resetReplicaSize").addQueryStringParameters())
			}
			/** Reschedules the maintenance on the given instance. */
			class rescheduleMaintenance(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sqlservice.admin""")
				/** Perform the request */
				def withSqlInstancesRescheduleMaintenanceRequestBody(body: Schema.SqlInstancesRescheduleMaintenanceRequestBody) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object rescheduleMaintenance {
				def apply(project: String, instance: String)(using signer: RequestSigner, ec: ExecutionContext): rescheduleMaintenance = new rescheduleMaintenance(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/rescheduleMaintenance").addQueryStringParameters())
			}
		}
	}
	object backupRuns {
		/** Deletes the backup taken by a backup run. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sqlservice.admin""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
		}
		object delete {
			def apply(project: String, instance: String, id: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/backupRuns/${id}").addQueryStringParameters())
			given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
		}
		/** Retrieves a resource containing information about a backup run. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.BackupRun]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sqlservice.admin""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.BackupRun])
		}
		object get {
			def apply(project: String, instance: String, id: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/backupRuns/${id}").addQueryStringParameters())
			given Conversion[get, Future[Schema.BackupRun]] = (fun: get) => fun.apply()
		}
		/** Creates a new backup run on demand. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sqlservice.admin""")
			/** Perform the request */
			def withBackupRun(body: Schema.BackupRun) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object insert {
			def apply(project: String, instance: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/backupRuns").addQueryStringParameters())
		}
		/** Lists all backup runs associated with the project or a given instance and configuration in the reverse chronological order of the backup initiation time. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.BackupRunsListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sqlservice.admin""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.BackupRunsListResponse])
		}
		object list {
			def apply(project: String, instance: String, maxResults: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/backupRuns").addQueryStringParameters("maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.BackupRunsListResponse]] = (fun: list) => fun.apply()
		}
	}
	object connect {
		/** Retrieves connect settings about a Cloud SQL instance. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ConnectSettings]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sqlservice.admin""")
			/** Optional. Optional snapshot read timestamp to trade freshness for performance.<br>Format: google-datetime */
			def withReadTime(readTime: String) = new get(req.addQueryStringParameters("readTime" -> readTime.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ConnectSettings])
		}
		object get {
			def apply(project: String, instance: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/connectSettings").addQueryStringParameters())
			given Conversion[get, Future[Schema.ConnectSettings]] = (fun: get) => fun.apply()
		}
		/** Generates a short-lived X509 certificate containing the provided public key and signed by a private key specific to the target instance. Users may use the certificate to authenticate as themselves when connecting to the database. */
		class generateEphemeralCert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sqlservice.admin""")
			/** Perform the request */
			def withGenerateEphemeralCertRequest(body: Schema.GenerateEphemeralCertRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GenerateEphemeralCertResponse])
		}
		object generateEphemeralCert {
			def apply(project: String, instance: String)(using signer: RequestSigner, ec: ExecutionContext): generateEphemeralCert = new generateEphemeralCert(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}:generateEphemeralCert").addQueryStringParameters())
		}
	}
	object databases {
		/** Inserts a resource containing information about a database inside a Cloud SQL instance. &#42;&#42;Note:&#42;&#42; You can't modify the default character set and collation. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sqlservice.admin""")
			/** Perform the request */
			def withDatabase(body: Schema.Database) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object insert {
			def apply(project: String, instance: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/databases").addQueryStringParameters())
		}
		/** Deletes a database from a Cloud SQL instance. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sqlservice.admin""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
		}
		object delete {
			def apply(project: String, instance: String, database: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/databases/${database}").addQueryStringParameters())
			given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
		}
		/** Retrieves a resource containing information about a database inside a Cloud SQL instance. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Database]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sqlservice.admin""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Database])
		}
		object get {
			def apply(project: String, instance: String, database: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/databases/${database}").addQueryStringParameters())
			given Conversion[get, Future[Schema.Database]] = (fun: get) => fun.apply()
		}
		/** Updates a resource containing information about a database inside a Cloud SQL instance. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sqlservice.admin""")
			/** Perform the request */
			def withDatabase(body: Schema.Database) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Operation])
		}
		object update {
			def apply(project: String, instance: String, database: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/databases/${database}").addQueryStringParameters())
		}
		/** Partially updates a resource containing information about a database inside a Cloud SQL instance. This method supports patch semantics. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sqlservice.admin""")
			/** Perform the request */
			def withDatabase(body: Schema.Database) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
		}
		object patch {
			def apply(project: String, instance: String, database: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/databases/${database}").addQueryStringParameters())
		}
		/** Lists databases in the specified Cloud SQL instance. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.DatabasesListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sqlservice.admin""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.DatabasesListResponse])
		}
		object list {
			def apply(project: String, instance: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/databases").addQueryStringParameters())
			given Conversion[list, Future[Schema.DatabasesListResponse]] = (fun: list) => fun.apply()
		}
	}
	object flags {
		/** Lists all available database flags for Cloud SQL instances. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.FlagsListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sqlservice.admin""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.FlagsListResponse])
		}
		object list {
			def apply(databaseVersion: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/flags").addQueryStringParameters("databaseVersion" -> databaseVersion.toString))
			given Conversion[list, Future[Schema.FlagsListResponse]] = (fun: list) => fun.apply()
		}
	}
	object operations {
		/** Retrieves an instance operation that has been performed on an instance. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sqlservice.admin""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Operation])
		}
		object get {
			def apply(project: String, operation: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${project}/operations/${operation}").addQueryStringParameters())
			given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
		}
		/** Lists all instance operations that have been performed on the given Cloud SQL instance in the reverse chronological order of the start time. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.OperationsListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sqlservice.admin""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.OperationsListResponse])
		}
		object list {
			def apply(project: String, instance: String, maxResults: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${project}/operations").addQueryStringParameters("instance" -> instance.toString, "maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.OperationsListResponse]] = (fun: list) => fun.apply()
		}
		/** Cancels an instance operation that has been performed on an instance. */
		class cancel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sqlservice.admin""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.Empty])
		}
		object cancel {
			def apply(project: String, operation: String)(using signer: RequestSigner, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/projects/${project}/operations/${operation}/cancel").addQueryStringParameters())
			given Conversion[cancel, Future[Schema.Empty]] = (fun: cancel) => fun.apply()
		}
	}
	object tiers {
		/** Lists all available machine types (tiers) for Cloud SQL, for example, `db-custom-1-3840`. For more information, see https://cloud.google.com/sql/pricing. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.TiersListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sqlservice.admin""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.TiersListResponse])
		}
		object list {
			def apply(project: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${project}/tiers").addQueryStringParameters())
			given Conversion[list, Future[Schema.TiersListResponse]] = (fun: list) => fun.apply()
		}
	}
	object users {
		/** Creates a new user in a Cloud SQL instance. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sqlservice.admin""")
			/** Perform the request */
			def withUser(body: Schema.User) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object insert {
			def apply(project: String, instance: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/users").addQueryStringParameters())
		}
		/** Deletes a user from a Cloud SQL instance. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sqlservice.admin""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
		}
		object delete {
			def apply(project: String, instance: String, host: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/users").addQueryStringParameters("host" -> host.toString, "name" -> name.toString))
			given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
		}
		/** Retrieves a resource containing information about a user. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.User]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sqlservice.admin""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.User])
		}
		object get {
			def apply(project: String, instance: String, name: String, host: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/users/${name}").addQueryStringParameters("host" -> host.toString))
			given Conversion[get, Future[Schema.User]] = (fun: get) => fun.apply()
		}
		/** Updates an existing user in a Cloud SQL instance. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sqlservice.admin""")
			/** Optional. Host of the user in the instance. */
			def withHost(host: String) = new update(req.addQueryStringParameters("host" -> host.toString))
			/** Perform the request */
			def withUser(body: Schema.User) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Operation])
		}
		object update {
			def apply(project: String, instance: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/users").addQueryStringParameters("name" -> name.toString))
		}
		/** Lists users in the specified Cloud SQL instance. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.UsersListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sqlservice.admin""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.UsersListResponse])
		}
		object list {
			def apply(project: String, instance: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${project}/instances/${instance}/users").addQueryStringParameters())
			given Conversion[list, Future[Schema.UsersListResponse]] = (fun: list) => fun.apply()
		}
	}
}
