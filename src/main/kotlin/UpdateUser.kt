package mirai.bot

import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody

class UpdateUser {
    fun update(updateUserParam: UpdateUserParam):SignInBack{
        val client = OkHttpClient()
        val result:String
        val JSON: MediaType = "application/json; charset=utf-8".toMediaType()
        val body: RequestBody = RequestBody.create(JSON,Gson().toJson(updateUserParam))
        val request = Request.Builder().post(body).url("http://localhost:8800/user/update").build()
        val response = client.newCall(request).execute()
        result=response.body?.string().toString()
        val jsclass= Gson().fromJson(result,SignInBack::class.java)
        return jsclass
    }
}