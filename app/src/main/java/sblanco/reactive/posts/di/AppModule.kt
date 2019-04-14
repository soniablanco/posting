package sblanco.reactive.posts.di

import org.koin.dsl.module
import sblanco.reactive.posts.model.network.MessagesService
import sblanco.reactive.posts.model.network.retrofit.RetrofitMessagesService

val appModule = module {

    // single instance of HelloRepository
    single<MessagesService> { RetrofitMessagesService() }

}