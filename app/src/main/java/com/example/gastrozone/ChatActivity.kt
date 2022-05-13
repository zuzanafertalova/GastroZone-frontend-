package com.example.gastrozone

import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gastrozone.adapters.ChatAdapter
import com.example.gastrozone.adapters.ChatDataModels
import com.example.gastrozone.adapters.SearchResultAdapter
import com.example.gastrozone.http.HttpActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.fragment_set_username.*
import kotlinx.android.synthetic.main.list_messages.*

import io.socket.client.Socket

import com.example.gastrozone.adapters.SocketHandler
import org.json.JSONObject
import org.json.JSONTokener

const val ENDPOINT_HOST = "37.9.170.36"
const val ENDPOINT_PORT = 8080

class ChatActivity : AppCompatActivity(), View.OnClickListener {

    val TAG = ChatActivity::class.java.simpleName

    lateinit var userName: String
    lateinit var roomName: String

    val chatList: ArrayList<JSONObject> = arrayListOf();
    lateinit var chatAdapter: ChatAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        var userName: String = ""
        var roomName = "general" // TODO: Change


        chatAdapter = ChatAdapter(this, chatList)
        messagesRW.adapter = chatAdapter

        val layoutManager = LinearLayoutManager(this)
        messagesRW.layoutManager = layoutManager

        SocketHandler.setSocket()
        val socketHandler = SocketHandler.getSocket()
        socketHandler.connect()

        val httpClient = HttpActivity()
        Thread(Runnable {
            val data =
                httpClient.ExecGETRequest(uri = "http://$ENDPOINT_HOST:$ENDPOINT_PORT/v1/api/user")
            val responseData = JSONTokener(data).nextValue() as JSONObject

            println(responseData)

            userName = responseData.getString("name").toString()


            val payload = JSONObject()
            payload.put("username", userName)
            payload.put("room", roomName)

            socketHandler.emit("join", payload)

            runOnUiThread {
                btnChat(socketHandler = socketHandler, userName, roomName)
            }

        }).start()



        socketHandler.on("join") { args ->
            val data = args[0] as JSONObject
            runOnUiThread {

                Toast.makeText(
                    this, "${data["message"].toString()}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        socketHandler.on("chat") { args ->
            val data = args[0] as JSONObject
            val message = data["message"].toString()
            val user = data["user"].toString()

            addItemToRecyclerView(data)
        }

    }


    private fun addItemToRecyclerView(message: JSONObject) {

        //Since this function is inside of the listener,
        // You need to do it on UIThread!
        runOnUiThread {
            chatList.add(message)
            chatAdapter.notifyItemInserted(chatList.size)
            tvChat.setText("")
            messagesRW.scrollToPosition(chatList.size - 1) //move focus on last message
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, UserChatActivity::class.java).apply {
            //putExtra("DESIRED_FRAGMENT", "profile")
        }
        startActivity(intent)
    }



    private fun btnChat(socketHandler: Socket, userName: String?, roomName: String?) {
        button_chat.setOnClickListener {
            val messageContent: String = tvChat.text.toString()
            val sendData = ChatDataModels.SendMessage(messageContent)
            val message = ChatDataModels.Messages(messageContent)



            val payload = JSONObject()
            payload.put("user", userName)
            payload.put("room", roomName)
            payload.put("message", messageContent)

            socketHandler.emit("chat", payload)

            tvChat.text.clear()

        }
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

}