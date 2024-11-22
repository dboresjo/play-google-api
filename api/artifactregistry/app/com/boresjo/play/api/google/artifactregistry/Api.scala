package com.boresjo.play.api.google.artifactregistry

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
		"""https://www.googleapis.com/auth/cloud-platform.read-only""" /* View your data across Google Cloud services and see the email address of your Google Account */
	)

	private val BASE_URL = "https://artifactregistry.googleapis.com/"

	object projects {
		/** Retrieves the Settings for the Project. */
		class getProjectSettings(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ProjectSettings]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ProjectSettings])
		}
		object getProjectSettings {
			def apply(projectsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getProjectSettings = new getProjectSettings(ws.url(BASE_URL + s"v1/projects/${projectsId}/projectSettings").addQueryStringParameters("name" -> name.toString))
			given Conversion[getProjectSettings, Future[Schema.ProjectSettings]] = (fun: getProjectSettings) => fun.apply()
		}
		/** Updates the Settings for the Project. */
		class updateProjectSettings(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withProjectSettings(body: Schema.ProjectSettings) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.ProjectSettings])
		}
		object updateProjectSettings {
			def apply(projectsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): updateProjectSettings = new updateProjectSettings(ws.url(BASE_URL + s"v1/projects/${projectsId}/projectSettings").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
		}
		object locations {
			/** Updates the VPCSC Config for the Project. */
			class updateVpcscConfig(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withVPCSCConfig(body: Schema.VPCSCConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.VPCSCConfig])
			}
			object updateVpcscConfig {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): updateVpcscConfig = new updateVpcscConfig(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vpcscConfig").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			/** Gets information about a location. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Location]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Location])
			}
			object get {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Location]] = (fun: get) => fun.apply()
			}
			/** Retrieves the VPCSC Config for the Project. */
			class getVpcscConfig(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.VPCSCConfig]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.VPCSCConfig])
			}
			object getVpcscConfig {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getVpcscConfig = new getVpcscConfig(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vpcscConfig").addQueryStringParameters("name" -> name.toString))
				given Conversion[getVpcscConfig, Future[Schema.VPCSCConfig]] = (fun: getVpcscConfig) => fun.apply()
			}
			/** Lists information about the supported locations for this service. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListLocationsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListLocationsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListLocationsResponse]] = (fun: list) => fun.apply()
			}
			object operations {
				/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Operation])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
				}
			}
			object repositories {
				/** Tests if the caller has a list of permissions on a resource. */
				class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
					/** Perform the request */
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
				}
				/** Deletes a repository and all of its contents. The returned Operation will finish once the repository has been deleted. It will not have any Operation metadata and will return a google.protobuf.Empty response. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				/** Updates a repository. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withRepository(body: Schema.Repository) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Repository])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Lists repositories. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListRepositoriesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
					/** Optional. An expression for filtering the results of the request. Filter rules are case insensitive. The fields eligible for filtering are: &#42; `name` Examples of using a filter: To filter the results of your request to repositories with the name `my-repo` in project `my-project` in the `us-central` region, append the following filter expression to your request: &#42; `name="projects/my-project/locations/us-central1/repositories/my-repo"` You can also use wildcards to match any number of characters before or after the value: &#42; `name="projects/my-project/locations/us-central1/repositories/my-&#42;"` &#42; `name="projects/my-project/locations/us-central1/repositories/&#42;repo"` &#42; `name="projects/my-project/locations/us-central1/repositories/&#42;repo&#42;"` */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Optional. The field to order the results by. */
					def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListRepositoriesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListRepositoriesResponse]] = (fun: list) => fun.apply()
				}
				/** Creates a repository. The returned Operation will finish once the repository has been created. Its response will be the created Repository. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withRepository(body: Schema.Repository) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, repositoryId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories").addQueryStringParameters("parent" -> parent.toString, "repositoryId" -> repositoryId.toString))
				}
				/** Updates the IAM policy for a given resource. */
				class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				/** Gets the IAM policy for a given resource. */
				class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
					/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
					def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
				}
				/** Gets a repository. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Repository]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Repository])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Repository]] = (fun: get) => fun.apply()
				}
				object aptArtifacts {
					/** Imports Apt artifacts. The returned Operation will complete once the resources are imported. Package, Version, and File resources are created based on the imported artifacts. Imported artifacts that conflict with existing resources are ignored. */
					class `import`(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withImportAptArtifactsRequest(body: Schema.ImportAptArtifactsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object `import` {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): `import` = new `import`(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/aptArtifacts:import").addQueryStringParameters("parent" -> parent.toString))
					}
					/** Directly uploads an Apt artifact. The returned Operation will complete once the resources are uploaded. Package, Version, and File resources are created based on the imported artifact. Imported artifacts that conflict with existing resources are ignored. */
					class upload(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withUploadAptArtifactRequest(body: Schema.UploadAptArtifactRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.UploadAptArtifactMediaResponse])
					}
					object upload {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): upload = new upload(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/aptArtifacts:create").addQueryStringParameters("parent" -> parent.toString))
					}
				}
				object mavenArtifacts {
					/** Lists maven artifacts. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListMavenArtifactsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListMavenArtifactsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/mavenArtifacts").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListMavenArtifactsResponse]] = (fun: list) => fun.apply()
					}
					/** Gets a maven artifact. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.MavenArtifact]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.MavenArtifact])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, mavenArtifactsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/mavenArtifacts/${mavenArtifactsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.MavenArtifact]] = (fun: get) => fun.apply()
					}
				}
				object kfpArtifacts {
					/** Directly uploads a KFP artifact. The returned Operation will complete once the resource is uploaded. Package, Version, and File resources will be created based on the uploaded artifact. Uploaded artifacts that conflict with existing resources will be overwritten. */
					class upload(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withUploadKfpArtifactRequest(body: Schema.UploadKfpArtifactRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.UploadKfpArtifactMediaResponse])
					}
					object upload {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): upload = new upload(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/kfpArtifacts:create").addQueryStringParameters("parent" -> parent.toString))
					}
				}
				object npmPackages {
					/** Lists npm packages. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListNpmPackagesResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListNpmPackagesResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/npmPackages").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListNpmPackagesResponse]] = (fun: list) => fun.apply()
					}
					/** Gets a npm package. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.NpmPackage]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.NpmPackage])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, npmPackagesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/npmPackages/${npmPackagesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.NpmPackage]] = (fun: get) => fun.apply()
					}
				}
				object files {
					/** Gets a file. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleDevtoolsArtifactregistryV1File]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleDevtoolsArtifactregistryV1File])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, filesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/files/${filesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleDevtoolsArtifactregistryV1File]] = (fun: get) => fun.apply()
					}
					/** Updates a file. */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleDevtoolsArtifactregistryV1File(body: Schema.GoogleDevtoolsArtifactregistryV1File) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleDevtoolsArtifactregistryV1File])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, filesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/files/${filesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					/** Download a file. */
					class download(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.DownloadFileResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.DownloadFileResponse])
					}
					object download {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, filesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): download = new download(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/files/${filesId}:download").addQueryStringParameters("name" -> name.toString))
						given Conversion[download, Future[Schema.DownloadFileResponse]] = (fun: download) => fun.apply()
					}
					/** Lists files. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListFilesResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListFilesResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, parent: String, filter: String, pageSize: Int, pageToken: String, orderBy: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/files").addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString))
						given Conversion[list, Future[Schema.ListFilesResponse]] = (fun: list) => fun.apply()
					}
					/** Directly uploads a file to a repository. The returned Operation will complete once the resources are uploaded. */
					class upload(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withUploadFileRequest(body: Schema.UploadFileRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.UploadFileMediaResponse])
					}
					object upload {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): upload = new upload(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/files:upload").addQueryStringParameters("parent" -> parent.toString))
					}
					/** Deletes a file and all of its content. It is only allowed on generic repositories. The returned operation will complete once the file has been deleted. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, filesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/files/${filesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
					}
				}
				object dockerImages {
					/** Lists docker images. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListDockerImagesResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListDockerImagesResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, parent: String, pageSize: Int, pageToken: String, orderBy: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/dockerImages").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString))
						given Conversion[list, Future[Schema.ListDockerImagesResponse]] = (fun: list) => fun.apply()
					}
					/** Gets a docker image. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.DockerImage]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.DockerImage])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, dockerImagesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/dockerImages/${dockerImagesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.DockerImage]] = (fun: get) => fun.apply()
					}
				}
				object packages {
					/** Deletes a package and all of its versions and tags. The returned operation will complete once the package has been deleted. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, packagesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/packages/${packagesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
					}
					/** Gets a package. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Package]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Package])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, packagesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/packages/${packagesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Package]] = (fun: get) => fun.apply()
					}
					/** Updates a package. */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withPackage(body: Schema.Package) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Package])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, packagesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/packages/${packagesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					/** Lists packages. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListPackagesResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
						/** Optional. An expression for filtering the results of the request. Filter rules are case insensitive. The fields eligible for filtering are: &#42; `name` &#42; `annotations` Examples of using a filter: To filter the results of your request to packages with the name `my-package` in project `my-project` in the `us-central` region, in repository `my-repo`, append the following filter expression to your request: &#42; `name="projects/my-project/locations/us-central1/repositories/my-repo/packages/my-package"` You can also use wildcards to match any number of characters before or after the value: &#42; `name="projects/my-project/locations/us-central1/repositories/my-repo/packages/my-&#42;"` &#42; `name="projects/my-project/locations/us-central1/repositories/my-repo/packages/&#42;package"` &#42; `name="projects/my-project/locations/us-central1/repositories/my-repo/packages/&#42;pack&#42;"` To filter the results of your request to packages with the annotation key-value pair [`external_link`: `external_link_value`], append the following filter expression to your request": &#42; `"annotations.external_link:external_link_value"` To filter the results just for a specific annotation key `external_link`, append the following filter expression to your request: &#42; `"annotations.external_link"` If the annotation key or value contains special characters, you can escape them by surrounding the value with backticks. For example, to filter the results of your request to packages with the annotation key-value pair [`external.link`:`https://example.com/my-package`], append the following filter expression to your request: &#42; `` "annotations.`external.link`:`https://example.com/my-package`" `` You can also filter with annotations with a wildcard to match any number of characters before or after the value: &#42; `` "annotations.&#42;_link:`&#42;example.com&#42;`" `` */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						/** Optional. The field to order the results by. */
						def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListPackagesResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/packages").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListPackagesResponse]] = (fun: list) => fun.apply()
					}
					object versions {
						/** Deletes multiple versions across a repository. The returned operation will complete once the versions have been deleted. */
						class batchDelete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withBatchDeleteVersionsRequest(body: Schema.BatchDeleteVersionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
						}
						object batchDelete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, packagesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): batchDelete = new batchDelete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/packages/${packagesId}/versions:batchDelete").addQueryStringParameters("parent" -> parent.toString))
						}
						/** Deletes a version and all of its content. The returned operation will complete once the version has been deleted. */
						class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, packagesId :PlayApi, versionsId :PlayApi, name: String, force: Boolean)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/packages/${packagesId}/versions/${versionsId}").addQueryStringParameters("name" -> name.toString, "force" -> force.toString))
							given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
						}
						/** Gets a version */
						class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Version]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Version])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, packagesId :PlayApi, versionsId :PlayApi, name: String, view: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/packages/${packagesId}/versions/${versionsId}").addQueryStringParameters("name" -> name.toString, "view" -> view.toString))
							given Conversion[get, Future[Schema.Version]] = (fun: get) => fun.apply()
						}
						/** Updates a version. */
						class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withVersion(body: Schema.Version) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Version])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, packagesId :PlayApi, versionsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/packages/${packagesId}/versions/${versionsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
						}
						/** Lists versions. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListVersionsResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
							/** Optional. The field to order the results by. */
							def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
							/** Optional. An expression for filtering the results of the request. Filter rules are case insensitive. The fields eligible for filtering are: &#42; `name` &#42; `annotations` Examples of using a filter: To filter the results of your request to versions with the name `my-version` in project `my-project` in the `us-central` region, in repository `my-repo`, append the following filter expression to your request: &#42; `name="projects/my-project/locations/us-central1/repositories/my-repo/packages/my-package/versions/my-version"` You can also use wildcards to match any number of characters before or after the value: &#42; `name="projects/my-project/locations/us-central1/repositories/my-repo/packages/my-package/versions/&#42;version"` &#42; `name="projects/my-project/locations/us-central1/repositories/my-repo/packages/my-package/versions/my&#42;"` &#42; `name="projects/my-project/locations/us-central1/repositories/my-repo/packages/my-package/versions/&#42;version&#42;"` To filter the results of your request to versions with the annotation key-value pair [`external_link`: `external_link_value`], append the following filter expression to your request: &#42; `"annotations.external_link:external_link_value"` To filter just for a specific annotation key `external_link`, append the following filter expression to your request: &#42; `"annotations.external_link"` If the annotation key or value contains special characters, you can escape them by surrounding the value with backticks. For example, to filter the results of your request to versions with the annotation key-value pair [`external.link`:`https://example.com/my-version`], append the following filter expression to your request: &#42; `` "annotations.`external.link`:`https://example.com/my-version`" `` You can also filter with annotations with a wildcard to match any number of characters before or after the value: &#42; `` "annotations.&#42;_link:`&#42;example.com&#42;`" `` */
							def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListVersionsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, packagesId :PlayApi, parent: String, pageSize: Int, pageToken: String, view: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/packages/${packagesId}/versions").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "view" -> view.toString))
							given Conversion[list, Future[Schema.ListVersionsResponse]] = (fun: list) => fun.apply()
						}
					}
					object tags {
						/** Creates a tag. */
						class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withTag(body: Schema.Tag) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Tag])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, packagesId :PlayApi, parent: String, tagId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/packages/${packagesId}/tags").addQueryStringParameters("parent" -> parent.toString, "tagId" -> tagId.toString))
						}
						/** Deletes a tag. */
						class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, packagesId :PlayApi, tagsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/packages/${packagesId}/tags/${tagsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
						}
						/** Gets a tag. */
						class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Tag]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Tag])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, packagesId :PlayApi, tagsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/packages/${packagesId}/tags/${tagsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.Tag]] = (fun: get) => fun.apply()
						}
						/** Updates a tag. */
						class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withTag(body: Schema.Tag) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Tag])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, packagesId :PlayApi, tagsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/packages/${packagesId}/tags/${tagsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
						}
						/** Lists tags. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListTagsResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListTagsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, packagesId :PlayApi, parent: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/packages/${packagesId}/tags").addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
							given Conversion[list, Future[Schema.ListTagsResponse]] = (fun: list) => fun.apply()
						}
					}
				}
				object goModules {
					/** Directly uploads a Go module. The returned Operation will complete once the Go module is uploaded. Package, Version, and File resources are created based on the uploaded Go module. */
					class upload(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withUploadGoModuleRequest(body: Schema.UploadGoModuleRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.UploadGoModuleMediaResponse])
					}
					object upload {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): upload = new upload(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/goModules:create").addQueryStringParameters("parent" -> parent.toString))
					}
				}
				object yumArtifacts {
					/** Imports Yum (RPM) artifacts. The returned Operation will complete once the resources are imported. Package, Version, and File resources are created based on the imported artifacts. Imported artifacts that conflict with existing resources are ignored. */
					class `import`(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withImportYumArtifactsRequest(body: Schema.ImportYumArtifactsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object `import` {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): `import` = new `import`(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/yumArtifacts:import").addQueryStringParameters("parent" -> parent.toString))
					}
					/** Directly uploads a Yum artifact. The returned Operation will complete once the resources are uploaded. Package, Version, and File resources are created based on the imported artifact. Imported artifacts that conflict with existing resources are ignored. */
					class upload(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withUploadYumArtifactRequest(body: Schema.UploadYumArtifactRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.UploadYumArtifactMediaResponse])
					}
					object upload {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): upload = new upload(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/yumArtifacts:create").addQueryStringParameters("parent" -> parent.toString))
					}
				}
				object googetArtifacts {
					/** Imports GooGet artifacts. The returned Operation will complete once the resources are imported. Package, Version, and File resources are created based on the imported artifacts. Imported artifacts that conflict with existing resources are ignored. */
					class `import`(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withImportGoogetArtifactsRequest(body: Schema.ImportGoogetArtifactsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object `import` {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): `import` = new `import`(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/googetArtifacts:import").addQueryStringParameters("parent" -> parent.toString))
					}
					/** Directly uploads a GooGet artifact. The returned Operation will complete once the resources are uploaded. Package, Version, and File resources are created based on the imported artifact. Imported artifacts that conflict with existing resources are ignored. */
					class upload(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withUploadGoogetArtifactRequest(body: Schema.UploadGoogetArtifactRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.UploadGoogetArtifactMediaResponse])
					}
					object upload {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): upload = new upload(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/googetArtifacts:create").addQueryStringParameters("parent" -> parent.toString))
					}
				}
				object genericArtifacts {
					/** Directly uploads a Generic artifact. The returned operation will complete once the resources are uploaded. Package, version, and file resources are created based on the uploaded artifact. Uploaded artifacts that conflict with existing resources will raise an `ALREADY_EXISTS` error. */
					class upload(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withUploadGenericArtifactRequest(body: Schema.UploadGenericArtifactRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.UploadGenericArtifactMediaResponse])
					}
					object upload {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): upload = new upload(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/genericArtifacts:create").addQueryStringParameters("parent" -> parent.toString))
					}
				}
				object rules {
					/** Creates a rule. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleDevtoolsArtifactregistryV1Rule(body: Schema.GoogleDevtoolsArtifactregistryV1Rule) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleDevtoolsArtifactregistryV1Rule])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, parent: String, ruleId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/rules").addQueryStringParameters("parent" -> parent.toString, "ruleId" -> ruleId.toString))
					}
					/** Deletes a rule. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, rulesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/rules/${rulesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					/** Gets a rule. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleDevtoolsArtifactregistryV1Rule]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleDevtoolsArtifactregistryV1Rule])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, rulesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/rules/${rulesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleDevtoolsArtifactregistryV1Rule]] = (fun: get) => fun.apply()
					}
					/** Updates a rule. */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleDevtoolsArtifactregistryV1Rule(body: Schema.GoogleDevtoolsArtifactregistryV1Rule) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleDevtoolsArtifactregistryV1Rule])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, rulesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/rules/${rulesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					/** Lists rules. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListRulesResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListRulesResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/rules").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListRulesResponse]] = (fun: list) => fun.apply()
					}
				}
				object pythonPackages {
					/** Lists python packages. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListPythonPackagesResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListPythonPackagesResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/pythonPackages").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListPythonPackagesResponse]] = (fun: list) => fun.apply()
					}
					/** Gets a python package. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.PythonPackage]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.PythonPackage])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, pythonPackagesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/pythonPackages/${pythonPackagesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.PythonPackage]] = (fun: get) => fun.apply()
					}
				}
				object attachments {
					/** Lists attachments. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListAttachmentsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
						/** Optional. An expression for filtering the results of the request. Filter rules are case insensitive. The fields eligible for filtering are: &#42; `target` &#42; `type` &#42; `attachment_namespace` */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListAttachmentsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/attachments").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListAttachmentsResponse]] = (fun: list) => fun.apply()
					}
					/** Gets an attachment. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Attachment]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Attachment])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, attachmentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/attachments/${attachmentsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Attachment]] = (fun: get) => fun.apply()
					}
					/** Creates an attachment. The returned Operation will finish once the attachment has been created. Its response will be the created attachment. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withAttachment(body: Schema.Attachment) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, parent: String, attachmentId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/attachments").addQueryStringParameters("parent" -> parent.toString, "attachmentId" -> attachmentId.toString))
					}
					/** Deletes an attachment. The returned Operation will finish once the attachments has been deleted. It will not have any Operation metadata and will return a `google.protobuf.Empty` response. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, attachmentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/attachments/${attachmentsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
					}
				}
			}
		}
	}
}
