package com.boresjo.play.api.google.blogger

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putListSchemaPage: Conversion[List[Schema.Page], Option[List[Schema.Page]]] = (fun: List[Schema.Page]) => Option(fun)
		given putListSchemaPost: Conversion[List[Schema.Post], Option[List[Schema.Post]]] = (fun: List[Schema.Post]) => Option(fun)
		given putListSchemaBlogUserInfo: Conversion[List[Schema.BlogUserInfo], Option[List[Schema.BlogUserInfo]]] = (fun: List[Schema.BlogUserInfo]) => Option(fun)
		given putListSchemaBlog: Conversion[List[Schema.Blog], Option[List[Schema.Blog]]] = (fun: List[Schema.Blog]) => Option(fun)
		given putSchemaPageBlogItem: Conversion[Schema.Page.BlogItem, Option[Schema.Page.BlogItem]] = (fun: Schema.Page.BlogItem) => Option(fun)
		given putSchemaPageAuthorItem: Conversion[Schema.Page.AuthorItem, Option[Schema.Page.AuthorItem]] = (fun: Schema.Page.AuthorItem) => Option(fun)
		given putSchemaPageStatusEnum: Conversion[Schema.Page.StatusEnum, Option[Schema.Page.StatusEnum]] = (fun: Schema.Page.StatusEnum) => Option(fun)
		given putSchemaPost: Conversion[Schema.Post, Option[Schema.Post]] = (fun: Schema.Post) => Option(fun)
		given putSchemaPostPerUserInfo: Conversion[Schema.PostPerUserInfo, Option[Schema.PostPerUserInfo]] = (fun: Schema.PostPerUserInfo) => Option(fun)
		given putListSchemaPostUserInfo: Conversion[List[Schema.PostUserInfo], Option[List[Schema.PostUserInfo]]] = (fun: List[Schema.PostUserInfo]) => Option(fun)
		given putListSchemaPageviewsCountsItem: Conversion[List[Schema.Pageviews.CountsItem], Option[List[Schema.Pageviews.CountsItem]]] = (fun: List[Schema.Pageviews.CountsItem]) => Option(fun)
		given putSchemaBlogPerUserInfoRoleEnum: Conversion[Schema.BlogPerUserInfo.RoleEnum, Option[Schema.BlogPerUserInfo.RoleEnum]] = (fun: Schema.BlogPerUserInfo.RoleEnum) => Option(fun)
		given putListSchemaComment: Conversion[List[Schema.Comment], Option[List[Schema.Comment]]] = (fun: List[Schema.Comment]) => Option(fun)
		given putSchemaCommentInReplyToItem: Conversion[Schema.Comment.InReplyToItem, Option[Schema.Comment.InReplyToItem]] = (fun: Schema.Comment.InReplyToItem) => Option(fun)
		given putSchemaCommentStatusEnum: Conversion[Schema.Comment.StatusEnum, Option[Schema.Comment.StatusEnum]] = (fun: Schema.Comment.StatusEnum) => Option(fun)
		given putSchemaCommentBlogItem: Conversion[Schema.Comment.BlogItem, Option[Schema.Comment.BlogItem]] = (fun: Schema.Comment.BlogItem) => Option(fun)
		given putSchemaCommentAuthorItem: Conversion[Schema.Comment.AuthorItem, Option[Schema.Comment.AuthorItem]] = (fun: Schema.Comment.AuthorItem) => Option(fun)
		given putSchemaCommentPostItem: Conversion[Schema.Comment.PostItem, Option[Schema.Comment.PostItem]] = (fun: Schema.Comment.PostItem) => Option(fun)
		given putSchemaBlogStatusEnum: Conversion[Schema.Blog.StatusEnum, Option[Schema.Blog.StatusEnum]] = (fun: Schema.Blog.StatusEnum) => Option(fun)
		given putSchemaBlogPostsItem: Conversion[Schema.Blog.PostsItem, Option[Schema.Blog.PostsItem]] = (fun: Schema.Blog.PostsItem) => Option(fun)
		given putSchemaBlogPagesItem: Conversion[Schema.Blog.PagesItem, Option[Schema.Blog.PagesItem]] = (fun: Schema.Blog.PagesItem) => Option(fun)
		given putSchemaBlogLocaleItem: Conversion[Schema.Blog.LocaleItem, Option[Schema.Blog.LocaleItem]] = (fun: Schema.Blog.LocaleItem) => Option(fun)
		given putSchemaPostAuthorItem: Conversion[Schema.Post.AuthorItem, Option[Schema.Post.AuthorItem]] = (fun: Schema.Post.AuthorItem) => Option(fun)
		given putSchemaPostReaderCommentsEnum: Conversion[Schema.Post.ReaderCommentsEnum, Option[Schema.Post.ReaderCommentsEnum]] = (fun: Schema.Post.ReaderCommentsEnum) => Option(fun)
		given putSchemaPostLocationItem: Conversion[Schema.Post.LocationItem, Option[Schema.Post.LocationItem]] = (fun: Schema.Post.LocationItem) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putListSchemaPostImagesItem: Conversion[List[Schema.Post.ImagesItem], Option[List[Schema.Post.ImagesItem]]] = (fun: List[Schema.Post.ImagesItem]) => Option(fun)
		given putSchemaPostStatusEnum: Conversion[Schema.Post.StatusEnum, Option[Schema.Post.StatusEnum]] = (fun: Schema.Post.StatusEnum) => Option(fun)
		given putSchemaPostBlogItem: Conversion[Schema.Post.BlogItem, Option[Schema.Post.BlogItem]] = (fun: Schema.Post.BlogItem) => Option(fun)
		given putSchemaPostRepliesItem: Conversion[Schema.Post.RepliesItem, Option[Schema.Post.RepliesItem]] = (fun: Schema.Post.RepliesItem) => Option(fun)
		given putSchemaUserLocaleItem: Conversion[Schema.User.LocaleItem, Option[Schema.User.LocaleItem]] = (fun: Schema.User.LocaleItem) => Option(fun)
		given putSchemaUserBlogsItem: Conversion[Schema.User.BlogsItem, Option[Schema.User.BlogsItem]] = (fun: Schema.User.BlogsItem) => Option(fun)
		given putSchemaBlog: Conversion[Schema.Blog, Option[Schema.Blog]] = (fun: Schema.Blog) => Option(fun)
		given putSchemaBlogPerUserInfo: Conversion[Schema.BlogPerUserInfo, Option[Schema.BlogPerUserInfo]] = (fun: Schema.BlogPerUserInfo) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
