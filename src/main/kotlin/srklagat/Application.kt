package srklagat

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo
import srklagat.data.models.user.MongoUserDataSource
import srklagat.data.models.user.User
import srklagat.plugins.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

fun Application.module(){
    val mongoPw = System.getenv( "MONGO_PW")
    val dbName = "jungleAuthDb"
    val db = KMongo.createClient(

     connectionString =   "mongodb+srv://Sam4646:$mongoPw@cluster0.ha4acqv.mongodb.net/$dbName?retryWrites=true&w=majority"

    ).coroutine
        .getDatabase(dbName)

    val userDatabase= MongoUserDataSource(db)
GlobalScope.launch {
    val user = User(
        username ="sam",
        password = "password",
        salt = "salt"
    )
    userDatabase.insertUser(user)

}


    configureSecurity()
    configureMonitoring()
    configureSerialization()
    configureRouting()
}

