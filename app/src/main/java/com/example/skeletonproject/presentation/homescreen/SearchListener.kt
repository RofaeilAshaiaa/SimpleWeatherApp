package com.example.skeletonproject.presentation.homescreen

interface SearchListener {

    fun performSearchQuery(query: String)

    fun closeSearch()
}