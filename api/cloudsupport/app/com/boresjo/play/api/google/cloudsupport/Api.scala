package com.boresjo.play.api.google.cloudsupport

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
		"""https://www.googleapis.com/auth/cloud-platform""" /* See, edit, configure, and delete your Google Cloud data and see the email address for your Google Account. */
	)

	private val BASE_URL = "https://cloudsupport.googleapis.com/"

	object cases {
		/** Create a new case and associate it with a parent. It must have the following fields set: `display_name`, `description`, `classification`, and `priority`. If you're just testing the API and don't want to route your case to an agent, set `testCase=true`. EXAMPLES: cURL: ```shell parent="projects/some-project" curl \ --request POST \ --header "Authorization: Bearer $(gcloud auth print-access-token)" \ --header 'Content-Type: application/json' \ --data '{ "display_name": "Test case created by me.", "description": "a random test case, feel free to close", "classification": { "id": "100IK2AKCLHMGRJ9CDGMOCGP8DM6UTB4BT262T31BT1M2T31DHNMENPO6KS36CPJ786L2TBFEHGN6NPI64R3CDHN8880G08I1H3MURR7DHII0GRCDTQM8" }, "time_zone": "-07:00", "subscriber_email_addresses": [ "foo@domain.com", "bar@domain.com" ], "testCase": true, "priority": "P3" }' \ "https://cloudsupport.googleapis.com/v2/$parent/cases" ``` Python: ```python import googleapiclient.discovery api_version = "v2" supportApiService = googleapiclient.discovery.build( serviceName="cloudsupport", version=api_version, discoveryServiceUrl=f"https://cloudsupport.googleapis.com/$discovery/rest?version={api_version}", ) request = supportApiService.cases().create( parent="projects/some-project", body={ "displayName": "A Test Case", "description": "This is a test case.", "testCase": True, "priority": "P2", "classification": { "id": "100IK2AKCLHMGRJ9CDGMOCGP8DM6UTB4BT262T31BT1M2T31DHNMENPO6KS36CPJ786L2TBFEHGN6NPI64R3CDHN8880G08I1H3MURR7DHII0GRCDTQM8" }, }, ) print(request.execute()) ``` */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withCase(body: Schema.Case) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Case])
		}
		object create {
			def apply(v2Id :PlayApi, v2Id1 :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/cases").addQueryStringParameters("parent" -> parent.toString))
		}
		/** Escalate a case, starting the Google Cloud Support escalation management process. This operation is only available for some support services. Go to https://cloud.google.com/support and look for 'Technical support escalations' in the feature list to find out which ones let you do that. EXAMPLES: cURL: ```shell case="projects/some-project/cases/43595344" curl \ --request POST \ --header "Authorization: Bearer $(gcloud auth print-access-token)" \ --header "Content-Type: application/json" \ --data '{ "escalation": { "reason": "BUSINESS_IMPACT", "justification": "This is a test escalation." } }' \ "https://cloudsupport.googleapis.com/v2/$case:escalate" ``` Python: ```python import googleapiclient.discovery api_version = "v2" supportApiService = googleapiclient.discovery.build( serviceName="cloudsupport", version=api_version, discoveryServiceUrl=f"https://cloudsupport.googleapis.com/$discovery/rest?version={api_version}", ) request = supportApiService.cases().escalate( name="projects/some-project/cases/43595344", body={ "escalation": { "reason": "BUSINESS_IMPACT", "justification": "This is a test escalation.", }, }, ) print(request.execute()) ``` */
		class escalate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withEscalateCaseRequest(body: Schema.EscalateCaseRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Case])
		}
		object escalate {
			def apply(v2Id :PlayApi, v2Id1 :PlayApi, casesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): escalate = new escalate(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/cases/${casesId}:escalate").addQueryStringParameters("name" -> name.toString))
		}
		/** Retrieve a case. EXAMPLES: cURL: ```shell case="projects/some-project/cases/16033687" curl \ --header "Authorization: Bearer $(gcloud auth print-access-token)" \ "https://cloudsupport.googleapis.com/v2/$case" ``` Python: ```python import googleapiclient.discovery api_version = "v2" supportApiService = googleapiclient.discovery.build( serviceName="cloudsupport", version=api_version, discoveryServiceUrl=f"https://cloudsupport.googleapis.com/$discovery/rest?version={api_version}", ) request = supportApiService.cases().get( name="projects/some-project/cases/43595344", ) print(request.execute()) ``` */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Case]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Case])
		}
		object get {
			def apply(v2Id :PlayApi, v2Id1 :PlayApi, casesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/cases/${casesId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.Case]] = (fun: get) => fun.apply()
		}
		/** Update a case. Only some fields can be updated. EXAMPLES: cURL: ```shell case="projects/some-project/cases/43595344" curl \ --request PATCH \ --header "Authorization: Bearer $(gcloud auth print-access-token)" \ --header "Content-Type: application/json" \ --data '{ "priority": "P1" }' \ "https://cloudsupport.googleapis.com/v2/$case?updateMask=priority" ``` Python: ```python import googleapiclient.discovery api_version = "v2" supportApiService = googleapiclient.discovery.build( serviceName="cloudsupport", version=api_version, discoveryServiceUrl=f"https://cloudsupport.googleapis.com/$discovery/rest?version={api_version}", ) request = supportApiService.cases().patch( name="projects/some-project/cases/43112854", body={ "displayName": "This is Now a New Title", "priority": "P2", }, ) print(request.execute()) ``` */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withCase(body: Schema.Case) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Case])
		}
		object patch {
			def apply(v2Id :PlayApi, v2Id1 :PlayApi, casesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/cases/${casesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
		}
		/** Close a case. EXAMPLES: cURL: ```shell case="projects/some-project/cases/43595344" curl \ --request POST \ --header "Authorization: Bearer $(gcloud auth print-access-token)" \ "https://cloudsupport.googleapis.com/v2/$case:close" ``` Python: ```python import googleapiclient.discovery api_version = "v2" supportApiService = googleapiclient.discovery.build( serviceName="cloudsupport", version=api_version, discoveryServiceUrl=f"https://cloudsupport.googleapis.com/$discovery/rest?version={api_version}", ) request = supportApiService.cases().close( name="projects/some-project/cases/43595344" ) print(request.execute()) ``` */
		class close(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withCloseCaseRequest(body: Schema.CloseCaseRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Case])
		}
		object close {
			def apply(v2Id :PlayApi, v2Id1 :PlayApi, casesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): close = new close(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/cases/${casesId}:close").addQueryStringParameters("name" -> name.toString))
		}
		/** Retrieve all cases under a parent, but not its children. For example, listing cases under an organization only returns the cases that are directly parented by that organization. To retrieve cases under an organization and its projects, use `cases.search`. EXAMPLES: cURL: ```shell parent="projects/some-project" curl \ --header "Authorization: Bearer $(gcloud auth print-access-token)" \ "https://cloudsupport.googleapis.com/v2/$parent/cases" ``` Python: ```python import googleapiclient.discovery api_version = "v2" supportApiService = googleapiclient.discovery.build( serviceName="cloudsupport", version=api_version, discoveryServiceUrl=f"https://cloudsupport.googleapis.com/$discovery/rest?version={api_version}", ) request = supportApiService.cases().list(parent="projects/some-project") print(request.execute()) ``` */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListCasesResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListCasesResponse])
		}
		object list {
			def apply(v2Id :PlayApi, v2Id1 :PlayApi, pageSize: Int, parent: String, filter: String, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/cases").addQueryStringParameters("pageSize" -> pageSize.toString, "parent" -> parent.toString, "filter" -> filter.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListCasesResponse]] = (fun: list) => fun.apply()
		}
		/** Search for cases using a query. EXAMPLES: cURL: ```shell parent="projects/some-project" curl \ --header "Authorization: Bearer $(gcloud auth print-access-token)" \ "https://cloudsupport.googleapis.com/v2/$parent/cases:search" ``` Python: ```python import googleapiclient.discovery api_version = "v2" supportApiService = googleapiclient.discovery.build( serviceName="cloudsupport", version=api_version, discoveryServiceUrl=f"https://cloudsupport.googleapis.com/$discovery/rest?version={api_version}", ) request = supportApiService.cases().search( parent="projects/some-project", query="state=OPEN" ) print(request.execute()) ``` */
		class search(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SearchCasesResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SearchCasesResponse])
		}
		object search {
			def apply(v2Id :PlayApi, v2Id1 :PlayApi, parent: String, query: String, pageToken: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): search = new search(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/cases:search").addQueryStringParameters("parent" -> parent.toString, "query" -> query.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
			given Conversion[search, Future[Schema.SearchCasesResponse]] = (fun: search) => fun.apply()
		}
		object attachments {
			/** List all the attachments associated with a support case. EXAMPLES: cURL: ```shell case="projects/some-project/cases/23598314" curl \ --header "Authorization: Bearer $(gcloud auth print-access-token)" \ "https://cloudsupport.googleapis.com/v2/$case/attachments" ``` Python: ```python import googleapiclient.discovery api_version = "v2" supportApiService = googleapiclient.discovery.build( serviceName="cloudsupport", version=api_version, discoveryServiceUrl=f"https://cloudsupport.googleapis.com/$discovery/rest?version={api_version}", ) request = ( supportApiService.cases() .attachments() .list(parent="projects/some-project/cases/43595344") ) print(request.execute()) ``` */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListAttachmentsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListAttachmentsResponse])
			}
			object list {
				def apply(v2Id :PlayApi, v2Id1 :PlayApi, casesId :PlayApi, pageToken: String, parent: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/cases/${casesId}/attachments").addQueryStringParameters("pageToken" -> pageToken.toString, "parent" -> parent.toString, "pageSize" -> pageSize.toString))
				given Conversion[list, Future[Schema.ListAttachmentsResponse]] = (fun: list) => fun.apply()
			}
		}
		object comments {
			/** Add a new comment to a case. The comment must have the following fields set: `body`. EXAMPLES: cURL: ```shell case="projects/some-project/cases/43591344" curl \ --request POST \ --header "Authorization: Bearer $(gcloud auth print-access-token)" \ --header 'Content-Type: application/json' \ --data '{ "body": "This is a test comment." }' \ "https://cloudsupport.googleapis.com/v2/$case/comments" ``` Python: ```python import googleapiclient.discovery api_version = "v2" supportApiService = googleapiclient.discovery.build( serviceName="cloudsupport", version=api_version, discoveryServiceUrl=f"https://cloudsupport.googleapis.com/$discovery/rest?version={api_version}", ) request = ( supportApiService.cases() .comments() .create( parent="projects/some-project/cases/43595344", body={"body": "This is a test comment."}, ) ) print(request.execute()) ``` */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withComment(body: Schema.Comment) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Comment])
			}
			object create {
				def apply(v2Id :PlayApi, v2Id1 :PlayApi, casesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/cases/${casesId}/comments").addQueryStringParameters("parent" -> parent.toString))
			}
			/** List all the comments associated with a case. EXAMPLES: cURL: ```shell case="projects/some-project/cases/43595344" curl \ --header "Authorization: Bearer $(gcloud auth print-access-token)" \ "https://cloudsupport.googleapis.com/v2/$case/comments" ``` Python: ```python import googleapiclient.discovery api_version = "v2" supportApiService = googleapiclient.discovery.build( serviceName="cloudsupport", version=api_version, discoveryServiceUrl=f"https://cloudsupport.googleapis.com/$discovery/rest?version={api_version}", ) request = ( supportApiService.cases() .comments() .list(parent="projects/some-project/cases/43595344") ) print(request.execute()) ``` */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListCommentsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListCommentsResponse])
			}
			object list {
				def apply(v2Id :PlayApi, v2Id1 :PlayApi, casesId :PlayApi, pageSize: Int, parent: String, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/cases/${casesId}/comments").addQueryStringParameters("pageSize" -> pageSize.toString, "parent" -> parent.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListCommentsResponse]] = (fun: list) => fun.apply()
			}
		}
	}
	object media {
		/** Download a file attached to a case. Note: HTTP requests must append "?alt=media" to the URL. EXAMPLES: cURL: ```shell name="projects/some-project/cases/43594844/attachments/0674M00000WijAnZAJ" curl \ --header "Authorization: Bearer $(gcloud auth print-access-token)" \ "https://cloudsupport.googleapis.com/v2/$name:download?alt=media" ``` Python: ```python import googleapiclient.discovery api_version = "v2" supportApiService = googleapiclient.discovery.build( serviceName="cloudsupport", version=api_version, discoveryServiceUrl=f"https://cloudsupport.googleapis.com/$discovery/rest?version={api_version}", ) request = supportApiService.media().download( name="projects/some-project/cases/43595344/attachments/0684M00000Pw6pHQAR" ) request.uri = request.uri.split("?")[0] + "?alt=media" print(request.execute()) ``` */
		class download(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Media]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Media])
		}
		object download {
			def apply(v2Id :PlayApi, v2Id1 :PlayApi, casesId :PlayApi, attachmentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): download = new download(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/cases/${casesId}/attachments/${attachmentsId}:download").addQueryStringParameters("name" -> name.toString))
			given Conversion[download, Future[Schema.Media]] = (fun: download) => fun.apply()
		}
		/** Create a file attachment on a case or Cloud resource. The attachment must have the following fields set: `filename`. EXAMPLES: cURL: ```shell echo "This text is in a file I'm uploading using CSAPI." \ > "./example_file.txt" case="projects/some-project/cases/43594844" curl \ --header "Authorization: Bearer $(gcloud auth print-access-token)" \ --data-binary @"./example_file.txt" \ "https://cloudsupport.googleapis.com/upload/v2beta/$case/attachments?attachment.filename=uploaded_via_curl.txt" ``` Python: ```python import googleapiclient.discovery api_version = "v2" supportApiService = googleapiclient.discovery.build( serviceName="cloudsupport", version=api_version, discoveryServiceUrl=f"https://cloudsupport.googleapis.com/$discovery/rest?version={api_version}", ) file_path = "./example_file.txt" with open(file_path, "w") as file: file.write( "This text is inside a file I'm going to upload using the Cloud Support API.", ) request = supportApiService.media().upload( parent="projects/some-project/cases/43595344", media_body=file_path ) request.uri = request.uri.split("?")[0] + "?attachment.filename=uploaded_via_python.txt" print(request.execute()) ``` */
		class upload(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withCreateAttachmentRequest(body: Schema.CreateAttachmentRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Attachment])
		}
		object upload {
			def apply(v2Id :PlayApi, v2Id1 :PlayApi, casesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): upload = new upload(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/cases/${casesId}/attachments").addQueryStringParameters("parent" -> parent.toString))
		}
	}
	object caseClassifications {
		/** Retrieve valid classifications to use when creating a support case. Classifications are hierarchical. Each classification is a string containing all levels of the hierarchy separated by `" > "`. For example, `"Technical Issue > Compute > Compute Engine"`. Classification IDs returned by this endpoint are valid for at least six months. When a classification is deactivated, this endpoint immediately stops returning it. After six months, `case.create` requests using the classification will fail. EXAMPLES: cURL: ```shell curl \ --header "Authorization: Bearer $(gcloud auth print-access-token)" \ 'https://cloudsupport.googleapis.com/v2/caseClassifications:search?query=display_name:"&#42;Compute%20Engine&#42;"' ``` Python: ```python import googleapiclient.discovery supportApiService = googleapiclient.discovery.build( serviceName="cloudsupport", version="v2", discoveryServiceUrl=f"https://cloudsupport.googleapis.com/$discovery/rest?version=v2", ) request = supportApiService.caseClassifications().search( query='display_name:"&#42;Compute Engine&#42;"' ) print(request.execute()) ``` */
		class search(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SearchCaseClassificationsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SearchCaseClassificationsResponse])
		}
		object search {
			def apply(pageToken: String, query: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): search = new search(ws.url(BASE_URL + s"v2/caseClassifications:search").addQueryStringParameters("pageToken" -> pageToken.toString, "query" -> query.toString, "pageSize" -> pageSize.toString))
			given Conversion[search, Future[Schema.SearchCaseClassificationsResponse]] = (fun: search) => fun.apply()
		}
	}
}
