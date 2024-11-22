package com.boresjo.play.api.google.artifactregistry

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://artifactregistry.googleapis.com/"

	object projects {
		class getProjectSettings(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ProjectSettings]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ProjectSettings])
		}
		object getProjectSettings {
			def apply(projectsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getProjectSettings = new getProjectSettings(ws.url(BASE_URL + s"v1/projects/${projectsId}/projectSettings").addQueryStringParameters("name" -> name.toString))
			given Conversion[getProjectSettings, Future[Schema.ProjectSettings]] = (fun: getProjectSettings) => fun.apply()
		}
		class updateProjectSettings(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withProjectSettings(body: Schema.ProjectSettings) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.ProjectSettings])
		}
		object updateProjectSettings {
			def apply(projectsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): updateProjectSettings = new updateProjectSettings(ws.url(BASE_URL + s"v1/projects/${projectsId}/projectSettings").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
		}
		object locations {
			class updateVpcscConfig(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withVPCSCConfig(body: Schema.VPCSCConfig) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.VPCSCConfig])
			}
			object updateVpcscConfig {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): updateVpcscConfig = new updateVpcscConfig(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vpcscConfig").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Location]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Location])
			}
			object get {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Location]] = (fun: get) => fun.apply()
			}
			class getVpcscConfig(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.VPCSCConfig]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.VPCSCConfig])
			}
			object getVpcscConfig {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getVpcscConfig = new getVpcscConfig(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vpcscConfig").addQueryStringParameters("name" -> name.toString))
				given Conversion[getVpcscConfig, Future[Schema.VPCSCConfig]] = (fun: getVpcscConfig) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListLocationsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListLocationsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListLocationsResponse]] = (fun: list) => fun.apply()
			}
			object operations {
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Operation])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
				}
			}
			object repositories {
				class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withRepository(body: Schema.Repository) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Repository])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListRepositoriesResponse]) {
					/** Optional. An expression for filtering the results of the request. Filter rules are case insensitive. The fields eligible for filtering are: &#42; `name` Examples of using a filter: To filter the results of your request to repositories with the name `my-repo` in project `my-project` in the `us-central` region, append the following filter expression to your request: &#42; `name="projects/my-project/locations/us-central1/repositories/my-repo"` You can also use wildcards to match any number of characters before or after the value: &#42; `name="projects/my-project/locations/us-central1/repositories/my-&#42;"` &#42; `name="projects/my-project/locations/us-central1/repositories/&#42;repo"` &#42; `name="projects/my-project/locations/us-central1/repositories/&#42;repo&#42;"` */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Optional. The field to order the results by. */
					def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListRepositoriesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListRepositoriesResponse]] = (fun: list) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withRepository(body: Schema.Repository) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, repositoryId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories").addQueryStringParameters("parent" -> parent.toString, "repositoryId" -> repositoryId.toString))
				}
				class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
					/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
					def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Repository]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Repository])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Repository]] = (fun: get) => fun.apply()
				}
				object aptArtifacts {
					class `import`(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withImportAptArtifactsRequest(body: Schema.ImportAptArtifactsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object `import` {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): `import` = new `import`(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/aptArtifacts:import").addQueryStringParameters("parent" -> parent.toString))
					}
					class upload(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withUploadAptArtifactRequest(body: Schema.UploadAptArtifactRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.UploadAptArtifactMediaResponse])
					}
					object upload {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): upload = new upload(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/aptArtifacts:create").addQueryStringParameters("parent" -> parent.toString))
					}
				}
				object mavenArtifacts {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListMavenArtifactsResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListMavenArtifactsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/mavenArtifacts").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListMavenArtifactsResponse]] = (fun: list) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.MavenArtifact]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.MavenArtifact])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, mavenArtifactsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/mavenArtifacts/${mavenArtifactsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.MavenArtifact]] = (fun: get) => fun.apply()
					}
				}
				object kfpArtifacts {
					class upload(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withUploadKfpArtifactRequest(body: Schema.UploadKfpArtifactRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.UploadKfpArtifactMediaResponse])
					}
					object upload {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): upload = new upload(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/kfpArtifacts:create").addQueryStringParameters("parent" -> parent.toString))
					}
				}
				object npmPackages {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListNpmPackagesResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListNpmPackagesResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/npmPackages").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListNpmPackagesResponse]] = (fun: list) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.NpmPackage]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.NpmPackage])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, npmPackagesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/npmPackages/${npmPackagesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.NpmPackage]] = (fun: get) => fun.apply()
					}
				}
				object files {
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleDevtoolsArtifactregistryV1File]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleDevtoolsArtifactregistryV1File])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, filesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/files/${filesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleDevtoolsArtifactregistryV1File]] = (fun: get) => fun.apply()
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleDevtoolsArtifactregistryV1File(body: Schema.GoogleDevtoolsArtifactregistryV1File) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleDevtoolsArtifactregistryV1File])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, filesId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/files/${filesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					class download(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.DownloadFileResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.DownloadFileResponse])
					}
					object download {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, filesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): download = new download(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/files/${filesId}:download").addQueryStringParameters("name" -> name.toString))
						given Conversion[download, Future[Schema.DownloadFileResponse]] = (fun: download) => fun.apply()
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListFilesResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListFilesResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, parent: String, filter: String, pageSize: Int, pageToken: String, orderBy: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/files").addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString))
						given Conversion[list, Future[Schema.ListFilesResponse]] = (fun: list) => fun.apply()
					}
					class upload(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withUploadFileRequest(body: Schema.UploadFileRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.UploadFileMediaResponse])
					}
					object upload {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): upload = new upload(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/files:upload").addQueryStringParameters("parent" -> parent.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, filesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/files/${filesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
					}
				}
				object dockerImages {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListDockerImagesResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListDockerImagesResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, parent: String, pageSize: Int, pageToken: String, orderBy: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/dockerImages").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString))
						given Conversion[list, Future[Schema.ListDockerImagesResponse]] = (fun: list) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.DockerImage]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.DockerImage])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, dockerImagesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/dockerImages/${dockerImagesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.DockerImage]] = (fun: get) => fun.apply()
					}
				}
				object packages {
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, packagesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/packages/${packagesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Package]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Package])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, packagesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/packages/${packagesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Package]] = (fun: get) => fun.apply()
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withPackage(body: Schema.Package) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Package])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, packagesId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/packages/${packagesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListPackagesResponse]) {
						/** Optional. An expression for filtering the results of the request. Filter rules are case insensitive. The fields eligible for filtering are: &#42; `name` &#42; `annotations` Examples of using a filter: To filter the results of your request to packages with the name `my-package` in project `my-project` in the `us-central` region, in repository `my-repo`, append the following filter expression to your request: &#42; `name="projects/my-project/locations/us-central1/repositories/my-repo/packages/my-package"` You can also use wildcards to match any number of characters before or after the value: &#42; `name="projects/my-project/locations/us-central1/repositories/my-repo/packages/my-&#42;"` &#42; `name="projects/my-project/locations/us-central1/repositories/my-repo/packages/&#42;package"` &#42; `name="projects/my-project/locations/us-central1/repositories/my-repo/packages/&#42;pack&#42;"` To filter the results of your request to packages with the annotation key-value pair [`external_link`: `external_link_value`], append the following filter expression to your request": &#42; `"annotations.external_link:external_link_value"` To filter the results just for a specific annotation key `external_link`, append the following filter expression to your request: &#42; `"annotations.external_link"` If the annotation key or value contains special characters, you can escape them by surrounding the value with backticks. For example, to filter the results of your request to packages with the annotation key-value pair [`external.link`:`https://example.com/my-package`], append the following filter expression to your request: &#42; `` "annotations.`external.link`:`https://example.com/my-package`" `` You can also filter with annotations with a wildcard to match any number of characters before or after the value: &#42; `` "annotations.&#42;_link:`&#42;example.com&#42;`" `` */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						/** Optional. The field to order the results by. */
						def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListPackagesResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/packages").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListPackagesResponse]] = (fun: list) => fun.apply()
					}
					object versions {
						class batchDelete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withBatchDeleteVersionsRequest(body: Schema.BatchDeleteVersionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
						}
						object batchDelete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, packagesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): batchDelete = new batchDelete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/packages/${packagesId}/versions:batchDelete").addQueryStringParameters("parent" -> parent.toString))
						}
						class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
							def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, packagesId :PlayApi, versionsId :PlayApi, name: String, force: Boolean)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/packages/${packagesId}/versions/${versionsId}").addQueryStringParameters("name" -> name.toString, "force" -> force.toString))
							given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
						}
						class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Version]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Version])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, packagesId :PlayApi, versionsId :PlayApi, name: String, view: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/packages/${packagesId}/versions/${versionsId}").addQueryStringParameters("name" -> name.toString, "view" -> view.toString))
							given Conversion[get, Future[Schema.Version]] = (fun: get) => fun.apply()
						}
						class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withVersion(body: Schema.Version) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Version])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, packagesId :PlayApi, versionsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/packages/${packagesId}/versions/${versionsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
						}
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListVersionsResponse]) {
							/** Optional. The field to order the results by. */
							def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
							/** Optional. An expression for filtering the results of the request. Filter rules are case insensitive. The fields eligible for filtering are: &#42; `name` &#42; `annotations` Examples of using a filter: To filter the results of your request to versions with the name `my-version` in project `my-project` in the `us-central` region, in repository `my-repo`, append the following filter expression to your request: &#42; `name="projects/my-project/locations/us-central1/repositories/my-repo/packages/my-package/versions/my-version"` You can also use wildcards to match any number of characters before or after the value: &#42; `name="projects/my-project/locations/us-central1/repositories/my-repo/packages/my-package/versions/&#42;version"` &#42; `name="projects/my-project/locations/us-central1/repositories/my-repo/packages/my-package/versions/my&#42;"` &#42; `name="projects/my-project/locations/us-central1/repositories/my-repo/packages/my-package/versions/&#42;version&#42;"` To filter the results of your request to versions with the annotation key-value pair [`external_link`: `external_link_value`], append the following filter expression to your request: &#42; `"annotations.external_link:external_link_value"` To filter just for a specific annotation key `external_link`, append the following filter expression to your request: &#42; `"annotations.external_link"` If the annotation key or value contains special characters, you can escape them by surrounding the value with backticks. For example, to filter the results of your request to versions with the annotation key-value pair [`external.link`:`https://example.com/my-version`], append the following filter expression to your request: &#42; `` "annotations.`external.link`:`https://example.com/my-version`" `` You can also filter with annotations with a wildcard to match any number of characters before or after the value: &#42; `` "annotations.&#42;_link:`&#42;example.com&#42;`" `` */
							def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListVersionsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, packagesId :PlayApi, parent: String, pageSize: Int, pageToken: String, view: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/packages/${packagesId}/versions").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "view" -> view.toString))
							given Conversion[list, Future[Schema.ListVersionsResponse]] = (fun: list) => fun.apply()
						}
					}
					object tags {
						class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withTag(body: Schema.Tag) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Tag])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, packagesId :PlayApi, parent: String, tagId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/packages/${packagesId}/tags").addQueryStringParameters("parent" -> parent.toString, "tagId" -> tagId.toString))
						}
						class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
							def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, packagesId :PlayApi, tagsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/packages/${packagesId}/tags/${tagsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
						}
						class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Tag]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Tag])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, packagesId :PlayApi, tagsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/packages/${packagesId}/tags/${tagsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.Tag]] = (fun: get) => fun.apply()
						}
						class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withTag(body: Schema.Tag) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Tag])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, packagesId :PlayApi, tagsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/packages/${packagesId}/tags/${tagsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
						}
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListTagsResponse]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListTagsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, packagesId :PlayApi, parent: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/packages/${packagesId}/tags").addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
							given Conversion[list, Future[Schema.ListTagsResponse]] = (fun: list) => fun.apply()
						}
					}
				}
				object goModules {
					class upload(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withUploadGoModuleRequest(body: Schema.UploadGoModuleRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.UploadGoModuleMediaResponse])
					}
					object upload {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): upload = new upload(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/goModules:create").addQueryStringParameters("parent" -> parent.toString))
					}
				}
				object yumArtifacts {
					class `import`(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withImportYumArtifactsRequest(body: Schema.ImportYumArtifactsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object `import` {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): `import` = new `import`(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/yumArtifacts:import").addQueryStringParameters("parent" -> parent.toString))
					}
					class upload(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withUploadYumArtifactRequest(body: Schema.UploadYumArtifactRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.UploadYumArtifactMediaResponse])
					}
					object upload {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): upload = new upload(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/yumArtifacts:create").addQueryStringParameters("parent" -> parent.toString))
					}
				}
				object googetArtifacts {
					class `import`(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withImportGoogetArtifactsRequest(body: Schema.ImportGoogetArtifactsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object `import` {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): `import` = new `import`(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/googetArtifacts:import").addQueryStringParameters("parent" -> parent.toString))
					}
					class upload(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withUploadGoogetArtifactRequest(body: Schema.UploadGoogetArtifactRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.UploadGoogetArtifactMediaResponse])
					}
					object upload {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): upload = new upload(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/googetArtifacts:create").addQueryStringParameters("parent" -> parent.toString))
					}
				}
				object genericArtifacts {
					class upload(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withUploadGenericArtifactRequest(body: Schema.UploadGenericArtifactRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.UploadGenericArtifactMediaResponse])
					}
					object upload {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): upload = new upload(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/genericArtifacts:create").addQueryStringParameters("parent" -> parent.toString))
					}
				}
				object rules {
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleDevtoolsArtifactregistryV1Rule(body: Schema.GoogleDevtoolsArtifactregistryV1Rule) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleDevtoolsArtifactregistryV1Rule])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, parent: String, ruleId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/rules").addQueryStringParameters("parent" -> parent.toString, "ruleId" -> ruleId.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, rulesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/rules/${rulesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleDevtoolsArtifactregistryV1Rule]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleDevtoolsArtifactregistryV1Rule])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, rulesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/rules/${rulesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleDevtoolsArtifactregistryV1Rule]] = (fun: get) => fun.apply()
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleDevtoolsArtifactregistryV1Rule(body: Schema.GoogleDevtoolsArtifactregistryV1Rule) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleDevtoolsArtifactregistryV1Rule])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, rulesId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/rules/${rulesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListRulesResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListRulesResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/rules").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListRulesResponse]] = (fun: list) => fun.apply()
					}
				}
				object pythonPackages {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListPythonPackagesResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListPythonPackagesResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/pythonPackages").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListPythonPackagesResponse]] = (fun: list) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.PythonPackage]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.PythonPackage])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, pythonPackagesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/pythonPackages/${pythonPackagesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.PythonPackage]] = (fun: get) => fun.apply()
					}
				}
				object attachments {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListAttachmentsResponse]) {
						/** Optional. An expression for filtering the results of the request. Filter rules are case insensitive. The fields eligible for filtering are: &#42; `target` &#42; `type` &#42; `attachment_namespace` */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListAttachmentsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/attachments").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListAttachmentsResponse]] = (fun: list) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Attachment]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Attachment])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, attachmentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/attachments/${attachmentsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Attachment]] = (fun: get) => fun.apply()
					}
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withAttachment(body: Schema.Attachment) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, parent: String, attachmentId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/attachments").addQueryStringParameters("parent" -> parent.toString, "attachmentId" -> attachmentId.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, attachmentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/attachments/${attachmentsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
					}
				}
			}
		}
	}
}
