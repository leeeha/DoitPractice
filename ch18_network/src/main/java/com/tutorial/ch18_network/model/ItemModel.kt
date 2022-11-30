package com.tutorial.ch18_network.model

// 서버로부터 받은 뉴스 기사 내용을 모델 클래스에 저장한다.
class ItemModel {
    var author: String? = null
    var title: String? = null
    var description: String? = null
    var urlToImage: String? = null
    var publishedAt: String? = null
}