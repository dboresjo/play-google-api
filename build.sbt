name := "play-google-api"

ThisBuild / organization := "com.boresjo"
ThisBuild / scalaVersion := "3.3.4"

ThisBuild / maxErrors := 1

ThisBuild / libraryDependencies += guice
ThisBuild / libraryDependencies += ws
ThisBuild / libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.0" % Test

lazy val common = (project in file("common")).enablePlugins(PlayScala).settings (
  name := "play-google-api"
)

lazy val androiddeviceprovisioningApi = (project in file("api/androiddeviceprovisioning")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-androiddeviceprovisioning-api"
)
lazy val vmwareengineApi = (project in file("api/vmwareengine")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-vmwareengine-api"
)
lazy val factchecktoolsApi = (project in file("api/factchecktools")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-factchecktools-api"
)
lazy val dnsApi = (project in file("api/dns")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-dns-api"
)
lazy val datalineageApi = (project in file("api/datalineage")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-datalineage-api"
)
lazy val sqladminApi = (project in file("api/sqladmin")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-sqladmin-api"
)
lazy val playdeveloperreportingApi = (project in file("api/playdeveloperreporting")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-playdeveloperreporting-api"
)
lazy val streetviewpublishApi = (project in file("api/streetviewpublish")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-streetviewpublish-api"
)
lazy val driveApi = (project in file("api/drive")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-drive-api"
)
lazy val backupdrApi = (project in file("api/backupdr")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-backupdr-api"
)
lazy val apigatewayApi = (project in file("api/apigateway")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-apigateway-api"
)
lazy val cloudbuildApi = (project in file("api/cloudbuild")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-cloudbuild-api"
)
lazy val cloudresourcemanagerApi = (project in file("api/cloudresourcemanager")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-cloudresourcemanager-api"
)
lazy val eventarcApi = (project in file("api/eventarc")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-eventarc-api"
)
lazy val adminApi = (project in file("api/admin")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-admin-api"
)
lazy val trafficdirectorApi = (project in file("api/trafficdirector")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-trafficdirector-api"
)
lazy val cloudkmsApi = (project in file("api/cloudkms")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-cloudkms-api"
)
lazy val bigquerydatatransferApi = (project in file("api/bigquerydatatransfer")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-bigquerydatatransfer-api"
)
lazy val peopleApi = (project in file("api/people")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-people-api"
)
lazy val clouderrorreportingApi = (project in file("api/clouderrorreporting")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-clouderrorreporting-api"
)
lazy val chromeuxreportApi = (project in file("api/chromeuxreport")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-chromeuxreport-api"
)
lazy val gmailpostmastertoolsApi = (project in file("api/gmailpostmastertools")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-gmailpostmastertools-api"
)
lazy val accesscontextmanagerApi = (project in file("api/accesscontextmanager")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-accesscontextmanager-api"
)
lazy val policytroubleshooterApi = (project in file("api/policytroubleshooter")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-policytroubleshooter-api"
)
lazy val gamesConfigurationApi = (project in file("api/gamesConfiguration")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-gamesConfiguration-api"
)
lazy val alloydbApi = (project in file("api/alloydb")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-alloydb-api"
)
lazy val recaptchaenterpriseApi = (project in file("api/recaptchaenterprise")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-recaptchaenterprise-api"
)
lazy val firebasehostingApi = (project in file("api/firebasehosting")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-firebasehosting-api"
)
lazy val pollenApi = (project in file("api/pollen")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-pollen-api"
)
lazy val addressvalidationApi = (project in file("api/addressvalidation")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-addressvalidation-api"
)
lazy val certificatemanagerApi = (project in file("api/certificatemanager")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-certificatemanager-api"
)
lazy val dataprocApi = (project in file("api/dataproc")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-dataproc-api"
)
lazy val licensingApi = (project in file("api/licensing")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-licensing-api"
)
lazy val secretmanagerApi = (project in file("api/secretmanager")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-secretmanager-api"
)
lazy val cloudassetApi = (project in file("api/cloudasset")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-cloudasset-api"
)
lazy val documentaiApi = (project in file("api/documentai")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-documentai-api"
)
lazy val loggingApi = (project in file("api/logging")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-logging-api"
)
lazy val bigquerydatapolicyApi = (project in file("api/bigquerydatapolicy")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-bigquerydatapolicy-api"
)
lazy val firestoreApi = (project in file("api/firestore")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-firestore-api"
)
lazy val beyondcorpApi = (project in file("api/beyondcorp")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-beyondcorp-api"
)
lazy val memcacheApi = (project in file("api/memcache")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-memcache-api"
)
lazy val youtubereportingApi = (project in file("api/youtubereporting")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-youtubereporting-api"
)
lazy val gkeonpremApi = (project in file("api/gkeonprem")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-gkeonprem-api"
)
lazy val mybusinessbusinessinformationApi = (project in file("api/mybusinessbusinessinformation")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-mybusinessbusinessinformation-api"
)
lazy val bigtableadminApi = (project in file("api/bigtableadmin")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-bigtableadmin-api"
)
lazy val securitycenterApi = (project in file("api/securitycenter")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-securitycenter-api"
)
lazy val cssApi = (project in file("api/css")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-css-api"
)
lazy val smartdevicemanagementApi = (project in file("api/smartdevicemanagement")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-smartdevicemanagement-api"
)
lazy val servicedirectoryApi = (project in file("api/servicedirectory")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-servicedirectory-api"
)
lazy val youtubeApi = (project in file("api/youtube")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-youtube-api"
)
lazy val oracledatabaseApi = (project in file("api/oracledatabase")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-oracledatabase-api"
)
lazy val apigeeApi = (project in file("api/apigee")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-apigee-api"
)
lazy val contentwarehouseApi = (project in file("api/contentwarehouse")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-contentwarehouse-api"
)
lazy val mybusinesslodgingApi = (project in file("api/mybusinesslodging")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-mybusinesslodging-api"
)
lazy val pagespeedonlineApi = (project in file("api/pagespeedonline")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-pagespeedonline-api"
)
lazy val paymentsresellersubscriptionApi = (project in file("api/paymentsresellersubscription")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-paymentsresellersubscription-api"
)
lazy val networksecurityApi = (project in file("api/networksecurity")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-networksecurity-api"
)
lazy val firebasemlApi = (project in file("api/firebaseml")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-firebaseml-api"
)
lazy val groupssettingsApi = (project in file("api/groupssettings")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-groupssettings-api"
)
lazy val bloggerApi = (project in file("api/blogger")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-blogger-api"
)
lazy val authorizedbuyersmarketplaceApi = (project in file("api/authorizedbuyersmarketplace")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-authorizedbuyersmarketplace-api"
)
lazy val analyticshubApi = (project in file("api/analyticshub")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-analyticshub-api"
)
lazy val alertcenterApi = (project in file("api/alertcenter")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-alertcenter-api"
)
lazy val lookerApi = (project in file("api/looker")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-looker-api"
)
lazy val baremetalsolutionApi = (project in file("api/baremetalsolution")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-baremetalsolution-api"
)
lazy val cloudbillingApi = (project in file("api/cloudbilling")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-cloudbilling-api"
)
lazy val dlpApi = (project in file("api/dlp")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-dlp-api"
)
lazy val driveactivityApi = (project in file("api/driveactivity")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-driveactivity-api"
)
lazy val firebasedynamiclinksApi = (project in file("api/firebasedynamiclinks")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-firebasedynamiclinks-api"
)
lazy val datafusionApi = (project in file("api/datafusion")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-datafusion-api"
)
lazy val keepApi = (project in file("api/keep")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-keep-api"
)
lazy val apphubApi = (project in file("api/apphub")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-apphub-api"
)
lazy val cloudshellApi = (project in file("api/cloudshell")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-cloudshell-api"
)
lazy val texttospeechApi = (project in file("api/texttospeech")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-texttospeech-api"
)
lazy val dataformApi = (project in file("api/dataform")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-dataform-api"
)
lazy val policyanalyzerApi = (project in file("api/policyanalyzer")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-policyanalyzer-api"
)
lazy val toolresultsApi = (project in file("api/toolresults")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-toolresults-api"
)
lazy val adexchangebuyer2Api = (project in file("api/adexchangebuyer2")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-adexchangebuyer2-api"
)
lazy val classroomApi = (project in file("api/classroom")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-classroom-api"
)
lazy val cloudchannelApi = (project in file("api/cloudchannel")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-cloudchannel-api"
)
lazy val runApi = (project in file("api/run")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-run-api"
)
lazy val connectorsApi = (project in file("api/connectors")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-connectors-api"
)
lazy val verifiedaccessApi = (project in file("api/verifiedaccess")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-verifiedaccess-api"
)
lazy val gkebackupApi = (project in file("api/gkebackup")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-gkebackup-api"
)
lazy val libraryagentApi = (project in file("api/libraryagent")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-libraryagent-api"
)
lazy val merchantapiApi = (project in file("api/merchantapi")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-merchantapi-api"
)
lazy val cloudcontrolspartnerApi = (project in file("api/cloudcontrolspartner")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-cloudcontrolspartner-api"
)
lazy val marketingplatformadminApi = (project in file("api/marketingplatformadmin")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-marketingplatformadmin-api"
)
lazy val contactcenteraiplatformApi = (project in file("api/contactcenteraiplatform")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-contactcenteraiplatform-api"
)
lazy val containeranalysisApi = (project in file("api/containeranalysis")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-containeranalysis-api"
)
lazy val chromemanagementApi = (project in file("api/chromemanagement")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-chromemanagement-api"
)
lazy val safebrowsingApi = (project in file("api/safebrowsing")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-safebrowsing-api"
)
lazy val domainsApi = (project in file("api/domains")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-domains-api"
)
lazy val datamigrationApi = (project in file("api/datamigration")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-datamigration-api"
)
lazy val publiccaApi = (project in file("api/publicca")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-publicca-api"
)
lazy val containerApi = (project in file("api/container")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-container-api"
)
lazy val prod_tt_sasportalApi = (project in file("api/prod_tt_sasportal")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-prod_tt_sasportal-api"
)
lazy val composerApi = (project in file("api/composer")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-composer-api"
)
lazy val homegraphApi = (project in file("api/homegraph")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-homegraph-api"
)
lazy val mybusinessaccountmanagementApi = (project in file("api/mybusinessaccountmanagement")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-mybusinessaccountmanagement-api"
)
lazy val apikeysApi = (project in file("api/apikeys")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-apikeys-api"
)
lazy val essentialcontactsApi = (project in file("api/essentialcontacts")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-essentialcontacts-api"
)
lazy val firebasedatabaseApi = (project in file("api/firebasedatabase")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-firebasedatabase-api"
)
lazy val storagetransferApi = (project in file("api/storagetransfer")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-storagetransfer-api"
)
lazy val adexperiencereportApi = (project in file("api/adexperiencereport")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-adexperiencereport-api"
)
lazy val kgsearchApi = (project in file("api/kgsearch")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-kgsearch-api"
)
lazy val configApi = (project in file("api/config")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-config-api"
)
lazy val apimApi = (project in file("api/apim")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-apim-api"
)
lazy val integrationsApi = (project in file("api/integrations")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-integrations-api"
)
lazy val mybusinessqandaApi = (project in file("api/mybusinessqanda")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-mybusinessqanda-api"
)
lazy val doubleclickbidmanagerApi = (project in file("api/doubleclickbidmanager")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-doubleclickbidmanager-api"
)
lazy val deploymentmanagerApi = (project in file("api/deploymentmanager")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-deploymentmanager-api"
)
lazy val groupsmigrationApi = (project in file("api/groupsmigration")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-groupsmigration-api"
)
lazy val lifesciencesApi = (project in file("api/lifesciences")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-lifesciences-api"
)
lazy val dataportabilityApi = (project in file("api/dataportability")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-dataportability-api"
)
lazy val cloudschedulerApi = (project in file("api/cloudscheduler")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-cloudscheduler-api"
)
lazy val versionhistoryApi = (project in file("api/versionhistory")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-versionhistory-api"
)
lazy val fcmApi = (project in file("api/fcm")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-fcm-api"
)
lazy val firebaserulesApi = (project in file("api/firebaserules")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-firebaserules-api"
)
lazy val cloudtasksApi = (project in file("api/cloudtasks")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-cloudtasks-api"
)
lazy val indexingApi = (project in file("api/indexing")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-indexing-api"
)
lazy val bigqueryApi = (project in file("api/bigquery")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-bigquery-api"
)
lazy val customsearchApi = (project in file("api/customsearch")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-customsearch-api"
)
lazy val pubsubliteApi = (project in file("api/pubsublite")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-pubsublite-api"
)
lazy val testingApi = (project in file("api/testing")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-testing-api"
)
lazy val solarApi = (project in file("api/solar")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-solar-api"
)
lazy val stsApi = (project in file("api/sts")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-sts-api"
)
lazy val searchads360Api = (project in file("api/searchads360")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-searchads360-api"
)
lazy val cloudsupportApi = (project in file("api/cloudsupport")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-cloudsupport-api"
)
lazy val abusiveexperiencereportApi = (project in file("api/abusiveexperiencereport")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-abusiveexperiencereport-api"
)
lazy val assuredworkloadsApi = (project in file("api/assuredworkloads")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-assuredworkloads-api"
)
lazy val orgpolicyApi = (project in file("api/orgpolicy")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-orgpolicy-api"
)
lazy val searchconsoleApi = (project in file("api/searchconsole")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-searchconsole-api"
)
lazy val mybusinessplaceactionsApi = (project in file("api/mybusinessplaceactions")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-mybusinessplaceactions-api"
)
lazy val bigqueryreservationApi = (project in file("api/bigqueryreservation")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-bigqueryreservation-api"
)
lazy val readerrevenuesubscriptionlinkingApi = (project in file("api/readerrevenuesubscriptionlinking")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-readerrevenuesubscriptionlinking-api"
)
lazy val mybusinessnotificationsApi = (project in file("api/mybusinessnotifications")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-mybusinessnotifications-api"
)
lazy val admobApi = (project in file("api/admob")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-admob-api"
)
lazy val calendarApi = (project in file("api/calendar")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-calendar-api"
)
lazy val firebaseappdistributionApi = (project in file("api/firebaseappdistribution")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-firebaseappdistribution-api"
)
lazy val chatApi = (project in file("api/chat")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-chat-api"
)
lazy val mybusinessverificationsApi = (project in file("api/mybusinessverifications")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-mybusinessverifications-api"
)
lazy val discoveryengineApi = (project in file("api/discoveryengine")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-discoveryengine-api"
)
lazy val digitalassetlinksApi = (project in file("api/digitalassetlinks")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-digitalassetlinks-api"
)
lazy val playcustomappApi = (project in file("api/playcustomapp")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-playcustomapp-api"
)
lazy val vmmigrationApi = (project in file("api/vmmigration")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-vmmigration-api"
)
lazy val androidmanagementApi = (project in file("api/androidmanagement")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-androidmanagement-api"
)
lazy val dialogflowApi = (project in file("api/dialogflow")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-dialogflow-api"
)
lazy val artifactregistryApi = (project in file("api/artifactregistry")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-artifactregistry-api"
)
lazy val cloudidentityApi = (project in file("api/cloudidentity")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-cloudidentity-api"
)
lazy val batchApi = (project in file("api/batch")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-batch-api"
)
lazy val mlApi = (project in file("api/ml")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-ml-api"
)
lazy val osconfigApi = (project in file("api/osconfig")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-osconfig-api"
)
lazy val rapidmigrationassessmentApi = (project in file("api/rapidmigrationassessment")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-rapidmigrationassessment-api"
)
lazy val analyticsreportingApi = (project in file("api/analyticsreporting")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-analyticsreporting-api"
)
lazy val vpcaccessApi = (project in file("api/vpcaccess")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-vpcaccess-api"
)
lazy val playgroupingApi = (project in file("api/playgrouping")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-playgrouping-api"
)
lazy val billingbudgetsApi = (project in file("api/billingbudgets")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-billingbudgets-api"
)
lazy val tasksApi = (project in file("api/tasks")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-tasks-api"
)
lazy val servicecontrolApi = (project in file("api/servicecontrol")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-servicecontrol-api"
)
lazy val languageApi = (project in file("api/language")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-language-api"
)
lazy val dataflowApi = (project in file("api/dataflow")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-dataflow-api"
)
lazy val visionApi = (project in file("api/vision")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-vision-api"
)
lazy val datacatalogApi = (project in file("api/datacatalog")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-datacatalog-api"
)
lazy val workspaceeventsApi = (project in file("api/workspaceevents")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-workspaceevents-api"
)
lazy val binaryauthorizationApi = (project in file("api/binaryauthorization")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-binaryauthorization-api"
)
lazy val ondemandscanningApi = (project in file("api/ondemandscanning")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-ondemandscanning-api"
)
lazy val analyticsdataApi = (project in file("api/analyticsdata")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-analyticsdata-api"
)
lazy val acceleratedmobilepageurlApi = (project in file("api/acceleratedmobilepageurl")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-acceleratedmobilepageurl-api"
)
lazy val advisorynotificationsApi = (project in file("api/advisorynotifications")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-advisorynotifications-api"
)
lazy val runtimeconfigApi = (project in file("api/runtimeconfig")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-runtimeconfig-api"
)
lazy val apigeeregistryApi = (project in file("api/apigeeregistry")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-apigeeregistry-api"
)
lazy val gkehubApi = (project in file("api/gkehub")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-gkehub-api"
)
lazy val developerconnectApi = (project in file("api/developerconnect")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-developerconnect-api"
)
lazy val playintegrityApi = (project in file("api/playintegrity")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-playintegrity-api"
)
lazy val iamApi = (project in file("api/iam")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-iam-api"
)
lazy val videointelligenceApi = (project in file("api/videointelligence")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-videointelligence-api"
)
lazy val bigqueryconnectionApi = (project in file("api/bigqueryconnection")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-bigqueryconnection-api"
)
lazy val workflowsApi = (project in file("api/workflows")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-workflows-api"
)
lazy val youtubeAnalyticsApi = (project in file("api/youtubeAnalytics")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-youtubeAnalytics-api"
)
lazy val checksApi = (project in file("api/checks")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-checks-api"
)
lazy val gamesManagementApi = (project in file("api/gamesManagement")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-gamesManagement-api"
)
lazy val workstationsApi = (project in file("api/workstations")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-workstations-api"
)
lazy val realtimebiddingApi = (project in file("api/realtimebidding")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-realtimebidding-api"
)
lazy val networkmanagementApi = (project in file("api/networkmanagement")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-networkmanagement-api"
)
lazy val sheetsApi = (project in file("api/sheets")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-sheets-api"
)
lazy val fitnessApi = (project in file("api/fitness")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-fitness-api"
)
lazy val accessapprovalApi = (project in file("api/accessapproval")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-accessapproval-api"
)
lazy val gmailApi = (project in file("api/gmail")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-gmail-api"
)
lazy val booksApi = (project in file("api/books")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-books-api"
)
lazy val area120tablesApi = (project in file("api/area120tables")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-area120tables-api"
)
lazy val contactcenterinsightsApi = (project in file("api/contactcenterinsights")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-contactcenterinsights-api"
)
lazy val contentApi = (project in file("api/content")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-content-api"
)
lazy val jobsApi = (project in file("api/jobs")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-jobs-api"
)
lazy val metastoreApi = (project in file("api/metastore")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-metastore-api"
)
lazy val cloudprofilerApi = (project in file("api/cloudprofiler")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-cloudprofiler-api"
)
lazy val datastoreApi = (project in file("api/datastore")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-datastore-api"
)
lazy val resourcesettingsApi = (project in file("api/resourcesettings")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-resourcesettings-api"
)
lazy val websecurityscannerApi = (project in file("api/websecurityscanner")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-websecurityscanner-api"
)
lazy val airqualityApi = (project in file("api/airquality")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-airquality-api"
)
lazy val blockchainnodeengineApi = (project in file("api/blockchainnodeengine")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-blockchainnodeengine-api"
)
lazy val appengineApi = (project in file("api/appengine")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-appengine-api"
)
lazy val civicinfoApi = (project in file("api/civicinfo")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-civicinfo-api"
)
lazy val manufacturersApi = (project in file("api/manufacturers")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-manufacturers-api"
)
lazy val dataplexApi = (project in file("api/dataplex")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-dataplex-api"
)
lazy val resellerApi = (project in file("api/reseller")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-reseller-api"
)
lazy val storageApi = (project in file("api/storage")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-storage-api"
)
lazy val fileApi = (project in file("api/file")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-file-api"
)
lazy val datapipelinesApi = (project in file("api/datapipelines")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-datapipelines-api"
)
lazy val identitytoolkitApi = (project in file("api/identitytoolkit")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-identitytoolkit-api"
)
lazy val analyticsadminApi = (project in file("api/analyticsadmin")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-analyticsadmin-api"
)
lazy val redisApi = (project in file("api/redis")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-redis-api"
)
lazy val webfontsApi = (project in file("api/webfonts")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-webfonts-api"
)
lazy val netappApi = (project in file("api/netapp")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-netapp-api"
)
lazy val analyticsApi = (project in file("api/analytics")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-analytics-api"
)
lazy val meetApi = (project in file("api/meet")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-meet-api"
)
lazy val displayvideoApi = (project in file("api/displayvideo")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-displayvideo-api"
)
lazy val drivelabelsApi = (project in file("api/drivelabels")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-drivelabels-api"
)
lazy val scriptApi = (project in file("api/script")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-script-api"
)
lazy val cloudtraceApi = (project in file("api/cloudtrace")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-cloudtrace-api"
)
lazy val cloudfunctionsApi = (project in file("api/cloudfunctions")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-cloudfunctions-api"
)
lazy val privatecaApi = (project in file("api/privateca")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-privateca-api"
)
lazy val fcmdataApi = (project in file("api/fcmdata")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-fcmdata-api"
)
lazy val chromepolicyApi = (project in file("api/chromepolicy")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-chromepolicy-api"
)
lazy val tpuApi = (project in file("api/tpu")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-tpu-api"
)
lazy val idsApi = (project in file("api/ids")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-ids-api"
)
lazy val biglakeApi = (project in file("api/biglake")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-biglake-api"
)
lazy val adsenseApi = (project in file("api/adsense")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-adsense-api"
)
lazy val spannerApi = (project in file("api/spanner")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-spanner-api"
)
lazy val recommendationengineApi = (project in file("api/recommendationengine")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-recommendationengine-api"
)
lazy val healthcareApi = (project in file("api/healthcare")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-healthcare-api"
)
lazy val localservicesApi = (project in file("api/localservices")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-localservices-api"
)
lazy val policysimulatorApi = (project in file("api/policysimulator")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-policysimulator-api"
)
lazy val managedidentitiesApi = (project in file("api/managedidentities")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-managedidentities-api"
)
lazy val transcoderApi = (project in file("api/transcoder")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-transcoder-api"
)
lazy val vaultApi = (project in file("api/vault")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-vault-api"
)
lazy val osloginApi = (project in file("api/oslogin")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-oslogin-api"
)
lazy val iamcredentialsApi = (project in file("api/iamcredentials")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-iamcredentials-api"
)
lazy val kmsinventoryApi = (project in file("api/kmsinventory")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-kmsinventory-api"
)
lazy val webriskApi = (project in file("api/webrisk")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-webrisk-api"
)
lazy val workflowexecutionsApi = (project in file("api/workflowexecutions")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-workflowexecutions-api"
)
lazy val discoveryApi = (project in file("api/discovery")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-discovery-api"
)
lazy val datastreamApi = (project in file("api/datastream")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-datastream-api"
)
lazy val travelimpactmodelApi = (project in file("api/travelimpactmodel")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-travelimpactmodel-api"
)
lazy val workloadmanagerApi = (project in file("api/workloadmanager")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-workloadmanager-api"
)
lazy val retailApi = (project in file("api/retail")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-retail-api"
)
lazy val speechApi = (project in file("api/speech")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-speech-api"
)
lazy val migrationcenterApi = (project in file("api/migrationcenter")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-migrationcenter-api"
)
lazy val androidpublisherApi = (project in file("api/androidpublisher")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-androidpublisher-api"
)
lazy val androidenterpriseApi = (project in file("api/androidenterprise")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-androidenterprise-api"
)
lazy val iapApi = (project in file("api/iap")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-iap-api"
)
lazy val tagmanagerApi = (project in file("api/tagmanager")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-tagmanager-api"
)
lazy val gamesApi = (project in file("api/games")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-games-api"
)
lazy val placesApi = (project in file("api/places")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-places-api"
)
lazy val firebaseappcheckApi = (project in file("api/firebaseappcheck")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-firebaseappcheck-api"
)
lazy val networkconnectivityApi = (project in file("api/networkconnectivity")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-networkconnectivity-api"
)
lazy val walletobjectsApi = (project in file("api/walletobjects")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-walletobjects-api"
)
lazy val sasportalApi = (project in file("api/sasportal")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-sasportal-api"
)
lazy val businessprofileperformanceApi = (project in file("api/businessprofileperformance")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-businessprofileperformance-api"
)
lazy val firebaseApi = (project in file("api/firebase")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-firebase-api"
)
lazy val oauth2Api = (project in file("api/oauth2")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-oauth2-api"
)
lazy val notebooksApi = (project in file("api/notebooks")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-notebooks-api"
)
lazy val firebasestorageApi = (project in file("api/firebasestorage")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-firebasestorage-api"
)
lazy val siteVerificationApi = (project in file("api/siteVerification")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-siteVerification-api"
)
lazy val networkservicesApi = (project in file("api/networkservices")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-networkservices-api"
)
lazy val recommenderApi = (project in file("api/recommender")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-recommender-api"
)
lazy val adsenseplatformApi = (project in file("api/adsenseplatform")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-adsenseplatform-api"
)
lazy val datalabelingApi = (project in file("api/datalabeling")).enablePlugins(PlayScala).dependsOn(common).settings (
  name := "play-google-datalabeling-api"
)


lazy val all = (project in file(".")).aggregate(common, androiddeviceprovisioningApi, vmwareengineApi, factchecktoolsApi, dnsApi, datalineageApi, sqladminApi, playdeveloperreportingApi, streetviewpublishApi, driveApi, backupdrApi, apigatewayApi, cloudbuildApi, cloudresourcemanagerApi, eventarcApi, adminApi, trafficdirectorApi, cloudkmsApi, bigquerydatatransferApi, peopleApi, clouderrorreportingApi, chromeuxreportApi, gmailpostmastertoolsApi, accesscontextmanagerApi, policytroubleshooterApi, gamesConfigurationApi, alloydbApi, recaptchaenterpriseApi, firebasehostingApi, pollenApi, addressvalidationApi, certificatemanagerApi, dataprocApi, licensingApi, secretmanagerApi, cloudassetApi, documentaiApi, loggingApi, bigquerydatapolicyApi, firestoreApi, beyondcorpApi, memcacheApi, youtubereportingApi, gkeonpremApi, mybusinessbusinessinformationApi, bigtableadminApi, securitycenterApi, cssApi, smartdevicemanagementApi, servicedirectoryApi, youtubeApi, oracledatabaseApi, apigeeApi, contentwarehouseApi, mybusinesslodgingApi, pagespeedonlineApi, paymentsresellersubscriptionApi, networksecurityApi, firebasemlApi, groupssettingsApi, bloggerApi, authorizedbuyersmarketplaceApi, analyticshubApi, alertcenterApi, lookerApi, baremetalsolutionApi, cloudbillingApi, dlpApi, driveactivityApi, firebasedynamiclinksApi, datafusionApi, keepApi, apphubApi, cloudshellApi, texttospeechApi, dataformApi, policyanalyzerApi, toolresultsApi, adexchangebuyer2Api, classroomApi, cloudchannelApi, runApi, connectorsApi, verifiedaccessApi, gkebackupApi, libraryagentApi, merchantapiApi, cloudcontrolspartnerApi, marketingplatformadminApi, contactcenteraiplatformApi, containeranalysisApi, chromemanagementApi, safebrowsingApi, domainsApi, datamigrationApi, publiccaApi, containerApi, prod_tt_sasportalApi, composerApi, homegraphApi, mybusinessaccountmanagementApi, apikeysApi, essentialcontactsApi, firebasedatabaseApi, storagetransferApi, adexperiencereportApi, kgsearchApi, configApi, apimApi, integrationsApi, mybusinessqandaApi, doubleclickbidmanagerApi, deploymentmanagerApi, groupsmigrationApi, lifesciencesApi, dataportabilityApi, cloudschedulerApi, versionhistoryApi, fcmApi, firebaserulesApi, cloudtasksApi, indexingApi, bigqueryApi, customsearchApi, pubsubliteApi, testingApi, solarApi, stsApi, searchads360Api, cloudsupportApi, abusiveexperiencereportApi, assuredworkloadsApi, orgpolicyApi, searchconsoleApi, mybusinessplaceactionsApi, bigqueryreservationApi, readerrevenuesubscriptionlinkingApi, mybusinessnotificationsApi, admobApi, calendarApi, firebaseappdistributionApi, chatApi, mybusinessverificationsApi, discoveryengineApi, digitalassetlinksApi, playcustomappApi, vmmigrationApi, androidmanagementApi, dialogflowApi, artifactregistryApi, cloudidentityApi, batchApi, mlApi, osconfigApi, rapidmigrationassessmentApi, analyticsreportingApi, vpcaccessApi, playgroupingApi, billingbudgetsApi, tasksApi, servicecontrolApi, languageApi, dataflowApi, visionApi, datacatalogApi, workspaceeventsApi, binaryauthorizationApi, ondemandscanningApi, analyticsdataApi, acceleratedmobilepageurlApi, advisorynotificationsApi, runtimeconfigApi, apigeeregistryApi, gkehubApi, developerconnectApi, playintegrityApi, iamApi, videointelligenceApi, bigqueryconnectionApi, workflowsApi, youtubeAnalyticsApi, checksApi, gamesManagementApi, workstationsApi, realtimebiddingApi, networkmanagementApi, sheetsApi, fitnessApi, accessapprovalApi, gmailApi, booksApi, area120tablesApi, contactcenterinsightsApi, contentApi, jobsApi, metastoreApi, cloudprofilerApi, datastoreApi, resourcesettingsApi, websecurityscannerApi, airqualityApi, blockchainnodeengineApi, appengineApi, civicinfoApi, manufacturersApi, dataplexApi, resellerApi, storageApi, fileApi, datapipelinesApi, identitytoolkitApi, analyticsadminApi, redisApi, webfontsApi, netappApi, analyticsApi, meetApi, displayvideoApi, drivelabelsApi, scriptApi, cloudtraceApi, cloudfunctionsApi, privatecaApi, fcmdataApi, chromepolicyApi, tpuApi, idsApi, biglakeApi, adsenseApi, spannerApi, recommendationengineApi, healthcareApi, localservicesApi, policysimulatorApi, managedidentitiesApi, transcoderApi, vaultApi, osloginApi, iamcredentialsApi, kmsinventoryApi, webriskApi, workflowexecutionsApi, discoveryApi, datastreamApi, travelimpactmodelApi, workloadmanagerApi, retailApi, speechApi, migrationcenterApi, androidpublisherApi, androidenterpriseApi, iapApi, tagmanagerApi, gamesApi, placesApi, firebaseappcheckApi, networkconnectivityApi, walletobjectsApi, sasportalApi, businessprofileperformanceApi, firebaseApi, oauth2Api, notebooksApi, firebasestorageApi, siteVerificationApi, networkservicesApi, recommenderApi, adsenseplatformApi, datalabelingApi)
