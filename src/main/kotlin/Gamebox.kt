package mirai.bot

import com.google.gson.Gson
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.message.data.*
import net.mamoe.mirai.utils.info
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody

object Gamebox : KotlinPlugin(
    JvmPluginDescription(
        id = "mirai.bot.gamebox",
        version = "1.0-SNAPSHOT",
    )
) {
    override fun onEnable() {
        logger.info { "GameBox Plugin loaded" }

        GlobalEventChannel.subscribeAlways<GroupMessageEvent>{
            if(message.serializeToMiraiCode().split(':').size>2) {
                if (message.serializeToMiraiCode().split(':')[1]=="at" &&
                    message.serializeToMiraiCode().split(':')[2].split(']')[0]==sender.bot.id.toString()){
                    if(message.serializeToMiraiCode().split(']')[1].trim().startsWith("游戏查找")){
                        val name =message.serializeToMiraiCode().substring(
                            message.serializeToMiraiCode().indexOf("游戏查找")+4,
                            message.serializeToMiraiCode().length
                        )
                        val client = OkHttpClient()
                        val result:String
                        val JSON: MediaType = "application/json; charset=utf-8".toMediaType()
                        val body:RequestBody=RequestBody.create(JSON,"{\"name\":\"${name}\"}")
                        val request = Request.Builder().post(body).url("http://localhost:8800/game/select").build()
                        val response = client.newCall(request).execute()
                        result=response.body?.string().toString()
                        val jsclass= Gson().fromJson(result,GameResultBack::class.java)
                        if("${jsclass.status}"=="200"){
                            var content =""
                            for (d in jsclass.data){
                                content=content+"\n${d.name}:\n${d.content}"
                            }
                            val chain = buildMessageChain {
                                +At(sender.id)
                                +Face(145)
                                +Face(145)
                                +Face(145)
                                +Face(145)
                                +Face(145)
                                +PlainText("\n找到啦！一共有${jsclass.data.size}条数据！${content}")
                            }
                            subject.sendMessage(chain)
                        }else{
                            if("${jsclass.status}"=="201"){
                                subject.sendMessage(buildMessageChain {
                                    +Face(175)
                                    +Face(175)
                                    +Face(175)
                                    +Face(175)
                                    +Face(175)
                                    +PlainText("\n暂时没有这个游戏哦！！")
                                })
                            }else{
                                if("${jsclass.status}"=="500"){
                                    subject.sendMessage("\n出错了！！！")
                                }
                            }
                        }
                    }
                    if(message.serializeToMiraiCode().split(']')[1].trim().startsWith("游戏菜单")){
                        subject.sendMessage(buildMessageChain {
                            +Face(63)
                            +Face(63)
                            +Face(63)
                            +Face(63)
                            +Face(63)
                            +Face(63)
                            +PlainText("\n        游戏菜单\n")
                            +Face(54)
                            +Face(54)
                            +Face(54)
                            +Face(54)
                            +Face(54)
                            +Face(54)
                            +PlainText("\n        注册账号\n")
                            +Face(54)
                            +Face(54)
                            +Face(54)
                            +Face(54)
                            +Face(54)
                            +Face(54)
                            +PlainText("\n            签到  \n")
                            +Face(54)
                            +Face(54)
                            +Face(54)
                            +Face(54)
                            +Face(54)
                            +Face(54)
                            +PlainText("\n        我的资产\n")
                            +Face(54)
                            +Face(54)
                            +Face(54)
                            +Face(54)
                            +Face(54)
                            +Face(54)
                            +PlainText("\n    随机二次元游戏\n")
                            +Face(54)
                            +Face(54)
                            +Face(54)
                            +Face(54)
                            +Face(54)
                            +Face(54)
                            +PlainText("\n        游戏查找\n")
                            +Face(54)
                            +Face(54)
                            +Face(54)
                            +Face(54)
                            +Face(54)
                            +Face(54)
                            +PlainText("\n    二次元游戏查找\n")
                            +Face(54)
                            +Face(54)
                            +Face(54)
                            +Face(54)
                            +Face(54)
                            +Face(54)
                            +PlainText("\n        分享游戏\n")
                            +Face(54)
                            +Face(54)
                            +Face(54)
                            +Face(54)
                            +Face(54)
                            +Face(54)
                            +PlainText("\n        分享说明\n")
                            +Face(54)
                            +Face(54)
                            +Face(54)
                            +Face(54)
                            +Face(54)
                            +Face(54)
                            +PlainText("\n        分享查找\n")
                            +Face(54)
                            +Face(54)
                            +Face(54)
                            +Face(54)
                            +Face(54)
                            +Face(54)
                            +PlainText("\n            帮助\n")
                            +Face(54)
                            +Face(54)
                            +Face(54)
                            +Face(54)
                            +Face(54)
                            +Face(54)
                            +PlainText("\n            关于\n")
                            +Face(63)
                            +Face(63)
                            +Face(63)
                            +Face(63)
                            +Face(63)
                            +Face(63)
                        })
                        }
                    if(message.serializeToMiraiCode().split(']')[1].trim().startsWith("帮助")){
                        subject.sendMessage(buildMessageChain {
                            +Face(190)
                            +Face(190)
                            +Face(190)
                            +Face(190)
                            +Face(190)
                            +Face(190)
                            +PlainText("\n游戏查找功能不需要注册，其他功能需先进行注册。所有命令都在at机器人的前提\n")
                            +Face(190)
                            +Face(190)
                            +Face(190)
                            +Face(190)
                            +Face(190)
                            +Face(190)
                            +PlainText("\n（二次元/分享/ ）游戏查找，命令后加游戏名字即可（例：游戏查找GTA5），可进行模糊搜索，请不要过于模糊，结果过多将不能发出\n")
                            +Face(190)
                            +Face(190)
                            +Face(190)
                            +Face(190)
                            +Face(190)
                            +Face(190)
                            +PlainText("\n注册账号，我的资产，随机二次元游戏，关于，帮助直接输入命令即可\n")
                            +Face(190)
                            +Face(190)
                            +Face(190)
                            +Face(190)
                            +Face(190)
                            +Face(190)
                            +PlainText("\n二次元游戏查找，随机二次元游戏需要1金币才能使用，每日签到可随机获得1-5个金币，初始金币20\n")
                            +Face(190)
                            +Face(190)
                            +Face(190)
                            +Face(190)
                            +Face(190)
                            +Face(190)
                            +PlainText("\n分享游戏，用于用户上传，可搜索其他用户分享的游戏，对于没有且有用的游戏将会导入至机器人，并会对被采纳的用户奖励20金币，欢迎各位积极分享\n")
                            +Face(190)
                            +Face(190)
                            +Face(190)
                            +Face(190)
                            +Face(190)
                            +Face(190)
                        })
                    }
                    if(message.serializeToMiraiCode().split(']')[1].trim().startsWith("关于")){
                        subject.sendMessage(buildMessageChain {
                            +Face(66)
                            +Face(66)
                            +Face(66)
                            +Face(66)
                            +Face(66)
                            +PlainText("\n插件作者QQ：1441577495\n")
                            +Face(66)
                            +Face(66)
                            +Face(66)
                            +Face(66)
                            +Face(66)
                            +PlainText("\n如果你有好的意见或建议，发送到邮箱1441577495@qq.com,或加QQ联系，看到将第一时间回复。\n")
                            +Face(66)
                            +Face(66)
                            +Face(66)
                            +Face(66)
                            +Face(66)
                        })
                    }
                    if(message.serializeToMiraiCode().split(']')[1].trim().startsWith("注册账号")){
                        val client = OkHttpClient()
                        val result:String
                        val JSON: MediaType = "application/json; charset=utf-8".toMediaType()
                        val insertUser=InsertUser(qq=sender.id.toString(), qq_group = sender.group.id.toString(), point = 10)
                        val body:RequestBody=RequestBody.create(JSON,Gson().toJson(insertUser))
                        val request = Request.Builder().post(body).url("http://localhost:8800/user/insert").build()
                        val response = client.newCall(request).execute()
                        result=response.body?.string().toString()
                        val jsclass= Gson().fromJson(result,InsertUserBack::class.java)
                        if("${jsclass.status}"=="200"){
                            subject.sendMessage(buildMessageChain {
                                +At(sender.id)
                                +PlainText("注册成功！")
                                +Face(145)
                                +Face(145)
                                +Face(145)
                            })
                        }else{
                            if("${jsclass.status}"=="400"){
                                subject.sendMessage(buildMessageChain {
                                    +At(sender.id)
                                    +PlainText("失败")
                                    +Face(37)
                                    +Face(37)
                                    +Face(37)
                                })
                            }else{
                                if("${jsclass.status}"=="401"){
                                    subject.sendMessage(buildMessageChain {
                                        +At(sender.id)
                                        +PlainText("用户已存在！")
                                        +Face(38)
                                        +Face(38)
                                        +Face(38)
                                })
                            }else{
                                    if("${jsclass.status}"=="500"){
                                        subject.sendMessage(buildMessageChain {
                                            +At(sender.id)
                                            +PlainText("错误")
                                            +Face(37)
                                            +Face(37)
                                            +Face(37)
                                        })
                                    }

                                }
                            }
                        }
                    }
                    if(message.serializeToMiraiCode().split(']')[1].trim().startsWith("签到")){
                        val client = OkHttpClient()
                        val result:String
                        val JSON: MediaType = "application/json; charset=utf-8".toMediaType()
                        val body:RequestBody=RequestBody.create(JSON,"{\"qq\":\"${sender.id}\"}")
                        val request = Request.Builder().post(body).url("http://localhost:8800/user/signin").build()
                        val response = client.newCall(request).execute()
                        result=response.body?.string().toString()
                        val jsclass= Gson().fromJson(result,SignInBack::class.java)
                        if("${jsclass.status}"=="200"){
                            subject.sendMessage(buildMessageChain {
                                +At(sender.id)
                                +PlainText("\n签到成功！！")
                                +Face(144)
                                +Face(144)
                                +Face(144)
                                +PlainText("\n恭喜你获得了${jsclass.data}金币！")
                            })
                        }else{
                            if("${jsclass.status}"=="401"){
                                subject.sendMessage(buildMessageChain {
                                    +At(sender.id)
                                    +PlainText("\n重复签到！！")
                                    +Face(38)
                                    +Face(38)
                                    +Face(38)
                                    +PlainText("\n今天你已经签到过了，明天再来吧！")
                                })
                            }else{
                                if("${jsclass.status}"=="400"){
                                    subject.sendMessage(buildMessageChain {
                                        +At(sender.id)
                                        +PlainText("\n用户不存在！！")
                                        +Face(38)
                                        +Face(38)
                                        +Face(38)
                                        +PlainText("\n请先注册账号再试！！")
                                    })
                            }
                        }
                    }
                }
                    if(message.serializeToMiraiCode().split(']')[1].trim().startsWith("我的资产")){
                        val client = OkHttpClient()
                        val result:String
                        val JSON: MediaType = "application/json; charset=utf-8".toMediaType()
                        val body:RequestBody=RequestBody.create(JSON,"{\"qq\":\"${sender.id}\"}")
                        val request = Request.Builder().post(body).url("http://localhost:8800/user/select").build()
                        val response = client.newCall(request).execute()
                        result=response.body?.string().toString()
                        val jsclass= Gson().fromJson(result,UserBack::class.java)
                        if("${jsclass.status}"=="200"){
                            subject.sendMessage(buildMessageChain {
                                +At(sender.id)
                                +PlainText("\n你的金币还有${jsclass.data.point}个")
                                +Face(144)
                                +Face(144)
                                +Face(144)
                                +PlainText("\n你的上次签到时间为${jsclass.data.sign_in}加8小时！")
                            })
                        }else{
                            if("${jsclass.status}"=="201"){
                                subject.sendMessage(buildMessageChain {
                                    +At(sender.id)
                                    +Face(38)
                                    +Face(38)
                                    +Face(38)
                                    +PlainText("\n查无此人！")
                                })
                            }
                        }
                    }
                    if(message.serializeToMiraiCode().split(']')[1].trim().startsWith("随机二次元游戏")){
                        val updateUserParam=UpdateUserParam(qq = sender.id.toString(), point = 1)
                        val back= UpdateUser().update(updateUserParam)
                        if("${back.status}"=="400"){
                            subject.sendMessage(buildMessageChain {
                                +At(sender.id)
                                +PlainText("\n用户不存在！！")
                                +Face(38)
                                +Face(38)
                                +Face(38)
                                +PlainText("\n请先注册账号再试！！")
                            })
                        }else{
                            if("${back.status}"=="401"){
                                subject.sendMessage(buildMessageChain {
                                    +At(sender.id)
                                    +PlainText("\n金币不足！！")
                                    +Face(38)
                                    +Face(38)
                                    +Face(38)
                                    +PlainText("\n先凑够足够的金币再来吧！")
                                })
                            }else{
                                if("${back.status}"=="200"){
                                    subject.sendMessage(buildMessageChain {
                                        +At(sender.id)
                                        +PlainText("\n扣费成功！！")
                                        +Face(106)
                                        +Face(106)
                                        +Face(106)
                                        +PlainText("\n你失去了1金币！")
                                })
                                    val client = OkHttpClient()
                                    val result:String
                                    val JSON: MediaType = "application/json; charset=utf-8".toMediaType()
                                    val body:RequestBody=RequestBody.create(JSON,"")
                                    val request = Request.Builder().post(body).url("http://localhost:8800/hgame/random").build()
                                    val response = client.newCall(request).execute()
                                    result=response.body?.string().toString()
                                    val jsclass= Gson().fromJson(result,HGameBack::class.java)
                                    if("${jsclass.status}"=="200"){
                                        subject.sendMessage(buildMessageChain {
                                            +At(sender.id)
                                            +Face(144)
                                            +Face(144)
                                            +Face(144)
                                            +PlainText("\n找到了！")
                                            +Face(2)
                                            +Face(2)
                                            +Face(2)
                                            +PlainText("\n${jsclass.data.name}:\n${jsclass.data.content}")
                                        })
                                    }else{
                                        subject.sendMessage(buildMessageChain {
                                            +At(sender.id)
                                            +Face(38)
                                            +Face(38)
                                            +Face(38)
                                            +PlainText("\n失败了！")
                                        })
                                    }
                            }else{
                                subject.sendMessage("出错了！")
                                }
                            }
                        }
                    }
                    if(message.serializeToMiraiCode().split(']')[1].trim().startsWith("二次元游戏查找")){
                        val updateUserParam=UpdateUserParam(qq = sender.id.toString(), point = 1)
                        val back= UpdateUser().update(updateUserParam)
                        if("${back.status}"=="400"){
                            subject.sendMessage(buildMessageChain {
                                +At(sender.id)
                                +PlainText("\n用户不存在！！")
                                +Face(38)
                                +Face(38)
                                +Face(38)
                                +PlainText("\n请先注册账号再试！！")
                            })
                        }else{
                            if("${back.status}"=="401"){
                                subject.sendMessage(buildMessageChain {
                                    +At(sender.id)
                                    +PlainText("\n金币不足！！")
                                    +Face(38)
                                    +Face(38)
                                    +Face(38)
                                    +PlainText("\n先凑够足够的金币再来吧！")
                                })
                            }else{
                                if("${back.status}"=="200"){
                                    subject.sendMessage(buildMessageChain {
                                        +At(sender.id)
                                        +PlainText("\n扣费成功！！")
                                        +Face(106)
                                        +Face(106)
                                        +Face(106)
                                        +PlainText("\n你失去了1金币！")
                                    })
                                    val name =message.serializeToMiraiCode().substring(
                                        message.serializeToMiraiCode().indexOf("二次元游戏查找")+7,
                                        message.serializeToMiraiCode().length
                                    )
                                    val client = OkHttpClient()
                                    val result:String
                                    val JSON: MediaType = "application/json; charset=utf-8".toMediaType()
                                    val body:RequestBody=RequestBody.create(JSON,"{\"name\":\"${name}\"}")
                                    val request = Request.Builder().post(body).url("http://localhost:8800/hgame/select").build()
                                    val response = client.newCall(request).execute()
                                    result=response.body?.string().toString()
                                    val jsclass= Gson().fromJson(result,GameResultBack::class.java)
                                    if("${jsclass.status}"=="200"){
                                            val content="\n${jsclass.data[0].name}:\n${jsclass.data[0].content}"
                                            val chain = buildMessageChain {
                                            +At(sender.id)
                                            +Face(145)
                                            +Face(145)
                                            +Face(145)
                                            +Face(145)
                                            +Face(145)
                                            +PlainText("\n找到啦！一共有${jsclass.data.size}条数据！第一条：${content}")
                                        }
                                        subject.sendMessage(chain)
                                    }else{
                                        if("${jsclass.status}"=="201"){
                                            subject.sendMessage(buildMessageChain {
                                                +Face(175)
                                                +Face(175)
                                                +Face(175)
                                                +Face(175)
                                                +Face(175)
                                                +PlainText("\n暂时没有这个游戏哦！！")
                                            })
                                        }else{
                                            if("${jsclass.status}"=="500"){
                                                subject.sendMessage("\n出错了！！！")
                                            }
                                        }
                                    }
                                }else{
                                    subject.sendMessage("出错了！")
                                }
                            }
                        }
                    }
                    if(message.serializeToMiraiCode().split(']')[1].trim().startsWith("分享游戏")) {
                        if (message.serializeToMiraiCode().split('#').size <= 1) {
                            subject.sendMessage("分享格式错误！！！")
                        } else{
                        val n = message.serializeToMiraiCode().split('#')[1].trim()
                        val ctent = message.serializeToMiraiCode().split('#')[2].trim()
                        val insertShare = InsertShare(
                            name = n,
                            content = ctent,
                            up_qq = sender.id.toString(),
                            up_qqgroup = sender.group.id.toString()
                        )
                        val client = OkHttpClient()
                        val result: String
                        val JSON: MediaType = "application/json; charset=utf-8".toMediaType()
                        val body: RequestBody = RequestBody.create(JSON, Gson().toJson(insertShare))
                        val request = Request.Builder().post(body).url("http://localhost:8800/share/insert").build()
                        val response = client.newCall(request).execute()
                        result = response.body?.string().toString()
                        val jsclass = Gson().fromJson(result, SignInBack::class.java)
                        if ("${jsclass.status}" == "200") {
                            subject.sendMessage(buildMessageChain {
                                +At(sender.id)
                                +PlainText("\n感谢您的分享！祝你天天开心！经审核后会有积分奖励！\n")
                                +Face(66)
                                +Face(66)
                                +Face(66)
                                +Face(66)
                                +Face(66)
                            })
                        } else {
                            subject.sendMessage("分享失败了。。。")
                        }
                        }
                    }
                    if(message.serializeToMiraiCode().split(']')[1].trim().startsWith("分享查找")){
                        val name =message.serializeToMiraiCode().substring(
                            message.serializeToMiraiCode().indexOf("分享查找")+4,
                            message.serializeToMiraiCode().length
                        )
                        val client = OkHttpClient()
                        val result:String
                        val JSON: MediaType = "application/json; charset=utf-8".toMediaType()
                        val body:RequestBody=RequestBody.create(JSON,"{\"name\":\"${name}\"}")
                        val request = Request.Builder().post(body).url("http://localhost:8800/share/select").build()
                        val response = client.newCall(request).execute()
                        result=response.body?.string().toString()
                        val jsclass= Gson().fromJson(result,ShareGameBack::class.java)
                        if("${jsclass.status}"=="200"){
                            var content =""
                            for (d in jsclass.data){
                                content=content+"\n${d.name}:\n${d.content}"
                            }
                            val chain = buildMessageChain {
                                +At(sender.id)
                                +Face(145)
                                +Face(145)
                                +Face(145)
                                +Face(145)
                                +Face(145)
                                +PlainText("\n找到啦！一共有${jsclass.data.size}条数据！${content}")
                            }
                            subject.sendMessage(chain)
                        }else{
                            if("${jsclass.status}"=="201"){
                                subject.sendMessage(buildMessageChain {
                                    +Face(175)
                                    +Face(175)
                                    +Face(175)
                                    +Face(175)
                                    +Face(175)
                                    +PlainText("\n暂时没有人分享这个游戏哦！！")
                                })
                            }else{
                                if("${jsclass.status}"=="500"){
                                    subject.sendMessage("\n出错了！！！")
                                }
                            }
                        }
                    }
                    if(message.serializeToMiraiCode().split(']')[1].trim().startsWith("分享说明")){
                        subject.sendMessage(buildMessageChain {
                            +PlainText("分享游戏说明：\n分享游戏格式为：分享游戏#(里面填游戏名字，括号可删掉)#(里面填游戏说明，括号可删掉)\n游戏说明应包含游戏类型，游戏链接等有用信息")
                        })
                    }
                }
            }
        }
    }
}