package com.example.wgpchat.models

data class GPTReq(
    val prompt: String,
    val max_tokens: Int,
    val model: String,
    val top_p: Int,
    val frequency_penalty: Int,
    val presence_penalty: Int
)

data class  GPTRes(val choices: List<Choice>)


data class Choice(val text: String)