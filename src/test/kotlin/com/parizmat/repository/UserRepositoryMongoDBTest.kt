import com.parizmat.models.dao.UserMongoDB
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.runBlocking
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.utility.DockerImageName

internal class UserRepositoryMongoDBTest : FunSpec({
    lateinit var client: CoroutineDatabase
    val mongoContainer = MongoDBContainer(DockerImageName.parse("mongo:latest"))

    beforeSpec {
        mongoContainer.start()
        client = KMongo.createClient(mongoContainer.replicaSetUrl).coroutine.getDatabase("test")
    }

    afterSpec {
        mongoContainer.stop()
    }

    context("testing MongoDB repository") {
        test("insert a user") {
            runBlocking {
                val collection = client.getCollection<UserMongoDB>()
                val user = UserMongoDB(salt = "salt", username = "username", password = "password")

                collection.insertOne(user)
                val count = collection.countDocuments()

                count shouldBe 1L
            }
        }
    }
})
