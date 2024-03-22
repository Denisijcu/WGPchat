


<h1> GPTChat-App </h1>
<p>
    GPT-ChatApp represents the culmination of the concepts and techniques covered in our book, bringing together various components to create a powerful AI Assistant application. As technology continues to advance, AI assistants play an increasingly significant role in our lives, offering assistance with tasks ranging from information retrieval to task automation. With GPT-ChatApp, we aim to showcase the potential of integrating UI design, data management, and conversational capabilities to create an intelligent and versatile AI assistant.

</p>

<h2>To use this code.</h2>
<p>Get your OpenAI API's key first and Go Model package inside GPTApi and replace  </p>
<pre>
    <code>
 interface GPTApi {
    @Headers(
        "Content-Type: application/json",
        "Authorization: Bearer "Your OpenAI key here"
    )
    @POST("/v1/completions")
    fun getCompletion(
        @Body requestBody: GPTReq
    ): Call<GPTRes>
}
    </code>
</pre>

<div>
 <img src="app/src/androidTest/java/com/example/wgpchat/home_screen.png" height="640" width="440">
<img src="app/src/androidTest/java/com/example/wgpchat/home_screenA.png" height="640" width="440">
<img src="app/src/androidTest/java/com/example/wgpchat/home_screenB.png" height="640" width="440">
 
<img src="app/src/androidTest/java/com/example/wgpchat/home_screenC.png" height="640" width="440" /> 
<img src="app/src/androidTest/java/com/example/wgpchat/send_email.png" height="640" width="440" />
<img src="app/src/androidTest/java/com/example/wgpchat/history_screen.png" height="640" width="440" /> 
</div>

    
